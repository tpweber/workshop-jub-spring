package com.jambit.workshop.jib.spring.data.jpa;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Base class for integration tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public abstract class AbstractIntegrationTest {
    private static final String POPULATION_DATA = "data.sql";

    @Autowired
    DataSource dataSource;

    @Before
    public void populateDatabase() throws SQLException {
        final ResourceDatabasePopulator populator = createPopulator();
        doPopulate(populator);
    }

    private ResourceDatabasePopulator createPopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource(POPULATION_DATA));
        return populator;
    }

    private void doPopulate(final ResourceDatabasePopulator populator) {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            populator.populate(connection);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }
}
