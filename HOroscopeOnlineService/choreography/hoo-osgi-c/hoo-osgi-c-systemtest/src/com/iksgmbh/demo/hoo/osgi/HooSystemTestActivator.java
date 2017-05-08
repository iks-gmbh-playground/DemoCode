package com.iksgmbh.demo.hoo.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Starter class that registers OrderFactory at the OSGi Service Registry
 * where it is fetched from the OnlineHoroscopeService.
 * 
 * @author Reik Oberrath
 */
public class HooSystemTestActivator implements BundleActivator {

	private static BundleContext context;

	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		HooSystemTestActivator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		HooSystemTestActivator.context = null;
	}

}
