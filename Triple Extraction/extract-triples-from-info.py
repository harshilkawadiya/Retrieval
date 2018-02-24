# read NLP-Info directory and get dependency labels from collapsed-ccprocessed-dependencies

from sgmllib import SGMLParser
from xml.dom import minidom

import urllib, webbrowser, os

nlp_info_files = os.listdir("./Inputs/");

distinct_labels = []
for info_file in nlp_info_files:
	# parse each file
	output_file = open("./" + info_file,'w');
	xmldoc = minidom.parse("./Inputs/" + info_file)

	################ PROCESS EACH SENTENCE ##############################
	sentences = xmldoc.getElementsByTagName('sentences');
	sentence = sentences[0].getElementsByTagName('sentence');
	for each_sentence in sentence:
		print each_sentence
		id_exactword = [];
		id_rootword = [];
		governor = {};
		dependent = {};
		# store id_word mapping for both exact word and root word
		id_word_info =each_sentence.getElementsByTagName('tokens');
		
		# process for each sentence
		id_info = id_word_info[0].getElementsByTagName("token");
		for j in id_info:
			word=j.getElementsByTagName('word');
			lemma=j.getElementsByTagName('lemma');
			word = word[0].childNodes[0].data;
			lemma = lemma[0].childNodes[0].data;
			id_exactword.append(word);
			id_rootword.append(lemma); 
			#print word + " " + lemma;
		# write sentence (both exact and root) in output file for analysis of results.
		for abc in range(0,len(id_exactword),1):
			output_file.write(id_exactword[abc] + " ");
		output_file.write("\n");
		
		for abc in range(0,len(id_rootword),1):
			output_file.write(id_rootword[abc] + " ");
		output_file.write("\n");
		output_file.write("\n");
		cc=each_sentence.getElementsByTagName('dependencies')
		for cps in cc:
			if cps.hasAttribute("type"):
				num=cps.getAttribute("type")
				if num=="collapsed-ccprocessed-dependencies":
					collapsed_cc_dependencies=cps

		dep = collapsed_cc_dependencies.getElementsByTagName("dep");
		for j in dep:
			g = j.getElementsByTagName('governor');
			d = j.getElementsByTagName('dependent');
			
			id_g = g[0].getAttribute('idx');
			id_d = d[0].getAttribute('idx');
			
			g = g[0].childNodes[0].data;
			d = d[0].childNodes[0].data;
			temp = j.getAttribute('type');
			if governor.has_key(temp):
				governor[temp].append(id_g);
				dependent[temp].append(id_d);
			else:
				governor[temp] = [];
				dependent[temp] = [];
				governor[temp].append(id_g);
				dependent[temp].append(id_d);
				
		labels = governor.keys();

		for label in labels:
			temp = label.split("prep_");
			if(len(temp)>1):
				preposition = temp[1];
				for k in range(0,len(governor[label]),1):
					prep_x_governor_id = governor[label][k];
					output_file.write("verb-prep-object" + '\t' + id_rootword[int(governor[label][k])-1] + " " + temp[1] + " " + id_rootword[int(dependent[label][k])-1] + '\n');			       
					if(governor.has_key("nsubj")):
						for each_nsubj in range(0,len(governor["nsubj"]),1):
							found_match = 0;
							nsubj_governor_id = governor["nsubj"][each_nsubj];
							if(nsubj_governor_id == prep_x_governor_id):
								found_match = 1;
								output_file.write("object-prep-object" + '\t' + id_rootword[int(dependent["nsubj"][each_nsubj])-1] + " " + preposition + " " + id_rootword[int(dependent[label][k])-1]+'\n');
					
			temp = label.split("prepc_");
			if(len(temp)>1):
				preposition = temp[1];
				for k in range(0,len(governor[label]),1):
					prep_x_governor_id = governor[label][k];
					output_file.write("verb-prep-verb" + '\t' + id_rootword[int(governor[label][k])-1] + " " + temp[1] + " " + id_rootword[int(dependent[label][k])-1] + '\n');			       
					if(governor.has_key("nsubj")):
						for each_nsubj in range(0,len(governor["nsubj"]),1):
							found_match = 0;
							nsubj_governor_id = governor["nsubj"][each_nsubj];
							if(nsubj_governor_id == prep_x_governor_id):
								found_match = 1;
								output_file.write("object-prep-object" + '\t' + id_rootword[int(dependent["nsubj"][each_nsubj])-1] + " " + preposition + " " + id_rootword[int(dependent[label][k])-1]+'\n');

		nsubj_combined = {};
		nsubjpass_combined = {};
		partmod_combined = {};

		if governor.has_key("acomp"):
			for each_acomp in range(0,len(governor["acomp"]),1):
				found_match = 0;
				acomp_governor_id = governor["acomp"][each_acomp];
				acomp_governor = id_rootword[int(governor["acomp"][each_acomp])-1];
				acomp_dependent = id_rootword[int(dependent["acomp"][each_acomp])-1];
				if governor.has_key("nsubj"):
					for each_nsubj in range(0,len(governor["nsubj"]),1):
						nsubj_governor_id = governor["nsubj"][each_nsubj];
						nsubj_dependent_id = dependent["nsubj"][each_nsubj];
						nsubj_governor = id_rootword[int(governor["nsubj"][each_nsubj])-1];
						nsubj_dependent = id_rootword[int(dependent["nsubj"][each_nsubj])-1];
						if (acomp_governor_id == nsubj_governor_id):
							found_match = 1;
							if(nsubj_combined.has_key(nsubj_governor_id)):
								nsubj_combined[nsubj_governor_id].append(nsubj_dependent_id);
							else:
								nsubj_combined[nsubj_governor_id] = [];
								nsubj_combined[nsubj_governor_id].append(nsubj_dependent_id);
							output_file.write("object-verb-object" + '\t' + nsubj_dependent + " " + nsubj_governor + " " + acomp_dependent + "\n");
						
				if (found_match==0 and governor.has_key("nsubjpass")):
					for each_nsubjpass in range(0,len(governor["nsubjpass"]),1):
						nsubjpass_governor_id = governor["nsubjpass"][each_nsubjpass];
						nsubjpass_dependent_id = dependent["nsubjpass"][each_nsubjpass];
						nsubjpass_governor = id_rootword[int(governor["nsubjpass"][each_nsubjpass])-1];
						nsubjpass_dependent = id_rootword[int(dependent["nsubjpass"][each_nsubjpass])-1];
						if (acomp_governor_id == nsubjpass_governor_id):
							found_match = 1;
				
							if(nsubjpass_combined.has_key(nsubjpass_governor_id)):
								nsubjpass_combined[nsubjpass_governor_id].append(nsubjpass_dependent_id);
							else:
								nsubjpass_combined[nsubjpass_governor_id] = [];
								nsubjpass_combined[nsubjpass_governor_id].append(nsubjpass_dependent_id);
							
							output_file.write("object-verb-object" + '\t' + nsubjpass_dependent + " " + nsubjpass_governor + " " + acomp_dependent + "\n");
				if (found_match==0 and governor.has_key("partmod")):
					for each_partmod in range(0,len(governor["partmod"]),1):
						partmod_governor_id = governor["partmod"][each_partmod];
						partmod_dependent_id = dependent["partmod"][each_partmod];
						partmod_governor = id_rootword[int(governor["partmod"][each_partmod])-1];
						partmod_dependent = id_rootword[int(dependent["partmod"][each_partmod])-1];
						if (acomp_governor_id == partmod_dependent_id):
							found_match = 1;
							if(partmod_combined.has_key(partmod_governor_id)):
								partmod_combined[partmod_governor_id].append(partmod_dependent_id);
							else:
								partmod_combined[partmod_governor_id] = [];
								partmod_combined[partmod_governor_id].append(partmod_dependent_id);
							output_file.write("object-verb-object" + '\t' + partmod_governor + " " + partmod_dependent + " " + acomp_dependent + "\n");
				if (found_match==0 and governor.has_key("dobj")):
					for each_dobj in range(0,len(governor["dobj"]),1):
						dobj_governor_id = governor["dobj"][each_dobj];
						dobj_governor = id_rootword[int(governor["dobj"][each_dobj])-1];
						dobj_dependent = id_rootword[int(dependent["dobj"][each_dobj])-1];
						if (acomp_governor_id == dobj_governor_id):
							found_match = 1;
							output_file.write("object-verb-object" + '\t' + acomp_dependent + " " + acomp_governor + " " + dobj_dependent + "\n");
				if (found_match==0 and governor.has_key("rcmod")):
					for each_rcmod in range(0,len(governor["rcmod"]),1):
						rcmod_dependent_id = dependent["rcmod"][each_rcmod];
						rcmod_governor = id_rootword[int(governor["rcmod"][each_rcmod])-1];
						rcmod_dependent = id_rootword[int(dependent["rcmod"][each_rcmod])-1];
						if (acomp_governor_id == rcmod_dependent_id):
							found_match = 1;
							output_file.write("object-verb-object" + '\t' + rcmod_governor + " " + rcmod_dependent + " " + acomp_dependent + "\n");
				 	
				if (found_match==0 and governor.has_key("dep")):
					for each_dep in range(0,len(governor["dep"]),1):
						dep_dependent_id = dependent["dep"][each_dep];
						dep_governor = id_rootword[int(governor["dep"][each_dep])-1];
						dep_dependent = id_rootword[int(dependent["dep"][each_dep])-1];
						if (acomp_governor_id == dep_dependent_id):
							found_match = 1;
							output_file.write("object-verb-object" + '\t' + dep_governor + " " + dep_dependent + " " + acomp_dependent + "\n");

		if (governor.has_key("advmod")):
			for each_advmod in range(0,len(governor["advmod"]),1):
				advmod_governor = id_rootword[int(governor["advmod"][each_advmod])-1];
				advmod_dependent = id_rootword[int(dependent["advmod"][each_advmod])-1];
				output_file.write("attribute-verb" + '\t' + advmod_dependent + " " + advmod_governor + "\n");

		if (governor.has_key("agent")):
			for each_agent in range(0,len(governor["agent"]),1):
				agent_governor = id_rootword[int(governor["agent"][each_agent])-1];
				agent_dependent = id_rootword[int(dependent["agent"][each_agent])-1];
				output_file.write("verb-prep-object" + '\t' + agent_governor + " " + "by" + " " + agent_dependent + "\n");

		if (governor.has_key("amod")):
			for each_amod in range(0,len(governor["amod"]),1):
				amod_governor = id_rootword[int(governor["amod"][each_amod])-1];
				amod_dependent = id_rootword[int(dependent["amod"][each_amod])-1];
				output_file.write("attribute-object" + '\t' + amod_dependent + " " + amod_governor + "\n");

		########## handle "dobj" #############
		if (governor.has_key("dobj")):
			for each_dobj in range(0,len(governor["dobj"]),1):
				found_match = 0;
				dobj_governor = id_rootword[int(governor["dobj"][each_dobj])-1];
				dobj_dependent = id_rootword[int(dependent["dobj"][each_dobj])-1];
				dobj_governor_id = governor["dobj"][each_dobj];
				if (governor.has_key("partmod")):
					for each_partmod in range(0,len(governor["partmod"]),1):
						partmod_governor = id_rootword[int(governor["partmod"][each_partmod])-1];
						partmod_dependent = id_rootword[int(dependent["partmod"][each_partmod])-1];
						partmod_dependent_id = dependent["partmod"][each_partmod];
						partmod_governor_id = governor["partmod"][each_partmod];
						
						if(dobj_governor_id == partmod_dependent_id):
							found_match = 1;
							if(partmod_combined.has_key(partmod_governor_id)):
								partmod_combined[partmod_governor_id].append(partmod_dependent_id);
							else:
								partmod_combined[partmod_governor_id] = [];
								partmod_combined[partmod_governor_id].append(partmod_dependent_id);
							output_file.write("object-verb-object" + "\t" + partmod_governor + " " + partmod_dependent + " " + dobj_dependent + "\n");

				if (found_match==0 and governor.has_key("nsubj")):
					for each_nsubj in range(0,len(governor["nsubj"]),1):
						nsubj_governor = id_rootword[int(governor["nsubj"][each_nsubj])-1];
						nsubj_dependent = id_rootword[int(dependent["nsubj"][each_nsubj])-1];
						nsubj_governor_id = governor["nsubj"][each_nsubj];
						nsubj_dependent_id = dependent["nsubj"][each_nsubj];

						if(dobj_governor_id == nsubj_governor_id):
							found_match = 1;
							
							if(nsubj_combined.has_key(nsubj_governor_id)):
								nsubj_combined[nsubj_governor_id].append(nsubj_dependent_id);
							else:
								nsubj_combined[nsubj_governor_id] = [];
								nsubj_combined[nsubj_governor_id].append(nsubj_dependent_id);
							output_file.write("object-verb-object" + "\t" + nsubj_dependent + " " + nsubj_governor + " " + dobj_dependent + "\n");
		######### handle "iobj" ##################
		if (governor.has_key("iobj")):
			for each_iobj in range(0,len(governor["iobj"]),1):
				found_match = 0;
				iobj_governor = id_rootword[int(governor["iobj"][each_iobj])-1];
				iobj_dependent = id_rootword[int(dependent["iobj"][each_iobj])-1];
				iobj_governor_id = governor["iobj"][each_iobj];
				if (governor.has_key("partmod")):
					for each_partmod in range(0,len(governor["partmod"]),1):
						partmod_governor = id_rootword[int(governor["partmod"][each_partmod])-1];
						partmod_dependent_id = dependent["partmod"][each_partmod];
						partmod_governor_id = governor["partmod"][each_partmod];
						if(iobj_governor_id==partmod_dependent_id):
							found_match = 1;
							if(partmod_combined.has_key(partmod_governor_id)):
								partmod_combined[partmod_governor_id].append(partmod_dependent_id);
							else:
								partmod_combined[partmod_governor_id] = [];
								partmod_combined[partmod_governor_id].append(partmod_dependent_id);
							output_file.write("object-verb-iobject" + "\t" + partmod_governor + " " + iobj_governor + " " + iobj_dependent + "\n");
		
		######### handle "nn" ################	
		if (governor.has_key("nn")):
			for each_nn in range(0,len(governor["nn"]),1):
				nn_governor = id_rootword[int(governor["nn"][each_nn])-1];
				nn_dependent = id_rootword[int(dependent["nn"][each_nn])-1];
				output_file.write("attribute-object" + '\t' + nn_dependent + " " + nn_governor + "\n");

		######## handle "npadvmod" ############
		if (governor.has_key("npadvmod")):
			for each_npadvmod in range(0,len(governor["npadvmod"]),1):
				npadvmod_governor = id_rootword[int(governor["npadvmod"][each_npadvmod])-1];
				npadvmod_dependent = id_rootword[int(dependent["npadvmod"][each_npadvmod])-1];
				output_file.write("attribute-object" + '\t' + npadvmod_dependent + " " + npadvmod_governor + "\n");
	
		###### handle "nsubj" ########## (cases where it is joined with acomp are already handled above)
		if (governor.has_key("nsubj")):
			for each_nsubj in range(0,len(governor["nsubj"]),1):
				combined = 0;
				nsubj_governor_id = governor["nsubj"][each_nsubj];
				nsubj_dependent_id = dependent["nsubj"][each_nsubj];

				nsubj_governor = id_rootword[int(governor["nsubj"][each_nsubj])-1];
				nsubj_dependent = id_rootword[int(dependent["nsubj"][each_nsubj])-1];
				if (nsubj_combined.has_key(nsubj_governor_id)):
					if (nsubj_dependent_id in nsubj_combined[nsubj_governor_id]):
						combined = 1;
				if (combined==0):
					output_file.write("object-verb" + '\t' + nsubj_dependent + " " + nsubj_governor + "\n");
				
		###### handle "nsubjpass" ########## (cases where it is joined with acomp are already handled above)
		if (governor.has_key("nsubjpass")):
			for each_nsubjpass in range(0,len(governor["nsubjpass"]),1):
				combined = 0;
				nsubjpass_governor_id = governor["nsubjpass"][each_nsubjpass];
				nsubjpass_dependent_id = dependent["nsubjpass"][each_nsubjpass];

				nsubjpass_governor = id_rootword[int(governor["nsubjpass"][each_nsubjpass])-1];
				nsubjpass_dependent = id_rootword[int(dependent["nsubjpass"][each_nsubjpass])-1];
				if (nsubjpass_combined.has_key(nsubjpass_governor_id)):
					if (nsubjpass_dependent_id in nsubjpass_combined[nsubjpass_governor_id]):
						combined = 1;
				if (combined==0):
					output_file.write("object-verb" + '\t' + nsubjpass_dependent + " " + nsubjpass_governor + "\n");


		############ handle "partmod" ##############
		if (governor.has_key("partmod")):
			for each_partmod in range(0,len(governor["partmod"]),1):
				combined = 0;
				partmod_governor_id = governor["partmod"][each_partmod];
				partmod_dependent_id = dependent["partmod"][each_partmod];

				partmod_governor = id_rootword[int(governor["partmod"][each_partmod])-1];
				partmod_dependent = id_rootword[int(dependent["partmod"][each_partmod])-1];
				if (partmod_combined.has_key(partmod_governor_id)):
					if (partmod_dependent_id in partmod_combined[partmod_governor_id]):
						combined = 1;
				if (combined==0):
					output_file.write("object-verb" + '\t' + partmod_governor + " " + partmod_dependent + "\n");
	
		output_file.write("#####################################################\n");
	output_file.close();
