package com.jobreadyprogrammer.spark;
import java.util.Arrays;

public class ApplicationTest{
	
	public static void main(String[] args) {
	
	SparkSession spark = SparkSession.builder()
									 .appName("DataFrame vs DataSet")
									 .master("local")
									 .getOrCreate();
	
	String[] stringList = new String[]{"Banana", "Car", "Glass", "Banana", "Computer", "Car", "IS", "HE"};
	
	List<String> data =  Arrays.asList(stringList);
	
	// User defined dataset
	Dataset<String> ds = spark.createDataset(data, Encoders.STRING());
	ds.printSchema();
	ds.show();
	Dataset<Row> df = ds.groupBy("value").count();
	df.show(5);
	
	
	Dataset<Row> wordsDf = spark.createDataset(data, Encoders.STRING()).toDF();
	
	String[] boringWords = new String[] {"this", "is", "he"};
	List<String> boringList = Arrays.asList(boringWords);
	Dataset<Row> boringWordsDf = spark.createDataset(boringList, Encoders.STRING()).toDF();
	
	String filter = "('this', 'is', 'he')";
	
	wordsDf = wordsDf.filter("value not in "+filter);
	wordsDf.show();
	
	}
	
	
}
