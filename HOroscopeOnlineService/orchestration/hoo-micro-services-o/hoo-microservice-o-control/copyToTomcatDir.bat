set tomcatHome="C:\dev\utils\tomcat\apache-tomcat-8.5.11"

rd %tomcatHome%\webapps\HOOServiceControl /s /q

del %tomcatHome%\webapps\HOOServiceControl.war

copy target\HOOServiceControl.war %tomcatHome%\webapps\HOOServiceControl.war