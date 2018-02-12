module hoo.horoscope 
{
    exports com.iksgmbh.demo.hoo.horoscope.api;
    exports com.iksgmbh.demo.hoo.horoscope;
    
    requires java.sql;
    requires sqlpojomemodb;
    
    requires hoo.order;
}