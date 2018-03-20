package dmlExport;

import dmlExport.handler.ProcessHandler;
import dmlExport.manager.ConfigManager;

public class DmlExportMain {
	public static void main(String[] args) {
		String configFilePath = "C:\\DevelopTools\\Eclipse\\Workspace\\GIT\\DML-Export\\config\\database.properties";
		ConfigManager.instance().loadProperties(configFilePath);

		ProcessHandler processHandler = new ProcessHandler();
		processHandler.processStart();
		processHandler.prcoessEnd();
	}
}
