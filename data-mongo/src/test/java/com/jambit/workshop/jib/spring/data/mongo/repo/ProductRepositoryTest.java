package com.jambit.workshop.jib.spring.data.mongo.repo;

import com.google.common.collect.Iterables;
import com.jambit.workshop.jib.spring.data.mongo.domain.Product;
import com.jambit.workshop.jib.spring.data.mongo.domain.QProduct;
import com.mysema.query.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/testApplicationContext.xml"})
public class ProductRepositoryTest extends AbstractJUnit4SpringContextTests {

    static final QProduct qProduct = QProduct.product;

    @Autowired
    @Qualifier("productRepository")
    ProductRepository mProductRepository;

    final DateTimeFormatter mFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS").withZone(ZoneId.systemDefault());

    @Before
    public void setUp() throws Exception {
        assertNotNull(applicationContext);
        assertNotNull(mProductRepository);
    }

    private String createId() {
        return mFormatter.format(Instant.now());
    }

    @Test
    public void testAddProduct() {
        final String id = createId();
        Product product = new Product(id);
        product.withDescription("testAddProduct_description").withName("testAddProduct_name").withPriceInCents(1000);
        final Product added = mProductRepository.save(product);
        assertNotNull(added);
        assertEquals(product, added);
        Product found = mProductRepository.findOne(id);
        assertNotNull(found);
        assertEquals(product, found);
    }

    @Test
    public void testFindAll() {
        final String id = createId();
        final String name = "testFindAll_name_" + id;
        Product product = new Product(id);
        product.withDescription("testFindAll_description").withName(name).withPriceInCents(2222);
        final Product added = mProductRepository.save(product);
        assertNotNull(added);
        assertEquals(product, added);
        List<Product> found = mProductRepository.findAll();
        assertNotNull(found);
        assertFalse(found.isEmpty());
        assertTrue(found.contains(product));
    }

    @Test
    public void testFindAllSort() {
        final String id1 = createId();
        final String name1 = "testFindAllSort1_name_" + id1;
        Product product1 = new Product(id1);
        product1.withDescription("testFindAllSort1_description").withName(name1).withPriceInCents(1111);
        final Product added1 = mProductRepository.save(product1);
        assertNotNull(added1);
        assertEquals(product1, added1);
        final String id2 = createId();
        final String name2 = "testFindAllSort2_name_" + id2;
        Product product2 = new Product(id2);
        product2.withDescription("testFindAllSort2_description").withName(name2).withPriceInCents(2222);
        final Product added2 = mProductRepository.save(product2);
        assertNotNull(added2);
        assertEquals(product2, added2);
        Sort sortAsc = Sorts.NAME_ASC.getSort();
        List<Product> foundAsc = mProductRepository.findAll(sortAsc);
        assertNotNull(foundAsc);
        assertFalse(foundAsc.isEmpty());
        assertTrue(foundAsc.contains(product1));
        assertTrue(foundAsc.contains(product2));
        assertTrue(foundAsc.indexOf(product1) < foundAsc.indexOf(product2));
        Sort sortDesc = Sorts.NAME_DESC.getSort();
        List<Product> foundDesc = mProductRepository.findAll(sortDesc);
        assertNotNull(foundDesc);
        assertFalse(foundDesc.isEmpty());
        assertTrue(foundDesc.contains(product1));
        assertTrue(foundDesc.contains(product2));
        assertTrue(foundDesc.indexOf(product1) > foundDesc.indexOf(product2));
    }

    @Test
    public void testFindByName() {
        final String id = createId();
        final String tName = "testFindByName_name_" + id;
        Product product = new Product(id);
        product.withDescription("testFindByName_description").withName(tName).withPriceInCents(2222);
        final Product added = mProductRepository.save(product);
        assertNotNull(added);
        assertEquals(product, added);
        List<Product> found = mProductRepository.findByName(tName);
        assertNotNull(found);
        assertFalse(found.isEmpty());
        assertTrue(found.contains(added));
    }

    @Test
    public void testFindProductsByQuerydslPredicate() {
        final String id = createId();
        final String name = "testFindAll_name_" + id;
        final String desc = "testFindAll_description" + id;
        Product product = new Product(id);
        product.withDescription(desc).withName(name).withPriceInCents(2222);
        final Product added = mProductRepository.save(product);
        assertNotNull(added);
        assertEquals(product, added);
        final Product found1 = mProductRepository.findOne(qProduct.mId.eq(added.getId()));
        assertEquals(added, found1);
        final Product found2 = mProductRepository.findOne(qProduct.mName.eq(name));
        assertEquals(added, found2);
        final Predicate pFoundByDesc = qProduct.description.contains(desc);
        final Iterable<Product> result = mProductRepository.findAll(pFoundByDesc);
        assertEquals(1, Iterables.size(result));
        assertTrue(Iterables.contains(result, found1));
    }


}
