/**
 *  Copyright 2005-2014 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package com.iksgmbh.ohocamel.backend.camel.config;


import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.iksgmbh.ohocamel.backend.camel.CamelService;
import com.iksgmbh.ohocamel.backend.camel.processor.GroovyScriptRouteProcessor;

@Configuration
//@EnableConfigurationProperties(CamelConfigurationProperties.class)
public class CamelConfig implements CamelContextConfiguration 
{
	private static final Logger CAMEL_LOGGER = LoggerFactory.getLogger(CamelService.class);
	
	@Autowired
	private CamelContext camelContext;

	@Bean
	public org.slf4j.Logger camelLogger() { return CAMEL_LOGGER; }
	
	@Bean(name="GroovyScriptRouteProcessor")
	public GroovyScriptRouteProcessor groovyInjectScriptProcessor() {return new GroovyScriptRouteProcessor(); }
	
	@Override
	public void beforeApplicationStart(CamelContext camelContext) {
		// do nothing
	}

	@Override
	public void afterApplicationStart(CamelContext camelContext) {
		// do nothing
	}
	
	public CamelContext getCamelContext() {
		return camelContext;
	}
	
}