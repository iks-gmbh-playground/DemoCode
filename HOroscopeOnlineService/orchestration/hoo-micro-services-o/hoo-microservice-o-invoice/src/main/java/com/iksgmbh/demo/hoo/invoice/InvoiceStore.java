package com.iksgmbh.demo.hoo.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOValidationUtil;
import com.iksgmbh.demo.hoo.api.utils.JaxbToStringUtil;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.invoice.api.CreateInvoiceRequest;
import com.iksgmbh.demo.hoo.invoice.api.Invoice;
import com.iksgmbh.demo.hoo.invoice.dao.InvoiceDAO;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
* Factory and repository for invoices and main class of the HOO-Invoice microservice. 
* This file contains the module contract of the invoice module to its environment. 
* Exported functionality available from outside see annotated service methods.
* Imported functionality: none.
* 
* @author Reik Oberrath
*/
@Path("/")
public class InvoiceStore
{
    private static final Logger LOGGER = Logger.getLogger("InvoiceServiceImpl");

	private static final BigDecimal BASIC_JOB_HOROSCOPE_PRICE = new BigDecimal("9.99");
	private static final BigDecimal BASIC_LOVE_HOROSCOPE_PRICE = new BigDecimal("5.49");
	private static final BigDecimal BASIC_MISC_HOROSCOPE_PRICE = new BigDecimal("10.49");

	public static final String PREPAID_CUSTOMER = "Prepaid";  // to do: should be read from DB 
	
	private InvoiceDAO invoiceDAO = new InvoiceDAO(SqlPojoMemoDB.getConnection());
	 
    static {
		HOOValidationUtil.setXsdFileLocation("/HOO-Invoice.xsd",      // schema location for tomcat runtime 
				                             "src/main/resources/HOO-Invoice.xsd");  // location for unit test runtime
	}   	
    
	// #####################################################################
	//                          Service Methods
    //                 (Exported module functionality)
	// #####################################################################
    

    @PUT
    @Path("/newInvoice")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceInvoice/createInvoice
     */
    public Invoice createInvoice(final CreateInvoiceRequest request) throws HOOServiceException
    {
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method createInvoice() called.");
        HOOValidationUtil.validateContractObject("CreateInvoiceRequest", request);
        LOGGER.info("CreateInvoiceRequest received: " + JaxbToStringUtil.toString(request));

        final Invoice invoice = createInvoice(request.getOrder(), request.getHoroscope());
        try {
			invoiceDAO.save(invoice);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
        
        HOOValidationUtil.validateContractObject("Invoice", invoice);
        LOGGER.info("Invoice created: " + JaxbToStringUtil.toString(invoice));
        LOGGER.info("---------------------------------------------------------------------------");

        return invoice;
    }
    
    @GET
    @Path("/invoice/{orderNumber}")
    @Produces(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceInvoice/invoice/{orderNumber}
     */
    public Invoice findInvoiceByOrderNumber(@PathParam("orderNumber") final long orderNumber) throws HOOServiceException
    {
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method findInvoiceByOrderNumber() called with orderNumber=" + orderNumber);

        try {
			return invoiceDAO.find(orderNumber);
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

    private Invoice createInvoice(final Order order, final Horoscope horoscope) throws HOOServiceException
    {
        final Invoice toReturn = new Invoice();
        toReturn.setOrderNumber(order.getOrderNumber());
		toReturn.setCustomerName(order.getCustomerName());
		toReturn.setHoroscopeType(order.getHoroscopeType());
		toReturn.setInvoiceFactor(horoscope.getInvoiceFactor());
		
        BigDecimal price;
        
		if (order.getHoroscopeType() == HoroscopeType.JOB) {
			price = BASIC_JOB_HOROSCOPE_PRICE;
		} else if (order.getHoroscopeType() == HoroscopeType.LOVE) {
			price = BASIC_LOVE_HOROSCOPE_PRICE;
		} else {
			price = BASIC_MISC_HOROSCOPE_PRICE;
		}
		
		price = price.multiply(horoscope.getInvoiceFactor())
				     .setScale(2, RoundingMode.HALF_DOWN);
		
		String bill = "Please pay " + price.toPlainString() + " Euro.";
		
		toReturn.setBill(bill);
		toReturn.setPrice(price);
		toReturn.setPrePaid(isPrePaid(order.getCustomerName()));
		
        return toReturn;
    }
    
	private boolean isPrePaid(String customerName) {
		return customerName.equals(PREPAID_CUSTOMER);
	}    


}