module hoo.horoscope 
{
    exports com.iksgmbh.demo.hoo.horoscope.api;
    
    requires java.sql;
    
    requires hoo.order;
    
    requires sqlpojomemodb;
}