package com.beyonideas.textpattern;

public class WordPattern {

	private String word;
	private long total;
	private double ratio;
	
	public WordPattern() {
		this.word = "";
		this.total = 0;
		this.ratio = 0d;
	}
	
	public String getAll(){
		return ""+word+";"+total+";"+ratio;
	}


	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

}
