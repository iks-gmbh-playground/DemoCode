package com.iksgmbh.demo.hoo.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOServiceException.HOOErrorType;
import com.iksgmbh.demo.hoo.api.utils.XMLGregorianCalendarUtil;
import com.iksgmbh.demo.hoo.order.api.CreateOrderRequest;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.OrderList;
import com.iksgmbh.demo.hoo.order.api.PaymentRequest;
import com.iksgmbh.demo.hoo.order.dao.OrderDAO;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

public class OrderServiceImplTest
{
    private OrderStore sut;
    
    @Before
    public void setup() throws SQLException {
    	sut = new OrderStore();
    	SqlPojoMemoDB.reset();
    	SqlPojoMemoDB.execute(OrderDAO.CREATE_TABLE_ORDER_SQL);
	}

    @Test
    public void createsSuccessfullOrder()
    {
        // arrange
        CreateOrderRequest request = new CreateOrderRequest();
        int testYear = 1980;
        request.setBirthdayOfTargetPerson("10.10." + testYear);
        request.setHoroscopeType("JOB");
        request.setCustomerName("aCustomer");
        
        // act
        Order result = sut.createOrder(request);

        // assert
        int expectedAge = result.getCreationDateTime().getYear() - testYear;
        assertEquals("result", expectedAge, result.getAgeOfTargetPerson());
        assertEquals("result", "JOB", result.getHoroscopeType().name());
    }

    @Test
    public void throwsValidationErrorForMissingInputDataInOrder()
    {
        // arrange
        CreateOrderRequest request = new CreateOrderRequest();
        Locale.setDefault(Locale.ENGLISH);

        try {
            // act
            sut.createOrder(request);
            fail("Expected exception was not thrown!");
        } catch (HOOServiceException e) {
            // assert
        	System.err.println(e.getMessage());
            assertEquals("Error type", HOOErrorType.MISSING_INPUT, e.getErrorType());
            assertMessageContains(e.getMessage(), "MISSING_INPUT CreateOrderRequest!");
            assertMessageContains(e.getMessage(), "The content of element 'CreateOrderRequest' is not complete.");
            assertMessageContains(e.getMessage(), "One of '{\"api.order.hoo.demo.iksgmbh.com\":customerName}' is expected.");
        }
    }


    @Test
    public void savesOrderSuccessfully()
    {
        // arrange
        Order request = new Order();
        request.setCustomerName("aCustomer");
        request.setAgeOfTargetPerson(33);
        request.setHoroscopeType(HoroscopeType.JOB);
        request.setCreationDateTime(XMLGregorianCalendarUtil.toGregorianCaldendar(new Date()));
        request.setPaid(false);
        request.setHoroscopeFetched(false);
        
        // act
        sut.saveOrder(request);

        // asserted by no exception thrown
    }
    
    @Test
    public void updatesOrderStatus()
    {
        // arrange
        Order request = new Order();
        request.setOrderNumber(100);
        request.setCustomerName("aCustomer");
        request.setAgeOfTargetPerson(33);
        request.setHoroscopeType(HoroscopeType.JOB);
        request.setCreationDateTime(XMLGregorianCalendarUtil.toGregorianCaldendar(new Date()));
        request.setPrice(new BigDecimal("9.99"));
        request.setPaid(false);
        request.setHoroscopeFetched(false);
        sut.saveOrder(request);
        assertFalse("false expected", sut.findOrderByOrderNumber(100).isPaid());
        PaymentRequest request2 = new PaymentRequest();
        request2.setOrderNumber(request.getOrderNumber());
        request2.setPaid(true);
        
        // act
		sut.setPaidStatus( request2 );
		sut.setHoroscopeFetched(request.getOrderNumber());

        // assert
        assertTrue("true expected", sut.findOrderByOrderNumber(100).isPaid());
    }
    
    
    

    @Test
    public void throwsValidationErrorForSavingAnEmptyOrder()
    {
        // arrange
        Order request = new Order();
        Locale.setDefault(Locale.ENGLISH);

        try {
            // act
            sut.saveOrder(request);
            fail("Expected exception was not thrown!");
        } catch (HOOServiceException e) {
            // assert
            assertEquals("Error type", HOOErrorType.MISSING_INPUT, e.getErrorType());
            assertMessageContains(e.getMessage(), "MISSING_INPUT Order");
        }
    }


    @Test
    public void findsAllOrders()
    {
        // arrange
        Order request = new Order();
        request.setCustomerName("bubu");
        request.setOrderNumber(1);
        request.setAgeOfTargetPerson(33);
        request.setHoroscopeType(HoroscopeType.JOB);
        request.setCreationDateTime(XMLGregorianCalendarUtil.toGregorianCaldendar(new Date(1486723374944L)));  // long value represents 10.02.2017 11:42:54
        request.setPrice(new BigDecimal("9.99"));
        request.setPaid(false);
        request.setHoroscopeFetched(false);
        sut.saveOrder(request);
        request.setOrderNumber(2);
        sut.saveOrder(request);
        
        // act
        OrderList result = sut.findAll();

        // assert
        assertEquals("result", 2, result.getOrders().size());
    }


    private void assertMessageContains(String message, String toFind) {
        assertTrue("Message does not contain '" + toFind + "'!", message.contains(toFind));
    }
}