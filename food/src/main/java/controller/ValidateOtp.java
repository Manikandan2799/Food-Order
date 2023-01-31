package controller;

import java.util.Random;

public class ValidateOtp {
	public static String otp() {
		
	  Random rand = new Random();
      int num = rand.nextInt(1000000);
      
      String strNum = Integer.toString(num);
      String nums = "";
      String letters = "";
      for (int i = 0; i < strNum.length(); i++) {
          char c = strNum.charAt(i);
          if (Character.isDigit(c)) {
              nums += c;
          } else if (Character.isLetter(c)) {
              letters += c;
          }
      }
      String otp = nums + letters;
	return otp;
   
		   
		}


}
