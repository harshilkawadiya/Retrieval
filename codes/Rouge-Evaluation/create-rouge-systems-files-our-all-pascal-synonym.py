import os;

files = os.listdir("/home/user/NLG-Images/Results/AllPascal/s/NLG-out1");

for each_file in files:
	file = open("/home/user/NLG-Images/Results/AllPascal/s/NLG-out1/" + each_file,"r");
	lines = file.readlines();
	file.close();

	file_name = each_file.split('.');
	file_name = file_name[0];
	file_name = file_name.split('_',1);
	file_name = file_name[1];

	count = 1;
	for i in range(0,1,1):				# testing only first sentence
		temp = lines[i].split('\n');
		if (len(temp[0]) > 0):
                        if (temp[0][len(temp[0])-1]!='.'):
                                temp[0] = temp[0] + ".";

		out_file = open("/home/user/NLG-Images/Rouge-Eval/systems-our-all-pascal-synonym-results/system" + str(count) + "_" + file_name + ".html", "w");
		out_file.write("<html>" + '\n' + "<head>" + '\n' + "<title>" + "system" + str(count) + "_" + file_name + "</title>" + "\n"
		+ "</head>" + '\n' + '<body bgcolor="white">' + '\n' + '<a name="1">[1]</a> <a href="#1" id=1>' + temp[0] + " </a>" + '\n'
		+ "</body>" + '\n' + "</html>");
		out_file.close();
		count = count + 1;
