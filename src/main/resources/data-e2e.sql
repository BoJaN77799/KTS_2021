INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_MANAGER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_WAITER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_HEAD_COOK');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_COOK');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_BARMAN');

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('U','$2a$10$L0RXf3.bj4pIw8jmzIgha..M3Cb1JRZBPV0HKCISz/btBeh3hO0Lq', 'Filip', 'Markovic',
'admin@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'ADMINISTRATOR', null, 'Luzicka 32, Becej', false); --1

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('U', '$2a$10$ZqRoZridlWZYrJhPbIek3.xOtlG4M8cZ3aWWpBPW2IU7yreEZd8jq', 'Petar', 'Djuric',
'manager@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'MANAGER', null, 'Luzicka 32, Trebinje', false); --2

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed,
is_email_verified, user_type, salary, address, deleted, profile_photo)
values ('E', '$2a$10$hB5YCgeGsfGSwJZLmG3tXeTn.CrfkyksuzfcNTKgIgoL0CjBwkIcq', 'Marko', 'Bjelica',
'waiter_marko@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'WAITER', 4000.0, 'Luzicka 32, Livno', false,
'/user_profile_photos/test/default.jpg'); --3

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed,
is_email_verified, user_type, salary, address, deleted, profile_photo)
values ('E', '$2a$10$hB5YCgeGsfGSwJZLmG3tXeTn.CrfkyksuzfcNTKgIgoL0CjBwkIcq', 'Darko', 'Tica',
'waiter_milorad@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'WAITER', 7000.0, 'Luzicka 15, Laktasi', false,
'/user_profile_photos/test/default.jpg'); --4

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed,
 is_email_verified, user_type, salary, address, deleted, profile_photo)
values ('E', '$2a$10$nMP47GLIXnJBN24FFK79tew9Wt.uNC2tY0sDY4sDdz7gknQzkZmui', 'Bojan', 'Baskalo',
'headcook_igor@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'HEAD_COOK', 10000.0, 'Dunavska 32, Novi Sad', false,
'/user_profile_photos/test/default.jpg'); --5

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed,
is_email_verified, user_type, salary, address, deleted, profile_photo)
values ('E', '$2a$10$nMP47GLIXnJBN24FFK79tew9Wt.uNC2tY0sDY4sDdz7gknQzkZmui', 'Veljko', 'Tomic',
'cook_drasko@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'COOK', 3000.0, 'Luzicka 32, Gradiska', false,
'/user_profile_photos/test/default.jpg'); --6

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed,
is_email_verified, user_type, salary, address, deleted, profile_photo)
values ('E', '$2a$10$nMP47GLIXnJBN24FFK79tew9Wt.uNC2tY0sDY4sDdz7gknQzkZmui', 'Cvetko', 'Anova',
'cook_milan@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'COOK', 3500.0, 'Luzicka 32, Gradiska', false,
'/user_profile_photos/test/default.jpg'); --7

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed,
 is_email_verified, user_type, salary, address, deleted, profile_photo)
values ('E', '$2a$10$II8odOsKhUp4LS/cID0DFuuBFWWAcXAgdxYXtlstns6z5dxPjSRw2', 'Dunjica', 'Slatkica',
'barman_vuk@maildrop.cc', 'FEMALE', '0642312341', 'False', 'False', 'BARMAN', 4000.0, 'Luzicka 32, Becej', false,
'/user_profile_photos/test/default.jpg'); --8

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed,
is_email_verified, user_type, salary, address, deleted, profile_photo)
values ('E', '$2a$10$II8odOsKhUp4LS/cID0DFuuBFWWAcXAgdxYXtlstns6z5dxPjSRw2', 'Mica', 'Bradina',
'barman_andrej@maildrop.cc', 'MALE', '0642312341', 'False', 'False', 'BARMAN', 4200.0, 'Luzicka 32, Becej', false,
'/user_profile_photos/test/default.jpg'); --9

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('U', '$2a$10$ZqRoZridlWZYrJhPbIek3.xOtlG4M8cZ3aWWpBPW2IU7yreEZd8jq', 'Pele', 'Djuric',
        'manager2@maildrop.cc', 'MALE', '06433312341', 'False', 'False', 'MANAGER', null, 'Luzicka 32, Trebinje', false); --10

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('U', '$2a$10$ZqRoZridlWZYrJhPbIek3.xOtlG4M8cZ3aWWpBPW2IU7yreEZd8jq', 'Pex', 'Djuric',
        'manager3@maildrop.cc', 'MALE', '06433312341', 'False', 'False', 'MANAGER', null, 'Luzicka 32, Trebinje', false); --11

