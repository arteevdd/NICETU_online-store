# Тестовая часть приложения
Тесты написаны с использованием фреймворка для тестирования  `JUnit5`  и виртуальной базы данных  `H2`, которая доступна только во время выполнения тестов.
## Структура тестов
### Тесты написаны для четырех слоев приложения:
1) [Repository](https://github.com/arteevdd/NICETU_online-store/tree/master/src/test/java/test/project/onlineshop/repository) - проверка запросов на виртуальной тестовой базе данных **H2**.
   Все запросы никаким образом не влияют на реальную базу данных. Структура базы данных определяется ORM-фреймворком `Hibernate` на основе существующих Entity и заполняется согласно [`SQL-скрипту`](https://github.com/arteevdd/NICETU_online-store/blob/master/src/test/resources/sql_scripts/test_data_init.sql).
2) [Service](https://github.com/arteevdd/NICETU_online-store/tree/master/src/test/java/test/project/onlineshop/service) - проверка функциональности бизнес-логики, иными словами, обработка данных, полученных из `Repository` слоя.
3) [Controller](https://github.com/arteevdd/NICETU_online-store/tree/master/src/test/java/test/project/onlineshop/controller) - проверка корректности возвращаемых **HTTP-статусов** в зависимости от результата, полученного из `Service` слоя.
4) [Security](https://github.com/arteevdd/NICETU_online-store/tree/master/src/test/java/test/project/onlineshop/controller) - проверка генерации, валидации и маршрутизации `JSON Web Token`.

## Запуск тестов
### Запустить тесты можно двумя способами:
1) В корневой директории выполнить команду `mvn test`, которая скомпилирует исходный код и запустит все тесты.
2) В корневой директории выполнить команду `docker-compose up --build`. Каждый раз перед развертыванием приложения `JAR-файл` пересобирается, выполняя команду `mvn package`, в жизненный цикл которой входит `mvn compile` и `mvn test`.  
   Если контейнер с backend приложением успешно запустился, значит все тесты прошли успешно.

### **Dockerfile:**
```
FROM maven:3-ibmjava-8 AS build
COPY . /temp
WORKDIR /temp
RUN mvn clean package
...
```
