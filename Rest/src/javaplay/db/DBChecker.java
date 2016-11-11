package javaplay.db;

public class DBChecker {

	public static void main(String[] args) throws Exception {
		DatabaseAccess db = new DatabaseAccess(DatabaseAccess.DB_DEFAULT_HOST, DatabaseAccess.DB_DEFAULT_PORT,
				DatabaseAccess.DB_DEFAULT_NAME, DatabaseAccess.DB_DEFAULT_USER, DatabaseAccess.DB_DEFAULT_PASSWORD);
		
		db.exceute("Select * from users");
		db.dropUsers();
		db.createTableUsers();
		db.addLogin(10);

		System.out.println("Done");
	}

}
