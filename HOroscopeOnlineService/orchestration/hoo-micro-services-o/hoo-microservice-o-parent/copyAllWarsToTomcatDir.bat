echo Copying wars to TomCat...
echo.

cd ..
cd hoo-microservice-o-order
call copyToTomcatDir

cd ..
cd hoo-microservice-o-horoscope
call copyToTomcatDir

cd ..
cd hoo-microservice-o-invoice
call copyToTomcatDir

cd ..
cd hoo-microservice-o-control
call copyToTomcatDir

echo.
echo Done!