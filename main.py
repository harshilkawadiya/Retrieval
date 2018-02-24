import os
import numpy as np
import skimage.io as io
import matplotlib.pyplot as plt
from scipy.misc import imresize
import csv
import torch
import skimage.io as io
from torch.autograd import Variable
from torchvision import models
from torchvision import transforms as trn
from PIL import Image
preprocess = trn.Compose([
        trn.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
])

import nltk
from nltk.corpus import wordnet as wn

K=10 #selection of the retrieval number



vgg16 = models.vgg16('pretrained=true')
vgg16=vgg16.cuda()
print 'Model Parameters Loaded'


class phrase:
    def __init__(self):
        self.type = 0
        self.value = ''
# tr_data used as retrieval data.
# te_data used as query data for joint probability computation.

tr_data=[]
te_data=[]
tr_images=[]
te_images=[]
base='/scratch/harshil.j/Retrieval/Flicker8k/Flickr8k_Dataset/Flicker8k_Dataset/'
f=open('/scratch/harshil.j/Retrieval/Flicker8k/Flickr8k_Dataset/Flickr_8k.trainImages.txt','r+')
for line in f:
	temp=[]
	tr_images.append(line[0:-1])
	imgpath = '/scratch/harshil.j/Retrieval/Flicker8k/Flickr8k_Dataset/Flicker8k_Dataset/'+line[0:-1]
	I = io.imread(imgpath)
	I = imresize(I,(224,224,3), interp='bilinear')
	data = np.asarray(I)
	im = Image.fromarray(data)
	r, g, b= im.split()  
	I = Image.merge("RGB", (b, g, r))
	I = np.array(I)
	I = I.astype('float32')/255.0
	I = torch.from_numpy(I.transpose([2,0,1]))
	I = Variable(preprocess(I), volatile=True)
	G = I.contiguous().view(1,3,224,224)
	G=G.cuda()
	out1 = vgg16(G)
	out2 = out1[0].cpu().data.numpy()
	tr_data.append(out2)

k=open('/scratch/harshil.j/Retrieval/Flicker8k/Flickr8k_Dataset/Flickr_8k.testImages.txt','r+')
for line in k:
	temp=[]
	print line
	te_images.append(line[0:-1])
	imgpath = '/scratch/harshil.j/Retrieval/Flicker8k/Flickr8k_Dataset/Flicker8k_Dataset/'+line[0:-1]
	I = io.imread(imgpath)
	I = imresize(I,(224,224,3), interp='bilinear')
	data = np.asarray(I)
	im = Image.fromarray(data)
	r, g, b= im.split()  
	I = Image.merge("RGB", (b, g, r))
	I = np.array(I)
	I = I.astype('float32')/255.0
	I = torch.from_numpy(I.transpose([2,0,1]))
	I = Variable(preprocess(I), volatile=True)
	G = I.contiguous().view(1,3,224,224)
	G=G.cuda()
	out1 = vgg16(G)
	out2 = out1[0].cpu().data.numpy()
	te_data.append(out2)


# feature normalization
# tr_data,tr_images,te_data,te_images
import sklearn.preprocessing
from sklearn.preprocessing import normalize
tr_data1=normalize(tr_data,norm='l2')
te_data1=normalize(te_data,norm='l2')


# queries are treated as testing set and training data set are treated as retrieval set.
# testing set images - 1000 and training set/retrieval set - 5000
# K - 10
# get k neighors,distances and indices from te_data of re_set.
from sklearn.neighbors import KDTree
kdt = KDTree(tr_data1, leaf_size=30, metric='euclidean')
distances,indices=kdt.query(te_data1, k=10, return_distance=True) 

# second term computation in joint probability P(y,I)
def prb_I_given_J(dists):
	sigma = np.sum(dists)
	sigma=sigma+0.00000000000001
	dists=dists/sigma
	return dists

# Data Management
imgpath = open('/scratch/harshil.j/Retrieval/Flicker8k/Flickr8k_Dataset/Flicker8k_Dataset/image_name.txt','r+')
img_id=[]
inverse_map={}
count=0
for lines in imgpath:
	image_n=lines[0:-1]
	img_id.append(image_n)
	inverse_map[image_n]=count
	count=count+1


cnt=0
data_phrases={}

# File Parsing for the extracted file from Stanford Parser. 

for j in range(0,17):
	f_name="/scratch/harshil.j/Retrieval/codes/Triple Extraction/Outputs/processed_data"+str(j)+".txt.xml"
	with open(f_name) as f:
		for line in f:
			line=line.strip()
			if '#' in line:
				cnt=cnt+1
			category=0
			# ct=cnt/5 + 1
			ct=cnt/5			
			if(line.startswith("attribute-object")):
				category=1
			elif(line.startswith("object-verb-object")):
				category=2
			elif(line.startswith("object-verb")):
				category=3
			if category!=0:
				entry=phrase()
				entry.type=category
				entry.value=line.split('\t')[1]
				print ct,cnt%5,entry.value,entry.type
				if ct not in data_phrases:
					data_phrases[ct]={}
				if cnt%5 not in data_phrases[ct]:
					data_phrases[ct][cnt%5]=[]
				data_phrases[ct][cnt%5].append(entry)

f.close()

# Third term computation in the formula
def prob_y_given_J(data_te,data_tr):
	tr_scr=[]
	for j in range (0,len(data_tr)):
		curr_scr=0
		for i in data_te.keys():
			q_phrase=data_te[i]
			for k in data_tr[j].keys():
				tr_phrase=data_tr[j][k]
				for q in q_phrase:
					for t in tr_phrase:
						q_set1=q.value.split(' ')
						q_set2=t.value.split(' ')
						score=0
						if(q.type==t.type):
							for idx1 in range(len(q_set1)):
								comb=0
								for idx2 in range(len(q_set2)):
									# print q_set1[idx1],q_set2[idx2]
									syn1=wn.synsets(q_set1[idx1])
									syn2=wn.synsets(q_set2[idx2])
									if len(syn1)>0 and len(syn2)>0:
										comb=max(comb,syn1[0].path_similarity(syn2[0]))
								score=score+comb
							curr_scr=max(curr_scr,score)
		tr_scr.append(curr_scr)
	return tr_scr

# final scores calculation
retrieval_score={}
for k in range(100):
	if k%10==0:
		print k+1,'set image getting processed...'
	for i in range(100,1000):
		if i%100==0:
			print i,'th image getting transacted....'
		idx=inverse_map[te_images[i]]
		tr_phrases=[]
		for j in range(K):
			idx2=inverse_map[tr_images[indices[i][j]]]
			tr_phrases.append(data_phrases[idx2])
		pyj_scores=prob_y_given_J(data_phrases[k],tr_phrases)	
		norm_distances=prb_I_given_J(distances[i])
		joint_prob=0
		for j in range(K):
			joint_prob+=norm_distances[j]*pyj_scores[j]*(0.1)
		if k not in retrieval_score.keys():
			retrieval_score[k]={}
		retrieval_score[k][i]=joint_prob

print retrieval_score
top_k_images={}
for i in range(100):
	temp=sorted(retrieval_score[i].items(), key=operator.itemgetter(1))
	l=open('/home/harshil.j/Images/Results/cmpr'+str(i)+'_1.txt','w+')
	m=open('/home/harshil.j/Images/Results/cmpr'+str(i)+'_2.txt','w+')
	for j in range(1,11):
		l.write(base+te_images[i]+'\n')
		m.write(base+te_images[temp[-j][0]]+'\n')
	l.close()
	m.close()

print top_k_images

