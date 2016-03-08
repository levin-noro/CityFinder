package com.example.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.example.model.BeerExpert; // notice this

public class BeerSelect extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String c = request.getParameter("color"); // gets the colour fromt he request
		BeerExpert be = new BeerExpert();// does same thing as main
		List result = be.getBrands(c);// same as main
		request.setAttribute("styles", result); // get brand and store it an attribute of request called styles
		RequestDispatcher view = request.getRequestDispatcher("result.jsp"); // creates the view
		view.forward(request, response); // sends the date to view to be displayed
	}
}
