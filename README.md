jdbc-osgi
=========

Demo of using OSGi registry with a DataSourceFactory service instead of JNDI to to lookup DataSource.

The OSGi Enterprise Specification defines a JDBC DataSourceFactory Service.  Several things are
needed to set up and use this service.

1. The stock PostgreSQL JDBC jar must be wrapped as an OSGi bundle.
2. Implement a PostgreSQL specific version of the *DataSourceFactory* interface.  This interface
   is defined by the OSGi Enterprise Specification.
3. Register the DataSourceFactory implemention with the OSGi registry, specifying 
   *OSGI_JDBC_DRIVER_CLASS* and *OSGI_JDBC_DRIVER_NAME* registry keys.  
4. Client will look up the DataSourceFactory using these keys.
5. Client obtains a DataSource from the DataSourceFactory, providing various configuration properties,
   such as the name of the database and server address.
6. Finally a Connection can be obtained from the DataSource in the usual manner.

The *lydianblues/jdbc-osgi-runner* project contains the wrapped PostgreSQL JDBC driver.  The
GitHub project *ops4j/org.ops4j.pax.jdbc* contains a bundle that implements steps (2) and (3).
Finally, client code (in the present bundle) implements steps (4), (5), and (6).  

See the **lydianblues/jdbc-osgi-runner* project for a runnable demo that uses the *lydianblues/osgi-launcher*
launcher.

