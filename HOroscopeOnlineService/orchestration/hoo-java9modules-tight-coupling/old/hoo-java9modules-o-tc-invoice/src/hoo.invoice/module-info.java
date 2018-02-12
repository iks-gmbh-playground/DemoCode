module hoo.invoice 
{
    exports com.iksgmbh.demo.hoo.invoice.api;
    exports com.iksgmbh.demo.hoo.invoice;
    
    requires java.sql;
    requires sqlpojomemodb;
    
    requires hoo.order;
    requires hoo.horoscope;
}