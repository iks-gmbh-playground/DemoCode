package de.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;

import de.test.api.utils.XMLGregorianCalendarUtil;

@SuppressWarnings("serial")
@WebServlet("/startSmallTalkViaSoap")  // specifies endpoint of the WebClient
public class StartSmallTalkViaSoapServlet extends HttpServlet 
{
    private static final Logger LOGGER = Logger.getLogger(StartSmallTalkViaSoapServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/startSmallTalkViaSoap.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	LOGGER.info("####################################");
        final Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);
        final String name = request.getParameter("name");
        final String dateAsString = request.getParameter("date");
        
        Date date = null;
        try {
        	LOGGER.info("dateAsString: " + dateAsString);
        	XMLGregorianCalendar gregorianCaldendar = XMLGregorianCalendarUtil.toGregorianCaldendar(dateAsString);
        	date = XMLGregorianCalendarUtil.toDate(gregorianCaldendar);
		} catch (ParseException e) {
        	LOGGER.log(Level.WARNING, e.getClass().getSimpleName() + ": " + e.getMessage() 
        	           + " Expected date Format: " + XMLGregorianCalendarUtil.DATE_FORMAT);
        	
		}
        
        String smallTalkComment; 

        try {
        	smallTalkComment = HelloWorldSoapRequestExecutor.sendStartSmallTalkRequest(name, date);
        } catch (Exception e) {
        	LOGGER.log(Level.WARNING, e.getClass().getSimpleName() + ": " + e.getMessage());
        	smallTalkComment = "SORRY! The HelloWorldSoapService is not available.";
        }
        
        messages.put("smallTalkComment", smallTalkComment);        
        request.getRequestDispatcher("/startSmallTalkViaSoap.jsp").forward(request, response);
    }

}