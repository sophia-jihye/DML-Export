package dmlExport.service.dmlwriter;

import dmlExport.constants.IConstants;

public class DmlWriterFactory {

	public static ADmlWriter getDmlWRiter(String dbms) {
		switch (dbms) {
		case IConstants.DBMS.ORACLE:
			return new OracleDmlWriter();
		case IConstants.DBMS.POSTGRESQL:
			return new PostgreDmlWriter();
		}
		return null;
	}

}
