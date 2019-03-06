package com.revature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Manager {
	
	private final String URL = "jdbc:postgresql://localhost:5432/ers";
	private final String USERNAME = "postgres";
	private final String PASSWORD = "password";

	public boolean validateUser(String username, String password) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM managers WHERE username = ? AND password = ?");) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				rs.close();
				return true;
			}
			rs.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getId(String username, String password) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT id FROM managers WHERE username = ? AND password = ?");) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				rs.close();
				return id;
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}
	
	public int getEmployeeId(String firstName, String lastName) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT id FROM employees WHERE first_name = ? AND last_name = ?");) {
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				rs.close();
				return id;
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}
}
