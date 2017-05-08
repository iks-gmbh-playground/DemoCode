package com.iksgmbh.demo.hoo.api.utils;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.iksgmbh.demo.hoo.api.utils.XMLGregorianCalendarUtil;

public class XMLGregorianCalendarUtilTest {

	@Test
	public void convertsDateToPlainText() {
		
		Date date = new Date(1486720642933L);
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		assertEquals("date string", "10.02.2017 10:57:22", XMLGregorianCalendarUtil.toString(date, formatter));
	}

}
