set tomcatHome="C:\dev\utils\tomcat\apache-tomcat-8.5.11"
set serviceName="HelloWorldSoapService"

rd %tomcatHome%\webapps\%serviceName% /s /q

del %tomcatHome%\webapps\%serviceName%.war

copy target\%serviceName%.war %tomcatHome%\webapps\%serviceName%.war
