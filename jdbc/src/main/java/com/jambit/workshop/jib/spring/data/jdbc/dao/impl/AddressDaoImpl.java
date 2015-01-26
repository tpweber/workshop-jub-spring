package com.jambit.workshop.jib.spring.data.jdbc.dao.impl;

import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_CITY;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_COUNTRY;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_CUSTOMER_ID;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_ID;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.ADDRESS_STREET;
import static com.jambit.workshop.jib.spring.data.jdbc.dao.impl.DaoConstants.TABLE_ADDRESS;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jambit.workshop.jib.spring.data.jdbc.dao.AddressDao;
import com.jambit.workshop.jib.spring.data.jdbc.dao.impl.AddressMappingQueries.AddressMappingQueryByCustomer;
import com.jambit.workshop.jib.spring.data.jdbc.dao.impl.AddressMappingQueries.AddressMappingQueryById;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Address;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;

/**
 * Implementation of <code>AddressDao</code> with Springs SimpleJdbcInsert and .
 */
@Repository("addressDao")
public class AddressDaoImpl implements AddressDao {
	
	private AddressMappingQueryById mAddressQueryById;
	private AddressMappingQueryByCustomer mAddressQueryByCustomer;
	private SimpleJdbcInsert mInsertAddress;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		mAddressQueryById = new AddressMappingQueryById(dataSource);
		mAddressQueryByCustomer = new AddressMappingQueryByCustomer(dataSource);
		mInsertAddress = new SimpleJdbcInsert(dataSource).withTableName(
				TABLE_ADDRESS).usingGeneratedKeyColumns(ADDRESS_ID);
	}

	private Optional<Long> insert(final Address address, final Customer customer) {
		final SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(ADDRESS_CUSTOMER_ID, customer.getOptionalId().get()) 
				.addValue(ADDRESS_CITY, address.getCity())
				.addValue(ADDRESS_STREET, address.getStreet())
				.addValue(ADDRESS_COUNTRY, address.getCountry().getAlpha3());
		return Optional.ofNullable((Long) mInsertAddress.executeAndReturnKey(parameters));
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public Address addAddress(final Address address, final Customer customer) {
		final Optional<Long> oId = insert(address, customer);
		if (oId.isPresent()) {
			final Address added = new Address(oId.get());
			added.with(address.getStreet(), address.getCity(),
					address.getCountry());
			return added;
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public Address selectAddress(long id) {
		return mAddressQueryById.findObject(id);
	}

	@Override
	public List<Address> findAddressesByCustomer(final Customer customer) {
		final Long customerId = customer.getOptionalId().get();
		return mAddressQueryByCustomer.execute(customerId.longValue());
	}

}
