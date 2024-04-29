import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class databaseTest {

  private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String DB_CONNECTION = "jdbc:derby:beariforniaDB;";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public static void main(String[] argv) {
		try {
			createDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void createDbUserTable() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		String createTableSQL = "CREATE TABLE USERS(" + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "USERNAME VARCHAR(20) NOT NULL, " +
				" PASSWORD VARCHAR(20) " + ")";

//		String addUser = "INSERT INTO USERS(MARKJ, PASS123) VALUES (USERNAME, PASSWORD)";
		String deleteTable = "DROP TABLE USERS";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			String[] names = {"TABLE"};
			dbConnection.getMetaData().getTables(null, null, null, names);
//			if(names.length != 1){
//				statement.execute(createTableSQL);
//			}else{
//				String removeCommand = "DROP TABLE USERS";
//				statement.executeUpdate(removeCommand);
//				statement.execute(createTableSQL);
//			}
//			statement.execute(deleteTable);
//			statement.execute(createTableSQL);
//			System.out.println(createTableSQL);
			// execute the SQL stetement
//			statement.execute(createTableSQL);
			save();
			System.out.println(findAll());
			System.out.println("Table \"dbuser\" is deleted!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	public static void save() throws SQLException {
		String insertSQL = "INSERT INTO USERS (username, password) VALUES (?, ?)";
		try (PreparedStatement statement = getDBConnection().prepareStatement(insertSQL)) {
			statement.setString(1, "BEAR");
			statement.setString(2, "BAYLO)R");
			statement.executeUpdate();
		}
	}

	public static List<String> findAll() throws SQLException {
		List<String> entries = new ArrayList<>();
		String selectSQL = "SELECT * FROM USERS";
		try (PreparedStatement statement = getDBConnection().prepareStatement(selectSQL)) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				resultSet.getString("id");

				entries.add(resultSet.getString("username"));
				entries.add(resultSet.getString("password"));
			}
		}
		return entries;
	}

}
