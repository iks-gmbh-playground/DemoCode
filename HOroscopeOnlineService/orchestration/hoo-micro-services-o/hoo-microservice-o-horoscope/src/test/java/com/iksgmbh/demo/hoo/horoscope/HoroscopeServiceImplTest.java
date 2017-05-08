package com.iksgmbh.demo.hoo.horoscope;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOServiceException.HOOErrorType;
import com.iksgmbh.demo.hoo.api.utils.XMLGregorianCalendarUtil;
import com.iksgmbh.demo.hoo.horoscope.HoroscopeStore;
import com.iksgmbh.demo.hoo.horoscope.api.CreateHoroscopeRequest;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;
import com.iksgmbh.demo.hoo.order.api.Order;



public class HoroscopeServiceImplTest
{
    private  HoroscopeStore sut = new HoroscopeStore();

    @Test
    public void createsSuccessfullHoroscope()
    {
        // arrange
        CreateHoroscopeRequest request = new CreateHoroscopeRequest();
        Order order = new Order();
        order.setOrderNumber(1);
        order.setAgeOfTargetPerson(10);
        order.setHoroscopeType(HoroscopeType.LOVE);
        order.setCustomerName("aCustomer");
        order.setCreationDateTime(XMLGregorianCalendarUtil.toGregorianCaldendar(new Date()));
        request.setOrder(order);

        // act
        sut.createHoroscope(request);
        Horoscope result = sut.findHoroscopeByOrderNumber(1);

        // assert
        assertEquals("result", "Don't worry, be happy.", result.getHoroscopeText());
    }

    @Test
    public void throwsValidationErrorForMissingInputDataInCreateHoroscopeRequest()
    {
        // arrange
        CreateHoroscopeRequest request = new CreateHoroscopeRequest();
        Locale.setDefault(Locale.ENGLISH);

        try {
            // act
            sut.createHoroscope(request);
            fail("Expected exception was not thrown!");
        } catch (HOOServiceException e) {
            // assert
        	System.out.println(e.getMessage());
            assertEquals("Error type", HOOErrorType.MISSING_INPUT, e.getErrorType());
            assertMessageContains(e.getMessage(), "MISSING_INPUT CreateHoroscopeRequest!");
            assertMessageContains(e.getMessage(), "The content of element 'ns2:CreateHoroscopeRequest' is not complete.");
            assertMessageContains(e.getMessage(), "One of '{\"api.order.hoo.demo.iksgmbh.com\":Order}' is expected.");
        }
    }


    private void assertMessageContains(String message, String toFind) {
        assertTrue("Message does not contain '" + toFind + "'!", message.contains(toFind));
    }
}