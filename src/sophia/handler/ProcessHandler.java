package sophia.handler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import sophia.constants.IConstants;
import sophia.domain.Column;
import sophia.manager.ConfigManager;
import sophia.manager.DbConnectionManager;
import sophia.service.DmlWriter;
import sophia.service.MetaDataReader;
import sophia.service.QueryProcessor;
import sophia.service.SqlFormatter;

public class ProcessHandler {

	private MetaDataReader meataDataReader;
	private SqlFormatter sqlFormatter;
	private QueryProcessor queryProcessor;
	private DmlWriter dmlWriter;

	public ProcessHandler() {
	}

	public void processStart() {
		System.out.println("==========START==========");
		
		Connection conn = DbConnectionManager.instance().getConnection();
		meataDataReader = new MetaDataReader(conn);
		sqlFormatter = new SqlFormatter();
		queryProcessor = new QueryProcessor(conn);
		dmlWriter = new DmlWriter();

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

	public void prcoessEnd() {
		System.out.println("==========END==========");
	}

}
