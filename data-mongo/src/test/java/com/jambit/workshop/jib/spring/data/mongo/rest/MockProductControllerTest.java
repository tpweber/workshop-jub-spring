package com.jambit.workshop.jib.spring.data.mongo.rest;

import com.jambit.workshop.jib.spring.data.mongo.domain.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/testApplicationContext.xml"})
@WebAppConfiguration
public class MockProductControllerTest extends AbstractJUnit4SpringContextTests {

    static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    WebApplicationContext mWebApplicationContext;
    @Autowired
    MockHttpSession session;
    @Autowired
    MockHttpServletRequest request;

    private MockMvc mMockMvcWithApplicationContext;

    @Before
    public void setup() {
        assertNotNull(mWebApplicationContext);
        assertTrue(mWebApplicationContext.containsBean("productController"));
        mMockMvcWithApplicationContext = MockMvcBuilders.webAppContextSetup(mWebApplicationContext).build();
    }

    private static String convertObjectToJson(final Product product) throws IOException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", product.getProductName());
        jsonObject.put("description", product.getDescription());
        jsonObject.put("priceInCents", product.getPriceInCents());
        if (product.hasValidId()) {
            jsonObject.put("id", product.getId());
        }
        return jsonObject.toJSONString();
    }

    private static Product convertJsonToObject(final String json) throws ParseException {
        final JSONParser parser = new JSONParser();
        final JSONObject parsed = (JSONObject) parser.parse(json);
        return createProduct(parsed);
    }

    private static Product createProduct(JSONObject parsed) {
        final Product product = new Product((String) parsed.get("id"));
        product.withDescription((String) parsed.get("description"));
        product.withName((String) parsed.get("name"));
        product.withPriceInCents(((Long) parsed.get("priceInCents")).intValue());
        return product;
    }

    private static List<Product> convertJsonToList(final String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray parsed = (JSONArray) parser.parse(json);
        int size = parsed.size();
        final List<Product> parsedProducts = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            JSONObject jo = (JSONObject) parsed.get(i);
            final Product product = createProduct(jo);
            parsedProducts.add(product);
        }
        return parsedProducts;
    }

    @Test
    public void testProductsAdd() throws Exception {
        Product productToAdd = new Product();
        productToAdd.withDescription("testProductsAdd_description").withName("testProductsAdd_name").withPriceInCents(8888);
        assertFalse(productToAdd.hasValidId());
        ResultActions resultActions = mMockMvcWithApplicationContext.perform(post("/products/add")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJson(productToAdd)));
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value("testProductsAdd_name"))
                .andExpect(jsonPath("$.description").value("testProductsAdd_description"))
                .andExpect(jsonPath("$.priceInCents").value(8888))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.optionalId").exists())
                .andExpect(jsonPath("$.optionalId.present").value(true));
    }

    @Test
    public void testGetExistingProduct() throws Exception {
        Product productToAdd = new Product();
        productToAdd.withDescription("testGetExistingProduct_description").withName("testGetExistingProduct_name").withPriceInCents(121212);
        assertFalse(productToAdd.hasValidId());
        ResultActions resultActions = mMockMvcWithApplicationContext.perform(post("/products/add")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJson(productToAdd)));
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").exists());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        final Product added = convertJsonToObject(contentAsString);
        ResultActions result = mMockMvcWithApplicationContext.perform(get("/products/" + added.getId())
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(result);
        result.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        result.andExpect(jsonPath("$.name").value("testGetExistingProduct_name"))
                .andExpect(jsonPath("$.description").value("testGetExistingProduct_description"))
                .andExpect(jsonPath("$.priceInCents").value(121212))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(added.getId()))
                .andExpect(jsonPath("$.optionalId").exists())
                .andExpect(jsonPath("$.optionalId.present").value(true));
    }

    @Test
    public void testFindProductByName() throws Exception {
        Product productToAdd = new Product();
        final String name = "testFindProductByName_name".concat(Instant.now().toString());
        productToAdd.withDescription("testFindProductByName_description").withName(name).withPriceInCents(121212);
        assertFalse(productToAdd.hasValidId());
        ResultActions resultActions = mMockMvcWithApplicationContext.perform(post("/products/add")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJson(productToAdd)));
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").exists());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        final Product added = convertJsonToObject(contentAsString);
        ResultActions result = mMockMvcWithApplicationContext.perform(get("/products/find").param("name", name)
                .accept(MediaType.APPLICATION_JSON));
        assertNotNull(result);
        result.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String contentAsStringForFind = result.andReturn().getResponse().getContentAsString();
        List<Product> products = convertJsonToList(contentAsStringForFind);
        assertNotNull(products);
        assertTrue(products.contains(added));
    }

    @Test
    public void testGetNotExistingProduct() throws Exception {
        ResultActions result = mMockMvcWithApplicationContext.perform(get("/products/" + Long.MAX_VALUE).accept(MediaType.APPLICATION_JSON));
        assertNotNull(result);
        result.andExpect(status().is4xxClientError()).andExpect(status().isNotFound());
    }

}
