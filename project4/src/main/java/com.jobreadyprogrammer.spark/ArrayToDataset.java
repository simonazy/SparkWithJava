package com.jobreadyprogrammer.spark;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.ReduceFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

public class ArrayToDataset {

	public void start() {
		SparkSession spark = new SparkSession.Builder()
				.appName("Array To Dataset<String>")
				.master("local")
				.getOrCreate();
		
		String [] stringList = new String[] {"Banana", "Car", "Glass", "Banana", "Computer", "Car"};
		
		List<String> data = Arrays.asList(stringList);
		
		Dataset<String> ds =  spark.createDataset(data, Encoders.STRING());
		
		// Map function 
		ds = ds.map(new StringMapper(), Encoders.STRING());
//		ds = ds.map((MapFunction<String, String>) row -> "word: " + row, Encoders.STRING());
		ds.show(10);
		
		// Reduce function 
		String stringValue = ds.reduce(new StringReducer());
		System.out.println(stringValue);
		
	}
	
	// implement mapfunction
	static class StringMapper implements MapFunction<String, String>, Serializable {

		
		private static final long serialVersionUID = 1L;
		
		@Override
		public String call(String value) throws Exception {
			// TODO Auto-generated method stub
			return "word" + value;
		}
	}
	
	// implement reducefunction
	static class StringReducer implements ReduceFunction<String>, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		public String call(String v1, String v2) throws Exception {
			// TODO Auto-generated method stub
			return v1+v2;
		}
		
	}

}

