package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserSelection {
	// Database Connection 
	public Connection connect() {
		Connection con = null; 
		try {			
			Class.forName("com.mysql.jdbc.Driver"); 
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget", "root", ""); 
			//For testing
			System.out.print("Successfully connected"); 
		} catch(Exception e) { 
			e.printStackTrace(); 
		}
		return con;
	}
	
	// User Selection & Read
	public String userSelect(int id) {
		String output = "";
		try {
			Connection con = connect(); 
			if (con == null) { 
				return "Error while connecting to the database for reading."; 
			} else {
				// Prepare the table to be displayed
				output = "<table border='3'><tr><th>User ID</th>" 
						+"<th>First Name</th><th>Last Name</th>"
						+ "<th>Date of Birth</th><th>Address</th><th>Telephone No</th><th>Username</th>" 
						+ "<th>Password</th><th>email</th><th>User Type</th></tr>"; 
				
				String query = "select * from user where id="+id;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set
				while (rs.next()) { 
					String userID = Integer.toString(rs.getInt("id")); 
					String fName = rs.getString("first_name"); 
					String lName = rs.getString("last_name"); 
					String dob = rs.getString("date_of_birth"); 
					String address = rs.getString("address"); 
					String tNo = rs.getString("telephone_no");
					String uNmae = rs.getString("username");
					String pwd = rs.getString("password");
					String email = rs.getString("email");
					String uType = rs.getString("user_type");
					
					// Add a row into the table
					output += "<tr><td>" + userID + "</td>"; 
					output += "<td>" + fName + "</td>"; 
					output += "<td>" + lName + "</td>";
					output += "<td>" + dob + "</td>";
					output += "<td>" + address + "</td>";
					output += "<td>" + tNo + "</td>";
					output += "<td>" + uNmae + "</td>";
					output += "<td>" + pwd + "</td>";
					output += "<td>" + email + "</td>";
					output += "<td>" + uType + "</td>";	
				} 
				rs.close();
				con.close();
				output += "</table>";
			}
		} catch (Exception e) {
			output = "Error while select the user."; 
			System.err.println(e.getMessage());
		}
		return output;
	}
}
