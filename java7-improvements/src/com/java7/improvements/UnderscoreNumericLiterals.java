package com.java7.improvements;

/**
 * In Java SE 7 and later, any number of underscore characters (_) can appear
 * anywhere between digits in a numerical literal. This feature enables you, for
 * example, to separate groups of digits in numeric literals, which can improve
 * the readability of your code.
 * 
 * For instance, if your code contains numbers with many digits, you can use an
 * underscore character to separate digits in groups of three, similar to how
 * you would use a punctuation mark like a comma, or a space, as a separator.
 * 
 * The following example shows other ways you can use the underscore in numeric
 * literals.
 * 
 * @author Rogério Oliveira
 * 
 */
public class UnderscoreNumericLiterals {

	public static void main(String[] args) {

		int mask = 0b1010_1010_1010;
		long big = 9_223_783_036_967_937L;
		long creditCardNumber = 1234_5678_9012_3456L;
		long socialSecurityNumber = 999_99_9999L;
		float pi = 3.14_15F;
		long hexBytes = 0xFF_EC_DE_5E;
		long hexWords = 0xCAFE_BFFE;
		long cpf = 600_788_999_05L;

		System.out.println(cpf);

	}

}