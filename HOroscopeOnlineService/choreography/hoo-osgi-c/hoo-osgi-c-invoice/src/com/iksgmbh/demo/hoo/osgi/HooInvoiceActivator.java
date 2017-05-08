package com.iksgmbh.demo.hoo.osgi;

import java.util.Dictionary;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.iksgmbh.demo.hoo.invoice.InvoiceStoreImpl;
import com.iksgmbh.demo.hoo.invoice.api.InvoiceStore;

/**
 * Starter class that registers InvoiceFactory at the OSGi Service Registry
 * where it is fetched from the OnlineHoroscopeService.
 * 
 * @author Reik Oberrath
 */
public class HooInvoiceActivator implements BundleActivator {

	private static BundleContext context;

	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		HooInvoiceActivator.context = bundleContext;
		
		// register InvoiceStore as OSGi service
		InvoiceStore store = new InvoiceStoreImpl(); 
		Dictionary<String, ?> configuration = null; // no configuration data needed 
		bundleContext.registerService(InvoiceStore.class.getName(), store, configuration);	
	}

	public void stop(BundleContext bundleContext) throws Exception {
		HooInvoiceActivator.context = null;
	}

}
