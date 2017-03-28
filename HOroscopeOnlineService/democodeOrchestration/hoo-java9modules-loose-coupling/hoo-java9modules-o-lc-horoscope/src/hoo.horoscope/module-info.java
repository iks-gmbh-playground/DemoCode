module hoo.horoscope 
{
    exports com.iksgmbh.demo.hoo.horoscope.api;
    
    requires java.sql;
    requires sqlpojomemodb;
    
    requires hoo.order;
}