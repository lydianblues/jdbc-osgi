package com.thirdmode.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Version {
	ConnectionHandler connHandler;
	
	Version(ConnectionHandler ch) {
		this.connHandler = ch;
	}
	
	public String getStatus() {		
        String result = null;
        Connection conn = connHandler.getConnection();
        if (conn != null) {
	        try (Statement st = conn.createStatement();
	        	ResultSet rs = st.executeQuery("SELECT VERSION()")) {
	            if (rs.next()) {
	                result = rs.getString(1);
	            }
	        } catch (SQLException ex) {
	        	result = ex.getMessage();
	        }
        }
        return result;
    }
}
