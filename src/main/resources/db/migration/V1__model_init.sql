CREATE TABLE sale (
    sale_id SERIAL PRIMARY KEY NOT NULL,
    sale_percent INTEGER NOT NULL
);

CREATE TABLE role (
    role_id SERIAL PRIMARY KEY NOT NULL,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE "user" (
    user_id SERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    second_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES "role"(role_id)
);

CREATE TABLE cart (
    cart_id SERIAL PRIMARY KEY NOT NULL,
    user_id INTEGER NOT NULL,
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES "user"(user_id)
);

CREATE TABLE "product" (
    product_id SERIAL PRIMARY KEY NOT NULL,
    name_product varchar(255) UNIQUE NOT NULL,
    price DECIMAL(8, 2) NOT NULL ,
    sale_price DECIMAL(8, 2) NOT NULL ,
    count INTEGER,
    description VARCHAR(300) NOT NULL,
    road VARCHAR(255) NOT NULL,
    sale_id INTEGER NOT NULL,
    CONSTRAINT fk_product_sale FOREIGN KEY (sale_id) REFERENCES sale(sale_id)
);

CREATE TABLE "order" (
    order_id SERIAL PRIMARY KEY NOT NULL,
    quantity INTEGER NOT NULL,
    price DECIMAL(8, 2),
    cart_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    CONSTRAINT fk_order_cart FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    CONSTRAINT fk_order_product FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE category (
    category_id SERIAL PRIMARY KEY NOT NULL,
    name_category VARCHAR(70) NOT NULL,
    parent_category_id INTEGER NULL,
    CONSTRAINT fk_self_join FOREIGN KEY (parent_category_id) REFERENCES category(category_id)
);

CREATE TABLE product_category (
    product_category_id SERIAL PRIMARY KEY NOT NULL,
    product_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    CONSTRAINT fk_product_category_product FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    CONSTRAINT fk_product_category_category FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE
);
