f=open('captions.txt','r+')
cnt=0
l=open('image_names.txt','w+')
for lines in f:
        if cnt%5==0:
	        line= lines.split('\t')[0].strip()[0:-2]
	        print line
	        l.write(line+'\n')
	cnt=cnt+1


