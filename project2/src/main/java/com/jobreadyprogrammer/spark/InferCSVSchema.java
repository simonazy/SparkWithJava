package com.jobreadyprogrammer.spark;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class InferCSVSchema {
	
	public void printSchemafunc() {
		
		SparkSession spark = new SparkSession.Builder()
								 .appName("Complex CSV to Dataframe")
								 .master("local")
								 .getOrCreate();
		
		Dataset<Row> df = spark.read().format("csv")
				   		  .option("header", true)
				   		  .option("sep", ";")
				   		  .option("multiline", true)
				   		  .option("quote", "^")
				   		  .option("dateFormat", "M/d/y")
				   		  .option("inferSchema", true)
				   		  .load("src/main/resources/amazonProducts.txt");
		
		System.out.println("Excerpt of the dataframe content:");
		// df.show();
		df.show(7, 90);
		System.out.println("Dataframe's schema:");
		df.printSchema();		
		
	}
	
//  change printSchemafunc to static: 	
//	public static void main(String[] args) {
//		InferCSVSchema.printSchemafunc();
//	}
	
	
}