DROP TABLE books;
DROP TABLE client;
DROP TABLE order_book;
DROP TABLE deliveryCompany;
DROP TABLE deliveryOrder;

--------------------
--	BOOKS
--------------------

CREATE TABLE books
(
  isbn INT NOT NULL GENERATED ALWAYS AS IDENTITY
	CONSTRAINT BOOKS_PK PRIMARY KEY,
  unitsAvailable INT NOT NULL,
  unitsOnHold INT NOT NULL,
  price DECIMAL(6,2) NOT NULL
);



--------------------
--	CLIENT
--------------------
CREATE TABLE client
(
  clientId INT NOT NULL GENERATED ALWAYS AS IDENTITY
	(START WITH 1,INCREMENT BY 1)
	CONSTRAINT CLIENT_PK PRIMARY KEY,
  balance DECIMAL(6,2) NOT NULL
);




-----------------------
--	ORDER
-----------------------

CREATE TABLE order_book
(
  orderId INT NOT NULL GENERATED ALWAYS AS IDENTITY
	(START WITH 1, INCREMENT BY 1)
	CONSTRAINT ORDER_PK PRIMARY KEY,
  unitsOrdered INT NOT NULL,
  finalCost DECIMAL(6,2) NOT NULL,
  isbn int NOT NULL
	CONSTRAINT ISBN_FK REFERENCES books,
  clientId INT NOT NULL 
	CONSTRAINT CLIENT_ID_FK REFERENCES client
);

-----------------------
--	DELIVERY COMPANY
-----------------------
CREATE TABLE deliveryCompany
(
  idDelivery INT NOT NULL GENERATED ALWAYS AS IDENTITY
	(START WITH 1, INCREMENT BY 1)
	CONSTRAINT IDDELIVERY_PK PRIMARY KEY,
  name VARCHAR(30) NOT NULL
);

-----------------------
--	DELIVERY ORDER
-----------------------
CREATE TABLE deliveryOrder 
(
  deliverOrderId INT NOT NULL GENERATED ALWAYS AS IDENTITY
	(START WITH 1, INCREMENT BY 1)
	CONSTRAINT DELIVERY_ORDER_ID PRIMARY KEY,
  orderId INT NOT NULL
	CONSTRAINT ORDER_ID_FK REFERENCES order_book,
  idDelivery INT NOT NULL
	CONSTRAINT NAME_FK REFERENCES deliveryCompany,
  deliveryDays INT NOT NULL
);


INSERT INTO books (unitsAvailable,unitsOnHold,price) VALUES
(3,1,399.00),
(5,4,599.23),
(30,0,600.00),
(1,1,100.99),
(77,69,420.00),
(10,9,666.00),
(0,1,99.99),
(12,40,2000.00),
(70,45,199.00),
(100,20,322.01),
(13,11,799.00),
(0,0,600.00),
(34,34,500.99),
(12,4,200.00);

INSERT INTO client (balance) VALUES
(250.00),
(0.00),
(1000.99),
(322.98),
(99.17),
(34.78),
(600.00),
(999.99),
(1201.08);
