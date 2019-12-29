package com.rohit.dao;

import java.util.List;

import com.rohit.model.Product;
import com.rohit.model.ProductPrice;

public interface ProductDao {
	
	Product save(Product product) throws Exception;
	
	List<Product> getProductList() throws Exception;
	
	Product getProduct(Integer productId) throws Exception;
	
	Product update(Product product) throws Exception;
	
	ProductPrice getProductPrice(Integer productId) throws Exception;
}
