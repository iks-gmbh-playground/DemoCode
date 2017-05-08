package com.iksgmbh.demo.hoo.horoscope;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOValidationUtil;
import com.iksgmbh.demo.hoo.api.utils.JaxbToStringUtil;
import com.iksgmbh.demo.hoo.horoscope.api.CreateHoroscopeRequest;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.horoscope.dao.HoroscopeDAO;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;



/**
* Factory and repository for invoices and main class of the HOO-horoscope microservice. 
* This file contains the module contract of the horoscope module to its environment. 
* Exported functionality available from outside see annotated service methods.
* Imported functionality: none.
* 
* @author Reik Oberrath
*/
@Path("/")
public class HoroscopeStore
{
    private static final Logger LOGGER = Logger.getLogger("HoroscopeServiceImpl");

	private static final BigDecimal DISCOUNT_FOR_KIDS = new BigDecimal(0.5);
	private static final BigDecimal SURCHARGE_FOR_OLDIES = new BigDecimal(0.1);
	private static final int AGE_LIMIT_KIDS = 10;
	private static final int AGE_LIMIT_OLDIES = 60;

    private HoroscopeDAO horoscopeDAO = new HoroscopeDAO(SqlPojoMemoDB.getConnection());

    static {
		HOOValidationUtil.setXsdFileLocation("/HOO-Horoscope.xsd",      // schema location for tomcat runtime 
				                             "src/main/resources/HOO-Horoscope.xsd");  // location for unit test runtime
	}   	

	// #####################################################################
	//                          Service Methods
    //                 (Exported module functionality)
	// #####################################################################

    
    @POST
    @Path("/newHoroscope")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceHoroscope/newHoroscope
     */
    public Horoscope createHoroscope(final CreateHoroscopeRequest request) throws HOOServiceException
    {
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method createHoroscope() called.");
        HOOValidationUtil.validateContractObject("CreateHoroscopeRequest", request);
        LOGGER.info("CreateHoroscopeRequest received: " + JaxbToStringUtil.toString(request));

        final Horoscope horoscope = createHoroscope(request.getOrder());
        
        try {
			horoscopeDAO.save(horoscope);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
        
        LOGGER.info("Horoscope created: " + JaxbToStringUtil.toString(horoscope));
        LOGGER.info("---------------------------------------------------------------------------");
        
        return horoscope;
    }

    @GET
    @Path("/horoscope/{orderNumber}")
    @Produces(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceHoroscope/horoscope/{orderNumber}
     */
    public Horoscope findHoroscopeByOrderNumber(@PathParam("orderNumber") final long orderNumber) throws HOOServiceException
    {
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method findHoroscopeByOrderNumber() called with orderNumber=" + orderNumber);

        try {
			return horoscopeDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
        finally  {
        	LOGGER.info("---------------------------------------------------------------------------");
        }
    }
    
	// #####################################################################
	//                          Internal Methods
	// #####################################################################

    
    /**
     * Applies some simple business logic based on the order data to get a horoscope.
     * @param horoscopeType 
     * @param ageOfTargetPerson 
     * @param order
     * @return Horoscope
     */
	private Horoscope createHoroscope(final Order order) 
	{
        final Horoscope horoscope = new Horoscope();
        horoscope.setOrderNumber( order.getOrderNumber() );
        String horoscopeText;
		BigDecimal invoiceFactor = BigDecimal.ONE;
		
		if (order.getHoroscopeType() == HoroscopeType.JOB) {
			horoscopeText = "Divide and rule!";
		} else if (order.getHoroscopeType() == HoroscopeType.LOVE) {
			horoscopeText = "Don''t worry, be happy.";
		} else {
			horoscopeText = "Take it easy.";
		}
		
		horoscope.setHoroscopeText(horoscopeText);
		
		if (order.getAgeOfTargetPerson() < AGE_LIMIT_KIDS ) {
			invoiceFactor = invoiceFactor.subtract(DISCOUNT_FOR_KIDS);
		} else if (order.getAgeOfTargetPerson() > AGE_LIMIT_OLDIES ) {
			invoiceFactor = invoiceFactor.add(SURCHARGE_FOR_OLDIES);
		}	
		
		horoscope.setInvoiceFactor(invoiceFactor);
		horoscope.setHoroscopeType(order.getHoroscopeType());
		return horoscope;
	}
    

}