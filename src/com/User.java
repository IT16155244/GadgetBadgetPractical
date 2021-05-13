package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
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
	
	// Read Users
	public String readUsers() {
		String output = ""; 
		try {
			Connection con = connect(); 
			if (con == null) { 
				return "Error while connecting to the database for reading."; 
			} 
			// Prepare the table to be displayed
			output = "<table border='1'><tr>" 
					+"<th>First Name</th><th>Last Name</th>"
					+ "<th>Date of Birth</th><th>Address</th><th>Telephone No</th><th>Username</th>" 
					+ "<th>Password</th><th>email</th><th>User Type</th><th>Update</th><th>Remove</th></tr></tr>"; 
			String query = "select * from user"; 
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
				output += "<tr><td><input id='hidUserIDUpdate' name='hidUserIDUpdate' type='hidden' value='" + userID + "'>" + fName + "</td>"; 
				output += "<td>" + lName + "</td>";
				output += "<td>" + dob + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + tNo + "</td>";
				output += "<td>" + uNmae + "</td>";
				output += "<td>" + pwd + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + uType + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>" 
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-userid='"
						+ userID + "'>" + "</td></tr>";
			} 
			con.close(); 
			// Complete the table
			output += "</table>"; 
		} catch (Exception e) { 
			output = "Error while reading the users."; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	}
	
	// Insert User
	public String insertUser(String fname, String lName, String dob, String address, String tNo, String uName, String pwd, String email, String uType) {
		String output = ""; 
		try {
			Connection con = connect(); 
			if (con == null) {
				return "Error while connecting to the database"; 
			} else {
				// create a prepared statement
				String query = "insert into user (id,first_name,last_name,date_of_birth,address,telephone_no,username,password,email,user_type)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				// binding values
				preparedStmt.setInt(1, 0); 
				preparedStmt.setString(2, fname); 
				preparedStmt.setString(3, lName); 
				preparedStmt.setString(4, dob);
				preparedStmt.setString(5, address);
				preparedStmt.setString(6, tNo);
				preparedStmt.setString(7, uName);
				preparedStmt.setString(8, pwd);
				preparedStmt.setString(9, email);
				preparedStmt.setString(10, uType);
				//execute the statement
				preparedStmt.execute(); 
				con.close(); 
				String newUser = readUsers();
				output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";
			} 
		} catch (Exception e) { 		
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the user.\"}"; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	}
	
	// Update User
	public String updateUser(String id, String fname, String lName, String dob, String address, String tNo, String uName, String pwd, String email, String uType) { 
		String output = ""; 
		try {
			Connection con = connect(); 
			if (con == null) {
				return "Error while connecting to the database for updating.";
			} 
			// create a prepared statement
			String query = "UPDATE user SET first_name=?,last_name=?,date_of_birth=?,address=?,telephone_no=?,username=?,password=?,email=?,user_type=? WHERE id=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setString(1, fname); 
			preparedStmt.setString(2, lName); 
			preparedStmt.setString(3, dob);
			preparedStmt.setString(4, address);
			preparedStmt.setString(5, tNo);
			preparedStmt.setString(6, uName);
			preparedStmt.setString(7, pwd);
			preparedStmt.setString(8, email);
			preparedStmt.setString(9, uType);
			preparedStmt.setInt(10, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newUser = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";
		} catch (Exception e) { 
			output = "{\"status\":\"error\", \"data\":\"Error while update the user.\"}";
			System.err.println(e.getMessage()); 
		} 
		return output; 
	 }
	
	// Delete User
	public String deleteUser(String id) { 
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			} 
			// create a prepared statement
			String query = "delete from user where id=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newUser = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";
		} catch (Exception e) { 
			output = "{\"status\":\"error\", \"data\":\"Error while delete the user.\"}";
			System.err.println(e.getMessage()); 
		} 
		return output; 
	 }
}
