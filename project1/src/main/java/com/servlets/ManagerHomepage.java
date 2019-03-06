package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.Manager;

public class ManagerHomepage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Manager manager = new Manager();
		if (manager.validateUser(username, password)) {
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			PrintWriter writer = response.getWriter();
			writer.println("<h1>Hello " + username + "! What would you like to do?</h1>");
			request.getRequestDispatcher("managerHomepage.html").include(request, response);
			writer.close();
		} else {
			PrintWriter writer = response.getWriter();
			request.getRequestDispatcher("index.html").include(request, response);
			writer.println("<h2>Sorry, username and/or password is incorrect</h2>");
			writer.close();
		}
	}

}
