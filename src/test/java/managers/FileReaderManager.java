package managers;
 


import dataProviders.ConfigFileReader;
import dataProviders.ObjectFileReader;
 
public class FileReaderManager {
 
	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static ConfigFileReader configFileReader;
	private static ObjectFileReader objectFileReader;
	private FileReaderManager() {
	}
 
	 public static FileReaderManager getInstance( ) {
	      return fileReaderManager;
	 }
 
	 public ConfigFileReader getConfigReader() {
		 //forcing single object
		 if(configFileReader == null)
			 return  new ConfigFileReader();
		 else
			 return configFileReader;
	 }
	 public ObjectFileReader getObjectReader() {
		 //forcing single object
		 if(configFileReader == null)
			 return  new ObjectFileReader();
		 else
			 return objectFileReader;
		
	}
}