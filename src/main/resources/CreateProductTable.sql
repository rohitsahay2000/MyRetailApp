CREATE TABLE Product (
  id int(10) NOT NULL AUTO_INCREMENT,
  name varchar(256) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY product_name(name)
);