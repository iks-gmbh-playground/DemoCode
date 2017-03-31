cd ../HelloWorldServiceJavaClient
call mvn clean install

cd ../HelloWorldWebClient
call mvn install

echo.
echo.
echo ########################################
echo.
echo.

echo Deploying HelloWorldWebClient on Tomcat...
 
call copyToTomcatDir.bat

echo Done.