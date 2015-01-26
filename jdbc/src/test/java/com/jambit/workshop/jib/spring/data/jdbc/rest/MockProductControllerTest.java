package com.jambit.workshop.jib.spring.data.jdbc.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/testApplicationContext.xml" })
@WebAppConfiguration
public class MockProductControllerTest extends AbstractJUnit4SpringContextTests {

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

  @Test
  public void testGetExistingProduct() throws Exception {
    final Long id = Long.valueOf(1L);
    ResultActions result = mMockMvcWithApplicationContext.perform(get("/products/" + id.toString())
        .accept(MediaType.APPLICATION_JSON));
    assertNotNull(result);
    result.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    result.andExpect(jsonPath("$.name").value("test1_name"));
    result.andExpect(jsonPath("$.description").value(
        "description for test1_name"));
    result.andExpect(jsonPath("$.priceInCents").value(256));
    result.andExpect(jsonPath("$.optionalId").exists());
    result.andExpect(jsonPath("$.optionalId.present").value(true));
  }
  
  @Test
  public void testGetNotExistingProduct() throws Exception {
    ResultActions result = mMockMvcWithApplicationContext.perform(get("/products/" + Long.MAX_VALUE).accept(MediaType.APPLICATION_JSON));
    assertNotNull(result);
    result.andExpect(status().is4xxClientError()).andExpect(status().isNotFound());
  }
  
  @Test
  public void testGetProductWithInvalidId() throws Exception {
    ResultActions result = mMockMvcWithApplicationContext.perform(get("/products/-1" ).accept(MediaType.APPLICATION_JSON));
    assertNotNull(result);
    result.andExpect(status().is4xxClientError()).andExpect(status().isBadRequest());
  }

}
