package com.iksgmbh.demo.hoo.osgi;

import java.util.Dictionary;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.iksgmbh.demo.hoo.order.OrderStoreImpl;
import com.iksgmbh.demo.hoo.order.api.OrderStore;

/**
 * Starter class that registers OrderFactory at the OSGi Service Registry
 * where it is fetched from the OnlineHoroscopeService.
 * 
 * @author Reik Oberrath
 */
public class HooOrderActivator implements BundleActivator {

	private static BundleContext context;

	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		HooOrderActivator.context = bundleContext;
		
		// register OrderStore as OSGi service
		OrderStore store = new OrderStoreImpl(); 
		Dictionary<String, ?> configuration = null; // no configuration data needed 
		bundleContext.registerService(OrderStore.class.getName(), store, configuration);		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		HooOrderActivator.context = null;
	}

}
