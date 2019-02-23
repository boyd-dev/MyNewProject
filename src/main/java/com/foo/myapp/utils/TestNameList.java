package com.foo.myapp.utils;

import java.util.Random;

public class TestNameList {

	private final static String[] firstNames = new String[]{
			"Jessica"
			,"Tree"
			,"Israel"
			,"Carter"
			,"Phi"
			,"Ryan"
			,"Suraj"
			,"Samar"
			,"Sarah"
			,"Andrea"
			,"Rachel"
			,"Danielle"
			,"Ruby"
			,"Lori"
			,"Steve"
			,"Dean"
			,"Charles"
			,"Gregory"
			,"Laura"
			,"Stephanie"
			,"Missy"
			,"Julie"
			,"Jason"
			,"David"
			,"Caleb"
			,"Tim"
			,"Jimmy"
			,"Peter"
			,"Rob"
			,"John"
			,"Kenneth"
			,"James"
			,"Johnny"
			,"Lindsey"
			,"Nick"
			,"Brenda"
			,"Brady"
			,"Ramsey"
			,"Keith"
			,"Dwayne"
			,"Thomas"
			,"Young"
			,"Nick"
			,"Ricky"
			,"Lena"
			,"Julia"
			,"Ellie"
			,"Kim"
			,"Mary Elizabeth"
			,"James"
	};

	private final static String[] lastNames = new String[]{
			 "Rothe"
			,"Gelbman"
			,"Broussard"
			,"Davis"
			,"Vu"
			,"Phan"
			,"Sharma"
			,"Ghosh"
			,"Yarkin"
			,"Morgan"
			,"Matthews"
			,"Bouseman"
			,"Modine"
			,"Spengler"
			,"Zissis"
			,"Bronson"
			,"Aitken"
			,"Butler"
			,"Clifton"
			,"Butler"
			,"Yager"
			,"Gelbman"
			,"Bayle"
			,"Gelbman"
			,"Spillyards"
			,"Bauer"
			,"Gonzales"
			,"Jaymes Jr."
			,"Mello"
			,"Tombs"
			,"Israel"
			,"Evermore"
			,"Ballance"
			,"Smith"
			,"Sims"
			,"Currin"
			,"Lewis"
			,"Anderson"
			,"Lumbly"
			,"Johnson"
			,"Whilley"
			,"Zak"
			,"Frost"
			,"Knight"
			,"Headey"
			,"Knight"
			,"Gonsalves"
			,"Matula"
			,"Winstead"
			,"Jones"
	};



	public static String getRandomName() {

		Random rand = new Random();
		int n1 = rand.nextInt(50);
		int n2 = rand.nextInt(50);

		String name = firstNames[n1] + " " + lastNames[n2];
		return name;
	}


}