insert into app_user (dtype, password, first_name, last_name, email, gender, telephone, is_password_changed, is_email_verified, user_type, salary, address, deleted)
values ('U', '$2a$10$ZqRoZridlWZYrJhPbIek3.xOtlG4M8cZ3aWWpBPW2IU7yreEZd8jq', 'Pezo', 'Djuric',
        'manager4@maildrop.cc', 'MALE', '06433312341', 'False', 'False', 'MANAGER', null, 'Luzicka 32, Trebinje', false); --12

--- prva plata 1.11.2021
insert into salary (amount, date_from, employee_id) values (3700, 1635721200000, 3);
insert into salary (amount, date_from, employee_id) values (6500, 1635721200000, 4);
insert into salary (amount, date_from, employee_id) values (9500, 1635721200000, 5);
insert into salary (amount, date_from, employee_id) values (2800, 1635721200000, 6);
insert into salary (amount, date_from, employee_id) values (3300, 1635721200000, 7);
insert into salary (amount, date_from, employee_id) values (3800, 1635721200000, 8);
insert into salary (amount, date_from, employee_id) values (4000, 1635721200000, 9);
-- trenutna plata 1.1.2022
insert into salary (amount, date_from, employee_id) values (4000, 1640991600000, 3);
insert into salary (amount, date_from, employee_id) values (7000, 1640991600000, 4);
insert into salary (amount, date_from, employee_id) values (10000, 1640991600000, 5);
insert into salary (amount, date_from, employee_id) values (3000, 1640991600000, 6);
insert into salary (amount, date_from, employee_id) values (3500, 1640991600000, 7);
insert into salary (amount, date_from, employee_id) values (4000, 1640991600000, 8);
insert into salary (amount, date_from, employee_id) values (4200, 1640991600000, 9);

insert into bonus (amount, date_to, employee_id) values (500, 1637190000000, 3);
insert into bonus (amount, date_to, employee_id) values (650, 1637362800000, 3);
insert into bonus (amount, date_to, employee_id) values (720, 1639695600000, 6);
insert into bonus (amount, date_to, employee_id) values (300, 1640214000000, 7);

INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (1, 1); -- admin
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (2, 2); -- menager
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (3, 3); -- waiter1
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (4, 3); -- waiter2
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (5, 4); -- head_cook
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (5, 5); -- cook
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (6, 5); -- cook1
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (7, 5); -- cook2
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (8, 6); -- barman1
INSERT INTO USER_AUTHORITY (app_user_id, authority_id) VALUES (9, 6); -- barman2


insert into ingredient (name, allergen) values ('Secer', 'False'); --1
insert into ingredient (name, allergen) values ('Mleko', 'True'); --2
insert into ingredient (name, allergen) values ('Brasno', 'False'); --3
insert into ingredient (name, allergen) values ('Cimet', 'True'); --4
insert into ingredient (name, allergen) values ('Jaja', 'True'); --5
insert into ingredient (name, allergen) values ('Voda', 'False'); --6
insert into ingredient (name, allergen) values ('Prezle', 'False'); --7
insert into ingredient (name, allergen) values ('So', 'False'); --8
insert into ingredient (name, allergen) values ('Gnjecene jagode', 'True'); --9
insert into ingredient (name, allergen) values ('Jecam', 'False'); --10
insert into ingredient (name, allergen) values ('Slad', 'True'); --11
insert into ingredient (name, allergen) values ('Ekstrakt limuna', 'True'); --12

