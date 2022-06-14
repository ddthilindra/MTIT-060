package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Stock {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/pafproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertStock(String sid, String iname, String qty, String skeepr, String sdate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into Stock(`sid`, `iname`, `qty`, `skeepr`,`sdate`)" + " values ( ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, iname);
			preparedStmt.setString(3, qty);
			preparedStmt.setString(4, skeepr);
			preparedStmt.setString(5, sdate);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Stock.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readStock() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Stock ID</th><th>Item Name</th><th>Quantity</th><th>Stock Keeper</th><th>Date</th></tr>";
			String query = "select * from Stock";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String sid = Integer.toString(rs.getInt("sid"));
				String iname = rs.getString("iname");
				String qty = rs.getString("qty");
				String skeepr = rs.getString("skeepr");
				String sdate = rs.getString("sdate");

				output += "<tr><td>" + sid + "</td>";
				output += "<td>" + iname + "</td>";
				output += "<td>" + qty + "</td>";
				output += "<td>" + skeepr + "</td>";
				output += "<td>" + sdate + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Stock.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateStock(String sid, String iname, String qty, String skeepr, String sdate) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE Stock SET iname=?,qty=?,skeepr=?,sdate=?" + "WHERE sid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, iname);
			preparedStmt.setString(2, qty);
			preparedStmt.setString(3, skeepr);
			preparedStmt.setString(4, sdate);
			preparedStmt.setInt(5, Integer.parseInt(sid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Stock.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteStock(String sid) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from Stock where sid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(sid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Stock.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
