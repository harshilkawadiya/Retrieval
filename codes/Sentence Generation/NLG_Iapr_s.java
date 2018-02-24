import java.sql.SQLException;
import java.util.*;
import java.io.*;

import gov.nih.nlm.nls.lexAccess.Api.LexAccessApi;
import gov.nih.nlm.nls.lexAccess.Api.LexAccessApiResult;
import junit.framework.Assert;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

// All IAPR with synonym
public class NLG_Iapr_s {
	
	@SuppressWarnings("deprecation")
	public static void main (String[] args) throws SQLException
	{
	     //// using NIH Specialist lexicon 
	     String DB_FILENAME = "/home/user/NLG-Images/Tools/lexAccess2011lite/data/HSqlDb/lexAccess2011.data";
         Lexicon lexicon = new NIHDBLexicon(DB_FILENAME);
         
		 //Lexicon lexicon = Lexicon.getDefaultLexicon();
	     NLGFactory nlgFactory = new NLGFactory(lexicon);
	     Realiser realiser = new Realiser(lexicon);
	     
	     SPhraseSpec sps = nlgFactory.createClause();
	     
	     NPPhraseSpec npphrasespec_subject;
	     NPPhraseSpec npphrasespec_object;
	     
	     String out1;
	     // Read Results Directory
	     File dir = new File("/home/user/NLG-Images/Results/Iapr/s/out1");
	     String[] files = dir.list();
	     
	     String strLine, object1, verb, prep, object2, attribute1, attribute2, modifier, object1_copy;
	     String object2_1, verb2, prep2, object2_2, attribute2_1, attribute2_2, modifier2, object2_1_copy;
	     String object3_1, verb3, prep3, object3_2, attribute3_1, attribute3_2, modifier3, object3_1_copy;
	     String extra_complement;
	     
	     String nothing;
		 String[] temp, temp1, temp2, temp3;
		 
		 String[] verbs_with_passive_realization = new String[] {"park","dock","crowd","display","make"};
		 List<String> verbs_passive_list = Arrays.asList(verbs_with_passive_realization);
		 
		 String[] vowels = new String[] {"a","e","i","o","u"};
		 List<String> vowels_list = Arrays.asList(vowels);
		 
		 try
		 {
			 FileWriter fstream_out_aggregation = new FileWriter("/home/user/NLG-Images/Results/Iapr/s/Aggregation-possible/agg.txt");
			 BufferedWriter out_aggregation = new BufferedWriter(fstream_out_aggregation);
		  
		 Integer subject_grouping=0, predicate_grouping=0;
		 
	     for (int i =0; i<files.length; i++)
	     {
	    	 try
	    	 {
	    		  FileInputStream fstream = new FileInputStream("/home/user/NLG-Images/Results/Iapr/s/out1/" + files[i]);
	    		  DataInputStream in = new DataInputStream(fstream);
	    		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    		  
	    		  FileWriter fstream_out = new FileWriter("/home/user/NLG-Images/Results/Iapr/s/NLG-out1/" + files[i]);
	    		  BufferedWriter out = new BufferedWriter(fstream_out);
	    		  
	    		  subject_grouping = 0;
	    		  predicate_grouping = 0;
	    		  while ((strLine = br.readLine()) != null)
	    		  {
	    			  // Canned text
	    			  out.write("In this image, ");
	    			  temp = strLine.split("\t");
	    			  temp = temp[0].split(", ");
	    			  // Print the content on the console
	    			  // For All IAPR s
	    			  temp1 = temp[0].split("<<");
	    			  temp1 = temp1[1].split(">,");
	    			  temp2 = temp1[0].split(",");
	    			  temp3 = temp1[1].split(">");
	    			  if (temp2.length > 1)
	    			  {
	    				  attribute1 = temp2[0];
	    				  object1 = temp2[1];
	    			  }
	    			  else
	    			  {
	    				  attribute1 = "";
	    				  object1 = temp2[0];
	    			  }
	    			  verb = temp3[0];
	    			  temp1 = temp[1].split(">>");
	    			  temp1 = temp1[0].split(",<");
	    			  temp3 = temp1[0].split(",");
	    			  temp2 = temp1[1].split(",");
	    			  if (temp2.length > 1)
	    			  {
	    				  attribute2 = temp2[0];
	    				  object2 = temp2[1];
	    			  }
	    			  else
	    			  {
	    				  attribute2 = "";
	    				  object2 = temp2[0];
	    			  }
	    			  prep = temp3[1];
	    			  strLine = br.readLine();
	    			  temp = strLine.split("\t");
	    			  temp = temp[0].split(", ");
	    			  // Print the content on the console
	    			  // For All IAPR s
	    			  temp1 = temp[0].split("<<");
	    			  temp1 = temp1[1].split(">,");
	    			  temp2 = temp1[0].split(",");
	    			  temp3 = temp1[1].split(">");
	    			  if (temp2.length > 1)
	    			  {
	    				  attribute2_1 = temp2[0];
	    				  object2_1 = temp2[1];
	    			  }
	    			  else
	    			  {
	    				  attribute2_1 = "";
	    				  object2_1 = temp2[0];
	    			  }
	    			  verb2 = temp3[0];
	    			  
	    			  temp1 = temp[1].split(">>");
	    			  temp1 = temp1[0].split(",<");
	    			  temp3 = temp1[0].split(",");
	    			  temp2 = temp1[1].split(",");
	    			  if (temp2.length > 1)
	    			  {
	    				  attribute2_2 = temp2[0];
	    				  object2_2 = temp2[1];
	    			  }
	    			  else
	    			  {
	    				  attribute2_2 = "";
	    				  object2_2 = temp2[0];
	    			  }
	    			  prep2 = temp3[1];
	    			  
	    			  strLine = br.readLine();
	    			  
	    			  temp = strLine.split("\t");
	    			  temp = temp[0].split(", ");
	    			  // Print the content on the console
	    			  // For All IAPR s
	    			  temp1 = temp[0].split("<<");
	    			  temp1 = temp1[1].split(">,");
	    			  temp2 = temp1[0].split(",");
	    			  temp3 = temp1[1].split(">");
	    			  if (temp2.length > 1)
	    			  {
	    				  attribute3_1 = temp2[0];
	    				  object3_1 = temp2[1];
	    			  }
	    			  else
	    			  {
	    				  attribute3_1 = "";
	    				  object3_1 = temp2[0];
	    			  }
	    			  verb3 = temp3[0];
	    			  
	    			  temp1 = temp[1].split(">>");
	    			  temp1 = temp1[0].split(",<");
	    			  temp3 = temp1[0].split(",");
	    			  temp2 = temp1[1].split(",");
	    			  if (temp2.length > 1)
	    			  {
	    				  attribute3_2 = temp2[0];
	    				  object3_2 = temp2[1];
	    			  }
	    			  else
	    			  {
	    				  attribute3_2 = "";
	    				  object3_2 = temp2[0];
	    			  }
	    			  prep3 = temp3[1];
	    			
	    			  prep = prep.replace("_", " ");
	    			  prep2 = prep2.replace("_", " ");
	    			  prep3 = prep3.replace("_", " ");
	    			  
	    			  if(object1.equals("diningtable"))
	    				  object1="dining table";
	    			  if(object2.equals("diningtable"))
	    				  object2="dining table";
	    			  if(object1.equals("pottedplant"))
	    				  object1="potted plant";
	    			  if(object2.equals("pottedplant"))
	    				  object2="potted plant";
	    			  if(object1.equals("tvmonitor"))
	    				  object1="tv monitor";
	    			  if(object2.equals("tvmonitor"))
	    				  object2="tv monitor";
	    			  
	    			  object1_copy = object1;
	    			  object2_1_copy = object2_1;
	    			  object3_1_copy = object3_1;
	    			  
	    			  // Aggregation : Subject Grouping
	    			  if (verb.equals(verb2) && object2.equals(object2_2))
	    				  subject_grouping = 1;
	    			  else if (verb2.equals(verb3) && object2_2.equals(object3_2))
	    				  subject_grouping = 2;
	    			  else if (verb.equals(verb3) && object2.equals(object3_2))
	    				  subject_grouping = 3;
	    			  // Aggregation : Predicate Grouping
	    			  else if (object1.equals(object2_1))
	    				  predicate_grouping = 1;
	    			  else if (object2_1.equals(object3_1))
	    				  predicate_grouping = 2;
	    			  else if (object1.equals(object3_1))
	    				  predicate_grouping = 3;
	    			  
	    			  // Add modifier/attribute
	    			  if (attribute1.equals(""))
	    				  nothing = "";
	    			  else
	    				  object1 = attribute1 + " " + object1;
	    			  if (attribute2.equals(""))
	    				  nothing = "";
	    			  else
	    				  object2 = attribute2 + " " + object2;
	    			  
	    			  // Add determiner
	    			  if(vowels_list.contains(Character.toString(object1.charAt(0))))
	    				  object1 = "an " + object1;
	    			  else
	    				  object1 = "a " + object1;
	    			  
	    			  if(vowels_list.contains(Character.toString(object2.charAt(0))))
	    				  object2 = "an " + object2;
	    			  else
	    				  object2 = "a " + object2;
	    				
	    			  modifier = prep + " " + object2;
	    			  
	    			  if (attribute2_1.equals(""))
	    				  nothing = "";
	    			  else
	    				  object2_1 = attribute2_1 + " " + object2_1;
	    			  if (attribute2_2.equals(""))
	    				  nothing = "";
	    			  else
	    				  object2_2 = attribute2_2 + " " + object2_2;
	    			  
	    			  // Add determiner
	    			  if(vowels_list.contains(Character.toString(object2_1.charAt(0))))
	    				  object2_1 = "an " + object2_1;
	    			  else
	    				  object2_1 = "a " + object2_1;
	    			  
	    			  if(vowels_list.contains(Character.toString(object2_2.charAt(0))))
	    				  object2_2 = "an " + object2_2;
	    			  else
	    				  object2_2 = "a " + object2_2;
	    				
	    			  modifier2 = prep2 + " " + object2_2;
	    			  
	    			  if (attribute3_1.equals(""))
	    				  nothing = "";
	    			  else
	    				  object3_1 = attribute3_1 + " " + object3_1;
	    			  if (attribute3_2.equals(""))
	    				  nothing = "";
	    			  else
	    				  object3_2 = attribute3_2 + " " + object3_2;
	    			  
	    			  // Add determiner
	    			  if(vowels_list.contains(Character.toString(object3_1.charAt(0))))
	    				  object3_1 = "an " + object3_1;
	    			  else
	    				  object3_1 = "a " + object3_1;
	    			  
	    			  if(vowels_list.contains(Character.toString(object3_2.charAt(0))))
	    				  object3_2 = "an " + object3_2;
	    			  else
	    				  object3_2 = "a " + object3_2;
	    				
	    			  modifier3 = prep3 + " " + object3_2;
	    			  
	    			  // IF Any type of Aggregation is possible 
	    			  if (subject_grouping!=0 || predicate_grouping!=0)
	    			  {
	    				  System.out.println("Aggregation is possible in file " + files[i]);
	    				  out_aggregation.write(files[i]+ '\n');
	    				  
	    				  if (subject_grouping==1)
	    				  {
	    					  if (verbs_passive_list.contains(verb))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  NPPhraseSpec obj1 = nlgFactory.createNounPhrase(object1);
	    	    			      NPPhraseSpec obj2 = nlgFactory.createNounPhrase(object2_1);

	    	    			      CoordinatedPhraseElement oobbjj = nlgFactory.createCoordinatedPhrase(obj1, obj2); 
	    	    				  sps.setObject(oobbjj);
	    	    				  sps.setVerb(verb);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier); 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  NPPhraseSpec subj1 = nlgFactory.createNounPhrase(object1);
	    	    			      NPPhraseSpec subj2 = nlgFactory.createNounPhrase(object2_1);

	    	    			      CoordinatedPhraseElement ssuubbjj = nlgFactory.createCoordinatedPhrase(subj1, subj2);
	    	    				  sps.setSubject(ssuubbjj);
	    	    				  sps.setVerb(verb);
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier); // Prepositional phrase, string 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    					  if (verbs_passive_list.contains(verb3))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute3_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_object = nlgFactory.createNounPhrase(object3_1_copy);
	    	    					  npphrasespec_object.setPlural(true);
	    	    					  sps.setObject(npphrasespec_object);
	    	    					  sps.setFrontModifier(attribute3_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setObject(object3_1);
	    	    				  }   
	    	    				  sps.setVerb(verb3);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier3); 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute3_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object3_1_copy);
	    	    					  npphrasespec_subject.setPlural(true);
	    	    					  sps.setSubject(npphrasespec_subject);
	    	    					  sps.setFrontModifier(attribute3_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setSubject(object3_1);
	    	    				  } 
	    	    				  
	    	    				  sps.setVerb(verb3);
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier3); // Prepositional phrase, string 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    				  }
	    				  else if (subject_grouping==2)
	    				  {
	    					  if (verbs_passive_list.contains(verb))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_object = nlgFactory.createNounPhrase(object1_copy);
	    	    					  npphrasespec_object.setPlural(true);
	    	    					  sps.setObject(npphrasespec_object);
	    	    					  sps.setFrontModifier(attribute1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setObject(object1);
	    	    				  }   
	    	    				  sps.setVerb(verb);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier); 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object1_copy);
	    	    					  npphrasespec_subject.setPlural(true);
	    	    					  sps.setSubject(npphrasespec_subject);
	    	    					  sps.setFrontModifier(attribute1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setSubject(object1);
	    	    				  } 
	    	    				  
	    	    				  sps.setVerb(verb);
	    	    				  
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier); // Prepositional phrase, string 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  
	    	    			  }
	    					  
	    					  // Aggregation for second and third triple
	    					  if (verbs_passive_list.contains(verb2))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  NPPhraseSpec obj1 = nlgFactory.createNounPhrase(object2_1);
	    	    			      NPPhraseSpec obj2 = nlgFactory.createNounPhrase(object3_1);

	    	    			      CoordinatedPhraseElement oobbjj = nlgFactory.createCoordinatedPhrase(obj1, obj2); 
	    	    				  sps.setObject(oobbjj);
	    	    				  sps.setVerb(verb2);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier2); 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  NPPhraseSpec subj1 = nlgFactory.createNounPhrase(object2_1);
	    	    			      NPPhraseSpec subj2 = nlgFactory.createNounPhrase(object3_1);

	    	    			      CoordinatedPhraseElement ssuubbjj = nlgFactory.createCoordinatedPhrase(subj1, subj2);
	    	    				  sps.setSubject(ssuubbjj);
	    	    				  sps.setVerb(verb2);
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier2); // Prepositional phrase, string 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  
	    	    			  }
	    				  }
	    				  else if (subject_grouping==3)
	    				  {
	    					  // Aggregate 1 and 3 triple
	    					  if (verbs_passive_list.contains(verb))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  NPPhraseSpec obj1 = nlgFactory.createNounPhrase(object1);
	    	    			      NPPhraseSpec obj2 = nlgFactory.createNounPhrase(object3_1);

	    	    			      CoordinatedPhraseElement oobbjj = nlgFactory.createCoordinatedPhrase(obj1, obj2); 
	    	    				  sps.setObject(oobbjj);
	    	    				  sps.setVerb(verb);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier); 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  NPPhraseSpec subj1 = nlgFactory.createNounPhrase(object1);
	    	    			      NPPhraseSpec subj2 = nlgFactory.createNounPhrase(object3_1);

	    	    			      CoordinatedPhraseElement ssuubbjj = nlgFactory.createCoordinatedPhrase(subj1, subj2);
	    	    				  sps.setSubject(ssuubbjj);
	    	    				  sps.setVerb(verb);
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier); // Prepositional phrase, string 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    					  
	    					  // Sentece generate for 2nd triple
	    					  if (verbs_passive_list.contains(verb2))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute2_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_object = nlgFactory.createNounPhrase(object2_1_copy);
	    	    					  npphrasespec_object.setPlural(true);
	    	    					  sps.setObject(npphrasespec_object);
	    	    					  sps.setFrontModifier(attribute2_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setObject(object2_1);
	    	    				  }   
	    	    				  sps.setVerb(verb2);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier2); 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute2_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object2_1_copy);
	    	    					  npphrasespec_subject.setPlural(true);
	    	    					  sps.setSubject(npphrasespec_subject);
	    	    					  sps.setFrontModifier(attribute2_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setSubject(object2_1);
	    	    				  } 
	    	    				  
	    	    				  sps.setVerb(verb2);
	    	    				  
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier2); // Prepositional phrase, string 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    				  }
	    				  else if (predicate_grouping==1)
	    				  {
	    					  // Aggregate 1st and 2nd triple
	    					  if (verbs_passive_list.contains(verb))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_object = nlgFactory.createNounPhrase(object1_copy);
	    	    					  npphrasespec_object.setPlural(true);
	    	    					  sps.setObject(npphrasespec_object);
	    	    					  sps.setFrontModifier(attribute1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setObject(object1);
	    	    				  }   
	    	    				  sps.setVerb(verb);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier);
	    	    				  extra_complement = "and " + verb2 + " " + modifier2;
	    	    				  sps.addModifier(extra_complement);
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object1_copy);
	    	    					  npphrasespec_subject.setPlural(true);
	    	    					  sps.setSubject(npphrasespec_subject);
	    	    					  sps.setFrontModifier(attribute1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setSubject(object1);
	    	    				  } 
	    	    				  
	    	    				  sps.setVerb(verb);
	    	    				  
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier); // Prepositional phrase, string
	    	    				  
	    	    				  extra_complement = "and " + verb2 + " " + modifier2;
	    	    				  sps.addComplement(extra_complement);
	    	    				  
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    					  // Generate sentence for third triple
	    					  if (verbs_passive_list.contains(verb3))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute3_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_object = nlgFactory.createNounPhrase(object3_1_copy);
	    	    					  npphrasespec_object.setPlural(true);
	    	    					  sps.setObject(npphrasespec_object);
	    	    					  sps.setFrontModifier(attribute3_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setObject(object3_1);
	    	    				  }   
	    	    				  sps.setVerb(verb3);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier3); 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute3_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object3_1_copy);
	    	    					  npphrasespec_subject.setPlural(true);
	    	    					  sps.setSubject(npphrasespec_subject);
	    	    					  sps.setFrontModifier(attribute3_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setSubject(object3_1);
	    	    				  } 
	    	    				  
	    	    				  sps.setVerb(verb3);
	    	    				  
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier3); // Prepositional phrase, string 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    				  }
	    				  else if (predicate_grouping==2)
	    				  {
	    					  // Generate sentence for 1st triple
	    					  if (verbs_passive_list.contains(verb))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_object = nlgFactory.createNounPhrase(object1_copy);
	    	    					  npphrasespec_object.setPlural(true);
	    	    					  sps.setObject(npphrasespec_object);
	    	    					  sps.setFrontModifier(attribute1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setObject(object1);
	    	    				  }   
	    	    				  sps.setVerb(verb);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier); 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object1_copy);
	    	    					  npphrasespec_subject.setPlural(true);
	    	    					  sps.setSubject(npphrasespec_subject);
	    	    					  sps.setFrontModifier(attribute1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setSubject(object1);
	    	    				  } 
	    	    				  
	    	    				  sps.setVerb(verb);
	    	    				  
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier); // Prepositional phrase, string 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    					  // Aggregate 2nd and 3rd triple
	    					  if (verbs_passive_list.contains(verb2))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute2_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_object = nlgFactory.createNounPhrase(object2_1_copy);
	    	    					  npphrasespec_object.setPlural(true);
	    	    					  sps.setObject(npphrasespec_object);
	    	    					  sps.setFrontModifier(attribute2_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setObject(object2_1);
	    	    				  }   
	    	    				  sps.setVerb(verb2);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier2);
	    	    				  extra_complement = "and " + verb3 + " " + modifier3;
	    	    				  sps.addModifier(extra_complement);
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute2_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object2_1_copy);
	    	    					  npphrasespec_subject.setPlural(true);
	    	    					  sps.setSubject(npphrasespec_subject);
	    	    					  sps.setFrontModifier(attribute2_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setSubject(object2_1);
	    	    				  } 
	    	    				  
	    	    				  sps.setVerb(verb2);
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier2); // Prepositional phrase, string
	    	    				  
	    	    				  extra_complement = "and " + verb3 + " " + modifier3;
	    	    				  sps.addComplement(extra_complement);
	    	    				  
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    				  }
	    				  else if (predicate_grouping==3)
	    				  {
	    					 // Aggregate 1st and 3rd triple
	    					  if (verbs_passive_list.contains(verb))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_object = nlgFactory.createNounPhrase(object1_copy);
	    	    					  npphrasespec_object.setPlural(true);
	    	    					  sps.setObject(npphrasespec_object);
	    	    					  sps.setFrontModifier(attribute1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setObject(object1);
	    	    				  }   
	    	    				  sps.setVerb(verb);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier);
	    	    				  extra_complement = "and " + verb3 + " " + modifier3;
	    	    				  sps.addModifier(extra_complement);
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object1_copy);
	    	    					  npphrasespec_subject.setPlural(true);
	    	    					  sps.setSubject(npphrasespec_subject);
	    	    					  sps.setFrontModifier(attribute1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setSubject(object1);
	    	    				  } 
	    	    				  sps.setVerb(verb);
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier); // Prepositional phrase, string
	    	    				  
	    	    				  extra_complement = "and " + verb3 + " " + modifier3;
	    	    				  sps.addComplement(extra_complement);
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    					  // Generate sentence for 2nd triple
	    					  if (verbs_passive_list.contains(verb2))
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute2_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_object = nlgFactory.createNounPhrase(object2_1_copy);
	    	    					  npphrasespec_object.setPlural(true);
	    	    					  sps.setObject(npphrasespec_object);
	    	    					  sps.setFrontModifier(attribute2_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setObject(object2_1);
	    	    				  }   
	    	    				  sps.setVerb(verb2);
	    	    				  sps.setFeature(Feature.PASSIVE, true);
	    	    				  sps.addModifier(modifier2); 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    	    			  else
	    	    			  {
	    	    				  sps = nlgFactory.createClause();
	    	    				  
	    	    				  if (attribute2_1.equals("several"))
	    	    				  {
	    	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object2_1_copy);
	    	    					  npphrasespec_subject.setPlural(true);
	    	    					  sps.setSubject(npphrasespec_subject);
	    	    					  sps.setFrontModifier(attribute2_1);
	    	    				  }
	    	    				  else
	    	    				  {
	    	    					  sps.setSubject(object2_1);
	    	    				  } 
	    	    				  
	    	    				  sps.setVerb(verb2);
	    	    				  
	    	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    	    				  sps.addComplement(modifier2); // Prepositional phrase, string 
	    	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    	    				  out.write(out1 + " ");
	    	    			  }
	    				  }
	    			  }
	    			  // No Aggregation possible, generate separate sentences
	    			  else
	    			  {
	    				  
	    			  // First triple
	    			  if (verbs_passive_list.contains(verb))
	    			  {
	    				  sps = nlgFactory.createClause();
	    				  
	    				  if (attribute1.equals("several"))
	    				  {
	    					  npphrasespec_object = nlgFactory.createNounPhrase(object1_copy);
	    					  npphrasespec_object.setPlural(true);
	    					  sps.setObject(npphrasespec_object);
	    					  sps.setFrontModifier(attribute1);
	    				  }
	    				  else
	    				  {
	    					  sps.setObject(object1);
	    				  }   
	    				  sps.setVerb(verb);
	    				  sps.setFeature(Feature.PASSIVE, true);
	    				  sps.addModifier(modifier); 
	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    				  out.write(out1 + " ");
	    			  }
	    			  else
	    			  {
	    				  sps = nlgFactory.createClause();
	    				  
	    				  if (attribute1.equals("several"))
	    				  {
	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object1_copy);
	    					  npphrasespec_subject.setPlural(true);
	    					  sps.setSubject(npphrasespec_subject);
	    					  sps.setFrontModifier(attribute1);
	    				  }
	    				  else
	    				  {
	    					  sps.setSubject(object1);
	    				  } 
	    				  
	    				  sps.setVerb(verb);
	    				  
	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    				  sps.addComplement(modifier); // Prepositional phrase, string 
	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    				  out.write(out1 + " ");
	    			  }
	    			  // Second triple
	    			  if (verbs_passive_list.contains(verb2))
	    			  {
	    				  sps = nlgFactory.createClause();
	    				  
	    				  if (attribute2_1.equals("several"))
	    				  {
	    					  npphrasespec_object = nlgFactory.createNounPhrase(object2_1_copy);
	    					  npphrasespec_object.setPlural(true);
	    					  sps.setObject(npphrasespec_object);
	    					  sps.setFrontModifier(attribute2_1);
	    				  }
	    				  else
	    				  {
	    					  sps.setObject(object2_1);
	    				  }   
	    				  sps.setVerb(verb2);
	    				  sps.setFeature(Feature.PASSIVE, true);
	    				  sps.addModifier(modifier2); 
	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    				  out.write(out1 + " ");
	    			  }
	    			  else
	    			  {
	    				  sps = nlgFactory.createClause();
	    				  
	    				  if (attribute2_1.equals("several"))
	    				  {
	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object2_1_copy);
	    					  npphrasespec_subject.setPlural(true);
	    					  sps.setSubject(npphrasespec_subject);
	    					  sps.setFrontModifier(attribute2_1);
	    				  }
	    				  else
	    				  {
	    					  sps.setSubject(object2_1);
	    				  } 
	    				  
	    				  sps.setVerb(verb2);
	    				  
	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    				  sps.addComplement(modifier2); // Prepositional phrase, string 
	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    				  out.write(out1 + " ");
	    			  }
	    			  // Third triple
	    			  if (verbs_passive_list.contains(verb3))
	    			  {
	    				  sps = nlgFactory.createClause();
	    				  
	    				  if (attribute3_1.equals("several"))
	    				  {
	    					  npphrasespec_object = nlgFactory.createNounPhrase(object3_1_copy);
	    					  npphrasespec_object.setPlural(true);
	    					  sps.setObject(npphrasespec_object);
	    					  sps.setFrontModifier(attribute3_1);
	    				  }
	    				  else
	    				  {
	    					  sps.setObject(object3_1);
	    				  }   
	    				  sps.setVerb(verb3);
	    				  sps.setFeature(Feature.PASSIVE, true);
	    				  sps.addModifier(modifier3); 
	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    				  out.write(out1 + " ");
	    			  }
	    			  else
	    			  {
	    				  sps = nlgFactory.createClause();
	    				  
	    				  if (attribute3_1.equals("several"))
	    				  {
	    					  npphrasespec_subject = nlgFactory.createNounPhrase(object3_1_copy);
	    					  npphrasespec_subject.setPlural(true);
	    					  sps.setSubject(npphrasespec_subject);
	    					  sps.setFrontModifier(attribute3_1);
	    				  }
	    				  else
	    				  {
	    					  sps.setSubject(object3_1);
	    				  } 
	    				  
	    				  sps.setVerb(verb3);
	    				  
	    				  sps.setFeature(Feature.PROGRESSIVE, true);
	    				  sps.addComplement(modifier3); // Prepositional phrase, string 
	    				  out1 = realiser.realiseSentence(sps); // Realiser created earlier.
	    				  out.write(out1 + " ");
	    			  }
	    			  }
	    			  // DO NOT COMMENT THIS BREAK AS ALL THREE TRIPLES ARE GETTING GENERATED IN A SINGLE LOOP
	    			  break;
	    		  }
	    		  in.close();
	    		  out.close();
	    	 }
	    	 catch(Exception e)
	    	 {
	    		 System.err.println("Error: " + e.getMessage());
	    	 }
	    	 System.out.println();
	     }
	     out_aggregation.close();
		 }
		 catch (Exception e)
		 {
			 System.err.println("Error: " + e.getMessage());
		 }
	}
}
