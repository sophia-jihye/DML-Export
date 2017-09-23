# DML-Export
It generates 'insert' DML from database, and now only Oracle and PostgreSQL are allowed.
If you want to add another dbms, you have to modify followed source file: ProcessHandler.java, DmlWriterFactory.java

* Steps
1. You need to create a file and fill out the configuration information in it. You can refer to config/database.properties file in this project. 
   In the configuration file, 6 information are required as below:
	1) driverClassName : Information of DBMS which you want to export from. (Ex. oracle.jdbc.driver.OracleDriver)
	2) url : Information of DBMS which you want to export from. (Ex. jdbc:oracle:thin:@192.168.102.101:1521:orcl)
	3) username : Information of DBMS which you want to export from. (Ex. scott)
	4) password : Information of DBMS which you want to export from. (Ex. tiger)
	5) result-file-path : Full path of DML result file. (Ex. C:\\result.txt)
	6) table : Information of tables which you want to export from. (Ex. BOOK, USER)
2. You need to declare the configuration file path at 'configFilePath' in DmlExportMain.
3. You check the result of DML in the result-file as you declared in the configuration file.