CREATE TABLE customers
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `last_name`  varchar(80)  NOT NULL,
    `first_name` varchar(80)  NOT NULL,
    `email`      varchar(100) NOT NULL,
    `address`    varchar(100) NOT NULL,
    `gender`     varchar(6)   NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE orders
(
    `id`           bigint      NOT NULL AUTO_INCREMENT,
    `customer_id`  bigint      NOT NULL,
    `order_date`   timestamp   NOT NULL default current_timestamp,
    `order_status` varchar(20) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE order_detail
(
    `id`       bigint  NOT NULL AUTO_INCREMENT,
    `order_id` bigint  NOT NULL,
    `item_id`  bigint  NOT NULL,
    `price`    decimal NOT NULL,
    `quantity` int     NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE inventory
(
    `id`                 bigint       NOT NULL AUTO_INCREMENT,
    `item_name`          varchar(100) NOT NULL,
    `item_description`   varchar(200) NOT NULL,
    `price`              decimal      NOT NULL,
    `quantity_available` int          NOT NULL,
    PRIMARY KEY (`id`)
);

