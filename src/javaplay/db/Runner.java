package javaplay.db;

import java.sql.Connection;

public class Runner {

	public static void main(String[] args) {
		PostgreSQLJDBC db = new PostgreSQLJDBC(PostgreSQLJDBC.DB_DEFAULT_HOST, PostgreSQLJDBC.DB_DEFAULT_PORT,
				PostgreSQLJDBC.DB_DEFAULT_NAME, PostgreSQLJDBC.DB_DEFAULT_USER, PostgreSQLJDBC.DB_DEFAULT_PASSWORD);
		
		db.openDBConnection();
		System.out.println("Opened database successfully");
		
		db.createTableUsers();
		db.closeDBConnection();
		System.out.println("Closed database successfully");
	}

}
