INSERT INTO sale(sale_percent)
VALUES (0), (10), (20), (50);

INSERT INTO "role" (role_name)
VALUES ('ROLE_USER');

INSERT INTO "user"(first_name, second_name, email, password, role_id)
VALUES ('Danil', 'Arteev', 'arteic4@yandex.ru', '$2a$10$3ir9SZ8mwuNLBbTDlexWN.UsdMlm1nZ7ovCLzApck5C7.uXXZxpTq', 1),
       ('Anna', 'Khalaeva', 'khalaeva@gmail.com', '$2a$10$FUuA3vvOJuKRf2Emzah2Gu5LcxqlXjxfQP.sqZMVFa6CH3AsTvtwO', 1);

INSERT INTO "product" (name_product, price, count, sale_id, sale_price, description, road)
VALUES ('Смартфон Apple iPhone 14', 86990, 15, 2, 78291,
       'iPhone 14 - смартфон, который является одним из лучших в своем классе. Его основные преимущества - быстрый процессор, отличная камера и большой дисплей. Кроме того, этот телефон имеет долговечный аккумулятор и выдерживает пыль и воду.', 'iPhone14.jpg'),
       ('Смартфон Apple iPhone 13 Pro Max', 125990, 10, 3, 100792,
       'iPhone 13 Pro Max - смартфон, который является одним из лучших в своем классе. Его основные преимущества - быстрый процессор, отличная камера и большой дисплей. Кроме того, этот телефон имеет долговечный аккумулятор.', 'iPhone13ProMax.jpg'),
       ('Смартфон Apple iPhone 11', 53390, 20, 2, 48051,
       'iPhone 11 - смартфон, который является одним из лучших в своем классе. Его основные преимущества - быстрый процессор, отличная камера и большой дисплей. Кроме того, этот телефон имеет долговечный аккумулятор и выдерживает пыль и воду.', 'iPhone11.jpg'),
       ('Смартфон Samsung Galaxy S8', 11950, 13, 1, 11950,
       'Samsung Galaxy S8 - еще один из лидеров на рынке телефонов. Устройство получило ряд улучшений в сравнении с предыдущей моделью: более быстрый процессор, большой объем оперативной памяти и улучшенную камеру.', 'SamsungGalaxyS8.jpg'),
       ('Наушники Apple AirPods Pro', 23999, 40, 1, 23999,
       'Наушники AirPods Pro — это активное шумоподавление для иммерсивного звука и комфорт от использования целый день. Прозрачный режим, в котором слышно окружающий мир. Защита от пота и воды. Мягкие вкладыши трёх размеров в комплекте для индивидуальной фиксации наушников.', 'AirPodsPro.jpg'),
       ('Смартфон Huawei P50', 39999, 25, 2, 35991,
       'Huawei P40 Pro - устройство этого производителя можно отнести к топ-категории. Кроме быстрого процессора, большой оперативной памяти и отличных камер, P40 Pro оснащен большим дисплеем и поддерживает работу сетей 5G.', 'HuaweiP50.jpg'),
       ('Чехол для Huawei P50', 350, 100, 1, 350,
        'Чехол для Huawei P50 Pro изготовлен из поликарбоната и термопластичного полиуретана. Задняя панель абсолютно прозрачная и не скрывает цвет смартфона. Чехол-бампер имеет усиленные углы с "воздушной подушкой", способные принять на себя удар.', 'CaseHuaweiP50.jpg'),
       ('Умная колонка Яндекс Станция', 12590, 55, 2, 11331,
       'Умная колонка Яндекс Станция Макс с голосовым помощником Алиса представлена в эффектном цвете черный антрацит, мощностью звучания 65 Вт, беспроводной связью Wi-Fi (2.4 – 5 ГГц) и Bluetooth 5.0 и возможностью встраивания в экосистему Умного Дома.', 'SmartSpeaker.jpg');

INSERT INTO cart (user_id, transaction_time)
VALUES (1, '2023-05-01 12:00:00'),
       (2, '2023-05-07 13:20:00'),
       (1, '2023-05-010 10:45:00');

INSERT INTO "order" (product_id, quantity, price, cart_id)
VALUES (1, 2, 156582.0, 1),
       (3, 4, 192204.0, 1),
       (7, 10, 3500.0, 2),
       (8, 1, 11331, 2),
       (2, 1, 100792, 3);

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




