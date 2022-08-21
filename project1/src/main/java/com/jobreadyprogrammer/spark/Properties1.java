package com.jobreadyprogrammer.spark;

import java.util.*;
import java.io.*;

import java.util.Properties;
import java.util.Hashtable;

// Properties is a subclass of Hashtable 
// public class Properties extends Hashtable<Object, Object>


public class Properties1{
	public static void main(String[] args) throws Exception{
		
		FileReader reader = new FileReader("src/main/resources/db.properties");
		
		Properties p = new Properties();
		
		p.load(reader);
		
		System.out.println(p.getProperty("username"));
		System.out.println(p.getProperty("password"));
		
	}
}
