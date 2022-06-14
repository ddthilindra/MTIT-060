package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Delivery {

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

	public String insertDelivery(String did, String cname, String date, String Dest, String etime) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into Delivery(`did`, `cname`, `date`, `Dest`,`etime`)" + " values ( ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, cname);
			preparedStmt.setString(3, date);
			preparedStmt.setString(4, Dest);
			preparedStmt.setString(5, etime);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Delivery.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readDelivery() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Delivery ID</th><th>Cuctomer Name</th><th> Date </th><th>Destination</th><th>Estimated Time</th></tr>";
			String query = "select * from Delivery";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String did = Integer.toString(rs.getInt("did"));
				String cname = rs.getString("cname");
				String date = rs.getString("date");
				String Dest = rs.getString("Dest");
				String etime = rs.getString("etime");

				output += "<tr><td>" + did + "</td>";
				output += "<td>" + cname + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + Dest + "</td>";
				output += "<td>" + etime + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Delivery.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateDelivery(String did, String cname, String date, String Dest, String etime) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE Delivery SET cname=?,date=?,Dest=?,etime=?" + "WHERE did=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, cname);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, Dest);
			preparedStmt.setString(4, etime);
			preparedStmt.setInt(5, Integer.parseInt(did));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Delivery.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteDelivery(String did) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from Delivery where did=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(did));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Delivery.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
