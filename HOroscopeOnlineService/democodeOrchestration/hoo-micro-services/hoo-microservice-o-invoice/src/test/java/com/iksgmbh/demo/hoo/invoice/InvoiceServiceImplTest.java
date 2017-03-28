package com.iksgmbh.demo.hoo.invoice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOServiceException.HOOErrorType;
import com.iksgmbh.demo.hoo.api.utils.XMLGregorianCalendarUtil;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.invoice.api.CreateInvoiceRequest;
import com.iksgmbh.demo.hoo.invoice.api.Invoice;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;
import com.iksgmbh.demo.hoo.order.api.Order;


public class InvoiceServiceImplTest
{
    private  InvoiceStore sut = new  InvoiceStore();

    @Test
    public void createsSuccessfullInvoice()
    {
        // arrange
        final CreateInvoiceRequest request = new CreateInvoiceRequest();
        Order order = new Order();
        order.setOrderNumber(1234);
        order.setCustomerName("aCustomer");
        order.setAgeOfTargetPerson(10);
        order.setHoroscopeType(HoroscopeType.LOVE);
        order.setCreationDateTime(XMLGregorianCalendarUtil.toGregorianCaldendar(new Date()));
        request.setOrder(order);  
        final Horoscope horoscope = new Horoscope();
        horoscope.setOrderNumber(order.getOrderNumber());
        horoscope.setHoroscopeType(order.getHoroscopeType());
        horoscope.setHoroscopeText("Beware!");
        horoscope.setInvoiceFactor(BigDecimal.ONE);
        request.setHoroscope(horoscope);

        // act
        final Invoice result1 = sut.createInvoice(request);
        final Invoice result2 = sut.findInvoiceByOrderNumber(order.getOrderNumber());

        // assert
        assertEquals("result", "Please pay 5.49 Euro.", result1.getBill());
        assertEquals("result", "Please pay 5.49 Euro.", result2.getBill());
    }

    @Test
    public void throwsValidationErrorForMissingInputDataInInvoice()
    {
        // arrange
        CreateInvoiceRequest request = new CreateInvoiceRequest();
        Locale.setDefault(Locale.ENGLISH);

        try {
            // act
            sut.createInvoice(request);
            fail("Expected exception was not thrown!");
        } catch (HOOServiceException e) {
            // assert
        	System.out.println(e.getMessage());
            assertEquals("Error type", HOOErrorType.MISSING_INPUT, e.getErrorType());
            assertMessageContains(e.getMessage(), "MISSING_INPUT CreateInvoiceRequest!");
            assertMessageContains(e.getMessage(), "The content of element 'ns3:CreateInvoiceRequest' is not complete.");
            assertMessageContains(e.getMessage(), "One of '{\"api.order.hoo.demo.iksgmbh.com\":Order}' is expected.");
        }
    }


    private void assertMessageContains(String message, String toFind) {
        assertTrue("Message does not contain '" + toFind + "'!", message.contains(toFind));
    }
}