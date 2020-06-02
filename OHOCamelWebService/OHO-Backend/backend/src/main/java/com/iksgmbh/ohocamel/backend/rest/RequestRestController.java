/*
 * Copyright 2020 IKS Gesellschaft fuer Informations- und Kommunikationssysteme mbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iksgmbh.ohocamel.backend.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iksgmbh.oho.backend.HoroscopeRequestData;
import com.iksgmbh.ohocamel.backend.camel.CamelService;

@RestController
@CrossOrigin(origins = {"*"})
public class RequestRestController
{
	@Autowired
	private CamelService camelService;

	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = { "plain/text" })
    public ResponseEntity<?> createHoroscope(@Valid @RequestBody HoroscopeRequestData requestData) {
		String responseData = camelService.getHtmlHoroscope(requestData);
		return ResponseEntity.ok(responseData);
    }

	@RequestMapping(value = "/demo", method = RequestMethod.GET, produces = { "html/text" })
	public String demoHoroscope() {
		HoroscopeRequestData requestData = new HoroscopeRequestData();
		requestData.setName("World");
		requestData.setGender("d");
		requestData.setBirthday("01.01.0000");
		return camelService.getHtmlHoroscope(requestData);

	}

}