insert into category (name) values ('Supe'); --1
insert into category (name) values ('Mlecni proizvodi'); --2
insert into category (name) values ('Mesni proizvodi'); --3
insert into category (name) values ('Sladoledi'); --4
insert into category (name) values ('Kolaci'); --5
insert into category (name) values ('Palacinke'); --6
insert into category (name) values ('Gazirana pica'); --7
insert into category (name) values ('Bezalkoholna pica'); --8
insert into category (name) values ('Alkoholna pica'); --9
insert into category (name) values ('Kafe'); --10

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Govedja supa', 'Ukusna i slana', 'putanja/supa', 250.0, 1, 300, 'FOOD', 'Stalni meni', 'False');
insert into food (id, recipe, time_to_make, food_type) values (1, 'Ma lako se pravi.', 20, 'APPETIZER'); --1

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Pileca supa', 'Dobra kao dobar dan.', 'putanja/supa', 300.0, 1, 350, 'FOOD', 'Specijalni meni', 'False');
insert into food (id, recipe, time_to_make, food_type) values (2, 'Ma lako se pravi.', 20, 'APPETIZER'); --2

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Pohovani kackavalj', 'Pohovan i slan.', 'putanja/kackavalj', 300.0, 2, 600, 'FOOD', 'Leto', 'False');
insert into food (id, recipe, time_to_make, food_type) values (3, 'Brzo se pravi.', 15, 'APPETIZER'); --3

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Gorgonzola', 'Da je ukusnija ne bi valjala.', 'putanja/kackavalj', 400.0, 2, 650, 'FOOD', 'Stalni meni', 'False');
insert into food (id, recipe, time_to_make, food_type) values (4, 'Brzo se pravi.', 20, 'APPETIZER'); --4

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Prsuta', 'Bas je mnogo slana', 'putanja/prsuta', 750.0, 3, 950, 'FOOD', 'Specijalni meni', 'False');
insert into food (id, recipe, time_to_make, food_type) values (5, 'Dugo se susi.', 5, 'APPETIZER'); --5

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Jagnjece pecenje', 'Velja ovo dobro zasolio.', 'putanja/mmm', 2000.0, 3, 2500, 'FOOD', 'Zima', 'False');
insert into food (id, recipe, time_to_make, food_type) values (6, 'Treba dobro osoliti.', 120, 'MAIN_DISH'); --6

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Pileca krilca', 'Tope se u ustima.', 'putanja/krila', 850.0, 3, 960, 'FOOD', 'Specijalni meni', 'False');
insert into food (id, recipe, time_to_make, food_type) values (7, 'Treba ti pile sa bar jednim krilom.', 30, 'MAIN_DISH'); --7

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Sladoled Kapri', 'Bolji od kinga.', 'putanja/sladoled', 150.0, 4, 240, 'FOOD', 'Stalni meni', 'False');
insert into food (id, recipe, time_to_make, food_type) values (8, 'Treba mlijeka i sladera.', 2, 'DESERT'); --8

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Sladoled Rumenko', 'Niskobudzetno cudo.', 'putanja/sladoled', 100.0, 4, 200, 'FOOD', 'Specijalni meni', 'False');
insert into food (id, recipe, time_to_make, food_type) values (9, 'Treba vode i secera.', 2, 'DESERT'); --9

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Cheese-Cake', 'Kora meka napravljena od plazme.', 'putanja/kolac', 200.0, 5, 500, 'FOOD', 'Nedefinisan', 'False');
insert into food (id, recipe, time_to_make, food_type) values (10, 'Treba sira, malina i plazma.', 15, 'DESERT'); --10

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Ledene kocke', 'Ledno, slatko, preukusno i kockasto.', 'putanja/kolac', 300.0, 5, 450, 'FOOD', 'Specijalni meni', 'False');
insert into food (id, recipe, time_to_make, food_type) values (11, 'Treba dosta cokolade.', 40, 'DESERT'); --11

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Pohovana palacinka', 'Slana je.', 'putanja/palacinka', 200.0, 6, 500, 'FOOD', 'Stalni meni', 'False');
insert into food (id, recipe, time_to_make, food_type) values (12, 'Peces, napunis, pa onda pohujes.', 10, 'MAIN_DISH'); --12

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Nutela plazma palacinka', 'Slatka je.', 'putanja/palacinka', 200.0, 6, 400, 'FOOD', 'Zima', 'False');
insert into food (id, recipe, time_to_make, food_type) values (13, 'Klasicna palacinka koja se napuni sa nutelom i plazmom.', 10, 'DESERT'); --13

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Coca Cola', 'Bas je gazirana.', 'putanja/cola', 100.0, 7, 200, 'DRINK', 'Leto', 'False');
insert into drink (id, volume) values (14, 0.3); --14

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Sprite', 'Bas je gazirana.', 'putanja/sprite', 100.0, 7, 180, 'DRINK', 'Specijalni meni', 'False');
insert into drink (id, volume) values (15, 0.3); --15

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Bravo orange', 'Nerealno slatko.', 'putanja/sok', 100.0, 8, 220, 'DRINK', 'Stalni meni', 'False');
insert into drink (id, volume) values (16, 0.3); --16

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Exotic kruska', 'Bolja je limeta.', 'putanja/sok', 100.0, 8, 180, 'DRINK', 'Zima', 'False');
insert into drink (id, volume) values (17, 0.3); --17

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Niksicko pivo', 'Osvezavajuce.', 'putanja/vopi', 110.0, 9, 200, 'DRINK', 'Leto', 'False');
insert into drink (id, volume ) values (18, 0.5); --18

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Banjalucko pivo', 'Gorko kao djavo.', 'putanja/vopi', 110.0, 9, 220, 'DRINK', 'Nedefinisan', 'False');
insert into drink (id, volume ) values (19, 0.5); --19

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Espresso', 'Za dobro jutro.', 'putanja/coffee', 110.0, 10, 200, 'DRINK', 'Stalni meni', 'False');
insert into drink (id, volume ) values (20, 0.1); --20

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
            values ('Cappuccino', '5cm pjene.', 'putanja/coffee', 150.0, 10, 240, 'DRINK', 'Stalni meni', 'False');
