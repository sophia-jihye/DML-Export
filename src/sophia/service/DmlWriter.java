package sophia.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import sophia.constants.IConstants;
import sophia.domain.Column;
import sophia.manager.ConfigManager;

public class DmlWriter {

	public void write(List<Map<String, String>> rowList, String tableName,
			List<Column> columnList, String insertSqlFormat) {

		writeIntoFile("--" + tableName);

		try {

			for (int i = 0; i < rowList.size(); i++) {
				String insertSql = make(rowList.get(i), columnList,
						insertSqlFormat);
				writeIntoFile(insertSql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		writeIntoFile("\n");
		System.out.println("Succeeded in writing DML of " + tableName
				+ " into file");
	}

	private String make(Map<String, String> row, List<Column> columnList,
			String insertSqlFormat) throws SQLException {
		StringBuffer insertSql = new StringBuffer();
		insertSql.append(insertSqlFormat + " values (");
		for (int i = 0; i < columnList.size(); i++) {

			Column col = columnList.get(i);
			if (i != 0) {
				insertSql.append(", ");
			}
			if (col.getType().equals(IConstants.ORACLE_COLUMN_TYPE.VARCHAR)
					|| col.getType().equals(
							IConstants.ORACLE_COLUMN_TYPE.VARCHAR2)) {
				String text = row.get(col.getName());
				// String text = rs.getString(col.getName());
				if (text != null) {
					insertSql.append("'");
					text = text.replaceAll("'", "''");
					insertSql.append(text);
					insertSql.append("'");
				} else {
					insertSql.append("null");
				}
			} else if (col.getType().equals(
					IConstants.ORACLE_COLUMN_TYPE.NUMBER)) {
				Integer number = Integer.parseInt(row.get(col.getName()));
				insertSql.append(number);
			}
		}
		insertSql.append(");");
		return insertSql.toString();
	}

	private void writeIntoFile(String text) {

		PrintWriter pw = null;
		try {
			String filePath = ConfigManager.instance().getProperties()
					.getProperty(IConstants.CONFIG_PROPERTY.RESULT_FILE_PATH);
			pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath,
					true)));
			pw.write(text);
			pw.write("\n");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}

	}

	public void writeDate() {
		writeIntoFile("--" + new Date().toString());
	}
}
