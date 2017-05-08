echo Copying wars to TomCat...
echo.

cd ..
cd hoo-microservice-c-order
call copyToTomcatDir

cd ..
cd hoo-microservice-c-horoscope
call copyToTomcatDir

cd ..
cd hoo-microservice-c-invoice
call copyToTomcatDir

echo.
echo Done!