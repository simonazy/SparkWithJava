// Dataframe filter, where, join, union, select manipulations

package com.jobreadyprogrammer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;

public class Application {

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder()
		        .appName("Learning Spark SQL Dataframe API")
		        .master("local")
		        .getOrCreate();
		
		
		 String studentsFile = "src/main/resources/students.csv";
		 
		    Dataset<Row> studentDf = spark.read().format("csv")
		        .option("inferSchema", "true") // Make sure to use string version of true
		        .option("header", true)
		        .load(studentsFile); 
		    
		 String gradeChartFile = "src/main/resources/grade_chart.csv";
		 
		    Dataset<Row> gradesDf = spark.read().format("csv")
		        .option("inferSchema", "true") // Make sure to use string version of true
		        .option("header", true)
		        .load(gradeChartFile);
		    
		    
		    Dataset<Row> joinedDf = studentDf
		    						.join(gradesDf, studentDf.col("GPA").equalTo(gradesDf.col("gpa")))
		    						.select(studentDf.col("student_name"), studentDf.col("favorite_book_title"));
		    
		    Dataset<Row> filteredDf = studentDf
									  .join(gradesDf, studentDf.col("GPA").equalTo(gradesDf.col("gpa")))
									  .filter(gradesDf.col("gpa").between(2, 3.5))
									  .select("student_name","favorite_book_title");
		    
		    //filter is interchangeable with where clause. 
		    Dataset<Row> whereDf = studentDf
		    								.join(gradesDf, studentDf.col("gpa").equalTo(gradesDf.col("GPA")))
		    								.where(gradesDf.col("gpa").gt(3.0)
		    										.and(gradesDf.col("gpa").lt(4.5))
		    										.or(gradesDf.col("gpa").equalTo(1.0)))
		    								.select("student_name","favorite_book_title");	    
		    
		    //studentDf.join(gradesDf, studentDf.col("GPA").equalTo(gradesDf.col("gpa"))).filter(gradesDf.col("gpa").gt(3.0))
		    
		    filteredDf.show();	   
		    
	}

		
}
