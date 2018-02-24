f=open('captions.txt','r+')
wr=open('images_names.txt','a')
cnt = 0
for lines in f:
	if cnt==0:
		img_name=lines.split('\t')[0][0:-2]
		wr.write(img_name+'\n')
		print img_name+'\n'
	cnt=(cnt+1)%5
f.close()
wr.close()
