package de.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/sayHelloViaRest")   // specifies endpoint of the WebClient
public class SayHelloViaRestServlet extends HttpServlet 
{
    private static final Logger LOGGER = Logger.getLogger(SayHelloViaRestServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/sayHelloViaRest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        final Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);
        final String name = request.getParameter("name");
        String helloText; 

        try {
        	helloText = HelloWorldRestRequestExecutor.sendSayHelloWordRequest(name);
        } catch (Exception e) {
        	LOGGER.log(Level.WARNING, e.getClass().getSimpleName() + ": " + e.getMessage());
        	helloText = "SORRY! The HelloWorldRestService is not available.";

        }
        
        messages.put("helloText", helloText);

        request.getRequestDispatcher("/sayHelloViaRest.jsp").forward(request, response);
    }

}