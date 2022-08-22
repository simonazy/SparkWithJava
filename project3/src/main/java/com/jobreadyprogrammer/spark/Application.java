package com.jobreadyprogrammer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.lit;

import org.apache.spark.Partition;

import static org.apache.spark.sql.functions.concat;


public class Application{
	
	public static void main(String[] args) {
		
		SparkSession spark = new SparkSession.Builder().appName("Dataset").master("local").getOrCreate();
		
		Dataset<Row> durhamDf = buildDurhamParksDataFrame(spark);
		durhamDf.printSchema();
		durhamDf.show(10);
		
		Dataset<Row> phiDf = buildPhiParksDataFrame(spark);
		phiDf.printSchema();
		phiDf.show(10);
		
		combineDataframes(durhamDf, phiDf);
	
	}
	
	public static Dataset<Row> buildDurhamParksDataFrame(SparkSession spark){
	
		Dataset<Row> df = spark.read().format("json")
						  .option("multiline", true)
						  .load("src/main/resources/durham-parks.json");
		
		df = df.withColumn("park_id", concat(df.col("datasetid"), lit("_"), df.col("fields.objectid")))
				.withColumn("park_name", df.col("fields.park_name"))
				.withColumn("city", lit("Durham"))
				.withColumn("address", df.col("fields.address"))
				.withColumn("has_playground", df.col("fields.playground"))
				.withColumn("zipcode", df.col("fields.zip"))
				.withColumn("land_in_acres", df.col("fields.acres"))
				.withColumn("geoX", df.col("geometry.coordinates").getItem(0))
				.withColumn("geoY", df.col("geometry.coordinates").getItem(1))
				.drop("fields")
				.drop("geometry")
				.drop("record_timestamp")
				.drop("recordid")
				.drop("datasetid");
		
		return df;
	}
	
	public static Dataset<Row> buildPhiParksDataFrame(SparkSession spark){
		
		Dataset<Row> df = spark.read().format("csv").option("multiline", true)
						  .option("header", true)
						  .load("src/main/resources/philadelphia_recreations.csv");
		
		df = df.withColumn("park_id", concat(lit("phil_"), df.col("OBJECTID")))
				.withColumnRenamed("ASSET_NAME", "park_name")
				.withColumnRenamed("ZIPCODE", "zipcode")
				.withColumnRenamed("ACREAGE", "land_in_acres")
				.withColumn("city", lit("Philadelphia"))
				.withColumnRenamed("ADDRESS", "address")
				.withColumn("has_playground", lit("UNKNOWN"))
				.withColumn("geoX", lit("UNKNONW"))
				.withColumn("geoY", lit("UNKNONW"))
				.drop("SITE_NAME")
				.drop("OBJECTID")
				.drop("CHILD_OF")
				.drop("TYPE")
				.drop("USE_")
				.drop("DESCRIPTION")
				.drop("SQ_FEET")
				.drop("ALLIAS")
				.drop("CHRONOLOGY")
				.drop("NOTES")
				.drop("DATE_EDITED")
				.drop("EDITED_BY")
				.drop("OCCUPANT")
				.drop("TENANT")
				.drop("LABEL");
		
		return df;
	}
	
	private static void combineDataframes(Dataset<Row> df1, Dataset<Row> df2){
		
	// Match by column names using the unionByName() method.
	// if we use union(), it matches the columns based on order.
	Dataset<Row> df = df1.unionByName(df2);
	
	df.show();
	df.printSchema();
	System.out.println("We have " + df.count() + " records.");
	
	// df = df.repartition(5);
	
	Partition[] partitions = df.rdd().partitions();
	
	System.out.println("Total number of partitions: "+ partitions.length); //output: 2&5 partitions
		
	}
	
}

















