package com.jambit.workshop.jib.spring.data.jdbc.dao.impl;

import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.PRODUCT_DESCRIPTION;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.PRODUCT_ID;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.PRODUCT_NAME;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.PRODUCT_PRICE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jambit.workshop.jib.spring.data.jdbc.dao.ProductDao;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Product;

/**
 * Implementation of <code>CustomerDao</code> with Springs JdbcDaoSupport.
 */
@Repository("productDao")
public class ProductDaoImpl extends JdbcDaoSupport implements ProductDao {

  private static final String INSERT_SQL = "insert into PRODUCT(NAME, DESCRIPTION, PRICE) values(?, ?, ?)";
  private static final String SELECT_BY_ID = "select ID, NAME, DESCRIPTION, PRICE from PRODUCT where ID = ?";

  @Autowired
  private DataSource mDataSource;

  @PostConstruct
  private void initialize() {
    setDataSource(mDataSource);
  }

  @Transactional(propagation= Propagation.REQUIRED, readOnly=false)
  @Override
  public Product addProduct(final Product product) {
    final KeyHolder keyHolder = new GeneratedKeyHolder();
    int result = getJdbcTemplate().update(new ProductInsert(product), keyHolder);
    if(1 == result) {
      final Product updated = new Product(keyHolder.getKey().longValue());
      updated.withName(product.getName());
      updated.withDescription(product.getDescription());
      updated.withPriceInCents(product.getPriceInCents());
      return updated;
    }
    return null;
  }

  @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
  @Override
  public Product selectProduct(long id) {
    return getJdbcTemplate().queryForObject(SELECT_BY_ID, new Object[]{id}, new ProductRowMapper());
  }

  final class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(final ResultSet rs, int i) throws SQLException {
      final Product product = new Product(rs.getLong(PRODUCT_ID));
      product.withName(rs.getString(PRODUCT_NAME)).withDescription(rs.getString(PRODUCT_DESCRIPTION));
      product.withPriceInCents(rs.getInt(PRODUCT_PRICE));
      return product;
    }
  }

  private final class ProductInsert implements PreparedStatementCreator {
    private final Product mProduct;
    ProductInsert(final Product product) {
      mProduct = product;
    }

    @Override
    public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
      final PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {PRODUCT_ID});
      ps.setString(1, mProduct.getName());
      ps.setString(2, mProduct.getDescription());
      ps.setInt(3, mProduct.getPriceInCents());
      return ps;
    }
  }
}
