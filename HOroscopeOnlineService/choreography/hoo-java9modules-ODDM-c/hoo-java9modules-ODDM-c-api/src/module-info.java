module hoo.api 
{
	exports com.iksgmbh.demo.hoo.api.requestresponse;
	exports com.iksgmbh.demo.hoo.api.order;
	exports com.iksgmbh.demo.hoo.api.invoice;
	exports com.iksgmbh.demo.hoo.api.horoscope;
	
	// needed for the spring framework
	requires transitive spring.context;
	requires transitive spring.beans;
	requires transitive spring.core;
	requires transitive spring.aop;
	requires transitive spring.expression;
	requires transitive commons.logging;
}
