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

// All Pascal non synonym
public class NLG_AllPascal_ns {
	
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
	     File dir = new File("/home/user/NLG-Images/Results/AllPascal/ns/out1");
	     String[] files = dir.list();
	     
	     String strLine, object1, verb, prep, object2, attribute1, attribute2, modifier, object1_copy;
	     String nothing;
		 String[] temp, temp1, temp2, temp3;
		 
		 String[] verbs_with_passive_realization = new String[] {"park","dock","crowd","display"};
		 List<String> verbs_passive_list = Arrays.asList(verbs_with_passive_realization);
		 
		 String[] vowels = new String[] {"a","e","i","o","u"};
		 List<String> vowels_list = Arrays.asList(vowels);
		 
		 Integer count_loop = 0;
	     for (int i =0; i<files.length; i++)
	     //for (int i =0; i<1; i++)
	     {
	    	 count_loop = 0;
	    	 try
	    	 {
	    		  FileInputStream fstream = new FileInputStream("/home/user/NLG-Images/Results/AllPascal/ns/out1/" + files[i]);
	    		  DataInputStream in = new DataInputStream(fstream);
	    		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    		  
	    		  FileWriter fstream_out = new FileWriter("/home/user/NLG-Images/Results/AllPascal/ns/NLG-out1/" + files[i]);
	    		  BufferedWriter out = new BufferedWriter(fstream_out);
	    		
	    		  while ((strLine = br.readLine()) != null)
	    		  {
	    			  count_loop = count_loop + 1;
	    			  temp = strLine.split("\t");
	    			  temp = temp[0].split(", ");
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
	    			  prep = prep.replace("_", " ");
	    			  
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
	    			  // Add modifier/attribute
	    			  if (attribute1.equals(""))
	    			  {
	    				  nothing = "";
	    			  }
	    			  else
	    				  object1 = attribute1 + " " + object1;
	    			  if (attribute2.equals(""))
	    			  {
	    				  nothing = "";
	    			  }
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
	    				  out.write(out1 + "\n");
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
	    				  out.write(out1 + "\n");
	    			  }
	    			  if (count_loop>10)
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
	}
}
