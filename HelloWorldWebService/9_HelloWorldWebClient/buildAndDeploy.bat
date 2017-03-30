cd ../7_HelloWorldServiceJavaClient
call mvn clean install

cd ../9_HelloWorldWebClient
call mvn install

echo.
echo.
echo ########################################
echo.
echo.

echo Deploying HelloWorldWebClient on Tomcat...
 
call copyToTomcatDir.bat

echo Done.