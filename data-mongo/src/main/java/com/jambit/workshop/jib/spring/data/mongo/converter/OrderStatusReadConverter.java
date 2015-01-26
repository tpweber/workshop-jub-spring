package com.jambit.workshop.jib.spring.data.mongo.converter;

import com.jambit.workshop.jib.spring.data.mongo.domain.OrderStatus;
import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;

/**
 * Converter for converting objects of type <code>OrderStatus</code> from the mongodb.
 */
public class OrderStatusReadConverter implements Converter<DBObject, OrderStatus> {

    @Override
    public OrderStatus convert(final DBObject source) {
        return OrderStatus.getByStatusId((Integer) source.get("status"));
    }
}
