package com.iksgmbh.demo.hoo.invoice;

import java.sql.SQLException;

import com.iksgmbh.demo.hoo.horoscope.Horoscope;
import com.iksgmbh.demo.hoo.invoice.dao.InvoiceDAO;
import com.iksgmbh.demo.hoo.order.Order;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for invoices.
 * 
 * @author Reik Oberrath
 */
public class InvoiceStore
{
	private InvoiceDAO invoiceDAO = new InvoiceDAO(SqlPojoMemoDB.getConnection());
	
	public Invoice createNewInstance(Order aOrder, Horoscope aHoroscope) {
		return new Invoice(aOrder, aHoroscope);
	}
	
	public void save(final Invoice invoice) 
	{
		try {
			invoiceDAO.save(invoice);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
}
