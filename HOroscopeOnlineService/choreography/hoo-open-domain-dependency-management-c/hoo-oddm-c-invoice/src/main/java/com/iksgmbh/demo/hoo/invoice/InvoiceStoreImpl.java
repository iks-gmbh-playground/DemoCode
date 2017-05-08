package com.iksgmbh.demo.hoo.invoice;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iksgmbh.demo.hoo.horoscope.Horoscope;
import com.iksgmbh.demo.hoo.order.Order;
import com.iksgmbh.demo.hoo.order.OrderStore;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for invoices.
 * 
 * @author Reik Oberrath
 */
@Service
public class InvoiceStoreImpl implements InvoiceStore 
{
	private InvoiceDAO invoiceDAO = new InvoiceDAO(SqlPojoMemoDB.getConnection());
	
	@Autowired
	private OrderStore orderStore;	
	
	
	@Override
	public void createNewInstance(Order aOrder, Horoscope aHoroscope) {
		Invoice invoice = new InvoiceImpl(aOrder, aHoroscope);
		save(invoice);
		saveInvoiceDataWithOrder(invoice);
	}
	
	private void saveInvoiceDataWithOrder(Invoice invoice) {
		orderStore.saveInvoiceData(invoice);
	}

	private void save(final Invoice invoice) 
	{
		try {
			invoiceDAO.save(invoice);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
