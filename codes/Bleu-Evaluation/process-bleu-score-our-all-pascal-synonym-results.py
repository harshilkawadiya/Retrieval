#!/usr/bin/python
from socket import socket;
import os;
import sys;

########################### Process for ALl Pascal Synonym ################################################
files = os.listdir("/home/user/NLG-Images/Bleu-all-pascal-synonym-our-results");

bleu_score_b1 = {}
nist_score_b1 = {}

bleu_individual_score_b1 = {}
bleu_cumulative_score_b1 = {}

bleu_individual_score_b1_bigram = {}
bleu_cumulative_score_b1_bigram = {}

bleu_individual_score_b1_trigram = {}
bleu_cumulative_score_b1_trigram = {}

c = 0;
for each_file in files:	
	c = c + 1;
	file_name = each_file.split('\n');
	file_name = file_name[0];

	file = open("/home/user/NLG-Images/Bleu-all-pascal-synonym-our-results/" + each_file, "r");
	lines = file.readlines();
	file.close();
	
	count_individual_n_gram = 0;
        count_cumulative_n_gram = 0;

	for i in range(0,len(lines),1):
		temp = lines[i].split('NIST score = ');
		if (len(temp) > 1):
			temp = temp[1].split('  BLEU score = ');
			nist_score = temp[0];
			bleu_score = temp[1].split(' ');
			bleu_score = bleu_score[0];
			
			bleu_score = float(bleu_score);
			nist_score = float(nist_score);
		
			if (bleu_score_b1.has_key(file_name)):
				print each_file;
			bleu_score_b1[file_name] = bleu_score;
			nist_score_b1[file_name] = nist_score;
			if (bleu_score >= 0.0 and bleu_score <=1.0):
				fine = 1;
			else:
				print file_name;
		else:
                        temp = lines[i].split('BLEU:  ');
                        if (len(temp)>1 and count_individual_n_gram==0):
                                #Bleu Individual 1-gram score
                                count_individual_n_gram = 1;
                                temp = temp[1].split('   ');
                                bleu_individual_score_b1[file_name] = float(temp[0]);
                                
				bleu_individual_score_b1_bigram[file_name] = float(temp[1]);
                                bleu_individual_score_b1_trigram[file_name] = float(temp[2]);

                        elif (len(temp)>1 and count_individual_n_gram==1 and count_cumulative_n_gram==0):
                                #Bleu Cumulative 1-gram score
                                count_cumulative_n_gram = 1;
                                temp = temp[1].split('   ');
                                bleu_cumulative_score_b1[file_name] = float(temp[0]);
                                
				bleu_cumulative_score_b1_bigram[file_name] = float(temp[1]);
                                bleu_cumulative_score_b1_trigram[file_name] = float(temp[2]);

	
# find file_names which are not in bleu_score_b1.keys();
for each_file in files:
	temp = each_file.split('\n');
	if bleu_score_b1.has_key(temp[0]):
		fine = 1;
	else:
		print temp[0];

# process bleu_score_b1
keys = bleu_score_b1.keys();

total_count = 0.0;
total_count_individual_1_gram = 0.0;
total_count_cumulative_1_gram = 0.0;

total_count_individual_2_gram = 0.0;
total_count_cumulative_2_gram = 0.0;

total_count_individual_3_gram = 0.0;
total_count_cumulative_3_gram = 0.0;

for key in keys:
	total_count = total_count + bleu_score_b1[key];
	total_count_individual_1_gram = total_count_individual_1_gram + bleu_individual_score_b1[key];
        total_count_cumulative_1_gram = total_count_cumulative_1_gram + bleu_cumulative_score_b1[key];

	total_count_individual_2_gram = total_count_individual_2_gram + bleu_individual_score_b1_bigram[key];
        total_count_cumulative_2_gram = total_count_cumulative_2_gram + bleu_cumulative_score_b1_bigram[key];
	
	total_count_individual_3_gram = total_count_individual_3_gram + bleu_individual_score_b1_trigram[key];
        total_count_cumulative_3_gram = total_count_cumulative_3_gram + bleu_cumulative_score_b1_trigram[key];

print "BLEU All Pascal Synonym OUR RESULTS";

print "Total count BLEU is : " + str(total_count);
print "Length keys BLEU is : " + str(len(keys));
print "Total count individual 1-gram is : " + str(total_count_individual_1_gram);
print "Total count cumulative 1-gram is : " + str(total_count_cumulative_1_gram);

avg_bleu_score = float(total_count)/ (len(keys));
avg_bleu_individual_1_gram_score = float(total_count_individual_1_gram)/ (len(keys));
avg_bleu_cumulative_1_gram_score = float(total_count_cumulative_1_gram)/ (len(keys));

avg_bleu_individual_2_gram_score = float(total_count_individual_2_gram)/ (len(keys));
avg_bleu_cumulative_2_gram_score = float(total_count_cumulative_2_gram)/ (len(keys));

avg_bleu_individual_3_gram_score = float(total_count_individual_3_gram)/ (len(keys));
avg_bleu_cumulative_3_gram_score = float(total_count_cumulative_3_gram)/ (len(keys));

print "Average Smoothed Bleu Score for our all pascal synonym : " + str(avg_bleu_score);
print "Average Smoothed Individual 1-gram Bleu Score for our all pascal synonym : " + str(avg_bleu_individual_1_gram_score);
print "Average Smoothed Cumulative 1-gram Bleu Score for our all pascal synonym : " + str(avg_bleu_cumulative_1_gram_score);

print "Average Smoothed Individual 2-gram Bleu Score for our all pascal synonym : " + str(avg_bleu_individual_2_gram_score);
print "Average Smoothed Cumulative 2-gram Bleu Score for our all pascal synonym : " + str(avg_bleu_cumulative_2_gram_score);

print "Average Smoothed Individual 3-gram Bleu Score for our all pascal synonym : " + str(avg_bleu_individual_3_gram_score);
print "Average Smoothed Cumulative 3-gram Bleu Score for our all pascal synonym : " + str(avg_bleu_cumulative_3_gram_score);

# process nist_score_b1
keys = nist_score_b1.keys();

total_count = 0.0;
for key in keys:
	total_count = total_count + nist_score_b1[key];

print total_count;
print len(keys);
avg_nist_score = float(total_count)/ (len(keys));
print "Average Nist Score for our all pascal synonym : " + str(avg_nist_score);
print;
#################################################################################################################################
