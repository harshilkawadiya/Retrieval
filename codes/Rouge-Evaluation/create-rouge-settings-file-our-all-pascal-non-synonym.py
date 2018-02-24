import os;

files = os.listdir("/home/user/NLG-Images/Rouge-Eval/systems-our-all-pascal-non-synonym-results");

out_file = open("/home/user/NLG-Images/Rouge-Eval/settings-our-all-pascal-non-synonym-results.xml","w");
out_file.write('<ROUGE_EVAL version="1.5.5">' + '\n');
# Loop
count_id = 1;
for each_file in files:
	fname = each_file.split('\n');
	part_fname = fname[0].split('system1_');

	out_file.write('<EVAL ID="' + str(count_id) + '">' + '\n');
	count_id = count_id + 1;

	out_file.write("  <MODEL-ROOT>" + '\n');
	out_file.write("  /home/user/NLG-Images/Rouge-Eval/models" + '\n');
	out_file.write("  </MODEL-ROOT>" + '\n');

	out_file.write("  <PEER-ROOT>" + '\n');
	out_file.write("  /home/user/NLG-Images/Rouge-Eval/systems-our-all-pascal-non-synonym-results" + '\n');
	out_file.write("  </PEER-ROOT>" + '\n');

	out_file.write('  <INPUT-FORMAT TYPE="SEE">' + '\n');
	out_file.write('  </INPUT-FORMAT>' + '\n');

	out_file.write('  <PEERS>' + '\n');
	out_file.write('    <P ID="1">' + fname[0] + '</P>' + '\n');
	out_file.write('  </PEERS>' + '\n');

	out_file.write('  <MODELS>' + '\n');
	out_file.write('    <M ID="0">human1_' + part_fname[1] + '</M>' + '\n');
	out_file.write('    <M ID="1">human2_' + part_fname[1] + '</M>' + '\n');
	out_file.write('    <M ID="2">human3_' + part_fname[1] + '</M>' + '\n');
	out_file.write('    <M ID="3">human4_' + part_fname[1] + '</M>' + '\n');
	out_file.write('    <M ID="4">human5_' + part_fname[1] + '</M>' + '\n');
	out_file.write('  </MODELS>' + '\n');

	out_file.write("</EVAL>" + '\n');
# Loop End

out_file.write("</ROUGE_EVAL>");
out_file.close();
#####################################################################################################3
