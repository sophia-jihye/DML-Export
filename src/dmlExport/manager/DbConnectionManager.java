package dmlExport.manager;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import dmlExport.constants.IConstants;

public class DbConnectionManager {

	private static final DbConnectionManager instance = new DbConnectionManager();

	private DbConnectionManager() {
	}

	public static DbConnectionManager instance() {
		return instance;
	}

	private Connection conn;

	public synchronized Connection getConnection() {
		if (conn == null) {
			Properties properties = ConfigManager.instance().getProperties();

			String driverClassName = properties
					.getProperty(IConstants.CONFIG_PROPERTY.DRIVER_CLASS_NAME);
			String url = properties.getProperty(IConstants.CONFIG_PROPERTY.URL);
			String username = properties
					.getProperty(IConstants.CONFIG_PROPERTY.USERNAME);
			String password = properties
					.getProperty(IConstants.CONFIG_PROPERTY.PASSWORD);

			try {
				Class.forName(driverClassName);
				conn = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public void tryClose(Object... objs) {
		boolean hasConnection = false;
		for (Object obj : objs) {
			try {
				if (obj instanceof Closeable) {
					((Closeable) obj).close();
				} else if (obj instanceof PreparedStatement) {
					((PreparedStatement) obj).close();
				} else if (obj instanceof ResultSet) {
					((ResultSet) obj).close();
				} else if (obj instanceof Connection) {
					hasConnection = true;
				}
			} catch (Exception e) {
				System.out.println("Error occurred during closing");
			}
		}

		if (hasConnection) {
			close();
		}
	}

	private synchronized void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Error occurred during closing");
			}
		}
	}

}
