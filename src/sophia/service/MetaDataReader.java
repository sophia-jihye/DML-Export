package sophia.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sophia.constants.IConstants;
import sophia.domain.Column;

public class MetaDataReader {
	private Connection conn;
	private String dbms;

	public MetaDataReader(Connection conn, String dbms) {
		this.conn = conn;
		this.dbms = dbms;
	}

	public List<Column> getTableColumnList(String tableName) {
		List<Column> columnList = new ArrayList<Column>();

		try {
			DatabaseMetaData metaData = conn.getMetaData();

			switch (dbms) {
			case IConstants.DBMS.ORACLE:
				tableName = tableName.toUpperCase();
				break;
			case IConstants.DBMS.POSTGRESQL:
				tableName = tableName.toLowerCase();
				break;
			}

			ResultSet columnsResultSet = metaData.getColumns(null, null,
					tableName, null);

			while (columnsResultSet.next()) {
				String name = columnsResultSet.getString("COLUMN_NAME")
						.toUpperCase();
				String type = columnsResultSet.getString("TYPE_NAME")
						.toUpperCase();
				String size = columnsResultSet.getString("COLUMN_SIZE");
				String nullable = columnsResultSet.getString("NULLABLE");
				String remarks = columnsResultSet.getString("REMARKS");

				Column column = new Column();
				column.setName(name);
				column.setType(type);
				column.setSize(Integer.valueOf(size));
				if (type.contains("TIMESTAMP")) {
					column.setType("TIMESTAMP");
					if (type.contains("(") && type.contains(")")) {
						column.setSize(Integer.valueOf(type.substring(
								type.indexOf("(") + 1, type.indexOf(")"))));
					}
				} else if (type.equals("DATE")) {
					column.setSize(0);
				}
				column.setNullable(Integer.valueOf(nullable));
				column.setComment(remarks);

				columnList.add(column);
			}
		} catch (SQLException e) {
			System.out
					.println("Error occurred while getting metadata from database");
		}

		return columnList;
	}

}
