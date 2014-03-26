package com.thirdmode.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.jdbc.DataSourceFactory;
import org.osgi.util.tracker.ServiceTracker;

// Issues (a) closing the connection to DB, (b) closing the ServiceTracker
// Would be better if "connect" returns the Connection.  Error messages can
// be wrapped in an Exception object (to be printed in the shell).
public class ConnectionHandler implements AutoCloseable {
	
	BundleContext context;
	ServiceTracker<DataSourceFactory, DataSourceFactory> dsfTracker;
	Connection conn = null;
	
	public ConnectionHandler(BundleContext context) {
		this.context = context;
	}

	public Connection connect(String user, String password) throws SQLException {
		
		if (conn != null) {
			return conn;
		}
		Properties jdbcProps = new Configuration().getConfiguration();
		
		final String driverClassKey = DataSourceFactory.OSGI_JDBC_DRIVER_CLASS;
		final String driverClassValue =
				jdbcProps.getProperty("com.thirdmode.jdbc.driver");
		final String objectClassKey = org.osgi.framework.Constants.OBJECTCLASS;
		final String objectClassValue = DataSourceFactory.class.getName();
		Filter filter = null;
		
		try {
			filter = context.createFilter(
				"(&(" +  objectClassKey  + "=" + objectClassValue + ")(" +
					driverClassKey + "=" + driverClassValue + "))"
			);
		} catch (InvalidSyntaxException e) {
			// log e.getMessage();
			return null;
		}
		
		dsfTracker = new ServiceTracker<DataSourceFactory, DataSourceFactory>(context, filter, null);
		dsfTracker.open();
		DataSourceFactory dsf = (DataSourceFactory) dsfTracker.getService();
		
		try {
			if (dsf != null) {
				Properties props = new Properties();
				props.setProperty(DataSourceFactory.JDBC_SERVER_NAME,
					jdbcProps.getProperty("com.thirdmode.jdbc.host"));
				props.setProperty(DataSourceFactory.JDBC_DATABASE_NAME,
					jdbcProps.getProperty("com.thirdmode.jdbc.database"));
				DataSource ds = dsf.createDataSource(props);
				conn = ds.getConnection(user, password);
			}
		} finally {
			dsfTracker.close();
		}
		return conn;
	}
	public Connection getConnection() {
		return conn;
	}
	
	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
}