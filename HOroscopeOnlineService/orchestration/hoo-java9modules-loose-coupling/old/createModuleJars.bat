echo off

set path=C:\dev\java\jdk-9-160\bin;%path%

echo Creating module jar files...

jar --create --file=lib/order.jar -C hoo-java9modules-order .
jar --create --file=lib/horoscope.jar -C hoo-java9modules-horoscope . 
jar --create --file=lib/invoice.jar -C hoo-java9modules-invoice . 
jar --create --file=lib/control.jar -C hoo-java9modules-control . 
jar --create --file=lib/systemtest.jar -C hoo-java9modules-systemtest .  
 
echo Done.