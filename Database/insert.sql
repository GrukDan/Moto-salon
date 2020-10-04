INSERT INTO motosalon.role(role_name) VALUES('ADMIN');
INSERT INTO motosalon.role(role_name) VALUES('USER');

INSERT INTO motosalon.order_status(status) VALUES ('Закрыт');
INSERT INTO motosalon.order_status(status) VALUES ('В обработке');
INSERT INTO motosalon.order_status(status) VALUES ('Отправлен');

INSERT INTO motosalon.product_type(product_type) VALUES ('Мотоцикл');
INSERT INTO motosalon.product_type(product_type) VALUES ('Мопед');
INSERT INTO motosalon.product_type(product_type) VALUES ('Скутер');
INSERT INTO motosalon.product_type(product_type) VALUES ('Велосипед');
INSERT INTO motosalon.product_type(product_type) VALUES ('Квадроцикл');
INSERT INTO motosalon.product_type(product_type) VALUES ('Мотовелосипед');
INSERT INTO motosalon.product_type(product_type) VALUES ('Вездеход');
INSERT INTO motosalon.product_type(product_type) VALUES ('Экипировка');
INSERT INTO motosalon.product_type(product_type) VALUES ('Аксессуары');
INSERT INTO motosalon.product_type(product_type) VALUES ('Запчасти');
INSERT INTO motosalon.product_type(product_type) VALUES ('Комплектующие');

INSERT INTO motosalon.user(name, surname, email, password, role) VALUES ('Михаил','Горбачев','horb@mail.com','USSR91',2);
INSERT INTO motosalon.user(name, surname, email, password, role) VALUES ('Борис','Ельцин','elc@mail.com','RUSSIA91',3);