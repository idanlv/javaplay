package javaplay.db;

public class Runner {

	public static void main(String[] args) {
		DBHandler db = new DBHandler(DBHandler.DB_DEFAULT_HOST, DBHandler.DB_DEFAULT_PORT,
				DBHandler.DB_DEFAULT_NAME, DBHandler.DB_DEFAULT_USER, DBHandler.DB_DEFAULT_PASSWORD);
		
		db.openDBConnection();
		System.out.println("Opened database successfully");
		
		// run only once
		//db.createTableUsers();
		
		db.closeDBConnection();
		System.out.println("Closed database successfully");
	}

}
