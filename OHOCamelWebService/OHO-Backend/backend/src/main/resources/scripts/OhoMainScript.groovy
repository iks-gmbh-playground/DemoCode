import com.iksgmbh.ohocamel.backend.utils.Random;
import com.iksgmbh.ohocamel.backend.utils.DateUtil;
import com.iksgmbh.ohocamel.backend.utils.HoroscopeUtil;

requestData = exchange.getProperty("HoroscopeRequestData");
StringBuffer html = new StringBuffer("<section style=\&quot;background:" + requestData.getFavouriteColor() + "\&quot;>");
String sep = System.getProperty("line.separator");
html.append(sep);

if ( ! DateUtil.isDateValid(requestData.getBirthday()) ) {
	html.append("<p>Hello <i>").append(requestData.getName()).append("</i>,</p>").append(sep);
	html.append("<p>I cannot read your birthday. Use one of the formats <b>yyyy.MM.dd</b> or <b>dd.MM.yyyy</b> to tell me when you're born.").append(sep);
	html.append("<p>Try again with a reasonable birthday value!").append(sep);
} else {
	
	int age = DateUtil.getAge(requestData.getBirthday());
	if (age < 0) {
		html.append("Nice try to trick me! Please enter no future date!").append(sep);
	} else if (age < 6) {
		html.append("<p>How are you, <i>").append(requestData.getName()).append("</i>?</p>").append(sep);
		html.append("<p>As a " + age + "-year-old you can read and write. A wonder child, eh? Sorry, I cannot see the future for wonder childen!").append(sep);
		html.append("<p>Bye <i>Scarlet</i></p>").append(sep);
	} else if (age > 120) {
		html.append("<p>How are you, <i>").append(requestData.getName()).append("</i>?</p>").append(sep);
		html.append("<p>In your age of " + age + " years each day is a death trap, but always keep in mind that life is live!").append(sep);
		html.append("<p>Bye-bye <i>Scarlet</i></p>").append(sep);
	} else {		
		html.append("<p>Hello <i>").append(requestData.getName()).append("</i>,</p>").append(sep);
		executeScript:OhoCoreScript.groovy
		html.append("<p>Yours <i>Scarlet</i></p>").append(sep);
		
		if (DateUtil.isToday(requestData.getBirthday())) {
			html.append("<p><b>PS: Happy Birthday! :-)</b></p>").append(sep);
		}
	} 
}
html.append("</section>");
exchange.setProperty("HoroscopeResponseData", html.toString());