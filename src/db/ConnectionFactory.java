package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	public DataSource dataSource;

	public ConnectionFactory() {
		Properties props = loadProperties();

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl(props.getProperty("dburl"));
		cpds.setUser(props.getProperty("user"));
		cpds.setPassword(props.getProperty("password"));

		this.dataSource = cpds;
	}

	public Connection getConnection() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException  e) {
			throw new DbException(e.getMessage());
		}
	}

	private Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
}
