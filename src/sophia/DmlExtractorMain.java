package sophia;

import sophia.handler.ProcessHandler;
import sophia.manager.ConfigManager;

public class DmlExtractorMain {
	public static void main(String[] args) {
		ConfigManager
				.instance()
				.loadProperties(
						"C:\\DevelopTools\\Eclipse\\Workspace\\GIT\\DML-Export\\config\\database.properties");

		ProcessHandler processHandler = new ProcessHandler();
		processHandler.processStart();
		processHandler.prcoessEnd();
	}
}
