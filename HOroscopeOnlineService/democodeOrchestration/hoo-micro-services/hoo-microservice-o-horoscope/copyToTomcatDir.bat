set tomcatHome="C:\dev\utils\tomcat\apache-tomcat-8.5.11"

rd %tomcatHome%\webapps\HOOServiceHoroscope /s /q

del %tomcatHome%\webapps\HOOServiceHoroscope.war

copy target\HOOServiceHoroscope.war %tomcatHome%\webapps\HOOServiceHoroscope.war