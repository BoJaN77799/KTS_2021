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

insert into category (name) values ('Supe');
insert into category (name) values ('Gazirana pica');


insert into item (name, description, image, cost, category_id, deleted) values ('Supa', 'Bas je Slana', 'putanja/supa', 250.0, 1, 'False');
insert into food (id, recipe, time_to_make, food_type) values (1, 'Ma lako se pravi', 20, 'APETIZER');

insert into item (name, description, image, cost, category_id, deleted) values ('Coca Cola', 'Bas je gazirana', 'putanja/cola', 140.0, 2, 'False');
insert into drink (id, volume ) values (2, 0.5);

insert into menu (name) values ('Leto');

insert into menu_item (menu_id, item_id) values (1, 1);
insert into menu_item (menu_id, item_id) values (1, 2);

insert into item_ingredient (item_id, ingredient_id) values (1,3);
insert into item_ingredient (item_id, ingredient_id) values (1,2);
insert into item_ingredient (item_id, ingredient_id) values (1,6);
insert into item_ingredient (item_id, ingredient_id) values (2,1);
insert into item_ingredient (item_id, ingredient_id) values (2,6);


insert into price (amount, date_from, item_id) values (300.0, 1636730076405, 1);
insert into price (amount, date_from, item_id) values (160.0, 1636730076405, 2);

insert into restaurant_order (status, created_at, note) values ('NEW', 1636730076405, 'Pozuri, galdan sam.');

insert into order_item (quantity, status, price, priority, order_id, item_id) values (2, 'ORDERED', 300.0, 'True', 1, 1);
insert into order_item (quantity, status, price, priority, order_id, item_id) values (3, 'ORDERED', 160.0, 'False', 1, 2);

