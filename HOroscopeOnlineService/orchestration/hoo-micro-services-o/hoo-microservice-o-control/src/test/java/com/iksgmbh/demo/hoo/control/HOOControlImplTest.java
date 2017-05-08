package com.iksgmbh.demo.hoo.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Test;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOServiceException.HOOErrorType;
import com.iksgmbh.demo.hoo.control.api.HOOOrderRequest;
import com.iksgmbh.demo.hoo.control.api.HOOOrderResponse;

public class HOOControlImplTest
{
    private HOroscopeOnlineService sut = new HOOControlTestImpl();

    @Test
    public void createsSuccessfullHOOOrderResponse()
    {
        // arrange
        HOOOrderRequest request = new HOOOrderRequest();
        request.setCustomerName("aCustomer");
        request.setBirthdayOfTargetPerson("1.1.1999");
        request.setHoroscopeType("JOB");

        // act
        HOOOrderResponse result = sut.sendOrder(request);

        // assert
        assertEquals("result", "Test Bill", result.getBill());
    }

    @Test
    public void throwsValidationErrorForMissingInputDataInHOOOrderResponse()
    {
        // arrange
        HOOOrderRequest request = new HOOOrderRequest();
        Locale.setDefault(Locale.ENGLISH);

        try {
            // act
            sut.sendOrder(request);
            fail("Expected exception was not thrown!");
        } catch (HOOServiceException e) {
            // assert
            assertEquals("Error type", HOOErrorType.MISSING_INPUT, e.getErrorType());
            System.out.println(e.getMessage());
            assertMessageContains(e.getMessage(), "MISSING_INPUT HOOOrderRequest!");
            assertMessageContains(e.getMessage(), "The content of element 'HOO_OrderRequest' is not complete.");
            assertMessageContains(e.getMessage(), "One of '{\"api.control.hoo.demo.iksgmbh.com\":customerName}' is expected.");
        }
    }


    
    @Test
    public void createsSuccessfullString()
    {
    	// arrange
        HOOOrderRequest request = new HOOOrderRequest();
        request.setCustomerName("aCustomer");
        request.setBirthdayOfTargetPerson("1.1.1999");
        request.setHoroscopeType("JOB");
        sut.sendOrder(request);
    	
        // act
        String result = sut.findAllOrders();

        // assert
        assertTrue(result.contains("<Order xmlns=\"api.order.hoo.demo.iksgmbh.com\">"));
    }


    private void assertMessageContains(String message, String toFind) {
        assertTrue("Message does not contain '" + toFind + "'!", message.contains(toFind));
    }
}