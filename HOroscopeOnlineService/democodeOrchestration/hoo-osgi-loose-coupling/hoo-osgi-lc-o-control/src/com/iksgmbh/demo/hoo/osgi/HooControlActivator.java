package com.iksgmbh.demo.hoo.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Starter class that stores the instance of the bundle context which is
 * used in the OnlineHoroscopeService class to get the store instances
 * of the order, horoscope and invoice bundle.
 * 
 * @author Reik Oberrath
 */
public class HooControlActivator implements BundleActivator 
{
	private static BundleContext BUNDLE_CONTEXT;

	public static BundleContext getContext() {
		return BUNDLE_CONTEXT;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		BUNDLE_CONTEXT = bundleContext;
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		BUNDLE_CONTEXT = null;
	}
}
