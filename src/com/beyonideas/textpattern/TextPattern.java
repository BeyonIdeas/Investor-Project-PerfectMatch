package com.beyonideas.textpattern;

import java.util.ArrayList;

public class TextPattern {

	private ArrayList<WordPattern> wordpattern;

	public TextPattern() {
		this.wordpattern = new ArrayList<WordPattern>();
	}
	
	public ArrayList<WordPattern> getWordpattern() {
		return wordpattern;
	}

	public void setWordpattern(String word, long total, double ratio) {
		WordPattern wp = new WordPattern();
		wp.setWord(word);
		wp.setTotal(total);
		wp.setRatio(ratio);
		this.wordpattern.add(wp);
	}

	public int getSize(){
		return wordpattern.size();
	}


//	public void printPattern(){
//		Set<Entry<String, String>> set = pattern.entrySet();
//		Iterator<Entry<String, String>> i = set.iterator();
//		while(i.hasNext()){
//			Map.Entry me = (Map.Entry) i.next();
//			System.out.println(me.getKey().toString()+":"+pattern.get(me.getKey().toString()));
//		}
//	}

}
