INSERT INTO CUSTOMERS (FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, GENDER) VALUES ('Minh Hiep', 'Ho', 'hiep.ho@yahoo.com', 'Vancouver, Canada','Male');
INSERT INTO CUSTOMERS (FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, GENDER) VALUES ('Hoang Thien Kim', 'Le', 'kim.le@yahoo.com', 'Vancouver, Canada','Female');
INSERT INTO CUSTOMERS (FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, GENDER) VALUES ('Le Gia Binh', 'Ho', 'binh.ho@yahoo.com', 'Vancouver, Canada','Male');
INSERT INTO CUSTOMERS (FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, GENDER) VALUES ('Le Gia An', 'Ho', 'an.ho@yahoo.com', 'Vancouver, Canada','Male');

INSERT INTO INVENTORY (item_name, item_description, price, quantity_available) VALUES ('iPhone 10 - 32G', 'iPhone 10 - 32G Storage', 1000, 1000);
INSERT INTO INVENTORY (item_name, item_description, price, quantity_available) VALUES ('iPhone 10 - 64G', 'iPhone 10 - 64G Storage', 1300, 2000);
INSERT INTO INVENTORY (item_name, item_description, price, quantity_available) VALUES ('iPhone 10 Plus - 32G', 'iPhone 10 Plus - 32G Storage', 1200, 3000);
INSERT INTO INVENTORY (item_name, item_description, price, quantity_available) VALUES ('iPhone 10 Plus - 64G', 'iPhone 10 Plus - 64G Storage', 1500, 4000);

INSERT INTO ORDERS (CUSTOMER_ID, ORDER_STATUS) VALUES (1, 'PROCESSING');
INSERT INTO ORDERS (CUSTOMER_ID, ORDER_STATUS) VALUES (1, 'PROCESSED');

INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_ID, PRICE, QUANTITY) VALUES (1, 1, 1000, 5);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_ID, PRICE, QUANTITY) VALUES (1, 2, 1300, 4);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_ID, PRICE, QUANTITY) VALUES (2, 3, 1200, 3);
INSERT INTO ORDER_DETAIL (ORDER_ID, ITEM_ID, PRICE, QUANTITY) VALUES (2, 4, 1500, 2);

INSERT INTO ROLE (DESCRIPTION) VALUES ('ADMIN'), ('MANAGER'),('COMMON_USER');

INSERT INTO `USER` (`user_name`, `full_name`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`) VALUES
	('hiep.ho', 'Hiep Ho', '$2a$16$9qr2tv0HmXbHBsx.TZFjfux742wCZM32a8Wi6iBqwIqaizlHPuxHS', true, true, true, true),
	('kim.le', 'Kim Le', '$2a$16$9qr2tv0HmXbHBsx.TZFjfux742wCZM32a8Wi6iBqwIqaizlHPuxHS', true, true, true, true);

INSERT INTO `USER_ROLE` (`user_id`, `role_id`) VALUES (1, 1), (1, 2), (2, 3);
