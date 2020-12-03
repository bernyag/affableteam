connect 'jdbc:derby://localhost:1527/affablebean;user=app;password=app';

DROP TABLE books;
DROP TABLE order;
DROP TABLE client;
DROP TABLE deliveryCompany
DROP TABLE deliveryOrder

--------------------
--	BOOKS
--------------------

CREATE TABLE books
(
  isbn VARCHAR(17) NOT NULL GENERATED ALWAYS AS IDENTITY
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

CREATE TABLE order 
(
  orderId INT NOT NULL GENERATED ALWAYS AS IDENTITY
	(START WITH 1, INCREMENT BY 1)
	CONSTRAINT ORDER_PK PRIMARY KEY,
  unitsOrdered INT NOT NULL,
  finalCost DECIMAL(6,2) NOT NULL,
  isbn varchar(17) NOT NULL
	CONSTRAINT ISBN_FK REFERENCES books,
  clientId INT NOT NULL 
	CONSTRAINT CLIENT_ID_FK REFERENCES client
);

-----------------------
--	DELIVERY COMPANY
-----------------------
CREATE TABLE deliveryCompany
(
  name VARCHAR(30) NOT NULL GENERATED ALWAYS AS IDENTITY
	CONSTRAINT NAME_PK PRIMARY KEY
);

-----------------------
--	DELIVERY ORDER
-----------------------
CREATE TABLE deliveryOrder 
(
  deliverOrderId INT NOT NULL GENERATED ALWAYS AS IDENTITY
	(START WITH 1, INCREMENT BY 1)
	CONSTRAINT DELIVERY_ORDER_ID PRIMARY KEY,
  name VARCHAR(30) NOT NULL
	CONSTRAINT NAME_FK REFERENCES deliveryCompany,
  deliveryDays INT NOT NULL
);
