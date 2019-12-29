package com.rohit.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.model.Product;
import com.rohit.model.ProductPrice;
import com.rohit.response.ProductResponse;
import com.rohit.service.ProductService;

@RestController
@RequestMapping("/myRetail")
public class ProductController {

	private static final Logger LOGGER = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	/**
	 * Api to create a product
	 * Sample Api Request: POST http://localhost:8080/MyRetailApp/myRetail/product
	 * Sample Request Body: {
								"name":"productABC",	
								"price" : {"value":"217.49","currency":"INR"}
	
							}
	 * @param request
	 * @return Product Response
	 * Sample Response : {
    						"status": "201",
    						"message": "productABC successfully created"
						 }
	 */
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public ResponseEntity<ProductResponse> add(@RequestBody Product request) {

		try {
			Product product = productService.add(request);
			ProductResponse productResponse = new ProductResponse.ProductResponseBuilder(HttpStatus.CREATED.toString())
					.message(product.getName() + " successfully created").build();
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("productName", product.getName());

			return new ResponseEntity<ProductResponse>(productResponse, responseHeaders, HttpStatus.OK);
		}

		catch (DuplicateKeyException ex) {
			LOGGER.error("Product Creation Failed with exception {}", ex);
			ProductResponse productResponse = new ProductResponse.ProductResponseBuilder(HttpStatus.CONFLICT.toString())
					.message("Product Already Exists").build();
			return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.CONFLICT);
		} catch (Exception e) {
			LOGGER.error("Product Creation Failed with exception {}", e);

			ProductResponse productResponse = new ProductResponse.ProductResponseBuilder(
					HttpStatus.INTERNAL_SERVER_ERROR.toString()).message("Internal Server Error").build();
			return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	/**
	 * Api to get list of created products
	 * Sample Api Request: GET http://localhost:8080/MyRetailApp/myRetail/product
	 * @param request
	 * @return Product Response
	 * Sample Response : [
				    {
				        "id": 1,
				        "name": "test",
				        "price": {
				            "productId": null,
				            "value": 13.49,
				            "currency": "USD"
				        }
				    },
				    {
				        "id": 3,
				        "name": "test1",
				        "price": {
				            "productId": null,
				            "value": 13.49,
				            "currency": "USD"
				        }
				    },
				    {
				        "id": 17,
				        "name": "test3",
				        "price": {
				            "productId": null,
				            "value": 17.89,
				            "currency": "EUR"
				        }
				    },
				]
	 */
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductList() {

		try {
			List<Product> productList = productService.getProductList();
			return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Product List retrieval failed with exception {}", e);
			return new ResponseEntity<List<Product>>(new ArrayList<Product>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	/**
	 * Api to get product by product id.
	 * Sample Api Request: GET http://localhost:8080/MyRetailApp/myRetail/product/17
	 * @param request
	 * @return Product Response
	 * Sample Response : {
						    "id": 17,
						    "name": "test3",
						    "price": {
						        "productId": null,
						        "value": 317.89,
						        "currency": "EUR"
						    }
						}
	 */

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable Integer id) {

		try {
			Product product = productService.getProduct(id);
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Product retrieval failed with exception {}", e);
			return new ResponseEntity<Product>(new Product(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
	/**
	 * Api to update a product.
	 * Sample Api Request: PUT http://localhost:8080/MyRetailApp/myRetail/product/17
	 * Sample Request Body: {
							    "name": "test3",
							    "price": {
							        "value": 317.89,
							        "currency": "EUR"
							    }
							}
	 * @param request
	 * @return Product Response
	 * Sample Response : {
						    "status": "200",
						    "message": "test3 successfully updated"
						}
	 */

	@RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer id, @RequestBody Product request) {

		try {
			Product product = productService.updateProduct(request, id);

			ProductResponse productResponse = new ProductResponse.ProductResponseBuilder(HttpStatus.OK.toString())
					.message(product.getName() + " successfully updated").build();
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("productName", product.getName());

			return new ResponseEntity<ProductResponse>(productResponse, responseHeaders, HttpStatus.OK);

		} catch (EmptyResultDataAccessException ex) {
			LOGGER.error("Product to be updated not found {}", ex);
			ProductResponse productResponse = new ProductResponse.ProductResponseBuilder(
					HttpStatus.NOT_FOUND.toString()).message("Product Not Found").build();
			return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			LOGGER.error("Product Updatation Failed with exception {}", e);
			ProductResponse productResponse = new ProductResponse.ProductResponseBuilder(
					HttpStatus.INTERNAL_SERVER_ERROR.toString()).message("Internal Server Error").build();
			return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Api which will call an api to get pricing detail and combine with product id and
	 * name from http request into a single response.
	 * 
	 * Sample Api Request: GET http://localhost:8080/MyRetailApp/myRetail/product/17/name/rohit
	 * @param request
	 * @return Product Response
	 * Sample Response : {
						    "name": "rohit",
						    "price": {
						        "productId": 17,
						        "value": 317.89,
						        "currency": "EUR"
						    }
						}
	 */
	@RequestMapping(value = "/product/{productid}/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProductPricingNameCombined(@PathVariable Integer productid,
			@PathVariable String name) {

		try {
			// Internal Service Call equivalent of calling a pricing api.
			ProductPrice productPrice = productService.getProductPrice(productid);
			Product product = new Product();

			// Combine name and other details
			product.setName(name);
			product.setPrice(productPrice);
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			LOGGER.error("ProductId not found {}", ex);
			return new ResponseEntity<Product>(new Product(), HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			LOGGER.error("Product retrieval failed with exception {}", e);
			return new ResponseEntity<Product>(new Product(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/product/{productid}/price", method = RequestMethod.GET)
	public ResponseEntity<ProductPrice> getProductPricing(@PathVariable Integer productid) {

		try {
			ProductPrice productPrice = productService.getProductPrice(productid);
			return new ResponseEntity<ProductPrice>(productPrice, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Product retrieval failed with exception {}", e);
			return new ResponseEntity<ProductPrice>(new ProductPrice(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
	/**
	 * Api to get product name by product id.
	 * Sample Api Request: GET http://localhost:8080/MyRetailApp/myRetail/product/16/name/
	 * @param request
	 * @return Product Response
	 * Sample Response : {
							   "test5",							   
						  }
	 */
	@RequestMapping(value = "/product/{productid}/name", method = RequestMethod.GET)
	public ResponseEntity<String> getProductNameFromId(@PathVariable Integer productid) {

		try {
			Product product = productService.getProduct(productid);
			return new ResponseEntity<String>(product.getName(), HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			LOGGER.error("ProductId not found {}", ex);
			return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			LOGGER.error("Product retrieval failed with exception {}", e);
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


}