package com.jambit.workshop.jib.spring.data.mongo.converter;

import com.jambit.workshop.jib.spring.data.mongo.domain.OrderStatus;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;

/**
 * Converter for converting objects of type <code>OrderStatus</code> to the mongodb.
 */
public class OrderStatusWriteConverter implements Converter<OrderStatus, DBObject> {
    @Override
    public DBObject convert(final OrderStatus source) {
        final DBObject dbo = new BasicDBObject();
        dbo.put("status", source.getStatusId());
        dbo.put("name", source.name());
        return dbo;
    }
}
