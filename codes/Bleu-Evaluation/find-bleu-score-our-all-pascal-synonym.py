#!/usr/bin/python
from socket import socket;
import os;
import sys;

files = os.listdir("bleu-ref-files");

for each_file in files:
	file_name = each_file.split('.');
	test_file = file_name[0] + ".jpg.xml";
	
	os.system("./mteval-v13a.pl -r bleu-ref-files/" + each_file + " -s bleu-src-files-our-all-pascal-synonym/" + test_file + " -t bleu-test-files-our-all-pascal-synonym/" + test_file + " --no-smoothing > Bleu-all-pascal-synonym-our-results/" + each_file); 

	#exit(1);
	
