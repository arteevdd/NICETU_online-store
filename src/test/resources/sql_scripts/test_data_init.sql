INSERT INTO sale(sale_percent)
VALUES (0), (10), (20);

INSERT INTO "role" (role_name)
VALUES ('ROLE_USER');

INSERT INTO "user"(first_name, second_name, email, password, role_id)
VALUES ('Danil', 'Arteev', 'arteic4@yandex.ru', '$2a$10$3ir9SZ8mwuNLBbTDlexWN.UsdMlm1nZ7ovCLzApck5C7.uXXZxpTq', 1),
       ('Anna', 'Khalaeva', 'khalaeva@gmail.com', '$2a$10$FUuA3vvOJuKRf2Emzah2Gu5LcxqlXjxfQP.sqZMVFa6CH3AsTvtwO', 1);

INSERT INTO product (name_product, price, count, sale_id, sale_price, description, road)
VALUES ('Смартфон Apple iPhone 14', 86990, 15, 2, 78291,
        'iPhone 14 - смартфон, который является одним из лучших в своем классе.', 'iPhone14.jpg'),
       ('Смартфон Apple iPhone 13 Pro Max', 125990, 10, 3, 100792,
        'iPhone 13 Pro Max - смартфон, который является одним из лучших в своем классе.', 'iPhone13ProMax.jpg'),
       ('Смартфон Apple iPhone 11', 53390, 20, 2, 48051,
        'iPhone 11 - смартфон, который является одним из лучших в своем классе.', 'iPhone11.jpg'),
       ('Смартфон Samsung Galaxy S8', 11950, 13, 1, 11950,
        'Samsung Galaxy S8 - еще один из лидеров на рынке телефонов.', 'SamsungGalaxyS8.jpg'),
       ('Наушники Apple AirPods Pro', 23999, 40, 1, 23999,
        'Наушники AirPods Pro — это активное шумоподавление для иммерсивного звука и комфорт от использования целый день.', 'AirPodsPro.jpg'),
       ('Смартфон Huawei P50', 39999, 25, 2, 35991,
        'Huawei P40 Pro - устройство этого производителя можно отнести к топ-категории.', 'HuaweiP50.jpg');

INSERT INTO cart (user_id)
VALUES (1), (2), (1);

INSERT INTO "order" (product_id, quantity, price, cart_id)
VALUES (1, 2, 156582.0, 1),
       (3, 2, 192204.0, 1),
       (5, 1, 23999.0, 2),
       (4, 1, 11950.0, 2),
       (2, 1, 100792.0, 3);


INSERT INTO category (name_category, parent_category_id)
VALUES ('Смартфоны', null),
       ('Apple', 1),
       ('Samsung', 1),
       ('Сопутствующие товары', 1),
       ('Наушники', 4);

INSERT INTO product_category (product_id, category_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 3),
       (5, 5),
       (6, 1);