insert into drink (id, volume ) values (21, 0.1); --21

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
    values ('Irish cream cappuccino', '2cm pjene.', 'putanja/coffee', 170.0, 10, 280, 'DRINK', 'Stalni meni', 'False');
insert into drink (id, volume ) values (22, 0.1); --22

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
    values ('Chocolate Cappuccino', '2cm pjene.', 'putanja/coffee', 170.0, 10, 280, 'DRINK', 'Stalni meni', 'False');
insert into drink (id, volume ) values (23, 0.1); --23

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
    values ('Macchiato', '1cm pjene.', 'putanja/coffee', 180.0, 10, 290, 'DRINK', 'Stalni meni', 'False');
insert into drink (id, volume ) values (24, 0.1); --24

insert into item (name, description, image, cost, category_id, current_price, item_type, menu, deleted)
    values ('Chardonnay', 'Penusavo, ozbiljno, osvezenje.', 'putanja/champagne', 2000, 9, 3400, 'DRINK', 'Stalni meni', 'False');
insert into drink (id, volume ) values (25, 0.7); --25

insert into menu (name, active_menu) values ('Stalni meni', 'True');
insert into menu (name, active_menu) values ('Specijalni meni', 'True');
insert into menu (name, active_menu) values ('Meni za ljubitelje slatkog', 'True');
insert into menu (name, active_menu) values ('Leto', 'False');
insert into menu (name, active_menu) values ('Zima', 'False');

