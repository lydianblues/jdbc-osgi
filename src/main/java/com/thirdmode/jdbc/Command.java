package com.thirdmode.jdbc;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.BundleContext;

public class Command {
	BundleContext context;

    public Command(BundleContext context) {
        this.context = context;
    }
    
    @Descriptor("Show the Status of the DB Server")
    public String status(@Descriptor("the verbosity level") Integer level) {
        return "Just doing fine at level " + level.toString();
    }
    
    @Descriptor("Show the Configuration")
    public String configuration() {
    	Configuration cnf = new Configuration();
    	Properties props = cnf.getConfiguration();
    	
        return props.toString();
    }
    
    @Descriptor("Show the Version of the DB Server")
    public String version() {
    	String result;
    	try {
    		ConnectionHandler ch = new ConnectionHandler(context);
    		Properties jdbcProps = new Configuration().getConfiguration();
    		ch.connect(jdbcProps.getProperty("com.thirdmode.jdbc.user"),
    			jdbcProps.getProperty("com.thirdmode.jdbc.password"));
    		Version version = new Version(ch);
    		result = version.getStatus();
    		ch.close();
    	} catch (SQLException ex) {
    		result = ex.getMessage();
    	}
    	return result;
    }
}
