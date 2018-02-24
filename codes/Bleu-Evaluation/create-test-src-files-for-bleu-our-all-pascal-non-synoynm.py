import os;

files = os.listdir("/home/user/NLG-Images/Results/AllPascal/ns/NLG-out1");
for each_file in files:
	file = open("/home/user/NLG-Images/Results/AllPascal/ns/NLG-out1/" + each_file, "r");
	lines = file.readlines();
	file.close();

	########### TEST FILES #######################
	# file extension should be xml
	abc = each_file.replace(".txt",".xml");
	out_file = open("bleu-test-files-our-all-pascal-non-synonym/" + abc,"w");
	for i in range(0,1,1):					# assuming only one line in each file
		temp = lines[i].split('\n');
		if (len(temp[0]) > 0):
			if (temp[0][len(temp[0])-1]!='.'):
				temp[0] = temp[0] + '.';
		out_file.write('<?xml version="1.0" encoding="UTF-8"?>' + '\n' + '<!DOCTYPE mteval SYSTEM "ftp://jaguar.ncsl.nist.gov/mt/resources/mteval-xml-v1.3.dtd">'
			+ '\n' + "<mteval>" + '\n' + '<tstset setid="example_set" srclang="English" trglang="English" sysid="sample_system">' + '\n' + 
		'<doc docid="doc1" genre="nw">' + '\n' + "<p>" + '\n' + '<seg id="1">' + temp[0] + "</seg>" + '\n' + "</p>" + '\n' + "</doc>" + '\n' + "</tstset>"
		+ '\n' + "</mteval>");
	out_file.close();

	############## SRC FILES #######################
	out_file = open("bleu-src-files-our-all-pascal-non-synonym/" + abc,"w");
	for i in range(0,1,1):					# assuming only one line in each file
		temp = lines[i].split('\n');
		if (len(temp[0]) > 0):
			if (temp[0][len(temp[0])-1]!='.'):
				temp[0] = temp[0] + '.';
		out_file.write('<?xml version="1.0" encoding="UTF-8"?>' + '\n' + '<!DOCTYPE mteval SYSTEM "ftp://jaguar.ncsl.nist.gov/mt/resources/mteval-xml-v1.3.dtd">'
			+ '\n' + "<mteval>" + '\n' + '<srcset setid="example_set" srclang="English" trglang="English" sysid="sample_system">' + '\n' + 
		'<doc docid="doc1" genre="nw">' + '\n' + "<p>" + '\n' + '<seg id="1">' + temp[0] + "</seg>" + '\n' + "</p>" + '\n' + "</doc>" + '\n' + "</srcset>"
		+ '\n' + "</mteval>");
	out_file.close();