insert into item_ingredient (item_id, ingredient_id) values (1,3);
insert into item_ingredient (item_id, ingredient_id) values (2,2);
insert into item_ingredient (item_id, ingredient_id) values (3,6);
insert into item_ingredient (item_id, ingredient_id) values (4,9);
insert into item_ingredient (item_id, ingredient_id) values (5,10);
insert into item_ingredient (item_id, ingredient_id) values (6,9);
insert into item_ingredient (item_id, ingredient_id) values (7,10);
insert into item_ingredient (item_id, ingredient_id) values (8,1);
insert into item_ingredient (item_id, ingredient_id) values (8,2);
insert into item_ingredient (item_id, ingredient_id) values (9,1);
insert into item_ingredient (item_id, ingredient_id) values (10,1);
insert into item_ingredient (item_id, ingredient_id) values (10,2);
insert into item_ingredient (item_id, ingredient_id) values (10,5);
insert into item_ingredient (item_id, ingredient_id) values (11,1);
insert into item_ingredient (item_id, ingredient_id) values (11,2);
insert into item_ingredient (item_id, ingredient_id) values (11,5);
insert into item_ingredient (item_id, ingredient_id) values (12,1);
insert into item_ingredient (item_id, ingredient_id) values (12,3);
insert into item_ingredient (item_id, ingredient_id) values (12,5);
insert into item_ingredient (item_id, ingredient_id) values (12,8);
insert into item_ingredient (item_id, ingredient_id) values (13,1);
insert into item_ingredient (item_id, ingredient_id) values (13,2);
insert into item_ingredient (item_id, ingredient_id) values (13,3);
insert into item_ingredient (item_id, ingredient_id) values (13,5);
insert into item_ingredient (item_id, ingredient_id) values (14,1);
insert into item_ingredient (item_id, ingredient_id) values (14,6);
insert into item_ingredient (item_id, ingredient_id) values (15,1);
insert into item_ingredient (item_id, ingredient_id) values (15,6);
insert into item_ingredient (item_id, ingredient_id) values (16,6);
insert into item_ingredient (item_id, ingredient_id) values (17,6);
insert into item_ingredient (item_id, ingredient_id) values (18,10);
insert into item_ingredient (item_id, ingredient_id) values (18,11);
insert into item_ingredient (item_id, ingredient_id) values (19,10);
insert into item_ingredient (item_id, ingredient_id) values (19,11);
insert into item_ingredient (item_id, ingredient_id) values (20,6);
insert into item_ingredient (item_id, ingredient_id) values (21,6);
insert into item_ingredient (item_id, ingredient_id) values (21,2);

-- vrijedi od 1.11
insert into price (amount, date_from, item_id) values (300.0, 1635721200000, 1);
insert into price (amount, date_from, item_id) values (350.0, 1635721200000, 2);
insert into price (amount, date_from, item_id) values (600.0, 1635721200000, 3);
insert into price (amount, date_from, item_id) values (650.0, 1635721200000, 4);
insert into price (amount, date_from, item_id) values (950.0, 1635721200000, 5);
insert into price (amount, date_from, item_id) values (2500.0, 1635721200000, 6);
insert into price (amount, date_from, item_id) values (960.0, 1635721200000, 7);
insert into price (amount, date_from, item_id) values (240.0, 1635721200000, 8);
insert into price (amount, date_from, item_id) values (200.0, 1635721200000, 9);
insert into price (amount, date_from, item_id) values (500.0, 1635721200000, 10);
insert into price (amount, date_from, item_id) values (450.0, 1635721200000, 11);
insert into price (amount, date_from, item_id) values (500.0, 1635721200000, 12);
insert into price (amount, date_from, item_id) values (400.0, 1635721200000, 13);
insert into price (amount, date_from, item_id) values (200.0, 1635721200000, 14);
insert into price (amount, date_from, item_id) values (180.0, 1635721200000, 15);
insert into price (amount, date_from, item_id) values (220.0, 1635721200000, 16);
insert into price (amount, date_from, item_id) values (180.0, 1635721200000, 17);
insert into price (amount, date_from, item_id) values (200.0, 1635721200000, 18);
insert into price (amount, date_from, item_id) values (220.0, 1635721200000, 19);
insert into price (amount, date_from, item_id) values (200.0, 1635721200000, 20);
insert into price (amount, date_from, item_id) values (240.0, 1635721200000, 21);

insert into tables (active, x, y, floor) values (true, 53, 53, 0);
insert into tables (active, x, y, floor) values (true, 161, 56, 0);
insert into tables (active, x, y, floor) values (true, 272, 66, 0);
insert into tables (active, x, y, floor) values (true, 397, 70, 0);
insert into tables (active, x, y, floor) values (true, 508, 70, 0);
insert into tables (active, x, y, floor) values (true, 52, 276, 0);
insert into tables (active, x, y, floor) values (true, 163, 281, 0);
insert into tables (active, x, y, floor) values (true, 283, 297, 0);
insert into tables (active, x, y, floor) values (true, 396, 305, 0);
insert into tables (active, x, y, floor) values (true, 504, 243, 0);
insert into tables (active, x, y, floor) values (true, 53, 53, 1);
insert into tables (active, x, y, floor) values (true, 161, 56, 1);
insert into tables (active, x, y, floor) values (true, 272, 66, 1);
insert into tables (active, x, y, floor) values (true, 397, 70, 1);

