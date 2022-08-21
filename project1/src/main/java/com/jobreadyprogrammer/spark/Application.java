package com.jobreadyprogrammer.spark;

import org.apache.spark.sql.SparkSession;
import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import static org.apache.spark.sql.functions.*;


public class Application{
	
	public static void main(String args[]) {
		
		//create a session
		SparkSession spark = new SparkSession.Builder().appName("CSV to DB").master("local").getOrCreate();
		
		//get  data: usually hdfs format files, but here from local
		Dataset<Row> df = spark.read().format("csv").option("header", true).load("src/main/resources/name_and_comments.txt");
		
		
		// Transformation 
		//Dataset is immutable
		df = df.withColumn("full_name", concat(df.col("last_name"),lit(", "), df.col("first_name")));
		
		df = df.filter(df.col("comment").rlike("\\d+")); //d means digit, regx
		
		df = df.filter(df.col("comment").rlike("\\d+")).orderBy(df.col("last_name").asc());
		
		// df.show();
		
		// Connect and save to a postgre database
		String dbConnectionUrl = "jdbc:postgresql://localhost/course_data";
		Properties prop = new Properties();
		prop.setProperty("driver", "org.postgresql.Driver");
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "123456");
		
		df.write().mode(SaveMode.Overwrite).jdbc(dbConnectionUrl, "project1", prop);		
				
	}
}
