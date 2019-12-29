package com.rohit.service;

import java.util.List;

import com.rohit.model.Product;
import com.rohit.model.ProductPrice;

public interface ProductService {
	
	Product add(Product product) throws Exception;
	
	List<Product> getProductList() throws Exception;
	
	Product getProduct(Integer productId) throws Exception;
	
	Product updateProduct(Product product, Integer productId) throws Exception;
	
	ProductPrice getProductPrice(Integer productId) throws Exception;

}
