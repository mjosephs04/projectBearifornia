package springboot;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class databaseSetup {

	private static final String DB_CONNECTION = "jdbc:derby:beariforniaDB;";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public static void initialize(){
		try {
			Connection conn = getDBConnection();
			initDbTables(conn);
		} catch (SQLException e) {
			System.out.println("Couldn't create tables");
		}
	}

	public static void initDbTables(Connection conn) throws SQLException {
		Statement statement = null;
		String createUserTable = "CREATE TABLE USERS(" +
				"ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "USERNAME VARCHAR(255) NOT NULL, " +
				"PASSWORD VARCHAR(255), " +
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


		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Couldn't connect to database");
		}

		//initialize rooms, reservations, and users tables
		// it first checks if the tables already exist, and if so, they just
		// clear all the existing values in the table
		// if the table does not already exist, it creates a new one

		//after this try catch block, there will be 3 empty tables:
		// ROOMS, RESERVATIONS, and USERS
		try {
			if (tableExists(conn, "ROOMS")){
				statement.executeUpdate("DELETE FROM ROOMS");
			}
			else {
				statement.execute(createRoomTable);
			}

			if (tableExists(conn, "RESERVATIONS")) {
				statement.executeUpdate("DELETE FROM RESERVATIONS");
			}
			else {
				statement.execute(createReservationTable);
			}

			if (tableExists(conn, "USERS")){
				statement.executeUpdate("DELETE FROM USERS");
			}
			else {
				statement.execute(createUserTable);
			}
		}catch(SQLException e) {
			System.out.println("Couldn't create/modify tables");
		}

		//close relevant variables
		if (statement != null) {
			statement.close();
		}
	}

	public static Connection getDBConnection() {
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

	public static List<String> findAll(Connection conn) throws SQLException {
		List<String> entries = new ArrayList<>();
		String selectSQL = "SELECT * FROM USERS";
		try (PreparedStatement statement = conn.prepareStatement(selectSQL)) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				//resultSet.getString("id");

				entries.add(resultSet.getString("username"));
				entries.add(resultSet.getString("password"));
			}
		}
		return entries;
	}

	private static boolean tableExists(Connection conn, String tableName) throws SQLException {
		// Check if the table exists in the database
		try (var resultSet = conn.getMetaData().getTables(null, null, tableName,null)) {
			return resultSet.next();
		}
	}

}
