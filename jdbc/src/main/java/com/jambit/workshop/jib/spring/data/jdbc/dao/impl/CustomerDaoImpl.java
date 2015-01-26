package com.jambit.workshop.jib.spring.data.jdbc.dao.impl;

import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.CUSTOMER_EMAIL_ADDRESS;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.CUSTOMER_FIRST_NAME;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.CUSTOMER_ID;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.CUSTOMER_LAST_NAME;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jambit.workshop.jib.spring.data.jdbc.dao.CustomerDao;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;
import com.jambit.workshop.jib.spring.data.jdbc.domain.EmailAddress;

/**
 * Implementation of <code>CustomerDao</code> with Springs JdbcTemplate.
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

  private static final String INSERT_SQL = "insert into CUSTOMER(FIRST_NAME, LAST_NAME, NORMALIZED, EMAIL_ADDRESS) values(?, ?, ?, ?)";
  private static final String SELECT_BY_ID = "select ID, FIRST_NAME, LAST_NAME, EMAIL_ADDRESS from CUSTOMER where ID = ?";

  private JdbcTemplate mJdbcTemplate;

  public void setDataSource(final DataSource dataSource) {
    mJdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Transactional(propagation= Propagation.REQUIRED, readOnly=false)
  @Override
  public Customer addCustomer(final Customer customer) {
    final KeyHolder keyHolder = new GeneratedKeyHolder();
    int result = mJdbcTemplate.update(new CustomerInsert(customer), keyHolder);
    if(1 == result) {
      final Customer updatedCustomer = new Customer(keyHolder.getKey().longValue());
      updatedCustomer.withName(customer.getFirstName(), customer.getLastName());
      updatedCustomer.withEmailAddress(customer.getEmailAddress());
      return updatedCustomer;
    }
    return null;
  }

  @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
  @Override
  public Customer selectCustomer(long id) {
    return mJdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, new CustomerRowMapper());
  }

  final class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(final ResultSet rs, int i) throws SQLException {
      final Customer customer = new Customer(rs.getLong(CUSTOMER_ID));
      customer.withName(rs.getString(CUSTOMER_FIRST_NAME), rs.getString(CUSTOMER_LAST_NAME));
      customer.withEmailAddress(new EmailAddress(rs.getString(CUSTOMER_EMAIL_ADDRESS)));
      return customer;
    }
  }

  final class CustomerInsert implements PreparedStatementCreator {
    private final Customer mCustomer;
    CustomerInsert(final Customer customer) {
      mCustomer = customer;
    }

    @Override
    public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
      final PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {CUSTOMER_ID});
      ps.setString(1, mCustomer.getFirstName());
      ps.setString(2, mCustomer.getLastName());
      ps.setString(3, mCustomer.getNormalizedName());
      ps.setString(4, mCustomer.getEmailAddress().getAsString());
      return ps;
    }
  }
}
