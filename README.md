# My Retail Restful Service

*** A simple restful app for product entity/resource crud plus some other functionalities.

*** Uses h2 (in memory database) to persist data.

*** Can be directly imported into any java based IDE.

*** To run and build: maven build

*** To create the war: maven install

*** Deploy the war to tomcat. 

 1. Create Product
 
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
		  

2. 	Get List of products.
   
       
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

3.  Get Product by Product Id

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

 4. Update Product By Product Id	
   
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
 
 5.    
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
 	 
 6. Get Product Name by Product ID
 	
 	/**
	 * Api to get product name by product id.
	 * Sample Api Request: GET http://localhost:8080/MyRetailApp/myRetail/product/16/name/
	 * @param request
	 * @return Product Response
	 * Sample Response : {
						    "name": "test5"
						 }
	 */ 
