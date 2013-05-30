import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class words_test {
		
	public static String readFile(String path, Charset encoding) throws IOException {
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
			}
	
	public static void writeFile(String file, String textToSave) throws IOException {
	    BufferedWriter out = new BufferedWriter(new FileWriter(file));
	    out.write(textToSave);
	    out.close();
	}
	
	public static void main(String [] args){
		
		//read input file for testing
		String content = "";
		try {
			content = readFile("input.txt", Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//read excluded words file
		String excluded = "";
		try {
			excluded = readFile("exclude.txt", Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//read included words file
		String included = "";
		try {
			included = readFile("include.txt", Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//create excluded word list
		HashSet<String> excludedList = new HashSet<String>();
		String exc [] = excluded.split("\n");
		for(int l=0;l<exc.length;l++)
			excludedList.add(exc[l]);
		
		//create included word list
		HashSet<String> includedList = new HashSet<String>();
		String inc [] = included.split("\n");
		for(int l=0;l<inc.length;l++)
			includedList.add(inc[l]);

		
		String input = "Input text, with words, punctuation punctuation, etc. Well, it's rather short short text text text text text text text.";
		input = content;
		input = input.toLowerCase();
		//Pattern p = Pattern.compile("[\\w']+");
		Pattern p = Pattern.compile("[\\p{Alnum}]+");
		Matcher m = p.matcher(input);
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		HashMap<String, Float> hm_final = new HashMap<String, Float>();
		
		
		int temp = 0;
		while ( m.find() ) {
			if(hm.containsKey(input.substring(m.start(), m.end()))){
				temp = hm.get(input.substring(m.start(), m.end()));
				temp++;
				hm.put(input.substring(m.start(), m.end()), temp);
			}
			else
				hm.put(input.substring(m.start(), m.end()), 1);
		        //System.out.println(input.substring(m.start(), m.end()));
		}
		
		//System.out.println(hm.values());
		Set set = hm.entrySet();
	    Iterator i = set.iterator();
	    
	    /*while(i.hasNext()){
	      Map.Entry me = (Map.Entry) i.next();
	      System.out.println(me.getKey() + " : " + me.getValue() );
	    }*/
	    
	    i = set.iterator();
	    int total = 0;
	    while(i.hasNext()){
		      Map.Entry me = (Map.Entry) i.next();
		      total += Integer.parseInt(me.getValue().toString());
		    }
		
		i = set.iterator();
	    while(i.hasNext()){
		      Map.Entry me = (Map.Entry) i.next();
		      if( !excludedList.contains(me.getKey() ))
		    	  if( includedList.contains(me.getKey() ))
		    		  if( Float.parseFloat(me.getValue().toString()) / (float) total>=0.001 )
		    			  hm_final.put( me.getKey().toString(), (Float.parseFloat(me.getValue().toString()) / (float) total) );
		    }
	    
	    set = hm_final.entrySet();
	    i = set.iterator();
	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry) i.next();
	      //System.out.println(me.getKey() + " : " + me.getValue() );
	    }
	    
	    //copy to values to arraylist and sorte them
	    //ArrayList keys = new ArrayList(hm_final.values());
	    //ArrayList out_words = new ArrayList<String>();
	    //ArrayList out_values = new ArrayList<Float>();
	    //Collections.sort(keys);
	    
	    String out_words [] = new String[hm_final.size()];
	    float out_values [] = new float[hm_final.size()];
	    i = set.iterator();
	    int k = 0;
	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry) i.next();
	      out_words[k] = me.getKey().toString();
	      out_values[k] = Float.parseFloat(me.getValue().toString());
	      k++;
	    }
	    
	    //shellsort both arrays - start
	    int n = out_values.length;
	    int h = n / 2;
	    float c;
		int j;
	    String c_str = "";
	    while (h > 0) {
	        for (int iss = h; iss < n; iss++) {
	            c = out_values[iss];
	            c_str = out_words[iss];
	            j = iss;
	            while (j >= h && out_values[j - h] > c) {
	            	out_values[j] = out_values[j - h];
	                out_words[j] = out_words[j - h];
	                j = j - h;
	            }
	            out_values[j] = c;
	            out_words[j] = c_str;
	        }
	        h = h / 2;
	    }
	  //shellsort both arrays - end
	    
	    String out = "";
	    for(k=0;k<out_values.length;k++){
	    	out += out_words[k]+":"+out_values[k]+"\n";
	    	System.out.println(out_words[k]+":"+out_values[k]);
	    }
	    
	    try {
			writeFile("output.txt", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}//main - end
	
}//class -end
