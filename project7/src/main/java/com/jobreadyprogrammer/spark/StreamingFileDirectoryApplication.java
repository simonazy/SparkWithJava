package com.jobreadyprogrammer.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.StructType;
import static org.apache.spark.sql.functions.*;

public class StreamingFileDirectoryApplication {
	
	public static void main(String[] args) throws StreamingQueryException {
	
		
		SparkSession spark = SparkSession.builder()
		        .appName("StreamingFileDirectoryWordCount")
		        .master("local")
		        .getOrCreate();
		
		// Read all the csv files written atomically in a directory
		// StructType : schema;
		StructType userSchema = new StructType().add("date", "string").add("value", "float");
		
		Dataset<Row> stockData = spark
		  .readStream()
		  .option("sep", ",")
		  .schema(userSchema)      // Specify schema of the csv files
		  .csv("/Users/simona/Desktop/SparkCourse/sparkwithjava/IncomingStockFiles"); // Equivalent to format("csv").load("/path/to/directory"); could be S3 bucket;
		
		
		Dataset<Row> resultDf = stockData.groupBy("date").agg(avg(stockData.col("value")));
		
		StreamingQuery query = resultDf.writeStream()
				.outputMode("complete")
				.format("console")
				.start();
		
		query.awaitTermination();
		
	}
		
		
}
