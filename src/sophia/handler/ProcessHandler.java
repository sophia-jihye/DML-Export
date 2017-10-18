package sophia.handler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import sophia.constants.IConstants;
import sophia.domain.Column;
import sophia.manager.ConfigManager;
import sophia.manager.DbConnectionManager;
import sophia.service.MetaDataReader;
import sophia.service.QueryProcessor;
import sophia.service.SqlFormatter;
import sophia.service.dmlwriter.ADmlWriter;
import sophia.service.dmlwriter.DmlWriterFactory;

public class ProcessHandler {

	private String dbms;
	private MetaDataReader meataDataReader;
	private SqlFormatter sqlFormatter;
	private QueryProcessor queryProcessor;
	private ADmlWriter dmlWriter;

	public ProcessHandler() {
	}

	public void processStart() {
		System.out.println("==========START==========");
		configureDbms();

		Connection conn = DbConnectionManager.instance().getConnection();
		meataDataReader = new MetaDataReader(conn, dbms);
		sqlFormatter = new SqlFormatter();
		queryProcessor = new QueryProcessor(conn);
		dmlWriter = DmlWriterFactory.getDmlWRiter(dbms);

		String[] tables = ConfigManager.instance().getProperties()
				.getProperty(IConstants.CONFIG_PROPERTY.TABLE)
				.split(IConstants.CONFIG_PROPERTY.TABLE_DELIMITER);
		ResultSet rs = null;
		for (int i = 0; i < tables.length; i++) {

			String tableName = tables[i];

			// 1. metadata가져오기
			List<Column> columnList = meataDataReader
					.getTableColumnList(tableName);

			// 2. selectSql 포맷 만들기
			String selectSqlFormat = sqlFormatter.makeSelectSqlFormat(
					tableName, columnList);

			// 3. Select 하기
			List<Map<String, String>> rowList = queryProcessor
					.select(selectSqlFormat);

			// 4. insertSql 포맷 만들기
			String insertSqlFormat = sqlFormatter.makeInsertSqlFormat(
					tableName, columnList);

			dmlWriter.writeDate();

			// 5. insertSql Sql 만들어서 파일에 입력하기
			dmlWriter.write(rowList, tableName, columnList, insertSqlFormat);

		}

		DbConnectionManager.instance().tryClose(rs, conn);
	}

	private void configureDbms() {
		String driverClassName = ConfigManager.instance().getProperties()
				.getProperty(IConstants.CONFIG_PROPERTY.DRIVER_CLASS_NAME);
		if (driverClassName.indexOf(IConstants.DBMS.ORACLE) != -1) {
			dbms = IConstants.DBMS.ORACLE;
		} else if (driverClassName.indexOf(IConstants.DBMS.TIBERO) != -1) {

			// Detailed process of TIBERO is almost same with ORACLE.
			dbms = IConstants.DBMS.ORACLE;
		} else if (driverClassName.indexOf(IConstants.DBMS.POSTGRESQL) != -1) {
			dbms = IConstants.DBMS.POSTGRESQL;
		}

	}

	public void prcoessEnd() {
		System.out.println("==========END==========");
	}

}
