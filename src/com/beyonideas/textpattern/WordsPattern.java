package com.beyonideas.textpattern;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class WordsPattern {

	private HashMap<String, Double> hmTotal;
	private HashMap<String, Double> hmRatio;
	private Double globalTotal;

	public WordsPattern(){
		this.hmTotal = new HashMap<String, Double>();
		this.hmRatio = new HashMap<String, Double>();
		this.globalTotal = 0D;
	}
	
	public void putText(String text, Double relevance){
		Pattern p = Pattern.compile("[\\p{Alnum}]+");
		Matcher m = p.matcher(text.toLowerCase());
		while ( m.find() ) {
			this.putWord(m.group(), relevance);
		}
	}

	public void putWord(String word, Double relevance){
		if(this.hmTotal.containsKey(word)){
			this.hmTotal.put(word, this.hmTotal.get(word)+1D);
			this.globalTotal++;
		}
		else {
			this.hmTotal.put(word, 1D);
			this.globalTotal++;
		}
		Iterator<String> iterator = hmTotal.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			hmRatio.put(key, hmTotal.get(key)/globalTotal);
		}
		clean(relevance);
	}

	private void clean(Double relevance){
		if(relevance!=0){
			Iterator<String> iterator = hmTotal.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				if(hmRatio.get(key)<relevance){
					iterator.remove();
					hmRatio.remove(key);
					hmTotal.remove(key);
				}
			}
		}
	}

	public Double getTotal(String word){
		return hmTotal.get(word);
	}

	public Double getRatio(String word){
		return hmRatio.get(word);
	}

	public Double getGlobalTotal() {
		return globalTotal;
	}

	public void setGlobalTotal(Double globalTotal) {
		this.globalTotal = globalTotal;
	}

	public void writeToFile(String filename){
		String filecontent = "";
		filecontent += "total="+getGlobalTotal()+"\n";
		Iterator<String> iterator = hmTotal.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			filecontent += key+";"+hmTotal.get(key)+";"+hmRatio.get(key)+"\n";
		}
		try{
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(filecontent);
			out.close();
		}catch (IOException e){
			System.err.println("Error writing file: " + e.getMessage());
		}
	}

	public void writeToCompressedFile(String filename){
		filename += ".gz";
		String filecontent = "";
		filecontent += "total="+getGlobalTotal()+"\n";
		Iterator<String> iterator = hmTotal.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			filecontent += key+";"+hmTotal.get(key)+";"+hmRatio.get(key)+"\n";
		}
		try{
			FileOutputStream fstream = new FileOutputStream(filename);
			Writer out = new OutputStreamWriter(new GZIPOutputStream(fstream), "UTF-8");
			out.write(filecontent);
			out.close();
			fstream.close();
		}catch (IOException e){
			System.err.println("Error writing file: " + e.getMessage());
		}
	}

	public void readFromFile(String filename){

		try{
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = br.readLine();
			this.globalTotal = Double.parseDouble(strLine.split("=")[1]);
			while ((strLine = br.readLine()) != null)   {
				String strvec [] = strLine.split(";");
				hmTotal.put(strvec[0], Double.parseDouble(strvec[1]));
				hmRatio.put(strvec[0], Double.parseDouble(strvec[2]));
			}
			in.close();
		}catch (IOException e){
			System.err.println("Error reading file: " + e.getMessage());
		}

	}

	public void readFromCompressedFile(String filename){

		try{
			FileInputStream fstream = new FileInputStream(filename);
			GZIPInputStream gzip = new GZIPInputStream(fstream);
			InputStreamReader in = new InputStreamReader(gzip);
			BufferedReader br = new BufferedReader(in);
			String strLine = br.readLine();
			this.globalTotal = Double.parseDouble(strLine.split("=")[1]);
			while ((strLine = br.readLine()) != null)   {
				String strvec [] = strLine.split(";");
				hmTotal.put(strvec[0], Double.parseDouble(strvec[1]));
				hmRatio.put(strvec[0], Double.parseDouble(strvec[2]));
			}
			in.close();
		}catch (IOException e){
			System.err.println("Error reading file: " + e.getMessage());
		}

	}

	public void printContent(){
		String content = "";
		content += "total="+getGlobalTotal()+"\n";
		Iterator<String> iterator = hmTotal.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			content += key+";"+hmTotal.get(key)+";"+hmRatio.get(key)+"\n";
		}
		System.out.println(content);
	}

}