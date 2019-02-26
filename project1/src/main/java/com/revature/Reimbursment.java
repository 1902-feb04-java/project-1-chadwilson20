package com.revature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Reimbursment {
	
	private final String URL = "jdbc:postgresql://localhost:5432/ers";
	private final String USERNAME = "postgres";
	private final String PASSWORD = "password";
	
	public int create(double money, int employeeId) {
		try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reimbursment_requests (money, employee_id) VALUES (?, ?)");)
		{
			if(money <= 0) {
				return -1;
			}
			preparedStatement.setDouble(1, money);
			preparedStatement.setInt(2, employeeId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	public int update(double money, int employeeId, int id) {
		try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE reimbursment_requests SET money = ? WHERE employee_id = ? AND id = ?");)
		{
			if(money <= 0) {
				return -1;
			}
			preparedStatement.setDouble(1, money);
			preparedStatement.setInt(2, employeeId);
			preparedStatement.setInt(3, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	public int update(int employeeId, int id, String typeOfUpdate) {
		try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);)
		{
			PreparedStatement preparedStatement = connection.prepareStatement("hello");
			if(typeOfUpdate.equals("approve")) {
				preparedStatement = connection.prepareStatement("UPDATE reimbursment_requests SET approved = ?, pending = ?, resolved = ? WHERE employee_id = ? AND id = ?");
				preparedStatement.setBoolean(1, true);
				preparedStatement.setBoolean(2, false);
				preparedStatement.setBoolean(3, true);
				preparedStatement.setInt(4, employeeId);
				preparedStatement.setInt(5, id);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				return 0;
			}
			else if(typeOfUpdate.equals("deny")) {
				preparedStatement = connection.prepareStatement("UPDATE reimbursment_requests SET denied = ?, pending = ?, resolved = ? WHERE employee_id = ? AND id = ?");
				preparedStatement.setBoolean(1, true);
				preparedStatement.setBoolean(2, false);
				preparedStatement.setBoolean(3, true);
				preparedStatement.setInt(4, employeeId);
				preparedStatement.setInt(5, id);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}
	public int read() {
		try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reimbursment_requests");
			ResultSet rs = preparedStatement.executeQuery();)
		{
			while(rs.next()) {
				System.out.println(rs.getString("id"));
				System.out.println(rs.getString("money"));
				System.out.println(rs.getString("receipt"));
				System.out.println(rs.getString("employee_id"));
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	public int delete(double money, int employeeId, int id) {
		try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM reimbursment_requests WHERE money = ? AND employee_id = ? AND id = ?");)
		{
			preparedStatement.setDouble(1, money);
			preparedStatement.setInt(2, employeeId);
			preparedStatement.setInt(3, id);
			preparedStatement.executeUpdate();
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
