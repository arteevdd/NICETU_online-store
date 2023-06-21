-- noinspection SqlResolveForFile

INSERT INTO "producer"(name_producer)
VALUES ('Apple'), ('Samsung'), ('Huawei'), ('Яндекс');

INSERT INTO sale(sale_percent)
VALUES (0), (10), (20), (50);

INSERT INTO "role" (role_name)
VALUES ('ROLE_USER');

INSERT INTO "user"(first_name, second_name, email, password, role_id)
VALUES ('Danil', 'Arteev', 'arteic4@yandex.ru', '$2a$10$3ir9SZ8mwuNLBbTDlexWN.UsdMlm1nZ7ovCLzApck5C7.uXXZxpTq', 1),
       ('Anna', 'Khalaeva', 'khalaeva@gmail.com', '$2a$10$FUuA3vvOJuKRf2Emzah2Gu5LcxqlXjxfQP.sqZMVFa6CH3AsTvtwO', 1);

INSERT INTO "product" (producer_id, name_product, price, count, sale_id, sale_price, description, road)
VALUES (1, 'Смартфон Apple iPhone 14', 86990, 15, 2, 78291,
       'iPhone 14 - смартфон, который является одним из лучших в своем классе. Его основные преимущества - быстрый процессор, отличная камера и большой дисплей. Кроме того, этот телефон имеет долговечный аккумулятор и выдерживает пыль и воду.', 'iPhone14.jpg'),
       (1, 'Смартфон Apple iPhone 13 Pro Max', 125990, 10, 3, 100792,
       'iPhone 13 Pro Max - смартфон, который является одним из лучших в своем классе. Его основные преимущества - быстрый процессор, отличная камера и большой дисплей. Кроме того, этот телефон имеет долговечный аккумулятор.', 'iPhone13ProMax.jpg'),
       (1, 'Смартфон Apple iPhone 11', 53390, 20, 2, 48051,
       'iPhone 11 - смартфон, который является одним из лучших в своем классе. Его основные преимущества - быстрый процессор, отличная камера и большой дисплей. Кроме того, этот телефон имеет долговечный аккумулятор и выдерживает пыль и воду.', 'iPhone11.jpg'),
       (2, 'Смартфон Samsung Galaxy S8', 11950, 13, 1, 11950,
       'Samsung Galaxy S8 - еще один из лидеров на рынке телефонов. Устройство получило ряд улучшений в сравнении с предыдущей моделью: более быстрый процессор, большой объем оперативной памяти и улучшенную камеру.', 'SamsungGalaxyS8.jpg'),
       (1, 'Наушники Apple AirPods Pro', 23999, 40, 1, 23990,
       'Наушники AirPods Pro — это активное шумоподавление для иммерсивного звука и комфорт от использования целый день. Прозрачный режим, в котором слышно окружающий мир. Защита от пота и воды. Мягкие вкладыши трёх размеров в комплекте для индивидуальной фиксации наушников.', 'AirPodsPro.jpg'),
       (3, 'Смартфон Huawei P50', 39999, 25, 2, 35991,
       'Huawei P40 Pro - устройство этого производителя можно отнести к топ-категории. Кроме быстрого процессора, большой оперативной памяти и отличных камер, P40 Pro оснащен большим дисплеем и поддерживает работу сетей 5G.', 'HuaweiP50.jpg'),
       (3, 'Чехол для Huawei P50', 350, 100, 1, 350,
        'Чехол', 'CaseHuaweiP50.jpg'),
       (4, 'Умная колонка Яндекс Станция', 12590, 55, 2, 11331,
       'Колонка', 'SmartSpeaker.jpg');

INSERT INTO cart (user_id)
VALUES (1), (2), (1);

INSERT INTO "order" (product_id, quantity, price, cart_id)
VALUES (1, 2, 173980.0, 1),
       (3, 4, 213560.0, 1),
       (7, 10, 3500.0, 2),
       (8, 1, 12590, 2),
       (2, 1, 125990, 3);

INSERT INTO category (name_category, parent_category_id)
VALUES ('Смартфоны', null),
       ('Аудиотехника', null),
       ('Apple', 1),
       ('Samsung', 1),
       ('Сопутствующие товары', 1),
       ('Наушники', 5),
       ('Чехлы', 5),
       ('Портативные колонки', 2),
       ('Наушники', 2);

INSERT INTO product_category (product_id, category_id)
VALUES (1, 3),
       (2, 3),
       (3, 3),
       (4, 4),
       (5, 6),
       (6, 1),
       (7, 7),
       (8, 8),
       (5, 9);




