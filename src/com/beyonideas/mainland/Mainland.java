package com.beyonideas.mainland;

import com.beyonideas.httpcom.HttpCom;
import com.beyonideas.textpattern.*;

public class Mainland {

	public static void main(String[] args) {

		TextPatternGenerator tpg = new TextPatternGenerator();
		tpg.setText("Input text, with words, punctuation punctuation, etc. Well, it's rather short short text text text text text text text.");
		System.out.println(tpg.getText());
		tpg.genPattern();
		HttpCom httpCom = new HttpCom();
	}

}
