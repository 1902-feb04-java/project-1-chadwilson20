package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.Manager;
import com.revature.Reimbursment;

public class ViewReimbursmentRequestsFromSingleEmployee extends HttpServlet {
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
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Manager manager = new Manager();
		int employeeId = manager.getEmployeeId(firstName, lastName);
		Reimbursment reimbursment = new Reimbursment();
		List<String> result = reimbursment.read(true, employeeId, "view requests from single employee");
		PrintWriter writer = response.getWriter();
		request.getRequestDispatcher("viewReimbursmentRequestsFromSingleEmployee.html").include(request, response);
		if (result != null && employeeId != -1) {
			writer.println("<h2>Here are the reimbursment requests for " + firstName + "</h1>");
			writer.println("<table>");
			writer.println("<tr>");
			writer.println("<th>Reimbursment Request ID</th>");
			writer.println("<th>Money</th>");
			writer.println("<th>Description</th>");
			writer.println("<th>Resolved or Pending</th>");
			writer.println("<th>Approved or Denied</th>");
			writer.println("</tr>");
			for (int i = 0; i < result.size() - 1; i = i + 5) {
				writer.println("<tr>");
				writer.println("<td>" + result.get(i) + "</td>");
				writer.println("<td>" + result.get(i + 1) + "</td>");
				writer.println("<td>" + result.get(i + 2) + "</td>");
				if(result.get(i + 3).equals("t")) {
					writer.println("<td>Resolved</td>");
					if(result.get(i + 4).equals("t")) {
						writer.println("<td>Approved</td>");
					}
					else {
						writer.println("<td>Denied</td>");
					}
				}
				else {
					writer.println("<td>Pending</td>");
					writer.println("<td>N/A</td>");
				}
				writer.println("</tr>");
			}
			writer.println("</table>");
		} else if (employeeId == -1) {
			writer.println("<h2>The specified employee does not exist.</h2>");
		}
		else {
			writer.println("<h2>Sorry, an error occured while pulling up your results. Please try again.</h2>");
		}
		writer.close();
	}

}
