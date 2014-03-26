package com.thirdmode.jdbc;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Configuration {
	public static final String CONFIG_PROPERTIES_FILE_VALUE =
		"jdbc.properties";
	public static final String CONFIG_DIRECTORY = "conf";
	
	public Properties getConfiguration() {
		File confDir = null;
		URL propURL = null;
		
		// Look in the current directory for a subdirectory called "conf". This
		// should be enhanced so that the location of the config file is specified
		// by a System property.
		confDir = new File(System.getProperty("user.dir"), CONFIG_DIRECTORY);

		try {
            propURL = 
            	new File(confDir, CONFIG_PROPERTIES_FILE_VALUE).toURI().toURL();
        }
        catch (MalformedURLException ex) {
            // Log this problem
            return null;
        }
		
		// Read the properties file.
        Properties props = new Properties();
        try (InputStream is = propURL.openConnection().getInputStream()) {
            props.load(is);
        }
        catch (Exception ex) {
        	// Log this problem
        	return null;
        }
        return props;
	}

}
