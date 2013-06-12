package com.beyonideas.mainland;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beyonideas.textpattern.WordsPattern;

public class Mainland {

	public static String removeWhiteSpaces(String s){
		String res = "";
		char [] c = s.toCharArray();
		ArrayList<Character> al = new ArrayList<Character>();
		
		for(int i : c)
			if(c[i]!=' ')
				al.add(c[i]); 

		for(int i = 0; i<al.size(); i++)
			System.out.println(al.indexOf(i));
		return res;
	}
	
	public static String read(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	public static void main(String[] args) throws IOException {

		WordsPattern wp = new WordsPattern();
		String input = "Input text, with words, punctuation punctuation, etc. Well, it's rather short short text text text text text text text.";
		input = read("res/input.txt", Charset.defaultCharset());
//		input = input.toLowerCase();
//		Pattern p = Pattern.compile("[\\p{Alnum}]+");
//		Matcher m = p.matcher(input);
//		while ( m.find() ) {
//			wp.putWord(m.group(), 0.001D);
//		}
		wp.putText(input, 0D);
		System.out.println(wp.getGlobalTotal());
		wp.writeToFile("testPat.txt");
		//wp.writeToCompressedFile("testPat.txt");
		wp = new WordsPattern();
		//wp.readFromCompressedFile("testPat.txt.gz");
		wp.readFromFile("testPat.txt");
	}

}
