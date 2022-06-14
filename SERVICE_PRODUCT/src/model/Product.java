package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Product {

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

	public String insertProduct(String pname, String pdate, String price, String rname) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into productservice(`pId`, `pName`, `pDate`, `pPrice`, `rname`)" + " values ( ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, pname);
			preparedStmt.setString(3, pdate);
			preparedStmt.setString(4, price);
			preparedStmt.setString(5, rname);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Product.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	public String readProduct() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Product ID</th><th>Product Name</th><th>Date</th><th>Price</th><th>Researcher Name</th></tr>";
			String query = "select * from productservice";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String pId = Integer.toString(rs.getInt("pId"));
				String pName = rs.getString("pName");
				String pDate = rs.getString("pDate");
				String pPrice = rs.getString("pPrice");
				String rname = rs.getString("rname");

				output += "<tr><td>" + pId + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + pDate + "</td>";
				output += "<td>" + pPrice + "</td>";
				output += "<td>" + rname + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the product.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	public String updateProduct(String pId, String pname, String pdate, String price, String rname) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE productservice SET pName=?,pDate=?,pPrice=?,rname=? WHERE pId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, pname);
			preparedStmt.setString(2, pdate);
			preparedStmt.setString(3, price);
			preparedStmt.setString(4, rname);
			preparedStmt.setInt(5, Integer.parseInt(pId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Product.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteProduct(String pId) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from productservice where pId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the product.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
