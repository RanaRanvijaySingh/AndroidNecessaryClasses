package com.headsup.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateData {

	/**
	 * This method is used to validate the email entered by user.
	 * 
	 */
	public static boolean isValidEmail(String email){
		//Regex for validating the email. 
		Pattern regex = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = regex.matcher(email);
		return matcher.matches();
	}


}
