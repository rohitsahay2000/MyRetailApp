CREATE TABLE ProductPrice (
  productid int(10) NOT NULL,
  currency varchar(20) NOT NULL,
  value numeric(20,2) NOT NULL,
  foreign key (productid) references Product(id),
);