CREATE
DATABASE "foodtracker" /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE "Account"
(
    "account_id"   int          NOT NULL AUTO_INCREMENT,
    "email"        varchar(255) NOT NULL,
    "password"     varchar(255) NOT NULL,
    "account_type" varchar(255) NOT NULL,
    PRIMARY KEY ("account_id"),
    UNIQUE KEY "email" ("email")
);

CREATE TABLE "Client"
(
    "client_id"    int NOT NULL AUTO_INCREMENT,
    "account_id"   int NOT NULL,
    "client_name"  varchar(100) DEFAULT NULL,
    "phone_number" varchar(15)  DEFAULT NULL,
    PRIMARY KEY ("client_id"),
    KEY            "account_id" ("account_id"),
    CONSTRAINT "Client_ibfk_1" FOREIGN KEY ("account_id") REFERENCES "Account" ("account_id") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE "Restaurant"
(
    "restaurant_id"           int          NOT NULL AUTO_INCREMENT,
    "account_id"              int          NOT NULL,
    "category_name"           varchar(255) NOT NULL,
    "restaurant_name"         varchar(255) NOT NULL,
    "schedule"                json          DEFAULT NULL,
    "phone_number_restaurant" varchar(15)   DEFAULT NULL,
    "location"                varchar(500)  DEFAULT NULL,
    "image_url"               tinytext,
    "average_rating"          decimal(3, 2) DEFAULT NULL,
    PRIMARY KEY ("restaurant_id"),
    UNIQUE KEY "restaurant_name" ("restaurant_name","location"),
    KEY                       "account_id" ("account_id"),
    CONSTRAINT "Restaurant_ibfk_1" FOREIGN KEY ("account_id") REFERENCES "Account" ("account_id") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE "Menu"
(
    "menu_id"       int            NOT NULL AUTO_INCREMENT,
    "restaurant_id" int            NOT NULL,
    "dish"          varchar(255)   NOT NULL,
    "price"         decimal(10, 2) NOT NULL,
    "image_url"     tinytext,
    "description"   varchar(255) DEFAULT NULL,
    PRIMARY KEY ("menu_id"),
    UNIQUE KEY "restaurant_id" ("restaurant_id","dish"),
    CONSTRAINT "Menu_ibfk_1" FOREIGN KEY ("restaurant_id") REFERENCES "Restaurant" ("restaurant_id") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE "Rating"
(
    "rating_id"     int NOT NULL AUTO_INCREMENT,
    "restaurant_id" int NOT NULL,
    "client_id"     int NOT NULL,
    "rate"          decimal(3, 2) DEFAULT NULL,
    PRIMARY KEY ("rating_id"),
    KEY             "client_id" ("client_id"),
    KEY             "FKn667u8cj4wf631jesmci4t749" ("restaurant_id"),
    CONSTRAINT "FKn667u8cj4wf631jesmci4t749" FOREIGN KEY ("restaurant_id") REFERENCES "Restaurant" ("restaurant_id"),
    CONSTRAINT "Rating_ibfk_2" FOREIGN KEY ("client_id") REFERENCES "Client" ("client_id"),
    CONSTRAINT "Rating_chk_1" CHECK ((`rate` between 1 and 5))
);

