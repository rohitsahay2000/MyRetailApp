package com.rohit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Product {

	@JsonInclude(Include.NON_NULL)
	private Integer id;

	@JsonInclude(Include.NON_NULL)
	private String name;
	
	@JsonInclude(Include.NON_NULL)
	private ProductPrice price;

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductPrice getPrice() {
		return price;
	}

	public void setPrice(ProductPrice price) {
		this.price = price;
	}

}
