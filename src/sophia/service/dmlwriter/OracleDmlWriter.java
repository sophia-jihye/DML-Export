package sophia.service.dmlwriter;

import java.util.List;
import java.util.Map;

import sophia.constants.IConstants;
import sophia.domain.Column;

public class OracleDmlWriter extends ADmlWriter {

	@Override
	public String make(Map<String, String> row, List<Column> columnList,
			String insertSqlFormat) {
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

}
