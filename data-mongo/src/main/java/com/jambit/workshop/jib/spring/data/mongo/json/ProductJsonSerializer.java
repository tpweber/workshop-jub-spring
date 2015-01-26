package com.jambit.workshop.jib.spring.data.mongo.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jambit.workshop.jib.spring.data.mongo.domain.Product;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * JsonSerializer for <code>Product</code>
 */
public final class ProductJsonSerializer extends JsonSerializer<Product> {
  @Override
  public void serialize(final Product product, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeObjectField("id", product.getId());
    jsonGenerator.writeStringField("name", StringUtils.defaultString(product.getProductName(), ""));
    jsonGenerator.writeStringField("description", StringUtils.defaultString(product.getDescription(), ""));
    jsonGenerator.writeNumberField("price", product.getPriceInCents());
    jsonGenerator.writeEndObject();
  }
}
