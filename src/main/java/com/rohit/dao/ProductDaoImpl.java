package com.rohit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.rohit.model.Product;
import com.rohit.model.ProductPrice;

@Component
public class ProductDaoImpl implements ProductDao {

	private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final String SAVE_PRODUCT = "insert into product (name) values (:name); ";
	private static final String SAVE_PRODUCT_PRICE = "insert into productprice(productid,currency,value) values ((select id from product"
			+ " where name=:name),:currency,:value)";

	public Product save(Product product) throws Exception {

		Map<String, Object> productMap = new HashMap<String, Object>();
		productMap.put("name", product.getName());

		namedParameterJdbcTemplate.update(SAVE_PRODUCT, productMap);

		Map<String, Object> productPriceMap = new HashMap<String, Object>();
		productPriceMap.put("currency", product.getPrice().getCurrency());
		productPriceMap.put("value", product.getPrice().getValue());
		productPriceMap.put("name", product.getName());

		namedParameterJdbcTemplate.update(SAVE_PRODUCT_PRICE, productPriceMap);

		return product;

	}

	private static final String GET_PRODUCT_LIST = "select  prd.id, prd.name, prd_price.currency, prd_price.value "
			+ "from product prd, productprice prd_price where prd.id=prd_price.productid;";

	public List<Product> getProductList() throws Exception {
		return namedParameterJdbcTemplate.query(GET_PRODUCT_LIST, new ProductListRowMapper());
	}

	private static final String GET_PRODUCT = "select  prd.id, prd.name, prd_price.currency, prd_price.value "
			+ "from product prd, productprice prd_price where prd.id=prd_price.productid and prd.id=:product_id;";

	public Product getProduct(Integer productId) throws Exception {
		Map<String, Object> productMap = new HashMap<String, Object>();
		productMap.put("product_id", productId);

		return namedParameterJdbcTemplate.queryForObject(GET_PRODUCT, productMap, new ProductRowMapper());
	}

	private static final class ProductListRowMapper implements RowMapper<Product> {

		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

			Product product = new Product();
			product.setId(rs.getInt("id"));
			product.setName(rs.getString("name"));
			ProductPrice productPrice = new ProductPrice();
			productPrice.setCurrency(rs.getString("currency"));
			productPrice.setValue(rs.getDouble("value"));
			product.setPrice(productPrice);
			return product;
		}
	}

	private static final class ProductRowMapper implements RowMapper<Product> {

		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

			Product product = new Product();
			product.setId(rs.getInt("id"));
			product.setName(rs.getString("name"));
			ProductPrice productPrice = new ProductPrice();
			productPrice.setCurrency(rs.getString("currency"));
			productPrice.setValue(rs.getDouble("value"));
			product.setPrice(productPrice);
			return product;
		}
	}

	private static final String UPDATE_PRODUCT = "update product set name=:name where id=:product_id; ";
	private static final String UPDATE_PRODUCT_PRICE = "update productprice set currency=:currency,value=:value where "
			+ "productid=:product_id";

	public Product update(Product product) throws Exception {

		Map<String, Object> productMap = new HashMap<String, Object>();
		productMap.put("name", product.getName());
		productMap.put("product_id", product.getId());

		namedParameterJdbcTemplate.update(UPDATE_PRODUCT, productMap);

		Map<String, Object> productPriceMap = new HashMap<String, Object>();
		productPriceMap.put("currency", product.getPrice().getCurrency());
		productPriceMap.put("value", product.getPrice().getValue());
		productPriceMap.put("name", product.getName());
		productPriceMap.put("product_id", product.getId());

		namedParameterJdbcTemplate.update(UPDATE_PRODUCT_PRICE, productPriceMap);

		return product;
	}
	
	private static final String GET_PRODUCT_PRICE = "select * "
			+ "from productprice where productid=:product_id;";

	public ProductPrice getProductPrice(Integer productId) throws Exception {
		Map<String, Object> productMap = new HashMap<String, Object>();
		productMap.put("product_id", productId);

		return namedParameterJdbcTemplate.queryForObject(GET_PRODUCT_PRICE, productMap, new ProductPriceRowMapper());
	}
	
	private static final class ProductPriceRowMapper implements RowMapper<ProductPrice> {

		public ProductPrice mapRow(ResultSet rs, int rowNum) throws SQLException {

			ProductPrice productPrice = new ProductPrice();
			productPrice.setProductId(rs.getInt("productid"));
			productPrice.setCurrency(rs.getString("currency"));
			productPrice.setValue(rs.getDouble("value"));
			return productPrice;
		}
	}

}
