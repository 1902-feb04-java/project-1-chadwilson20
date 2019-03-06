package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.Employee;
import com.revature.Reimbursment;

public class UpdateEmployeeInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String updateValue = request.getParameter("updateValue");
		String updateText = request.getParameter("updateText");
		Employee employee = new Employee();
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		int id = employee.getId(username, password);
		Reimbursment reimbursment = new Reimbursment();
		int successful = -1;
		if(updateValue.equals("firstname")) {
			successful = reimbursment.update(id, "first_name", updateText);
		}
		else if(updateValue.equals("lastname")) {
			successful = reimbursment.update(id, "last_name", updateText);
		}
		else if(updateValue.equals("title")) {
			successful = reimbursment.update(id, "title", updateText);
		}
		else if(updateValue.equals("username")) {
			successful = reimbursment.update(id, "username", updateText);
			request.getRequestDispatcher("logout").forward(request, response);
		}
		else if(updateValue.equals("password")) {
			successful = reimbursment.update(id, "password", updateText);
			request.getRequestDispatcher("logout").forward(request, response);
		}
		PrintWriter writer = response.getWriter();
		request.getRequestDispatcher("updateEmployeeInformation.html").include(request, response);
		if(successful == 0) {
			writer.println("<h1>Your information has been successfully updated!</h1>");
		}
		else {
			writer.println("<h1>Sorry, an error occured while updating your information. Please try again.</h1>");
		}
	}

}
