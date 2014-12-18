package com.java7.improvements;

/**
 * The catch clause specifies the types of exceptions that the block can handle,
 * and each exception type is separated with a vertical bar (|).
 * 
 * @author Rogério Oliveira
 * 
 */
public class MultiCatch {

	public static void main(String args[]) {
		try {

			int a[] = new int[5];
			a[5] = 30 / 0;

		} catch (NullPointerException | ArithmeticException | ArrayIndexOutOfBoundsException e) {
			System.out
					.println("Catching a NullPointerException or ArithmeticException or ArrayIndexOutOfBoundsException");
		} catch (Exception e) {
			System.out.println("Catching the rest of Exception");
		}

	}

}
