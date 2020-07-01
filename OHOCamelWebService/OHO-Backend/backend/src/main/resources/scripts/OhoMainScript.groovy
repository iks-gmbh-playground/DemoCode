requestData = exchange.getProperty("HoroscopeRequestData");
StringBuffer html = new StringBuffer();
String sep = System.getProperty("line.separator");

html.append("<p>Hello <i>").append(requestData.getName()).append("</i>,</p>").append(sep);
executeScript:OhoCoreScript.groovy
html.append("<p>Yours <i>Scarlet</i></p>").append(sep);
exchange.setProperty("HoroscopeResponseData", html.toString());