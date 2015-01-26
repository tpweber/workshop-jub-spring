package com.jambit.workshop.jib.spring.data.jdbc.rest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Product;
import com.jambit.workshop.jib.spring.data.jdbc.rest.GlobalExceptionHandler;
import com.jambit.workshop.jib.spring.data.jdbc.rest.ProductController;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;
import com.jambit.workshop.jib.spring.data.shared.exception.InvalidParameterException;

@RunWith(MockitoJUnitRunner.class)
public class FullMockProductControllerTest {

  private MockMvc mMockMvc;

  @Mock
  ProductController mProductController;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mMockMvc = MockMvcBuilders.standaloneSetup(mProductController).setHandlerExceptionResolvers(createExceptionResolver()).build();
  }

  private static ExceptionHandlerExceptionResolver createExceptionResolver() {
    ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
      @Override
      protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
        Method method = new ExceptionHandlerMethodResolver(GlobalExceptionHandler.class).resolveMethod(exception);
        return new ServletInvocableHandlerMethod(new GlobalExceptionHandler(), method);
      }
    };
    exceptionResolver.afterPropertiesSet();
    return exceptionResolver;
  }

  @Test
  public void testGetExistingProduct() throws Exception {
    final Long id = Long.valueOf(1L);
    final Product mockProduct = new Product(id);
    mockProduct.withDescription("mockProduct_description").withName("mockProduct_name").withPriceInCents(1234);
    when(mProductController.getProduct(anyLong())).thenReturn(mockProduct);
    ResultActions result = mMockMvc.perform(get("/products/" + id.toString()).accept(MediaType.APPLICATION_JSON));
    assertNotNull(result);
    result.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    result.andExpect(jsonPath("$.name").value("mockProduct_name")).andExpect(jsonPath("$.description").value("mockProduct_description"))
      .andExpect(jsonPath("$.priceInCents").value(1234)).andExpect(jsonPath("$.optionalId").exists())
      .andExpect(jsonPath("$.optionalId.present").value(true));
  }

  @Test
  public void testGetNotExistingProduct() throws Exception {
    when(mProductController.getProduct(anyLong())).thenThrow(new EntityNotFoundException("test"));
    ResultActions result = mMockMvc.perform(get("/products/" + Long.MAX_VALUE).accept(MediaType.APPLICATION_JSON));
    assertNotNull(result);
    result.andExpect(status().is4xxClientError()).andExpect(status().isNotFound());
  }

  @Test
  public void testGetProductWithInvalidId() throws Exception {
    when(mProductController.getProduct(anyLong())).thenThrow(new InvalidParameterException("test"));
    ResultActions result = mMockMvc.perform(get("/products/-1").accept(MediaType.APPLICATION_JSON));
    assertNotNull(result);
    result.andExpect(status().is4xxClientError()).andExpect(status().isBadRequest());
  }

}
