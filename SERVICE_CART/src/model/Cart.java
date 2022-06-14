package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Cart {

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

	public String insertCart(String rid, String rname, String pname, String rDesp, String rdate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into Cart(`rid`, `rname`, `pname`, `rDesp`,`rdate`)" + " values ( ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, rname);
			preparedStmt.setString(3, pname);
			preparedStmt.setString(4, rDesp);
			preparedStmt.setString(5, rdate);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Cart.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readCart() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Cart ID</th><th>Customer Name</th><th>Item Name</th><th>Description</th><th>Date</th></tr>";
			String query = "select * from Cart";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String rid = Integer.toString(rs.getInt("rid"));
				String rname = rs.getString("rname");
				String pname = rs.getString("pname");
				String rDesp = rs.getString("rDesp");
				String rdate = rs.getString("rdate");

				output += "<tr><td>" + rid + "</td>";
				output += "<td>" + rname + "</td>";
				output += "<td>" + pname + "</td>";
				output += "<td>" + rDesp + "</td>";
				output += "<td>" + rdate + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Cart.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateCart(String rid, String rname, String pname, String rDesp, String rdate) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE Cart SET rname=?,pname=?,rDesp=?,rdate=?" + "WHERE rid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, rname);
			preparedStmt.setString(2, pname);
			preparedStmt.setString(3, rDesp);
			preparedStmt.setString(4, rdate);
			preparedStmt.setInt(5, Integer.parseInt(rid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Cart.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteCart(String rid) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from Cart where rid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(rid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Cart.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
