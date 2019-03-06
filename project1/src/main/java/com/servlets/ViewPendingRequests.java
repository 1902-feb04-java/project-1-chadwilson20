package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.Employee;
import com.revature.Reimbursment;

public class ViewPendingRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Employee employee = new Employee();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		int id = employee.getId(username, password);
		Reimbursment reimbursment = new Reimbursment();
		List<String> result = reimbursment.read(false, id, "all pending requests");
		PrintWriter writer = response.getWriter();
		request.getRequestDispatcher("viewPendingRequests.html").include(request, response);
		if (result != null) {
			writer.println("<table>");
			writer.println("<tr>");
			writer.println("<th>Amount</th>");
			writer.println("<th>Description</th>");
			writer.println("</tr>");
			for (int i = 0; i < result.size() - 1; i = i + 2) {
				writer.println("<tr>");
				writer.println("<td>" + result.get(i) + "</td>");
				writer.println("<td>" + result.get(i + 1) + "</td>");
				writer.println("</tr>");
				
			}
			writer.println("</table>");
		} else {
			writer.println("<h2>Sorry, an error occured while pulling up your results. Please try again.</h2>");
		}
		writer.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