---------------- MJESEC (NOVEMBAR)

insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, profit)
        values ('FINISHED', 1636930800000, 'Pozuri, galdan sam.', 1, 3, 6, 1120);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 300.0, 1, 1, 1); --govedja supa 250
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 600.0, 1, 1, 3); --pohovani kackavalj 300
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 960.0, 2, 1, 7); --pileca krilca 850
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (4, 'DELIVERED', 200.0, 3, 1, 10); --cheese-cake 200

insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, profit)
        values ('FINISHED', 1637190000000, 'Pozuri, zedan sam.', 2, 4, 7, 800);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 350.0, 1, 2, 2); --pileca supa 300
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (1, 'DELIVERED', 2500.0, 2, 2, 6); --jagnjece pecenje  2000
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 200.0, 3, 2, 9); --sladoled rumenko 100

insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, barman_id, profit)
    values ('FINISHED', 1637362800000, 'Pozuri', 3, 3, 6, 8, 660);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 350.0, 1, 3, 2); --pileca supa 300
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 950.0, 2, 3, 5); --prsuta 750
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 180.0, 0, 3, 15); --sprite 100

insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, barman_id, profit)
    values ('FINISHED', 1637967600000 , 'Pozuri', 4, 4, 7, 9, 1320);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 650.0, 1, 4, 4); --gorgonzola 400
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 960.0, 2, 4, 7); --pileca krilca 850
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 400.0, 3, 4, 13); --nutela plazma palacinka 200
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 200.0, 0, 4, 14); --cola 100

-------------- MJESEC (DECEMBAR)

insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, barman_id, profit)
        values ('FINISHED', 1639263600000, 'Pozuri, galdan sam.', 1, 3, 6, 8, 680);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 300.0, 1, 5, 1); --govedja supa 250
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 950.0, 1, 5, 5); --prsuta 750
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 240.0, 0, 5, 21); --cappucino 150


insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, profit)
        values ('FINISHED', 1639695600000, 'Pozuri, zedan sam.', 3, 4, 7, 1000);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 350.0, 1, 6, 2); --pileca supa 300
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (1, 'DELIVERED', 2500.0, 2, 6, 6); --jagnjece pecenje 2000
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (4, 'DELIVERED', 200.0, 3, 6, 9); --sladoled rumenko 100

insert into restaurant_order (status, created_at, note, table_id, waiter_id, barman_id, profit)
    values ('FINISHED', 1640214000000, 'Pozuri', 2, 3, 8, 570);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (3, 'DELIVERED', 200.0, 0, 7, 20); --espresso 110
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (3, 'DELIVERED', 200.0, 0, 7, 14); --cola 100


insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, barman_id, profit)
    values ('FINISHED', 1640732400000, 'Pozuri', 4, 4, 7, 9, 3000);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (5, 'DELIVERED', 650.0, 1, 8, 4); --gorgonzola 400
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (1, 'DELIVERED', 2500.0, 2, 8, 6); --jagnjece pecenje 2000
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (5, 'DELIVERED', 450.0, 3, 8, 11); --ledena kocka 300
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (5, 'DELIVERED', 200.0, 0, 8, 14); --cola 100

--------------- MJESEC (JANUAR)

insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, barman_id, profit)
        values ('FINISHED', 1641423600000, 'Pozuri, galdan sam.', 1, 3, 6, 8, 620);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 950.0, 2, 9, 5); --prsuta 750
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'DELIVERED', 220.0, 0, 9, 19); --banjalucko 110

insert into restaurant_order (status, created_at, note, table_id, waiter_id, cook_id, barman_id, profit)
        values ('FINISHED', 1641855600000, 'Pozuri, zedan sam.', 3, 4, 6, 9, 2360);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (8, 'DELIVERED', 220.0, 1, 10, 16); --bravo sok 100
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (4, 'DELIVERED', 450.0, 3, 10, 11); --ledene kocke 300
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (4, 'DELIVERED', 400.0, 2, 10, 13); --palacinke slatke 200

