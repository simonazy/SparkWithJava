package com.jobreadyprogrammer.spark;

import java.util.ArrayList;
import java.util.*;

// Java Arrays

// Primitive datatypes: boolean, char, byte, short, int, long;
// Non-primitive: String, Array, etc....


public class DataType{


	public static void main(String[] args) {
	
		// To declare an array, define the variable type with square brackets:
		String[] cars = {"Toyota", "BMV", "Jeep", "Tesla"};
		int[] numbers = {1, 2, 3, 4};
	
		for (String i: cars) {
				System.out.println(i);
				}
	
		for (int i=0; i<numbers.length; i++) {
				System.out.println(numbers[i]);
				}
	
		//The ArrayList class is a resizable array
		
		ArrayList<String> arraryList = new ArrayList<String>();
		arraryList.add("Volvo");
		String first = arraryList.get(0);
		
		DataType.ArrayVsArrayList();
		
	}
	
	public static void ArrayVsArrayList(){
		String[] array = new String[300];
		ArrayList<String> list = new ArrayList<String>(300);
		
		for (int i=0; i<300; i++) {
			if (Math.random()>0.5) {
				array[i] = "abc";
			}else {
				array[i] = "xyz";
			}
			list.add(array[i]);
		}
		
		int iterations = 100000000;
		long start_ms;
		int sum;
		
		start_ms = System.currentTimeMillis();
		sum = 0;
		
		for (int i=0; i<iterations; i++) {
			for (int j=0; j<300; ++j) {
				// sum += array[j].length();
				sum += list.get(j).length();
			}
		}
		
		System.out.println(System.currentTimeMillis()-start_ms);
		
	}
}
