package com.iksgmbh.demo.hoo.invoice;

import java.sql.SQLException;

import com.iksgmbh.demo.hoo.horoscope.Horoscope;
import com.iksgmbh.demo.hoo.order.Order;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for invoices.
 * 
 * @author Reik Oberrath
 */
public class InvoiceStoreImpl implements InvoiceStore 
{
	private InvoiceDAO invoiceDAO = new InvoiceDAO(SqlPojoMemoDB.getConnection());
	
	@Override
	public Invoice createNewInstance(Order aOrder, Horoscope aHoroscope) {
		return new InvoiceImpl(aOrder, aHoroscope);
	}
	
	@Override
	public void save(final Invoice invoice) 
	{
		try {
			invoiceDAO.save(invoice);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
}
