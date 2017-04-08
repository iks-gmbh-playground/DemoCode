echo off
set JAVA_HOME="C:\dev\java\jdk1.8.0_65"

set mavenHome="C:\dev\maven\apache-maven-3.2.5"

rem call %mavenHome%\bin\mvn clean install

cd..


echo.
echo.
echo ########################################
echo.
echo.
echo Deploying HelloWorldSoapService on Tomcat...

cd HelloWorldServiceSoap
call copyToTomcatDir.bat
cd..

echo.
echo.
echo ########################################
echo.
echo.
echo Deploying HelloWorldRestService on Tomcat...

cd HelloWorldServiceRest
call copyToTomcatDir.bat
cd..

echo.
echo.
echo ########################################
echo.
echo.
echo Deploying SmallTalkCommentSoapService on Tomcat...

cd SmallTalkCommentSoapService
call copyToTomcatDir.bat
cd..

echo.
echo.
echo ########################################
echo.
echo.
echo Deploying HelloWorldWebClient on Tomcat...

cd HelloWorldWebClient
call copyToTomcatDir.bat
cd..


echo.
echo.
echo ########################################
echo.
echo.

echo If your Tomcat is running locally then call following URL in your web browser:
echo "http://localhost:8080/HelloWorldWebClient/"
