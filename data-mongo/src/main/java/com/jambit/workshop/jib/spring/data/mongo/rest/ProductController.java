package com.jambit.workshop.jib.spring.data.mongo.rest;

import static com.jambit.workshop.jib.spring.data.shared.domain.Predicates.isValidStringId;
import static com.jambit.workshop.jib.spring.data.shared.rest.RestPathConstants.*;

import java.util.List;

import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotSavedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.jambit.workshop.jib.spring.data.mongo.manager.ProductManager;
import com.jambit.workshop.jib.spring.data.mongo.domain.Product;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;
import com.jambit.workshop.jib.spring.data.shared.exception.InvalidParameterException;

/**
 * Simple REST for <code>Hello World for Spring Data MongoDB!</code>
 */
@RestController("productController")
public class ProductController {

  @Autowired
  @Qualifier("productManager")
  ProductManager mProductManager;
  
  @RequestMapping(value = PATH_PRODUCT_ID, method = RequestMethod.GET)
  public Product getProduct(@PathVariable(VARIABLE_ID) String id) {
    if (isValidStringId().test(id)) {
      final Product product = mProductManager.getProduct(id);
      if (null != product) {
        return product;
      }
      throw new EntityNotFoundException("Product not found for id[" + id + "].");
    }
    throw new InvalidParameterException("Product invalid id[" + id + "].");
  }
  
  @RequestMapping(value= PATH_PRODUCT_ALL, method = RequestMethod.GET)
  public List<Product> findAllProducts(@RequestParam(value ="sorting", required = false, defaultValue = "asc") final String sorting) {
    return mProductManager.getAllProductsSortedByName(sorting);
  }

  @RequestMapping(value= PATH_PRODUCT_FIND, method = RequestMethod.GET)
  public List<Product> findProductByName(@RequestParam(value ="name", required = true) final String name) {
    return mProductManager.findByName(name);
  }

  @RequestMapping(value = PATH_PRODUCT_ADD, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus( HttpStatus.CREATED )
  public Product addProduct(@RequestBody(required = true) final Product product) {
    final Product added = mProductManager.addProduct(product);
    if(isSaved(added)) {
      return added;
    }
    throw new EntityNotSavedException("Product not saved");
  }



  private boolean isSaved(final Product added) {
    return null != added && added.hasValidId();
  }
}
