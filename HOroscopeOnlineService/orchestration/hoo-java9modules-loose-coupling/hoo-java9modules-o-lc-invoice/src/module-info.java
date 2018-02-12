module hoo.invoice 
{
    exports com.iksgmbh.demo.hoo.invoice.api;
    
    requires java.sql;
    requires sqlpojomemodb;
    
    requires hoo.order;
    requires hoo.horoscope;
}