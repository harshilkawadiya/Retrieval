import os;

# models/reference/gold standard
files = os.listdir("/home/user/NLG-Images/Description1");

for each_file in files:
	file = open("/home/user/NLG-Images/Description1/" + each_file,"r");
	lines = file.readlines();
	file.close();
	file_name = each_file.split('.');
	file_name = file_name[0];

	count = 1;
	for i in range(0,len(lines),1):
		temp = lines[i].split('\n');
	
		####### Add punctuation	
		if (len(temp[0]) > 0):
                        if (temp[0][len(temp[0])-1]!='.'):
                                temp[0] = temp[0] + ".";

		out_file = open("/home/user/NLG-Images/Rouge-Eval/models/human" + str(count) + "_" + file_name + ".html", "w");
		out_file.write("<html>" + '\n' + "<head>" + '\n' + "<title>" + "human" + str(count) + "_" + file_name + "</title>" + "\n"
		+ "</head>" + '\n' + '<body bgcolor="white">' + '\n' + '<a name="1">[1]</a> <a href="#1" id=1>' + temp[0] + " </a>" + '\n'
		+ "</body>" + '\n' + "</html>");
		out_file.close();
		count = count + 1;
	#break;
