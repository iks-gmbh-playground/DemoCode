package com.iksgmbh.ohocamel.backend;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.iksgmbh.oho.backend.HoroscopeRequestData;
import com.iksgmbh.ohocamel.backend.camel.CamelService;

@TestPropertySource("classpath:application-test.properties")
@ExtendWith(SpringExtension.class)  // RestDocumenationExtension ?
@SpringBootTest
public class CamelServiceTest 
{	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void getTestHoroscope()
	{
		// arrange
		HoroscopeRequestData data = new HoroscopeRequestData();
		data.setName("Tester");
		data.setBirthday("10.10.2010");
		data.setGender("f");
		
		CamelService service = createTestService();
		
		// act
		String result = service.getHtmlHoroscope(data);
		
		// assert
		assertEquals("Your PersonType is 'girl'.", result);
	}
	
	private CamelService createTestService() 
	{
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
		registry.removeBeanDefinition("GroovyScriptRouteProcessor");
		GenericBeanDefinition testBean = new GenericBeanDefinition();
        testBean.setBeanClass(GroovyScriptRouteTestProcessor.class);
		registry.registerBeanDefinition("GroovyScriptRouteProcessor", testBean);
		return (CamelService) applicationContext.getBean(CamelService.class);
	}
}