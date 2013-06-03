package com.beyonideas.textpattern;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TextPattern {

	HashMap<String, String> pattern;

	public TextPattern() {
		this.pattern = new HashMap<String, String>();
	}

	public void setEntry(String key, String value){
		this.pattern.put(key, value);
	}

	public String getEntry(String key){
		return key+":"+pattern.get(key);
	}
	
	public String [] entries(){
		return (String[]) pattern.keySet().toArray();
	}

	public void printPattern(){
		Set<Entry<String, String>> set = pattern.entrySet();
		Iterator<Entry<String, String>> i = set.iterator();
		while(i.hasNext()){
			Map.Entry me = (Map.Entry) i.next();
			System.out.println(me.getKey().toString()+":"+pattern.get(me.getKey().toString()));
		}
	}


}
