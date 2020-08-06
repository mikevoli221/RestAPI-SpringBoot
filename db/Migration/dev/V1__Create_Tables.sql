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
    `order_date`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
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


CREATE TABLE role
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `description` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE user
(
    `id`                      bigint       NOT NULL AUTO_INCREMENT,
    `user_name`               varchar(255) NOT NULL UNIQUE,
    `full_name`               varchar(255) NOT NULL,
    `password`                varchar(255) DEFAULT NULL,
    `account_non_expired`     boolean      DEFAULT NULL,
    `account_non_locked`      boolean      DEFAULT NULL,
    `credentials_non_expired` boolean      DEFAULT NULL,
    `enabled`                 boolean      DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE user_role
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`)
);

