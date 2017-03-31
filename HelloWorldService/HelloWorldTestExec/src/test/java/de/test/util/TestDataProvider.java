package de.test.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.test.api.autogen.SayHelloRequest;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.utils.XMLGregorianCalendarUtil;

public class TestDataProvider {

	private static final String STANDARD_TEST_NAME = "StandardTestName";
	private static SimpleDateFormat simpleDateFormat; 

	static {
		Locale.setDefault(Locale.ENGLISH);
		simpleDateFormat = new SimpleDateFormat("EEEE");

	}
	public static  SayHelloRequest createStandardSayHelloRequest() {
        SayHelloRequest toReturn = new SayHelloRequest();
		toReturn.setName( STANDARD_TEST_NAME );
		return toReturn;
	}

	public static SmallTalkCommentRequest createStandardSmallTalkCommentRequest() {
		SmallTalkCommentRequest toReturn = new SmallTalkCommentRequest();
		toReturn.setName( STANDARD_TEST_NAME );
		Date now = new Date();
		toReturn.setDate(XMLGregorianCalendarUtil.toGregorianCaldendar(now));
		return toReturn;
	}

	public static String getDayOfWeek() {
        return simpleDateFormat.format( new Date() );
	}	
}
