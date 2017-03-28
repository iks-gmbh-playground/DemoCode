package com.iksgmbh.demo.hoo.osgi;

import java.util.Dictionary;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.iksgmbh.demo.hoo.horoscope.HoroscopeStoreImpl;
import com.iksgmbh.demo.hoo.horoscope.api.HoroscopeStore;

/**
 * Starter class that registers HoroscopeFactory at the OSGi Service Registry
 * where it is fetched from the OnlineHoroscopeService.
 * 
 * @author Reik Oberrath
 */
public class HooHoroscopeActivator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		HooHoroscopeActivator.context = bundleContext;
		
		// register HoroscopeStore as OSGi service
		HoroscopeStore store = new HoroscopeStoreImpl(); 
		Dictionary<String, ?> configuration = null; // no configuration data needed 
		bundleContext.registerService(HoroscopeStore.class.getName(), store, configuration);	
	}

	public void stop(BundleContext bundleContext) throws Exception {
		HooHoroscopeActivator.context = null;
	}

}
