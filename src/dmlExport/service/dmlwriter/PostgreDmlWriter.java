package dmlExport.service.dmlwriter;

import java.util.List;
import java.util.Map;

import dmlExport.constants.IConstants;
import dmlExport.domain.Column;

public class PostgreDmlWriter extends ADmlWriter {

	@Override
	public String make(Map<String, String> row, List<Column> columnList,
			String insertSqlFormat) {
		StringBuffer insertSql = new StringBuffer();
		insertSql.append(insertSqlFormat + " values (");
		for (int i = 0; i < columnList.size(); i++) {

			Column col = columnList.get(i);
			String text = row.get(col.getName().toLowerCase());
			if (i != 0) {
				insertSql.append(", ");
			}
			if (col.getType().equals(IConstants.POSTGRESQL_COLUMN_TYPE.VARCHAR)) {
				if (text != null) {
					insertSql.append("'");
					text = text.replaceAll("'", "''");
					insertSql.append(text);
					insertSql.append("'");
				} else {
					insertSql.append("null");
				}
			} else if (col.getType().equals(
					IConstants.POSTGRESQL_COLUMN_TYPE.NUMERIC)
					|| col.getType().startsWith(
							IConstants.POSTGRESQL_COLUMN_TYPE.INT)) {
				Integer number = null;
				if (text != null) {
					number = Integer.parseInt(text);
				}
				insertSql.append(number);
			}
		}
		insertSql.append(");");
		return insertSql.toString();
	}

}
