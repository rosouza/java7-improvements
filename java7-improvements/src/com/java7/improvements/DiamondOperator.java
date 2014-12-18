package com.java7.improvements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Type Inference for Generic Instance Creation
 * 
 * You can replace the type arguments required to invoke the constructor of a
 * generic class with an empty set of type parameters (<>) as long as the
 * compiler can infer the type arguments from the context. This pair of angle
 * brackets is informally called the diamond.
 * 
 * @author Rogério Oliveira
 * 
 */
public class DiamondOperator {

	public static void main(String[] args) {

		List<String> strList = new ArrayList<>();

		List<Map<String, List<String>>> strList2 = new ArrayList<>();

	}
}
