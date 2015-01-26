package com.jambit.workshop.jib.spring.data.mongo.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jambit.workshop.jib.spring.data.mongo.domain.Product;

import java.io.IOException;

/**
 * JsonDeserializer for <code>Product</code>
 */
public final class ProductJsonDeserializer extends JsonDeserializer<Product> {
  @Override
  public Product deserialize(final JsonParser jsonParser, final DeserializationContext context) throws IOException, JsonProcessingException {
    final ObjectCodec codec = jsonParser.getCodec();
    final JsonNode node = codec.readTree(jsonParser);
    return createProduct(node);
  }

  private Product createProduct(final JsonNode node) {
    final String id = getNodeValue(node, "id", null);
    final String name = getNodeValue(node, "name", "");
    final String desc = getNodeValue(node, "description", "");
    int priceInCents = getNodeValue(node, "price", 0);
    return new Product(id, name, desc, priceInCents);
  }

  private int getNodeValue(final JsonNode root, final String field, int fallback) {
    final JsonNode child = root.get(field);
    return null != child ? child.asInt() : fallback;
  }


  private String getNodeValue(final JsonNode root, final String field, final String fallback) {
    final JsonNode child = root.get(field);
    return null != child ? child.asText() : fallback;
  }

}
