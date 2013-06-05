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
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextPatternGenerator {

	private String text;
	private TextPattern textpattern;

	public TextPatternGenerator() {
		this.text = "";
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

	public HashMap<String, Long> wordsTotal(String text){
		long temp = 0;
		String input = "";
		Pattern p;
		Matcher m;
		HashMap<String, Long> hmTotal = new HashMap<String, Long>();

		input = text.toLowerCase();
		p = Pattern.compile("[\\p{Alnum}]+");
		m = p.matcher(input);

		while ( m.find() ) {
			if(hmTotal.containsKey(input.substring(m.start(), m.end()))){
				temp += hmTotal.get(input.substring(m.start(), m.end()));
				hmTotal.put(input.substring(m.start(), m.end()), temp);
			}
			else{
				hmTotal.put(input.substring(m.start(), m.end()), 1l);
			}
		}
		return hmTotal;
	}

	public void buildPattern() {

		long temp = 0;
		int total = 0;
		String input = "";
		Pattern p;
		Matcher m;
		HashMap<String, Long> hmTotal = wordsTotal(getText());
		HashMap<String, Double> hmRatio = new HashMap<String, Double>();
		HashSet<String> includedList = new HashSet<>();
		HashSet<String> excludedList = new HashSet<>();

		input = getText().toLowerCase();
		p = Pattern.compile("[\\p{Alnum}]+");
		m = p.matcher(input);

		while ( m.find() ) {
			if(hmTotal.containsKey(input.substring(m.start(), m.end()))){
				temp = hmTotal.get(input.substring(m.start(), m.end()));
				temp++;
				hmTotal.put(input.substring(m.start(), m.end()), temp);
				total++;
			}
			else{
				hmTotal.put(input.substring(m.start(), m.end()), 1l);
				total++;
			}
		}
		
		hmTotal = new HashMap<String, Long>();
		hmTotal = wordsTotal(getText());
		Set set = hmTotal.entrySet();
		Iterator i = set.iterator();

		i = set.iterator();
		if(!excludedList.isEmpty() && !includedList.isEmpty()){
			while(i.hasNext()){
				Map.Entry me = (Map.Entry) i.next();
				if( !excludedList.contains(me.getKey() ))
					if( includedList.contains(me.getKey() ))
						if( Double.parseDouble(me.getValue().toString()) / (double) total>=0.001 )
							hmRatio.put( me.getKey().toString(), (Double.parseDouble(me.getValue().toString()) / (double) total) );
			}
		}
		else {
			while(i.hasNext()){
				Map.Entry me = (Map.Entry) i.next();
				if( Double.parseDouble(me.getValue().toString()) / (double) total>=0.001 )
					hmRatio.put( me.getKey().toString(), (Double.parseDouble(me.getValue().toString()) / (double) total) );
			}
		}


		set = hmRatio.entrySet();
		i = set.iterator();
		while(i.hasNext()){
			Map.Entry me = (Map.Entry) i.next();
			String key = me.getKey().toString();
			textpattern.setWordpattern(key, hmTotal.get(key), hmRatio.get(key));

		}

		for(int k=0; k<textpattern.getSize();k++)
			System.out.println(textpattern.getWordpattern().get(k).getAll());

	}

	public void updatePattern(){
		System.out.println("Entrou!");
		long temp = 0;
		int total = 0;
		String input = "";
		Pattern p;
		Matcher m;
		HashMap<String, Long> hmTotal = new HashMap<String, Long>();

		input = getText().toLowerCase();
		p = Pattern.compile("[\\p{Alnum}]+");
		m = p.matcher(input);

		while ( m.find() ) {
			if(hmTotal.containsKey(input.substring(m.start(), m.end()))){
				temp = hmTotal.get(input.substring(m.start(), m.end()));
				temp++;
				hmTotal.put(input.substring(m.start(), m.end()), temp);
				total++;
			}
			else{
				hmTotal.put(input.substring(m.start(), m.end()), 1l);
				total++;
			}
		}
		System.out.println("Actualizar!");
		Set<Entry<String, Long>> set = hmTotal.entrySet();
		Iterator<Entry<String, Long>> i = set.iterator();
		while(i.hasNext()){
			Map.Entry me = (Map.Entry) i.next();
			String word = me.getKey().toString();
			long num = Long.parseLong(me.getValue().toString());
			for(int j = 0; j<textpattern.getSize(); j++)
				if(textpattern.getWordpattern().get(j).getWord().equals(word)){
					String w = textpattern.getWordpattern().get(j).getWord();
					long t = textpattern.getWordpattern().get(j).getTotal();
					double r = textpattern.getWordpattern().get(j).getRatio();
					textpattern.getWordpattern().remove(j);
					textpattern.setWordpattern(w, (t+num), r);
				}
		}


		for(int k=0; k<textpattern.getSize();k++)
			System.out.println(textpattern.getWordpattern().get(k).getAll());

	}

	private static String read(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

}//public class - end