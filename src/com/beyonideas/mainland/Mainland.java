package com.beyonideas.mainland;

import java.util.ArrayList;
import java.util.Iterator;

import com.beyonideas.textpattern.TextPatternGenerator;

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

	public static void main(String[] args) {

		TextPatternGenerator tpg = new TextPatternGenerator();
		tpg.setText("Input text, with words, punctuation punctuation, etc. Well, it's rather short short text text text text text text text.");
		System.out.println("Text: "+tpg.getText());
		tpg.buildPattern();
		tpg.updatePattern();
		removeWhiteSpaces("Input text, with words, punctuation punctuation, etc. Well, it's rather short short text text text text text text text.");
	}

}
