package dmlExport.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmlExport.manager.DbConnectionManager;

public class QueryProcessor {
	private Connection conn;
	private PreparedStatement ps;

	public QueryProcessor(Connection conn) {
		this.conn = conn;
	}

	public List<Map<String, String>> select(String selectSqlFormat) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(selectSqlFormat);
			rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return convertRsToList(rs);
	}

	private List<Map<String, String>> convertRsToList(ResultSet rs) {

		List<Map<String, String>> rowList = null;

		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int sizeOfColumn = metaData.getColumnCount();

			rowList = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			String column = null;

			while (rs.next()) {

				map = new HashMap<String, String>();
				for (int i = 1; i <= sizeOfColumn; i++) {
					column = metaData.getColumnName(i);
					map.put(column, rs.getString(column));
				}
				rowList.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnectionManager.instance().tryClose(rs, ps);
		}
		return rowList;

	}

}