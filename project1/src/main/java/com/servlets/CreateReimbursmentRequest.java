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

/**
 * Servlet implementation class CreateReimbursmentRequest
 */
public class CreateReimbursmentRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		double money = Double.parseDouble(request.getParameter("money"));
		String description = request.getParameter("description");
		Employee employee = new Employee();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		int id = employee.getId(username, password);
		Reimbursment reimbursment = new Reimbursment();
		int successful = reimbursment.create(money, id, description);
		PrintWriter writer = response.getWriter();
		request.getRequestDispatcher("createRequestPage.html").include(request, response);
		if(successful == 0) {
			writer.println("<h2>Thank you for your reimbursment request! Your manager should resolve it shortly!</h2>");
		}
		else {
			writer.println("<h2>Sorry, an error occured while submitting your request. Please try again.</h2>");
		}
		writer.close();
	}
}
