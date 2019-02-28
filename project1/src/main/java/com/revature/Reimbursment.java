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

	/* Used to create a reimbursement request */
	public int create(double money, int employeeId) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection
						.prepareStatement("INSERT INTO reimbursment_requests (money, employee_id) VALUES (?, ?)");) {
			if (money <= 0) {
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

	/* Used to update an existing reimbursement request */
	public int update(double money, int employeeId, int id) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(
						"UPDATE reimbursment_requests SET money = ? WHERE employee_id = ? AND id = ?");) {
			if (money <= 0) {
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

	/* Used for a manager to either approve or deny a request */
	public int update(int employeeId, int id, String typeOfUpdate) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {
			PreparedStatement preparedStatement = connection.prepareStatement("hello");
			if (typeOfUpdate.equals("approve")) {
				preparedStatement = connection.prepareStatement(
						"UPDATE reimbursment_requests SET approved = ?, pending = ?, resolved = ? WHERE employee_id = ? AND id = ?");
				preparedStatement.setBoolean(1, true);
				preparedStatement.setBoolean(2, false);
				preparedStatement.setBoolean(3, true);
				preparedStatement.setInt(4, employeeId);
				preparedStatement.setInt(5, id);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				return 0;
			} else if (typeOfUpdate.equals("deny")) {
				preparedStatement = connection.prepareStatement(
						"UPDATE reimbursment_requests SET denied = ?, pending = ?, resolved = ? WHERE employee_id = ? AND id = ?");
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

	/* Used for an employee to update their information */
	public int update(int id, String typeOfUpdate, String updateText) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {
			if (typeOfUpdate.equals("first_name")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE employees SET first_name = ? WHERE id = ?");
				preparedStatement.setString(1, updateText);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				return 0;
			} else if (typeOfUpdate.equals("last_name")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE employees SET last_name = ? WHERE id = ?");
				preparedStatement.setString(1, updateText);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				return 0;
			} else if (typeOfUpdate.equals("title")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE employees SET title = ? WHERE id = ?");
				preparedStatement.setString(1, updateText);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				return 0;
			} else if (typeOfUpdate.equals("username")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE employees SET username = ? WHERE id = ?");
				preparedStatement.setString(1, updateText);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				return 0;
			} else if (typeOfUpdate.equals("password")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE employees SET password = ? WHERE id = ?");
				preparedStatement.setString(1, updateText);
				preparedStatement.setInt(2, id);
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

	/* Used for employees or managers to read certain information */
	public int read(boolean isManager, int employeeId, String typeOfView) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {
			if (isManager && typeOfView.equals("all pending requests")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM reimbursment_requests WHERE pending = ?");
				preparedStatement.setBoolean(1, true);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString("id"));
					System.out.println(rs.getString("money"));
					System.out.println(rs.getString("receipt"));
					System.out.println(rs.getString("employee_id"));
				}
				preparedStatement.close();
				rs.close();
			} else if (!isManager && typeOfView.equals("all pending requests")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM reimbursment_requests WHERE pending = ? AND employee_id = ?");
				preparedStatement.setBoolean(1, true);
				preparedStatement.setInt(2, employeeId);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString("id"));
					System.out.println(rs.getString("money"));
					System.out.println(rs.getString("receipt"));
					System.out.println(rs.getString("employee_id"));
				}
				preparedStatement.close();
				rs.close();
			} else if (!isManager && typeOfView.equals("all resolved requests")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM reimbursment_requests WHERE resolved = ? AND employee_id = ?");
				preparedStatement.setBoolean(1, true);
				preparedStatement.setInt(2, employeeId);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString("id"));
					System.out.println(rs.getString("money"));
					System.out.println(rs.getString("receipt"));
					System.out.println(rs.getString("employee_id"));
				}
				preparedStatement.close();
				rs.close();
			} else if (!isManager && typeOfView.equals("my info")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM employees WHERE id = ?");
				preparedStatement.setInt(1, employeeId);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString("first_name"));
					System.out.println(rs.getString("last_name"));
					System.out.println(rs.getString("title"));
					System.out.println(rs.getString("username"));
					System.out.println(rs.getString("password"));
					System.out.println(rs.getString("manager_id"));
				}
				preparedStatement.close();
				rs.close();
			} else if (isManager && typeOfView.equals("all employees")) {
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString("first_name"));
					System.out.println(rs.getString("last_name"));
					System.out.println(rs.getString("title"));
					System.out.println(rs.getString("username"));
					System.out.println(rs.getString("password"));
					System.out.println(rs.getString("manager_id"));
				}
				preparedStatement.close();
				rs.close();
			} else if (isManager && typeOfView.equals("view requests from single employee")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM reimbursment_requests WHERE employee_id = ?");
				preparedStatement.setInt(1, employeeId);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString("id"));
					System.out.println(rs.getString("money"));
					System.out.println(rs.getString("receipt"));
					System.out.println(rs.getString("employee_id"));
				}
				preparedStatement.close();
				rs.close();
			} else if (isManager && typeOfView.equals("view all requests and see who solved it")) {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT * FROM reimbursment_requests INNER JOIN employees ON reimbursment_requests.employee_id = employees.id");
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					System.out.println(rs.getString("money"));
					System.out.println(rs.getString("receipt"));
					System.out.println(rs.getString("employee_id"));
					System.out.println(rs.getString("first_name"));
					System.out.println(rs.getString("last_name"));
					System.out.println(rs.getString("title"));
					System.out.println(rs.getString("username"));
					System.out.println(rs.getString("password"));
					System.out.println(rs.getString("manager_id"));
				}
				preparedStatement.close();
				rs.close();
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/* Used to delete reimbursement requests */
	public int delete(double money, int employeeId, int id) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(
						"DELETE FROM reimbursment_requests WHERE money = ? AND employee_id = ? AND id = ?");) {
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
