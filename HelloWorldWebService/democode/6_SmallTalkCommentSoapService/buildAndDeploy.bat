call mvn clean install

echo.
echo.
echo ########################################
echo.
echo.

echo Deploying SmallTalkCommentService on Tomcat...

call copyToTomcatDir.bat

echo Done.