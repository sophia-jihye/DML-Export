package dmlExport.constants;

public interface IConstants {

	interface CONFIG_PROPERTY {
		String DRIVER_CLASS_NAME = "driverClassName";
		String URL = "url";
		String USERNAME = "username";
		String PASSWORD = "password";
		String RESULT_FILE_PATH = "result-file-path";
		String TABLE = "table";
		String TABLE_DELIMITER = ",";
	}

	interface ORACLE_COLUMN_TYPE {
		String VARCHAR = "VARCHAR";
		String VARCHAR2 = "VARCHAR2";
		String NUMBER = "NUMBER";
	}

	interface POSTGRESQL_COLUMN_TYPE {
		String VARCHAR = "VARCHAR";
		String NUMERIC = "NUMERIC";
		String INT = "INT";
	}

	interface DBMS {
		String ORACLE = "oracle";
		String POSTGRESQL = "postgresql";
		String TIBERO = "tibero";
	}
}
