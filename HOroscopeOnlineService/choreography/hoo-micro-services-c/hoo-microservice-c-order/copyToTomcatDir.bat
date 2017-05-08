set tomcatHome="C:\dev\utils\tomcat\apache-tomcat-8.5.11"

rd %tomcatHome%\webapps\HOOServiceOrder /s /q

del %tomcatHome%\webapps\HOOServiceOrder.war

copy target\HOOServiceOrder.war %tomcatHome%\webapps\HOOServiceOrder.war