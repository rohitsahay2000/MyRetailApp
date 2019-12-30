package com.rohit.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.rohit.dao.ProductDaoImpl;
import com.rohit.model.Product;
import com.rohit.model.ProductPrice;
import com.rohit.service.ProductServiceImpl;

import org.junit.Assert;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

	@Mock
	private ProductDaoImpl productDao;

	@InjectMocks
	private ProductServiceImpl prodService;

	@Before
	public void setup() throws Exception {
		prodService = new ProductServiceImpl();
		MockitoAnnotations.initMocks(this);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullProduct() throws Exception {
		prodService.add(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddProductAllParametersNull() throws Exception {
		Product product = new Product();
		prodService.add(product);

		Product product1 = new Product();
		product1.setName("test");
		prodService.add(product);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddProductAllParametersNullNameNotNull() throws Exception {
		Product product = new Product();
		product.setName("test");
		prodService.add(product);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddProductPriceParametersNull() throws Exception {
		Product product = new Product();
		product.setName("test");
		ProductPrice productPrice = new ProductPrice();
		product.setPrice(productPrice);
		prodService.add(product);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddProductAllParametersNullCurrencyNotNull() throws Exception {
		Product product = new Product();
		product.setName("test");
		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrency("USD");
		product.setPrice(productPrice);
		prodService.add(product);
	}

	@Test
	public void testAddProductAllParametersValid() throws Exception {
		Product product = new Product();
		product.setName("test");
		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrency("USD");
		productPrice.setValue(300.0);
		product.setPrice(productPrice);
		prodService.add(product);
		Mockito.verify(productDao, Mockito.times(1)).save(product);
	}

	@Test
	public void testgetProductList() throws Exception {
		prodService.getProductList();
		Mockito.verify(productDao, Mockito.times(1)).getProductList();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testgetProductWithProductIdNull() throws Exception {
		prodService.getProduct(null);
	}

	@Test
	public void testgetProduct() throws Exception {
		prodService.getProduct(123);
		Mockito.verify(productDao, Mockito.times(1)).getProduct(123);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateProductWithProductIdNull() throws Exception {
		prodService.updateProduct(new Product(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateProductWithProductNull() throws Exception {
		prodService.updateProduct(null, 123);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateProductWithPriceNull() throws Exception {
		prodService.updateProduct(new Product(), 123);
	}

	@Test
	public void testUpdateProduct() throws Exception {
		Product product = new Product();
		product.setName("test");
		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrency("USD");
		productPrice.setValue(300.0);
		product.setPrice(productPrice);

		Product updatedProduct = new Product();
		updatedProduct.setName("test1");
		ProductPrice updatedProductPrice = new ProductPrice();
		updatedProductPrice.setCurrency("USD");
		updatedProductPrice.setValue(310.0);
		updatedProduct.setPrice(updatedProductPrice);

		Mockito.when(productDao.getProduct(Mockito.anyInt())).thenReturn(product);
		prodService.updateProduct(updatedProduct, 123);
		Assert.assertEquals(product.getName(), updatedProduct.getName());
		Assert.assertEquals(product.getPrice().getCurrency(), updatedProduct.getPrice().getCurrency());
		Assert.assertEquals(product.getPrice().getValue(), updatedProduct.getPrice().getValue());
		Mockito.verify(productDao, Mockito.times(1)).update(product);

	}

	@Test
	public void testUpdateProductWithNullDetails() throws Exception {
		Product product = new Product();
		product.setName("test");
		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrency("USD");
		productPrice.setValue(300.0);
		product.setPrice(productPrice);

		Product updatedProduct = new Product();
		ProductPrice updatedProductPrice = new ProductPrice();
		updatedProduct.setPrice(updatedProductPrice);

		Mockito.when(productDao.getProduct(Mockito.anyInt())).thenReturn(product);
		prodService.updateProduct(updatedProduct, 123);
		Assert.assertEquals(product.getName(), "test");
		Assert.assertEquals(product.getPrice().getCurrency(), "USD");
		Mockito.verify(productDao, Mockito.times(1)).update(product);

	}

	@Test
	public void testgetProductName() throws Exception {
		prodService.getProductPrice(123);
		Mockito.verify(productDao, Mockito.times(1)).getProductPrice(123);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testgetProductNameProductIdNull() throws Exception {
		prodService.getProductPrice(null);
	}

}
