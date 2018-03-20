package dmlExport.service;

import java.util.List;

import dmlExport.domain.Column;

public class SqlFormatter {

	public String makeInsertSqlFormat(String tableName, List<Column> columnList) {
		StringBuffer insertSqlFormat = new StringBuffer();
		insertSqlFormat.append("insert into " + tableName + " (");
		for (int i = 0; i < columnList.size(); i++) {
			if (i != 0) {
				insertSqlFormat.append(", ");
			}
			insertSqlFormat.append(columnList.get(i).getName());
		}
		insertSqlFormat.append(") ");

		return insertSqlFormat.toString();
	}

	public String makeSelectSqlFormat(String tableName, List<Column> columnList) {
		StringBuffer selectSqlFormat = new StringBuffer();
		selectSqlFormat.append("select ");
		for (int i = 0; i < columnList.size(); i++) {
			if (i != 0) {
				selectSqlFormat.append(", ");
			}
			selectSqlFormat.append(columnList.get(i).getName());
		}
		selectSqlFormat.append(" from " + tableName);

		return selectSqlFormat.toString();
	}

}
