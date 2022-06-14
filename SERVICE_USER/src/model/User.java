package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertUser(String uName, String uAddress, String pName, String uDate, String uEmail, String pNo) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into userservice(`uID`,`uName`,`uAddress`,`pName`,`uDate`,`uEmail`,`pNo`)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, uName);
			preparedStmt.setString(3, uAddress);
			preparedStmt.setString(4, pName);
			preparedStmt.setString(5, uDate);
			preparedStmt.setString(6, uEmail);
			preparedStmt.setString(7, pNo);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readUser() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the  table to be displayed
			output = "<table border=\"1\"><tr><th>User Name</th><th>Address</th><th>Product Name</th><th>Date</th><th>Email</th><th>Phone No</th></tr>";
			String query = "select * from userservice";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String uID = Integer.toString(rs.getInt("uID"));
				String uName = rs.getString("uName");
				String uAddress = rs.getString("uAddress");
				String pName = rs.getString("pName");
				String uDate = rs.getString("uDate");
				String uEmail = rs.getString("uEmail");
				String pNo = rs.getString("pNo");

				// Add into the  table
				output += "<tr><td>" + uName + "</td>";
				output += "<td>" + uAddress + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + uDate + "</td>";
				output += "<td>" + uEmail + "</td>";
				output += "<td>" + pNo + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateUser(String ID, String uName, String uAddress, String pName, String uDate, String uEmail, String pNo) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE userservice SET uName=?,uAddress=?,pName=?,uDate=?,uEmail=?,pNo=?" + "WHERE uID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, uName);
			preparedStmt.setString(2, uAddress);
			preparedStmt.setString(3, pName);
			preparedStmt.setString(4, uDate);
			preparedStmt.setString(5, uEmail);
			preparedStmt.setString(6, pNo);
			preparedStmt.setInt(7, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the user.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteUser(String uID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from userservice where uID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(uID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the user.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
