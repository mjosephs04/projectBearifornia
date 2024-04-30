import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class databaseTest {

	private static final String DB_CONNECTION = "jdbc:derby:beariforniaDB;";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public static void main(String[] argv) {
		try {
			createDbTables();
		} catch (SQLException e) {
			System.out.println("Couldn't create tables");
		}
	}

	private static void createDbTables() throws SQLException {
		Connection conn = null;
		Statement statement = null;
		String createUserTable = "CREATE TABLE USERS(" +
				"ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "USERNAME VARCHAR(255) NOT NULL, " +
				" PASSWORD VARCHAR(255), " +
				"USERTYPE VARCHAR(255) NOT NULL)";

		String createRoomTable = "CREATE TABLE ROOMS(" +
				"ROOMNUMBER INT PRIMARY KEY, "
				+ "COST DECIMAL(10, 2), " +
				"ROOMTYPE VARCHAR(255), " +
				"NUMBEDS INT, " +
				"QUALITYLEVEL VARCHAR(255)," +
				"BEDTYPE VARCHAR(255), " +
				"SMOKING BOOLEAN)";

		String createReservationTable = "CREATE TABLE RESERVATIONS(" +
				"ROOMNUMBER INT PRIMARY KEY, "
				+ "STARTDATE DATE, " +
				"ENDDATE DATE, " +
				"USERNAME VARCHAR(255)," +
				"FOREIGN KEY (roomNumber) REFERENCES rooms(roomNumber))";


//		String addUser = "INSERT INTO USERS(MARKJ, PASS123) VALUES (USERNAME, PASSWORD)";
		try {
			conn = getDBConnection();
			statement = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Couldn't connect to database");
		}

		try {
			if (!tableExists(conn, "rooms")) {
				statement.execute(createRoomTable);
			} else {
				statement.execute("DELETE FROM rooms");
			}
			if (!tableExists(conn, "reservations")) {
				statement.execute(createReservationTable);
			} else {
				statement.execute("DELETE FROM reservations");
			}
			if (!tableExists(conn, "users")) {
				statement.execute(createUserTable);
			} else {
				statement.execute("DELETE FROM users");
			}
			save("cate", "hi");
			save("test", "sad");
			save("im", "Stressed");
		}catch(SQLException e) {
			System.out.println("Couldn't create/modify tables");
		}

		System.out.println(findAll());

		if (statement != null) {
			statement.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't connect to database");
		}
		return dbConnection;
	}

	public static void save(String user, String pass) throws SQLException {
		String insertSQL = "INSERT INTO USERS (username, password) VALUES (?, ?)";
		try (PreparedStatement statement = getDBConnection().prepareStatement(insertSQL)) {
			statement.setString(1, user);
			statement.setString(2, pass);
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

	private static boolean tableExists(Connection conn, String tableName) throws SQLException {
		// Check if the table exists in the database
		try (var resultSet = conn.getMetaData().getTables(null, null, tableName,null)) {
			return ! resultSet.next();
		}
	}

}
