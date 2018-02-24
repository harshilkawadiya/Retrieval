f=open('captions.txt','r+')
l=open('image_name.txt','w+')
for lines in f:
	line= lines.split('\t')[0]
	print line[0:-2]
	l.write(line[0:-2]+'\n')
