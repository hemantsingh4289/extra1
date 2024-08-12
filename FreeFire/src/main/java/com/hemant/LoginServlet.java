package com.hemant;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/loginForm")
public class LoginServlet extends HttpServlet 
{
 

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		String myemail = req.getParameter("email1");
		String mypass = req.getParameter("pass1");
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/freefire","root","Shyam@04289");
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO users (user, password) VALUES (?, ?)");
			
			ps.setString(1, myemail);
			ps.setString(2, mypass); 

			int count = ps.executeUpdate();
			if(count > 0)
			{
				resp.setContentType("text/html");
				out.print("<h3 style='color:green'> Registration Successfull wait 24 Hours </h3>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
				rd.include(req, resp);
			}
			else
			{
				resp.setContentType("text/html");
				out.print("<h3 style='color:red'> User registration failed...... </h3>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
				rd.include(req, resp);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			resp.setContentType("text/html");
			out.print("<h3 style='color:red'>Exception occurred: " + e.getMessage() + "</h3>");
			
			RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
			rd.include(req, resp);
		}
	}
}
   