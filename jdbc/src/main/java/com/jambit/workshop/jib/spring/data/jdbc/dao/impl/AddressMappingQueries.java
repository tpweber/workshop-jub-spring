package com.jambit.workshop.jib.spring.data.jdbc.dao.impl;

import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_CITY;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_COUNTRY;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_CUSTOMER_ID;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_ID;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_STREET;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Address;
import com.neovisionaries.i18n.CountryCode;

/**
 * Encapsulates the queries for <code>Address</code>
 */
final class AddressMappingQueries {

  private static final String SELECT_BY_ID = "select ID, STREET, CITY, COUNTRY from ADDRESS where ID = ?";
  private static final String FIND_BY_CUSTOMER = "select ID, STREET, CITY, COUNTRY from ADDRESS where CUSTOMER_ID = ?";

  static abstract class AbstractAddressMappingQuery extends MappingSqlQuery<Address> {
    protected AbstractAddressMappingQuery(final DataSource ds, final String sql) {
      super(ds, sql);
    }

    @Override
    protected Address mapRow(final ResultSet rs, int rowNumber) throws SQLException {
      final Address found = new Address(rs.getLong(ADDRESS_ID));
      found.with(rs.getString(ADDRESS_STREET), rs.getString(ADDRESS_CITY), 
    		  CountryCode.getByCodeIgnoreCase(rs.getString(ADDRESS_COUNTRY)));
      return found;
    }
  }

  static final class AddressMappingQueryById extends AbstractAddressMappingQuery {
    AddressMappingQueryById(final DataSource dataSource) {
      super(dataSource, SELECT_BY_ID);
      super.declareParameter(new SqlParameter(ADDRESS_ID, Types.BIGINT));
      compile();
    }
  }

  static final class AddressMappingQueryByCustomer extends AbstractAddressMappingQuery {
    AddressMappingQueryByCustomer(final DataSource dataSource) {
      super(dataSource, FIND_BY_CUSTOMER);
      super.declareParameter(new SqlParameter(ADDRESS_CUSTOMER_ID, Types.BIGINT));
      compile();
    }
  }


  private AddressMappingQueries() {
    // nop
  }
}
