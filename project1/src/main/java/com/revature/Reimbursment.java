package com.revature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reimbursment {

	private final String URL = "jdbc:postgresql://localhost:5432/ers";
	private final String USERNAME = "postgres";
	private final String PASSWORD = "password";

	/* Used to create a reimbursement request */
	public int create(double money, int employeeId, String description) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(
						"INSERT INTO reimbursment_requests (money, employee_id, description) VALUES (?, ?, ?)");) {
			preparedStatement.setDouble(1, money);
			preparedStatement.setInt(2, employeeId);
			preparedStatement.setString(3, description);
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
	public int update(int employeeId, int managerId, int id, String typeOfUpdate) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT pending FROM reimbursment_requests WHERE pending = ? AND employee_id = ? AND id = ?");
			preparedStatement.setBoolean(1, true);
			preparedStatement.setInt(2, employeeId);
			preparedStatement.setInt(3, id);
			ResultSet rs = preparedStatement.executeQuery();
			if (!rs.next()) {
				preparedStatement.close();
				rs.close();
				return -2;
			}
			preparedStatement = connection.prepareStatement("SELECT manager_id FROM employees WHERE id = ?");
			preparedStatement.setInt(1, employeeId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				int manager_id = Integer.parseInt(rs.getString("manager_id"));
				if (manager_id != managerId) {
					preparedStatement.close();
					rs.close();
					return -3;
				}
			}
			rs.close();
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
			preparedStatement.close();
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
	public List<String> read(boolean isManager, int employeeId, String typeOfView) {
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {
			ResultSet rs = null;
			List<String> result = new ArrayList<>();
			if (isManager && typeOfView.equals("all pending requests")) {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT reimbursment_requests.id, money, description, employees.first_name, employees.last_name, managers.first_name AS manager_first_name, managers.last_name AS manager_last_name FROM ((reimbursment_requests INNER JOIN employees ON reimbursment_requests.employee_id = employees.id) INNER JOIN managers ON employees.manager_id = managers.id) WHERE pending = ?");
				preparedStatement.setBoolean(1, true);
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					String reimbursment_id = rs.getString("id");
					String money = rs.getString("money");
					String description = rs.getString("description");
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String manager_first_name = rs.getString("manager_first_name");
					String manager_last_name = rs.getString("manager_last_name");
					result.add(reimbursment_id);
					result.add(money);
					result.add(description);
					result.add(first_name);
					result.add(last_name);
					result.add(manager_first_name);
					result.add(manager_last_name);
				}
				preparedStatement.close();
			} else if (!isManager && typeOfView.equals("all pending requests")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM reimbursment_requests WHERE pending = ? AND employee_id = ?");
				preparedStatement.setBoolean(1, true);
				preparedStatement.setInt(2, employeeId);
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					String money = rs.getString("money");
					String description = rs.getString("description");
					result.add(money);
					result.add(description);
				}
				preparedStatement.close();
			} else if (!isManager && typeOfView.equals("all resolved requests")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM reimbursment_requests WHERE resolved = ? AND employee_id = ?");
				preparedStatement.setBoolean(1, true);
				preparedStatement.setInt(2, employeeId);
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					String money = rs.getString("money");
					String description = rs.getString("description");
					String approved = rs.getString("approved");
					result.add(money);
					result.add(description);
					result.add(approved);
				}
				preparedStatement.close();
			} else if (!isManager && typeOfView.equals("my info")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM employees WHERE id = ?");
				preparedStatement.setInt(1, employeeId);
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String title = rs.getString("title");
					String username = rs.getString("username");
					String password = rs.getString("password");
					result.add(first_name);
					result.add(last_name);
					result.add(title);
					result.add(username);
					result.add(password);
				}
				preparedStatement.close();
			} else if (isManager && typeOfView.equals("all employees")) {
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees");
				rs = preparedStatement.executeQuery();
				while(rs.next()) {
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String title = rs.getString("title");
					result.add(first_name);
					result.add(last_name);
					result.add(title);
				}
				preparedStatement.close();
			} else if (isManager && typeOfView.equals("view requests from single employee")) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM reimbursment_requests WHERE employee_id = ?");
				preparedStatement.setInt(1, employeeId);
				rs = preparedStatement.executeQuery();
				while(rs.next()) {
					String reimbursmentRequestId = rs.getString("id");
					String money = rs.getString("money");
					String description = rs.getString("description");
					String resolved = rs.getString("resolved");
					String approved = rs.getString("approved");
					result.add(reimbursmentRequestId);
					result.add(money);
					result.add(description);
					result.add(resolved);
					result.add(approved);
				}
				preparedStatement.close();
			} else if (isManager && typeOfView.equals("view all resolved requests and see who solved it")) {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT money, description, employees.first_name, employees.last_name, managers.first_name AS manager_first_name, managers.last_name AS manager_last_name, approved FROM ((reimbursment_requests INNER JOIN employees ON reimbursment_requests.employee_id = employees.id) INNER JOIN managers ON employees.manager_id = managers.id) WHERE resolved = ?");
				preparedStatement.setBoolean(1, true);
				rs = preparedStatement.executeQuery();
				while(rs.next()) {
					String money = rs.getString("money");
					String description = rs.getString("description");
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String employee_first_name = rs.getString("manager_first_name");
					String employee_last_name = rs.getString("manager_last_name");
					String approved = rs.getString("approved");
					result.add(money);
					result.add(description);
					result.add(first_name);
					result.add(last_name);
					result.add(employee_first_name);
					result.add(employee_last_name);
					result.add(approved);
				}
				preparedStatement.close();
			}
			rs.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
