package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
	// Database Connection 
	public Connection connect() {
		Connection con = null; 
		try {			
			Class.forName("com.mysql.jdbc.Driver"); 
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget", "root", ""); 
			//For testing
			System.out.print("Successfully connected "); 
		} catch(Exception e) { 
			e.printStackTrace(); 
		}
		return con;
	}
	
	// Main Login
	public String loginProcess(String unameORemail, String password) {
		String output = ""; 
		try {
			Connection con = connect(); 
			if (con == null) {
				return "Error while connecting to the database"; 
			} else {
				String query = "SELECT * FROM user WHERE username=? or email=? and password=?";
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, unameORemail);
				stmt.setString(2, unameORemail);
				stmt.setString(3, password);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					String userID = Integer.toString(rs.getInt("id"));
					String usertype = rs.getString("user_type");
					if(output != null) {
						output = "Successfully Authenticated For User ID : "+userID+ " User Type : "+usertype;
					} else {
						output = "Authentication Failed";
					}
				} 
				stmt.close();
				rs.close();
				con.close();
			}
		} catch(Exception e) {
			output = "Error while reading the users ID."; 
			System.err.println(e.getMessage());
		}
		return output;
	}
}
