package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.Reimbursment;

public class ViewAllEmployeesResolvedRequests extends HttpServlet {
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
		List<String> result = reimbursment.read(true, 0, "view all resolved requests and see who solved it");
		PrintWriter writer = response.getWriter();
		request.getRequestDispatcher("viewAllEmployeesResolvedRequests.html").include(request, response);
		if (result != null) {
			writer.println("<table>");
			writer.println("<tr>");
			writer.println("<th>Amount</th>");
			writer.println("<th>Description</th>");
			writer.println("<th>Employee's First Name");
			writer.println("<th>Employee's Last Name");
			writer.println("<th>Employee Manager's First Name");
			writer.println("<th>Employee Manager's Last Name");
			writer.println("<th>Approved or Denied</th>");
			writer.println("</tr>");
			for (int i = 0; i < result.size() - 1; i = i + 7) {
				writer.println("<tr>");
				writer.println("<td>" + result.get(i) + "</td>");
				writer.println("<td>" + result.get(i + 1) + "</td>");
				writer.println("<td>" + result.get(i + 2) + "</td>");
				writer.println("<td>" + result.get(i + 3) + "</td>");
				writer.println("<td>" + result.get(i + 4) + "</td>");
				writer.println("<td>" + result.get(i + 5) + "</td>");
				if(result.get(i + 6).equals("t")) {
					writer.println("<td>Approved</td>");
				}
				else {
					writer.println("<td>Denied</td>");
				}
				writer.println("</tr>");
				
			}
			writer.println("</table>");
		} else {
			writer.println("<h2>Sorry, an error occured while pulling up these results. Please try again.</h2>");
		}
		writer.close();
	}

}
