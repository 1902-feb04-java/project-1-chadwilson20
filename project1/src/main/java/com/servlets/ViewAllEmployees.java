package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.Reimbursment;

public class ViewAllEmployees extends HttpServlet {
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
		Reimbursment reimbursment = new Reimbursment();
		List<String> result = reimbursment.read(true, 0, "all employees");
		PrintWriter writer = response.getWriter();
		if (result != null) {
			request.getRequestDispatcher("viewAllEmployees.html").include(request, response);
			writer.println("<table>");
			writer.println("<tr>");
			writer.println("<th>Employee First Name</th>");
			writer.println("<th>Employee Last Name</th>");
			writer.println("<th>Title</th>");
			writer.println("</tr>");
			for (int i = 0; i < result.size() - 1; i = i + 3) {
				writer.println("<tr>");
				writer.println("<td>" + result.get(i) + "</td>");
				writer.println("<td>" + result.get(i + 1) + "</td>");
				writer.println("<td>" + result.get(i + 2) + "</td>");
				writer.println("</tr>");
			}
			writer.println("</table>");
		} else {
			writer.println("<h2>Sorry, an error occured while pulling up your results. Please try again.</h2>");
		}
		writer.close();
	}

}
