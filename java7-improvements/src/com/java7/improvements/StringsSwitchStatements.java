package com.java7.improvements;

/**
 * In the JDK 7 release, you can use a String object in the expression of a
 * switch statement
 * 
 * @author Rogério Oliveira
 * 
 */
public class StringsSwitchStatements {

	public static void main(String[] args) {

		String month = "March";

		switch (month) {
		case "April":
		case "June":
		case "September":
		case "November":
			System.out.println(30);
			break;
		case "January":
		case "March":
		case "May":
		case "July":
		case "August":
		case "December":
			System.out.println(31);
			break;
		case "February":
			System.out.println(29);
			break;
		default:
			System.out.println(0);
		}

	}

}
