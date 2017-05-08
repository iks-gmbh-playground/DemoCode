package com.iksgmbh.demo.hoo.order;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOServiceException.HOOErrorType;
import com.iksgmbh.demo.hoo.api.utils.HOOValidationUtil;
import com.iksgmbh.demo.hoo.api.utils.JaxbToStringUtil;
import com.iksgmbh.demo.hoo.order.api.CreateOrderRequest;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.OrderList;
import com.iksgmbh.demo.hoo.order.api.PaymentRequest;
import com.iksgmbh.demo.hoo.order.dao.OrderDAO;
import com.iksgmbh.demo.hoo.order.utils.DateStringUtil;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
* Factory and repository for orders and main class of the HOO-order microservice. 
* This file contains the module contract of the order module to its environment. 
* Exported functionality available from outside see annotated service methods.
* Imported functionality: none.
* 
* @author Reik Oberrath
*/
@Path("/")
public class OrderStore
{
    private static final Logger LOGGER = Logger.getLogger("OrderServiceImpl");

    private OrderDAO orderDAO = new OrderDAO(SqlPojoMemoDB.getConnection());
    
    static {
		HOOValidationUtil.setXsdFileLocation("/HOO-Order.xsd",      // schema location for tomcat runtime 
				                             "src/main/resources/HOO-Order.xsd");  // location for unit test runtime
	}    
    
    
	// #####################################################################
	//                          Service Methods
    //                 (Exported module functionality)
	// #####################################################################
    
    @GET
    @Path("/order/all")
    @Produces(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/allOrders
     */
    public OrderList findAll() throws HOOServiceException
    {
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method findAll() called.");
        OrderList toReturn = new OrderList();

        try {
        	toReturn.getOrders().addAll( orderDAO.findAll() );
		} catch (SQLException e) {
			throw new HOOServiceException("Error finding orders.", HOOErrorType.UNEXPECTED, "Order");
		}
        LOGGER.info("List<Order> created with " + toReturn.getOrders().size() + " entries.");
        LOGGER.info("---------------------------------------------------------------------------");

        return toReturn;
    }
    
    @GET
    @Path("/order/{orderNumber}")
    @Produces(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceHoroscope/horoscope/{orderNumber}
     */
    public Order findOrderByOrderNumber(@PathParam("orderNumber") final long orderNumber) throws HOOServiceException
    {
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method findOrderByOrderNumber() called with orderNumber=" + orderNumber);

        try {
			final Order order = orderDAO.find(orderNumber);
	        LOGGER.info("Order read from DB: " + JaxbToStringUtil.toString(order));
	        return order;
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
        finally  {
        	LOGGER.info("---------------------------------------------------------------------------");
        }
    }
    
    
    @PUT
    @Path("/newOrder")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/newOrder
     */
    public Order createOrder(final CreateOrderRequest request) throws HOOServiceException
    {
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method createOrder() called.");
        HOOValidationUtil.validateContractObject("CreateOrderRequest", request);
        LOGGER.info("CreateOrderRequest received: " + JaxbToStringUtil.toString(request));

        final Order response = createResponse(request);
        HOOValidationUtil.validateContractObject("Order", response);
        LOGGER.info("Order created: " + JaxbToStringUtil.toString(response));
        LOGGER.info("---------------------------------------------------------------------------");

        return response;
    }

    
    @PUT
    @Path("/order/{orderNumber}/status/horoscopeFetched")
    /**
     * Administration method to set a payment to status PAID.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/order/{orderNumber}/status/horoscopeFetched
     */
	public void setHoroscopeFetched(@PathParam("orderNumber") final long orderNumber)  
	{
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method setHoroscopeFetched() called.");
        
		Order order;
		try {
			order = orderDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
		order.setHoroscopeFetched(true);
		
		try {
			orderDAO.updateIsHoroscopeFetchedStatus(order);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
	    LOGGER.info("HoroscopeFetched status of order " + orderNumber + " set to true.");
	    LOGGER.info("---------------------------------------------------------------------------");
	}    
    
    @PUT
    @Path("/order/status/paid")
    @Produces(MediaType.TEXT_PLAIN)
    /**
     * Administration method to set a payment to status PAID.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/order/status/paid
     */
	public void setPaidStatus(PaymentRequest request)  
	{
	  LOGGER.info("---------------------------------------------------------------------------");
      LOGGER.info("Service method setPaidStatus() called.");
      
		final long orderNumber = request.getOrderNumber();
		
		Order order;
		try {
			order = orderDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
		order.setPaid(request.isPaid());
		
		try {
			orderDAO.updatePaymentStatus(order);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
	    LOGGER.info("Paid status of order " + orderNumber + " set to " + request.isPaid() + ".");
	    LOGGER.info("---------------------------------------------------------------------------");	}    

    @POST
    @Path("/saveOrder")
    @Consumes(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/saveOrder
     */
    public void saveOrder(Order order) throws HOOServiceException
    {
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method saveOrder() called.");
        HOOValidationUtil.validateContractObject("Order", order);
        LOGGER.info("Order to save received: " + JaxbToStringUtil.toString(order));

        try {
        	orderDAO.save(order);
		} catch (SQLException e) {
			throw new HOOServiceException("Error saving order.", HOOErrorType.UNEXPECTED, "Order");
		}
        
        LOGGER.info("Order saved: " + JaxbToStringUtil.toString(order));
        LOGGER.info("---------------------------------------------------------------------------");
   }

    
    
	// #####################################################################
	//                          Internal Methods
	// #####################################################################    
    

    private Order createResponse(final CreateOrderRequest request) throws HOOServiceException
    {
        final Order toReturn = orderDAO.createNewOrder();

        try {
        	toReturn.setHoroscopeType( HoroscopeType.valueOf(request.getHoroscopeType()) );
		} catch (IllegalArgumentException e) {
			toReturn.setHoroscopeType( HoroscopeType.MISC );
		}
        
        toReturn.setAgeOfTargetPerson( DateStringUtil.calculateAgeFromYearOfDate(request.getBirthdayOfTargetPerson()) );
        toReturn.setCustomerName(request.getCustomerName());
        
        return toReturn;
    }

}