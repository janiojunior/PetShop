package br.unitins.petshop.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/helloworld")
public class HelloServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6160115403742437437L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter writer = response.getWriter();
		writer.append("<h1> Hello World </h1>");
		
		writer.close();
		
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}
	
}
