package dmlExport.service.dmlwriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dmlExport.constants.IConstants;
import dmlExport.domain.Column;
import dmlExport.manager.ConfigManager;

public abstract class ADmlWriter {

	public abstract String make(Map<String, String> row,
			List<Column> columnList, String insertSqlFormat);

	public void write(List<Map<String, String>> rowList, String tableName,
			List<Column> columnList, String insertSqlFormat) {

		writeIntoFile("--" + tableName);

		for (int i = 0; i < rowList.size(); i++) {
			String insertSql = make(rowList.get(i), columnList, insertSqlFormat);
			writeIntoFile(insertSql);
		}
		writeIntoFile("\n");
		System.out.println("Succeeded in writing DML of " + tableName
				+ " into file");
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
