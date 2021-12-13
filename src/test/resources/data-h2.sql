INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_MANAGER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_WAITER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_HEAD_COOK');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_COOK');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_BARMAN');

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('U','$2a$10$L0RXf3.bj4pIw8jmzIgha..M3Cb1JRZBPV0HKCISz/btBeh3hO0Lq', 'Filip', 'Markovic', 'admin@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'ADMINISTRATOR', null, 'Luzicka 32, Becej', false);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('U', '$2a$10$ZqRoZridlWZYrJhPbIek3.xOtlG4M8cZ3aWWpBPW2IU7yreEZd8jq', 'Petar', 'Markovic', 'manager@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'MANAGER', null, 'Luzicka 32, Trebinje', false);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('E', '$2a$10$hB5YCgeGsfGSwJZLmG3tXeTn.CrfkyksuzfcNTKgIgoL0CjBwkIcq', 'Milorad', 'Dodik', 'waiter@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'WAITER', 40000.0, 'Luzicka 32, Zajecar', false);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('E', '$2a$10$nMP47GLIXnJBN24FFK79tew9Wt.uNC2tY0sDY4sDdz7gknQzkZmui', 'Igor', 'Dodik', 'headcook@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'COOK', 40000.0, 'Luzicka 32, Gradiska', false);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('E', '$2a$10$nMP47GLIXnJBN24FFK79tew9Wt.uNC2tY0sDY4sDdz7gknQzkZmui', 'Milan', 'Dodik', 'cook@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'COOK', 40000.0, 'Luzicka 32, Gradiska', false);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('E', '$2a$10$II8odOsKhUp4LS/cID0DFuuBFWWAcXAgdxYXtlstns6z5dxPjSRw2', 'Brat', 'Dodik', 'barman@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'BARMAN', 40000.0, 'Luzicka 32, Becej', false);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('U', '$2a$10$II8odOsKhUp4LS/cID0DFuuBFWWAcXAgdxYXtlstns6z5dxPjSRw2', 'Toma', 'Dodik', 'manager123@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'MANAGER', null, 'Luzicka 32, Becej', true);

INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (1, 1); -- admin
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (2, 2); -- menager
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (3, 3); -- waiter
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (4, 4); -- head_cook
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (4, 5); -- cook
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (5, 5); -- cook
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (6, 6); -- barman


insert into ingredient (name, allergen) values ('Secer', 'False');
insert into ingredient (name, allergen) values ('Mleko', 'True');
insert into ingredient (name, allergen) values ('Brasno', 'False');
insert into ingredient (name, allergen) values ('Cimet', 'False');
insert into ingredient (name, allergen) values ('Jaja', 'True');
insert into ingredient (name, allergen) values ('Voda', 'False');
insert into ingredient (name, allergen) values ('Irski kackavalj', 'False');
insert into ingredient (name, allergen) values ('Prezle', 'False');
insert into ingredient (name, allergen) values ('So', 'False');
insert into ingredient (name, allergen) values ('Prsuta s njegusa', 'False');
insert into ingredient (name, allergen) values ('Jagnejce meso', 'False');
insert into ingredient (name, allergen) values ('Pileca krilca', 'False');
insert into ingredient (name, allergen) values ('Sos od ljutih paprika', 'False');
insert into ingredient (name, allergen) values ('Gnjecene jagode', 'False');
insert into ingredient (name, allergen) values ('Jecam', 'False');
insert into ingredient (name, allergen) values ('Slad', 'False');
insert into ingredient (name, allergen) values ('Ekstrakt limuna', 'False');

insert into category (name) values ('Supe');
insert into category (name) values ('Gazirana pica');
insert into category (name) values ('Mlecni proizvodi');
insert into category (name) values ('Pilece meso');
insert into category (name) values ('Jagnjece meso');
insert into category (name) values ('Sladoledi');
insert into category (name) values ('Alkoholna pica');

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Supa', 'Bas je Slana', 'putanja/supa', 250.0, 1, 300, 'FOOD', 'False');
insert into food (id, recipe, time_to_make, food_type) values (1, 'Ma lako se pravi', 20, 'APPETIZER');

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Pohovani kackavalj', 'I on je slan', 'putanja/kackavalj', 550.0, 3, 600, 'FOOD', 'False');
insert into food (id, recipe, time_to_make, food_type) values (2, 'Brzo se pravi', 15, 'APPETIZER');

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Prsuta', 'Bas je mnogo slana', 'putanja/prsuta', 750.0, 5, 950, 'FOOD', 'False');
insert into food (id, recipe, time_to_make, food_type) values (3, 'Ma tesko se pravi', 5, 'APPETIZER');

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Jagnjece pecenje', 'Bas je ultra Slana', 'putanja/mmm', 2000.0, 5, 2500, 'FOOD', 'False');
insert into food (id, recipe, time_to_make, food_type) values (4, 'Ma lako se pravi', 120, 'MAIN_DISH');

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Pileca krilca', 'Nisu losa', 'putanja/krila', 850.0, 4, 960, 'FOOD', 'False');
insert into food (id, recipe, time_to_make, food_type) values (5, 'Ma lako se pravi', 30, 'MAIN_DISH');

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Sladoled Kapri', 'Zaledjen', 'putanja/sladoled', 150.0, 6, 240, 'FOOD', 'False');
insert into food (id, recipe, time_to_make, food_type) values (6, 'Ma lako se pravi', 2, 'DESERT');

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Coca Cola', 'Bas je gazirana', 'putanja/cola', 140.0, 2, 160, 'DRINK', 'False');
insert into drink (id, volume ) values (7, 0.5);

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Niksicko pivo', 'Osvezavajuce', 'putanja/vopi', 110.0, 7, 140, 'DRINK', 'False');
insert into drink (id, volume ) values (8, 0.5);

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Zajecarsko pivo', 'Vise osvezavajuce nego Niksicko', 'putanja/vopiza', 120.0, 7, 150, 'DRINK', 'False');
insert into drink (id, volume ) values (9, 0.5);

insert into item (name, description, image, cost, category_id, current_price, item_type, deleted) values ('Jelen', 'Piva', 'putanja/rogonja', 140.0, 7, 170, 'DRINK', 'False');
insert into drink (id, volume ) values (10, 0.5);

insert into menu (name, active_menu) values ('Leto', 'True');

insert into menu_item (menu_id, item_id) values (1, 1);
insert into menu_item (menu_id, item_id) values (1, 2);

insert into item_ingredient (item_id, ingredient_id) values (1,3);
insert into item_ingredient (item_id, ingredient_id) values (1,2);
insert into item_ingredient (item_id, ingredient_id) values (1,6);
insert into item_ingredient (item_id, ingredient_id) values (2,9);
insert into item_ingredient (item_id, ingredient_id) values (2,10);
insert into item_ingredient (item_id, ingredient_id) values (3,9);
insert into item_ingredient (item_id, ingredient_id) values (3,10);
insert into item_ingredient (item_id, ingredient_id) values (4,9);
insert into item_ingredient (item_id, ingredient_id) values (4,10);
insert into item_ingredient (item_id, ingredient_id) values (4,11);
insert into item_ingredient (item_id, ingredient_id) values (5,9);
insert into item_ingredient (item_id, ingredient_id) values (5,12);
insert into item_ingredient (item_id, ingredient_id) values (5,13);
insert into item_ingredient (item_id, ingredient_id) values (6,1);
insert into item_ingredient (item_id, ingredient_id) values (6,14);
insert into item_ingredient (item_id, ingredient_id) values (7,1);
insert into item_ingredient (item_id, ingredient_id) values (7,6);
insert into item_ingredient (item_id, ingredient_id) values (8,15);
insert into item_ingredient (item_id, ingredient_id) values (8,17);
insert into item_ingredient (item_id, ingredient_id) values (9,15);
insert into item_ingredient (item_id, ingredient_id) values (9,16);
insert into item_ingredient (item_id, ingredient_id) values (10,1);
insert into item_ingredient (item_id, ingredient_id) values (10,6);
insert into item_ingredient (item_id, ingredient_id) values (10,17);

insert into price (amount, date_from, item_id) values (300.0, 1636730076405, 1);
insert into price (amount, date_from, item_id) values (600.0, 1636730076405, 2);
insert into price (amount, date_from, item_id) values (960.0, 1636730076405, 3);
insert into price (amount, date_from, item_id) values (2500.0, 1636730076405, 4);
insert into price (amount, date_from, item_id) values (960.0, 1636730076405, 5);
insert into price (amount, date_from, item_id) values (240.0, 1636730076405, 6);
insert into price (amount, date_from, item_id) values (160.0, 1636730076405, 7);
insert into price (amount, date_from, item_id) values (140.0, 1636730076405, 8);
insert into price (amount, date_from, item_id) values (150.0, 1636730076405, 9);
insert into price (amount, date_from, item_id) values (160.0, 1636730076405, 10);


insert into tables (active, x, y, floor) values (true, 1.0, 1.0, 0);
insert into tables (active, x, y, floor) values (true, 2.0, 2.0, 0);
insert into tables (active, x, y, floor) values (true, 3.0, 3.0, 0);
insert into tables (active, x, y, floor) values (true, 4.0, 4.0, 0);
insert into tables (active, x, y, floor) values (true, 5.0, 5.0, 0);
insert into tables (id, active, x, y, floor) values (120, true, 5.0, 5.0, 0);

insert into restaurant_order (status, created_at, note, table_id, waiter_id) values ('NEW', 1636730076405, 'Pozuri, galdan sam.', 1, 3);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (2, 'ORDERED', 300.0, 2, 1, 1);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (3, 'ORDERED', 600.0, 2, 1, 2);

insert into restaurant_order (status, created_at, note, table_id, waiter_id) values ('NEW', 1636730076405, 'Pozuri, zedan sam.', 3, 3);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (4, 'ORDERED', 180.0, -1, 2, 7);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'ORDERED', 140.0, -1, 2, 8);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, barman_id) values ('IN_PROGRESS', 1636730076405, 'Pozuri, gladan i zedan sam.', 2, 3, 4, 5);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'ORDERED', 280.0, 2, 3, 1);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'ORDERED', 140.0, 0, 3, 8);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, barman_id) values ('IN_PROGRESS', 1636730076405, 'Pozuri, gladan i zedan sam.',2, 3, 5);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'ORDERED', 600.0, 2, 4, 2);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'ORDERED', 160.0, -1, 4, 7);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, barman_id, cook_id) values ('IN_PROGRESS', 1636730076405, 'Pozuri gladan sam.', 1, 3, 5, 4);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (11, 'ORDERED', 2500.0, 1, 5, 4);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (11, 'ORDERED', 160.0, -1, 5, 7);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, barman_id) values ('FINISHED', 1636730076405, 'Pozuri, gladan i zedan sam.', 1, 3, 5);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'DELIVERED', 600.0, -1, 6, 8);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'DELIVERED', 300.0, -1, 6, 7);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, barman_id, cook_id) values ('FINISHED', 1636730076405, 'Pozuri gladan sam.', 1, 3, 5, 4);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (11, 'DELIVERED', 2500.0, 1, 7, 4);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (11, 'DELIVERED', 160.0, -1, 7, 7);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id) values ('FINISHED', 1636730076405, 'Pozuri, gladan i zedan sam.', 1, 3, 4);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'DELIVERED', 600.0, 2, 8, 2);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'DELIVERED', 300.0, 2, 8, 1);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, barman_id, cook_id) values ('FINISHED', 1636730076405, 'Pozuri gladan sam.', 1, 3, 5, 4);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (11, 'FINISHED', 2500.0, 1, 9, 4);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (11, 'FINISHED', 160.0, -1, 9, 7);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, profit) values ('FINISHED', 1636930800000, 'Pozuri, galdan sam.', 1, 3, 200);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (2, 'FINISHED', 300.0, 2, 10, 1);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (3, 'FINISHED', 600.0, 2, 10, 2);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, profit) values ('FINISHED', 1636844400000, 'Pozuri, zedan sam.', 3, 3, 200);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (4, 'FINISHED', 180.0, 2, 11, 1);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (1, 'DELIVERED', 140.0, -1, 11, 8);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, barman_id, cook_id) values ('IN_PROGRESS', 1636730076405, 'Probam izmenu porudzbine.', 1, 3, 6, 4);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (5, 'ORDERED', 2500.0, 1, 12, 4);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (5, 'ORDERED', 160.0, -1, 12, 7);

