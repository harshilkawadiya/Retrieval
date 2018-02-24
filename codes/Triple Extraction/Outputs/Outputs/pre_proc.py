import random
import numpy as np
import sys
import re
import collections
from xml.sax import make_parser, handler
import os

cnt=1
dict = {}
for j in range(0,15):
	f_name="processed_data"+str(j) + ".txt.xml"
	with open(f_name) as f:
		for line in f:
			line=line.strip()
			foo = line.split(" ")
			# if 'str' in line:
			# 	break    line = f.readline()
			if '#' in line:
				cnt=cnt+1
			ct=cnt/5 +1
			if(line.startswith("attribute-object")):
				print (line)
				print (cnt)
				temp=""
				for i in range(1,len(foo)):
					if(len(foo[i])!=0):
						temp=temp+foo[i]+" "
				temp = temp[0:-1]
				print (temp)
				if temp in dict:
					ct=cnt/5 +1
					if (bool(dict[temp][0])):
						if(bool(dict[temp][0][ct])):
							dict[temp][0][ct]+=1
						else:
							dict[temp][0][ct]=1
					else:
						dict[temp][0]={}
						dict[temp][0][ct]=1
				else:
					dict[temp]={}
					dict[temp][0]={}
					dict[temp][0][ct]=1

			elif(line.startswith("object-verb-object")):
				print (line)
				print (cnt)
				temp=""
				for i in range(1,len(foo)):
					if(len(foo[i])!=0):
						temp=temp+foo[i]+" "
				temp = temp[0:-1]
				print (temp)
				if temp in dict:
					if (bool(dict[temp][1])):
						if(bool(dict[temp][1][ct])):
							dict[temp][1][ct]+=1
						else:
							dict[temp][1][ct]=1
					else:
						dict[temp][1]={}
						dict[temp][1][ct]=1
				else:
					dict[temp]={}
					dict[temp][1]={}
					dict[temp][1][ct]=1
			elif(line.startswith("object-verb")):
				print (line)
				print (cnt)
				temp=""
				for i in range(1,len(foo)):
					if(len(foo[i])!=0):
						temp=temp+foo[i]+" "
				temp = temp[0:-1]
				print (temp)
				if temp in dict:
					if (bool(dict[temp][2])):
						if(bool(dict[temp][2][ct])):
							dict[temp][2][ct]+=1
						else:
							dict[temp][2][ct]=1
					else:
						dict[temp][2]={}
						dict[temp][2][ct]=1
				else:
					dict[temp]={}
					dict[temp][2]={}
					dict[temp][2][ct]=1
print(dict)
f.close()

