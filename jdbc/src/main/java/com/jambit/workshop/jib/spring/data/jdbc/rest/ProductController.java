package com.jambit.workshop.jib.spring.data.jdbc.rest;

import static com.jambit.workshop.jib.spring.data.shared.rest.RestPathConstants.PATH_PRODUCT_ID;
import static com.jambit.workshop.jib.spring.data.shared.rest.RestPathConstants.VARIABLE_ID;
import static com.jambit.workshop.jib.spring.data.shared.domain.Predicates.isValidId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Product;
import com.jambit.workshop.jib.spring.data.jdbc.manager.ProductManager;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;
import com.jambit.workshop.jib.spring.data.shared.exception.InvalidParameterException;

/**
 * Controller for REST
 */
@RestController("productController")
public class ProductController {

  @Autowired
  @Qualifier("productManager")
  ProductManager mProductManager;

  /**
   * Returns the product for the given id.
   * 
   * @param id
   *          the id of a product.
   * @return the product
   * @exception EntityNotFoundException
   *              if the id is valid, but no product with the given id exists.
   * @exception InvalidParameterException
   *              if the given id is not valid aka is negative or <code>0</code>.
   */
  @RequestMapping(value = PATH_PRODUCT_ID, method = RequestMethod.GET)
  public Product getProduct(@PathVariable(VARIABLE_ID) long id) {
    if (isValidId().test(id)) {
      final Product product = mProductManager.getProduct(id);
      if (null != product) {
        return product;
      }
      throw new EntityNotFoundException("Product not found for id[" + id + "].");
    }
    throw new InvalidParameterException("Product invalid id[" + id + "].");
  }
}
