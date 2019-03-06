package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.Manager;
import com.revature.Reimbursment;

public class ApproveARequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		String employeeFirstName = request.getParameter("employeeFirstName");
		String employeeLastName = request.getParameter("employeeLastName");
		int reimbursmentRequestId = Integer.parseInt(request.getParameter("reimbursmentRequestID"));
		Manager manager = new Manager();
		int employeeId = manager.getEmployeeId(employeeFirstName, employeeLastName);
		int managerId = manager.getId(username, password);
		Reimbursment reimbursment = new Reimbursment();
		int successful = reimbursment.update(employeeId, managerId, reimbursmentRequestId, "approve");
		PrintWriter writer = response.getWriter();
		request.getRequestDispatcher("approveARequest.html").include(request, response);
		if (successful == 0) {
			writer.println("<h1>This employee's reimbursment request has been successfully approved!</h1>");
		} else if (successful == -1) {
			writer.println(
					"<h1>Sorry, an error occured while approving this employee's reimbursement request. Please try again.</h1>");
		} else if (successful == -2) {
			writer.println(
					"<h1>Either this employee's reimbursment request has already been resolved or this employee and/or request does not exist.</h1>");
		} else if (successful == -3) {
			writer.println("<h1>You do not manage this employee. You cannot approve their requests.</h1>");
		}
	}
}
