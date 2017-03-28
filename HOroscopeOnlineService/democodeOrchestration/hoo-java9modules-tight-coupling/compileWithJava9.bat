echo off

rem set java9 as VM
set path=C:\dev\java\jdk-9-160\bin;%path%

echo compiling hoo.order...
cd hoo-java9modules-o-tc-order
javac --module-path ../lib -d ../bin/hoo.order src/hoo.order/com/iksgmbh/demo/hoo/order/utils/DateStringUtil.java  
javac --module-path ../lib -d ../bin/hoo.order src/hoo.order/com/iksgmbh/demo/hoo/order/api/Order.java
javac --module-path ../lib -d ../bin/hoo.order src/hoo.order/module-info.java src/hoo.order/com/iksgmbh/demo/hoo/order/api/OrderStore.java
javac --module-path ../lib -d ../bin/hoo.order src/hoo.order/module-info.java src/hoo.order/com/iksgmbh/demo/hoo/order/OrderImpl.java
javac --module-path ../lib -d ../bin/hoo.order src/hoo.order/module-info.java src/hoo.order/com/iksgmbh/demo/hoo/order/OrderDAO.java
javac --module-path ../lib -d ../bin/hoo.order src/hoo.order/module-info.java src/hoo.order/com/iksgmbh/demo/hoo/order/OrderStoreImpl.java
cd ..


echo compiling hoo.horoscope...
cd hoo-java9modules-o-tc-horoscope
javac --module-path ../bin;../lib -d ../bin/hoo.horoscope src/hoo.horoscope/module-info.java src/hoo.horoscope/com/iksgmbh/demo/hoo/horoscope/api/Horoscope.java
javac --module-path ../bin;../lib -d ../bin/hoo.horoscope src/hoo.horoscope/module-info.java src/hoo.horoscope/com/iksgmbh/demo/hoo/horoscope/api/HoroscopeStore.java
javac --module-path ../bin;../lib -d ../bin/hoo.horoscope src/hoo.horoscope/module-info.java src/hoo.horoscope/com/iksgmbh/demo/hoo/horoscope/HoroscopeImpl.java
javac --module-path ../bin;../lib -d ../bin/hoo.horoscope src/hoo.horoscope/module-info.java src/hoo.horoscope/com/iksgmbh/demo/hoo/horoscope/HoroscopeDAO.java
javac --module-path ../bin;../lib -d ../bin/hoo.horoscope src/hoo.horoscope/module-info.java src/hoo.horoscope/com/iksgmbh/demo/hoo/horoscope/HoroscopeStoreImpl.java
cd ..



echo compiling hoo.invoice...
cd hoo-java9modules-o-tc-invoice
javac --module-path ../bin;../lib -d ../bin/hoo.invoice src/hoo.invoice/module-info.java src/hoo.invoice/com/iksgmbh/demo/hoo/invoice/api/Invoice.java
javac --module-path ../bin;../lib -d ../bin/hoo.invoice src/hoo.invoice/module-info.java src/hoo.invoice/com/iksgmbh/demo/hoo/invoice/api/InvoiceStore.java
javac --module-path ../bin;../lib -d ../bin/hoo.invoice src/hoo.invoice/module-info.java src/hoo.invoice/com/iksgmbh/demo/hoo/invoice/InvoiceImpl.java
javac --module-path ../bin;../lib -d ../bin/hoo.invoice src/hoo.invoice/module-info.java src/hoo.invoice/com/iksgmbh/demo/hoo/invoice/InvoiceDAO.java
javac --module-path ../bin;../lib -d ../bin/hoo.invoice src/hoo.invoice/module-info.java src/hoo.invoice/com/iksgmbh/demo/hoo/invoice/InvoiceStoreImpl.java
cd ..


echo compiling hoo.control...
cd hoo-java9modules-o-tc-control
javac --module-path ../bin;../lib -d ../bin/hoo.control src/hoo.control/module-info.java src/hoo.control/com/iksgmbh/demo/hoo/requestresponse/HOO_HoroscopeRequest.java
javac --module-path ../bin;../lib -d ../bin/hoo.control src/hoo.control/module-info.java src/hoo.control/com/iksgmbh/demo/hoo/requestresponse/HOO_HoroscopeResponse.java
javac --module-path ../bin;../lib -d ../bin/hoo.control src/hoo.control/module-info.java src/hoo.control/com/iksgmbh/demo/hoo/requestresponse/HOO_OrderRequest.java
javac --module-path ../bin;../lib -d ../bin/hoo.control src/hoo.control/module-info.java src/hoo.control/com/iksgmbh/demo/hoo/requestresponse/HOO_OrderResponse.java
javac --module-path ../bin;../lib -d ../bin/hoo.control src/hoo.control/module-info.java src/hoo.control/com/iksgmbh/demo/hoo/requestresponse/HOO_PaymentRequest.java
javac --module-path ../bin;../lib -d ../bin/hoo.control src/hoo.control/module-info.java src/hoo.control/com/iksgmbh/demo/hoo/HOroscopeOnlineService.java
cd ..

echo compiling hoo.systemtest...
cd hoo-java9modules-o-tc-systemtest
javac --module-path ../bin;../lib -d ../bin/hoo.systemtest src/hoo.systemtest/module-info.java src/hoo.systemtest/com/iksgmbh/demo/hoo/test/HOOSystemTest.java
cd ..

echo Done!
