package com.loanmanagement.transaction.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ConvertUtils {

	public static String dateToString(Date input, String format) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Date stringToDate(String input, String format) {
		try {
			return new SimpleDateFormat(format).parse(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String genKey(String prefix) {
		Date refundDate = Calendar.getInstance().getTime();
		return prefix + dateToString(refundDate, "yyyyDDmmhhmmss") + getRandomAlphabet() + getRandomAlphabet()
				+ getRandomAlphabet();
	}

	public static Double stringToDouble(String input) {
		try {
			return Double.parseDouble(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getRandomCharacter() {
		Random r = new Random();
		return String.valueOf((char) (r.nextInt(95) + 32));
	}

	private static String getRandomAlphabet() {
		Random r = new Random();
		return String.valueOf((char) (r.nextInt(26) + 'a'));
	}

}
