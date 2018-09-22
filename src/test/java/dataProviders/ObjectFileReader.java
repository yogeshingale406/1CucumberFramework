package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import stepDefinitions.Hooks;

public class ObjectFileReader {
	private Properties properties;
	private final String propertyFilePath= System.getProperty("user.dir")+"/Configs/object.properties";

	
	public ObjectFileReader(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			
			properties = new Properties();
			try {
				properties.load(reader);
				Hooks.app_logs.debug("Loading object property file");
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Object.properties not found at " + propertyFilePath);
		}		
	}
	public String getLocator(String locatorName){
		String locator = properties.getProperty(locatorName);
		if(locator!= null) return locator;
		else throw new RuntimeException("Locator:"+ locatorName +" not specified in the Object.properties file.");		
	}
}
