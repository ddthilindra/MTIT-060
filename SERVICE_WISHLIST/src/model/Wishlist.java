package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Wishlist {

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

	public String insertWishlist(String wid, String cname, String items, String des, String date) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into Wishlist(`wid`, `cname`, `items`, `des`,`date`)" + " values ( ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, cname);
			preparedStmt.setString(3, items);
			preparedStmt.setString(4, des);
			preparedStmt.setString(5, date);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Wishlist.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readWishlist() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Wishlist ID</th><th>customer Name</th><th>Items</th><th>Description</th><th>Date</th></tr>";
			String query = "select * from Wishlist";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String wid = Integer.toString(rs.getInt("wid"));
				String cname = rs.getString("cname");
				String items = rs.getString("items");
				String des = rs.getString("des");
				String date = rs.getString("date");

				output += "<tr><td>" + wid + "</td>";
				output += "<td>" + cname + "</td>";
				output += "<td>" + items + "</td>";
				output += "<td>" + des + "</td>";
				output += "<td>" + date + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Wishlist.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateWishlist(String wid, String cname, String items, String des, String date) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE Wishlist SET cname=?,items=?,des=?,date=?" + "WHERE wid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, cname);
			preparedStmt.setString(2, items);
			preparedStmt.setString(3, des);
			preparedStmt.setString(4, date);
			preparedStmt.setInt(5, Integer.parseInt(wid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Wishlist.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteWishlist(String wid) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from Wishlist where rid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(wid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Wishlist.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
