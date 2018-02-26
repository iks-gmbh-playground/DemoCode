package com.iksgmbh.demo.spring.boot.thymeleaf.helloworld;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iksgmbh.demo.spring.boot.thymeleaf.helloworld.data.GreetingData;
import com.iksgmbh.demo.spring.boot.thymeleaf.helloworld.data.UserLoginData;

@Controller
@RequestMapping(value="hello/world")
public class HelloWorldController 
{
	@RequestMapping(value="/form", 
			        method=RequestMethod.GET)
	public String form(GreetingData greetingData) {
		return "form";
	}
	
	@RequestMapping(value = "/performLogin", method = RequestMethod.POST)
	public String performLogin(@Valid UserLoginData userLoginData, 
			                   BindingResult bindingResult, 
			                   Model model) {
		if ("Bob".equals(userLoginData.getUsername())) {
			if ("123".equals(userLoginData.getPassword())) {
				model.addAttribute("greetingData", new GreetingData());
				return "redirect:form";
			}
		}
		model.addAttribute("error", "Invalid User Login Data.");
		return loginError(model);
	}
	
	
	@RequestMapping(value="/result", 
			        method = RequestMethod.POST)
	public String performPostRequest(@Valid GreetingData greetingData, 
			                         BindingResult bindingResult, 
			                         Model model) 
	{
		if (bindingResult.hasErrors()) {
			return "form";
		}
		
		if (greetingData.getGreeting().length() == 0) greetingData.setGreeting("Hello");
		model.addAttribute("greeting", greetingData.getGreeting());
		if (greetingData.getName().length() == 0) greetingData.setName("World");
		model.addAttribute("name", greetingData.getName());
		
		return "result";
	}
	
	@RequestMapping("/login")
	public String login(UserLoginData userLoginData) {
	    return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(UserLoginData userLoginData) {
		userLoginData.setPassword(null);
		userLoginData.setUsername(null);
	    return "login";
	}


	@RequestMapping("/loginerror")
	public String loginError(Model model) {
		model.addAttribute("error", "Invalid User Login Data.");
	    return "error";
	}
}