insert into restaurant_order (status, created_at, note, table_id, waiter_id, barman_id, cook_id) values ('IN_PROGRESS', 1636730076405, 'Probam izmenu porudzbine.', 1, 3, 6, 4);

insert into order_item (quantity, status, price, priority, order_id, item_id) values (5, 'ORDERED', 2500.0, 1, 13, 4);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (5, 'FINISHED', 160.0, -1, 13, 7);




insert into salary (amount, date_from, employee_id) values (100, 1633816800000, 4);
insert into salary (amount, date_from, employee_id) values (200, 1634680800000, 4);
insert into salary (amount, date_from, employee_id) values (300, 1636239600000, 4);

insert into bonus (amount, date_to, employee_id) values (100, 1631743200000, 4);
insert into bonus (amount, date_to, employee_id) values (100, 1637017200000, 4);
insert into bonus (amount, date_to, employee_id) values (150, 1637017200000, 4);
insert into bonus (amount, date_to, employee_id) values (150, 1637017200000, 4);

insert into menu (name, active_menu) values ('Jesenja Ponuda', 'True');
insert into menu (name, active_menu) values ('Zimska Ponuda', 'False');

insert into tables (active, x, y, floor) values (true, 10, 10, 0);
insert into tables (active, x, y, floor) values (true, 20, 20, 0);
insert into tables (active, x, y, floor) values (true, 30, 20, 0);
insert into tables (active, x, y, floor) values (true, 40, 20, 0);
insert into tables (active, x, y, floor) values (true, 50, 20, 0);
insert into tables (active, x, y, floor) values (true, 10, 10, 1);
insert into tables (active, x, y, floor) values (true, 14, 15, 1);
insert into tables (active, x, y, floor) values (true, 5, 11, 1);
insert into tables (active, x, y, floor) values (true, 22, 10, 1);

insert into price (amount, date_from, item_id) values (300.0, 1635724800, 1);
insert into price (amount, date_from, item_id) values (300.0, 1638316799, 1);
insert into price (amount, date_from, item_id) values (300.0, 1640995199, 1);


insert into order_notification(seen, message, order_id, employee_id) values ('False', 'New order from table #1', 12, 6);
insert into order_notification(seen, message, order_id, employee_id) values ('False', 'New order from table #1', 12, 4);