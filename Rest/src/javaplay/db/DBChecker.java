package javaplay.db;

public class DBChecker {

	public static void main(String[] args) throws Exception {
		DBHandler db = new DBHandler(DBHandler.DB_DEFAULT_HOST, DBHandler.DB_DEFAULT_PORT,
				DBHandler.DB_DEFAULT_NAME, DBHandler.DB_DEFAULT_USER, DBHandler.DB_DEFAULT_PASSWORD);
		
		db.exceute("Select * from users");
		db.dropUsers();
		db.createTableUsers();
		db.addLogin(10);

		System.out.println("Done");
	}

}
