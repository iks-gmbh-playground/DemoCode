set tomcatHome="C:\dev\utils\tomcat\apache-tomcat-8.5.11"

rd %tomcatHome%\webapps\HOOServiceInvoice /s /q

del %tomcatHome%\webapps\HOOServiceInvoice.war

copy target\HOOServiceInvoice.war %tomcatHome%\webapps\HOOServiceInvoice.war