------- NEW
insert into restaurant_order (status, created_at, note, table_id, waiter_id)
        values ('NEW', 1642028400000, 'Pozuri, galdan sam.', 1, 3);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'ORDERED', 300.0, 1, 11, 1); --govednja supa 250
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'ORDERED', 350.0, 1, 11, 2); --pileca supa 300
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (1, 'ORDERED', 2500.0, 2, 11, 6); --jagnjece pecenje 2000

insert into restaurant_order (status, created_at, note, table_id, waiter_id)
        values ('NEW', 1642028400000, 'Pozuri, zedan sam.', 4, 3);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'ORDERED', 220.0, 0, 12, 16); --bravo sok 100
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (2, 'ORDERED', 450.0, 2, 12, 11); --ledene kocke 300


insert into restaurant_order (status, created_at, note, table_id, waiter_id) -- Order to update
values ('NEW', 1642028400000, 'Pozuri, zedan sam za stolom 13.', 13, 4);
insert into order_item (quantity, status, price, priority, order_id, item_id)
values (2, 'ORDERED', 220.0, 0, 13, 16); --bravo sok 100
insert into order_item (quantity, status, price, priority, order_id, item_id)
values (2, 'ORDERED', 450.0, 2, 13, 11); --ledene kocke 300

insert into restaurant_order (status, created_at, note, table_id, waiter_id) -- Order to finish
values ('NEW', 1642028400000, 'Pozuri, zedan sam za stolom 14.', 14, 4);
insert into order_item (quantity, status, price, priority, order_id, item_id)
values (2, 'ORDERED', 220.0, 0, 14, 16); --bravo sok 100
insert into order_item (quantity, status, price, priority, order_id, item_id)
values (2, 'ORDERED', 450.0, 2, 14, 11); --ledene kocke 300

insert into restaurant_order (status, created_at, note, table_id, waiter_id) -- Order to finish from table view
values ('NEW', 1642028400000, 'Pozuri, zedan sam za stolom 11.', 11, 4);
insert into order_item (quantity, status, price, priority, order_id, item_id)
values (2, 'ORDERED', 220.0, 0, 15, 16); --bravo sok 100
insert into order_item (quantity, status, price, priority, order_id, item_id)
values (2, 'ORDERED', 450.0, 2, 15, 11); --ledene kocke 300

insert into restaurant_order (status, created_at, note, table_id, waiter_id) -- Order to finish from table view
values ('NEW', 1642028400000, 'Pozuri, zedan sam za stolom 11.', 10, 4);
insert into order_item (quantity, status, price, priority, order_id, item_id)
values (2, 'FINISHED', 220.0, 0, 16, 16); --bravo sok 100
insert into order_item (quantity, status, price, priority, order_id, item_id)
values (2, 'ORDERED', 450.0, 2, 16, 11); --ledene kocke 300

------- IN_PROGRESS
insert into restaurant_order (status, created_at, note, table_id, waiter_id, barman_id, profit)
    values ('IN_PROGRESS', 1640214000000, 'Pozuri', 2, 4, 8, 570);
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (3, 'ORDERED', 200.0, 0, 17, 20); --espresso 110
insert into order_item (quantity, status, price, priority, order_id, item_id)
                values (3, 'DELIVERED', 200.0, 0, 17, 14); --cola 100

-- notifications
insert into order_notification(seen, message, order_id, employee_id)
values (False, 'New order from table 1.', 11, 5); -- cook

insert into order_notification(seen, message, order_id, employee_id)
values (False, 'New order from table 1.', 11, 6); -- cook

insert into order_notification(seen, message, order_id, employee_id)
values (False, 'New order from table 1.', 11, 7); -- cook

insert into order_notification(seen, message, order_id, employee_id)
values (False, 'New order from table 4.', 12, 5); -- cook

insert into order_notification(seen, message, order_id, employee_id)
values (False, 'New order from table 4.', 12, 6); -- cook

insert into order_notification(seen, message, order_id, employee_id)
values (False, 'New order from table 4.', 12, 7); -- cook

insert into order_notification(seen, message, order_id, employee_id)
values (False, 'New order from table 4.', 12, 9); -- cook
