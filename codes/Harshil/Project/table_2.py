f=open('image_name.txt')
cnt=0
img_table={}
for line in f:
	img_table[(cnt/5) + 1]=line.strip()
	print cnt/5 + 1,img_table[cnt/5 + 1]
	cnt=cnt+1
