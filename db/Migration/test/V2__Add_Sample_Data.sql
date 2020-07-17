INSERT INTO CUSTOMERS (FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, GENDER) VALUES ('Minh Hiep', 'Ho', 'hiep.ho@yahoo.com', 'Vancouver, Canada','Male');
INSERT INTO CUSTOMERS (FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, GENDER) VALUES ('Hoang Thien Kim', 'Le', 'kim.le@yahoo.com', 'Vancouver, Canada','Female');
INSERT INTO CUSTOMERS (FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, GENDER) VALUES ('Le Gia Binh', 'Ho', 'binh.ho@yahoo.com', 'Vancouver, Canada','Male');
INSERT INTO CUSTOMERS (FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, GENDER) VALUES ('Le Gia An', 'Ho', 'an.ho@yahoo.com', 'Vancouver, Canada','Male');

INSERT INTO INVENTORY (item_name, item_description, price, quantity_available) VALUES ('iPhone 10 - 32G', 'iPhone 10 - 32G Storage', 1000, 1000);
INSERT INTO INVENTORY (item_name, item_description, price, quantity_available) VALUES ('iPhone 10 - 64G', 'iPhone 10 - 64G Storage', 1300, 2000);
INSERT INTO INVENTORY (item_name, item_description, price, quantity_available) VALUES ('iPhone 10 Plus - 32G', 'iPhone 10 Plus - 32G Storage', 1200, 3000);
INSERT INTO INVENTORY (item_name, item_description, price, quantity_available) VALUES ('iPhone 10 Plus - 64G', 'iPhone 10 Plus - 64G Storage', 1500, 4000);

INSERT INTO ORDERS (CUSTOMER_ID, ORDER_STATUS) VALUES (1, 'PROCESSING');
INSERT INTO ORDERS (CUSTOMER_ID, ORDER_STATUS) VALUES (1, 'ON_THE_WAY');

INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_ID, PRICE, QUANTITY) VALUES (1, 1, 1000, 5);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_ID, PRICE, QUANTITY) VALUES (1, 2, 1300, 4);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_ID, PRICE, QUANTITY) VALUES (2, 3, 1200, 3);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_ID, PRICE, QUANTITY) VALUES (2, 4, 1500, 2);
