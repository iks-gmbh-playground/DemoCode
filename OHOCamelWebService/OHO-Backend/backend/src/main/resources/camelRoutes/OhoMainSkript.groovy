horoscopeRequestData = exchange.getProperty("HoroscopeRequestData");
name = horoscopeRequestData.getGender();
exchange.setProperty("HoroscopeResponseData", "Hello &lt;i&gt;" + name + ".&lt;/i&gt;");
