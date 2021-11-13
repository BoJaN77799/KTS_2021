insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary)
values ('U','admin', 'Filip', 'Markovic', 'perazdera2@gmail.com', 'MALE', '0642312341', 'False', 'False', 'ADMINISTRATOR', null);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary)
values ('U', 'manager', 'Petar', 'Markovic', 'perazdera223@gmail.com', 'MALE', '0642312341', 'False', 'False', 'MANAGER', null);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary)
values ('E', 'e123', 'Milorad', 'Dodik', 'perazdera2223@gmail.com', 'MALE', '0642312341', 'False', 'False', 'WAITER', 40000.0);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary)
values ('E', 'e123', 'AAA', 'Dodik', 'perazdera22333@gmail.com', 'MALE', '0642312341', 'False', 'False', 'COOK', 40000.0);

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary)
values ('E', 'e123', 'MMM', 'Dodik', 'perazdera22443@gmail.com', 'MALE', '0642312341', 'False', 'False', 'BARMAN', 40000.0);


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

insert into item (name, description, image, cost, category_id, current_price) values ('Supa', 'Bas je Slana', 'putanja/supa', 250.0, 1, 300);
insert into food (id, recipe, time_to_make, food_type) values (1, 'Ma lako se pravi', 20, 'APETIZER');

insert into item (name, description, image, cost, category_id, current_price) values ('Pohovani kackavalj', 'I on je slan', 'putanja/kackavalj', 550.0, 3, 600);
insert into food (id, recipe, time_to_make, food_type) values (2, 'Brzo se pravi', 15, 'APETIZER');

insert into item (name, description, image, cost, category_id, current_price) values ('Prsuta', 'Bas je mnogo slana', 'putanja/prsuta', 750.0, 5, 950);
insert into food (id, recipe, time_to_make, food_type) values (3, 'Ma tesko se pravi', 5, 'APETIZER');

insert into item (name, description, image, cost, category_id, current_price) values ('Jagnjece pecenje', 'Bas je ultra Slana', 'putanja/mmm', 2000.0, 5, 2500);
insert into food (id, recipe, time_to_make, food_type) values (4, 'Ma lako se pravi', 120, 'MAIN_DISH');

insert into item (name, description, image, cost, category_id, current_price) values ('Pileca krilca', 'Nisu losa', 'putanja/krila', 850.0, 4, 960);
insert into food (id, recipe, time_to_make, food_type) values (5, 'Ma lako se pravi', 30, 'MAIN_DISH');

insert into item (name, description, image, cost, category_id, current_price) values ('Sladoled Kapri', 'Zaledjen', 'putanja/sladoled', 150.0, 6, 240);
insert into food (id, recipe, time_to_make, food_type) values (6, 'Ma lako se pravi', 2, 'DESERT');

insert into item (name, description, image, cost, category_id, current_price) values ('Coca Cola', 'Bas je gazirana', 'putanja/cola', 140.0, 2, 160);
insert into drink (id, volume ) values (7, 0.5);

insert into item (name, description, image, cost, category_id, current_price) values ('Niksicko pivo', 'Osvezavajuce', 'putanja/vopi', 110.0, 7, 140);
insert into drink (id, volume ) values (8, 0.5);

insert into item (name, description, image, cost, category_id, current_price) values ('Zajecarsko pivo', 'Vise osvezavajuce nego Niksicko', 'putanja/vopiza', 120.0, 7, 150);
insert into drink (id, volume ) values (9, 0.5);

insert into item (name, description, image, cost, category_id, current_price) values ('Coca Cola', 'Bas je gazirana', 'putanja/cola', 140.0, 2, 160);
insert into drink (id, volume ) values (10, 0.5);

insert into menu (name) values ('Leto');

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

insert into restaurant_order (status, created_at, note) values ('NEW', 1636730076405, 'Pozuri, galdan sam.');

insert into order_item (quantity, status, price, priority, order_id, item_id) values (2, 'ORDERED', 300.0, 'True', 1, 1);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (3, 'ORDERED', 160.0, 'False', 1, 2);

