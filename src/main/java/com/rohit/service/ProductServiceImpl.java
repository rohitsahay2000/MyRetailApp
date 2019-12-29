package com.rohit.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rohit.dao.ProductDao;
import com.rohit.model.Product;
import com.rohit.model.ProductPrice;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDao productDao;

	@Transactional("txManager")
	public Product add(Product product) throws Exception {
		return productDao.save(product);
	}

	public List<Product> getProductList() throws Exception {
		return productDao.getProductList();
	}

	public Product getProduct(Integer productId) throws Exception {
		return productDao.getProduct(productId);
	}

	public Product updateProduct(Product product, Integer productId) throws Exception {

		Product existingProduct = productDao.getProduct(productId);
		if (product.getName() != null)
			existingProduct.setName(product.getName());
		if (product.getPrice().getCurrency() != null)
			existingProduct.getPrice().setCurrency(product.getPrice().getCurrency());
		if (product.getPrice().getValue() != null)
			existingProduct.getPrice().setValue(product.getPrice().getValue());	
		return productDao.update(existingProduct);
	}

	public ProductPrice getProductPrice(Integer productId) throws Exception {
		return productDao.getProductPrice(productId);
	}

}
