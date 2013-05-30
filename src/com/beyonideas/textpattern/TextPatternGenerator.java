package com.beyonideas.textpattern;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextPatternGenerator {
	
	private String text;
	private TextPattern textpattern;
	
	public TextPatternGenerator() {
		
		this.setText("");
		this.textpattern = new TextPattern();
		
	}
	

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public TextPattern getTextpattern() {
		return textpattern;
	}


	public void setTextpattern(TextPattern textpattern) {
		this.textpattern = textpattern;
	}
	
	public void setTextFromFile(String file, Charset encoding){
		try {
			this.setText(read(file, encoding));
		} catch (IOException e) {
			System.err.println("TextPatternGenarator: Error reading file: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void genPattern() {
		
		float temp = 0f;
		String input = "";
		Pattern p;
		Matcher m;
		HashMap<String, Float> hmTotal;
		HashMap<String, Float> hmRatio;
		
		input = getText().toLowerCase();
		p = Pattern.compile("[\\p{Alnum}]+");
		m = p.matcher(input);
		
		hmTotal = new HashMap<String, Float>();
		hmRatio = new HashMap<String, Float>();
		//temp
		HashSet<String> includedList = new HashSet<>();
		HashSet<String> excludedList = new HashSet<>();
		
		while ( m.find() ) {
			if(hmTotal.containsKey(input.substring(m.start(), m.end()))){
				temp = hmTotal.get(input.substring(m.start(), m.end()));
				temp++;
				hmTotal.put(input.substring(m.start(), m.end()), temp);
			}
			else
				hmTotal.put(input.substring(m.start(), m.end()), 1f);
		}
		
		Set set = hmTotal.entrySet();
	    Iterator i = set.iterator();
	    
	   
	    
	    i = set.iterator();
	    int total = 0;
	    while(i.hasNext()){
		      Map.Entry me = (Map.Entry) i.next();
		      total += Float.parseFloat(me.getValue().toString());
		    }
		
		i = set.iterator();
		if(!excludedList.isEmpty() && !includedList.isEmpty()){
		    while(i.hasNext()){
			      Map.Entry me = (Map.Entry) i.next();
			      if( !excludedList.contains(me.getKey() ))
			    	  if( includedList.contains(me.getKey() ))
			    		  if( Float.parseFloat(me.getValue().toString()) / (float) total>=0.001 )
			    			  hmRatio.put( me.getKey().toString(), (Float.parseFloat(me.getValue().toString()) / (float) total) );
			    }
		}
		else {
			while(i.hasNext()){
				Map.Entry me = (Map.Entry) i.next();
					if( Float.parseFloat(me.getValue().toString()) / (float) total>=0.001 )
						hmRatio.put( me.getKey().toString(), (Float.parseFloat(me.getValue().toString()) / (float) total) );
			    }
		}
		
	    
	    set = hmRatio.entrySet();
	    i = set.iterator();
	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry) i.next();
	      String key = me.getKey().toString();
	      textpattern.setEntry(key, hmTotal.get(key)+":"+hmRatio.get(key));
	      
	    }
	    
	    textpattern.printPattern();
		
	}
	
	private static String read(String path, Charset encoding) throws IOException {
		  byte[] encoded = Files.readAllBytes(Paths.get(path));
		  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
		}

}//public class - end