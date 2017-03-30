echo off
set JAVA_HOME="C:\dev\java\jdk1.8.0_65"

set mavenHome="C:\dev\maven\apache-maven-3.2.5"

call %mavenHome%\bin\mvn clean install

cd..


echo.
echo.
echo ########################################
echo.
echo.
echo Deploying HelloWorldSoapService on Tomcat...

cd 4_HelloWorldServiceSoap
call copyToTomcatDir.bat
cd..

echo.
echo.
echo ########################################
echo.
echo.
echo Deploying HelloWorldRestService on Tomcat...

cd 5_HelloWorldServiceRest
call copyToTomcatDir.bat
cd..

echo.
echo.
echo ########################################
echo.
echo.
echo Deploying SmallTalkCommentSoapService on Tomcat...

cd 6_SmallTalkCommentSoapService
call copyToTomcatDir.bat
cd..

echo.
echo.
echo ########################################
echo.
echo.
echo Deploying HelloWorldWebClient on Tomcat...

cd 9_HelloWorldWebClient
call copyToTomcatDir.bat
cd..


echo.
echo.
echo ########################################
echo.
echo.

echo If your Tomcat is running locally then call following URL in your web browser:
echo "http://localhost:<tomcatPort>/HelloWorldWebClient/"
