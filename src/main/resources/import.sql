insert into role (name) values ('ROLE_USER')
                               insert into role (name) values ('ROLE_ADMIN')

insert into user (username, enabled, password, role_id) values ('user', true, '$2a$10$L4ghC4bWiMG69Pt0dYrjfOPlHBVw46L0daaY7lrJ8pAdgwOvMsPd2', 1)
insert into user (username, enabled, password, role_id) values ('user2', true, '$2a$10$L4ghC4bWiMG69Pt0dYrjfOPlHBVw46L0daaY7lrJ8pAdgwOvMsPd2', 1)
insert into user (username, enabled, password, role_id) values ('admin', true, '$2a$10$L4ghC4bWiMG69Pt0dYrjfOPlHBVw46L0daaY7lrJ8pAdgwOvMsPd2', 2)

insert into warehouse (name, city, address, image) values ('Tirane', 'Tirane', 'Home', 'warehouse-1.jpg')
insert into warehouse (name, city, address, image) values ('Vlore', 'Vlore', 'Home', 'warehouse-2.jpg')
insert into warehouse (name, city, address, image) values ('Shkoder', 'Shkoder', 'Home', 'warehouse-3.jpg')

insert into category (name) values ('Fruta')
insert into category (name) values ('Perime')
insert into category (name) values ('Pije Alkoolike')
insert into category (name) values ('Pije Freskuese')
insert into category (name) values ('Te Konservuara')
insert into measuringunit (name,symbol) values ('Kilogram', 'kg')
insert into measuringunit (name,symbol) values ('Liter', 'l')
insert into measuringunit (name,symbol) values ('Cope', 'cope')

insert into product (name, barcode, price, amount, measuringUnit_id, category_id) values ('Molle', '123465879111', 70, 50, 1, 1)
insert into product (name, barcode, price, amount, measuringUnit_id, category_id) values ('Dardha', '123465879112', 80, 50, 1, 1)
insert into product (name, barcode, price, amount, measuringUnit_id, category_id) values ('Karrota', '123465879113', 60, 20, 1, 2)

INSERT INTO `agent` (`address`, `businessName`, `email`, `firstName`, `lastName`, `latitude`, `longitude`, `type`) VALUES ('Tirane', 'Pepsi', 'pepsi@email.com', 'Pepsi', 'Cola', '48', '48', 'S')
INSERT INTO `agent` (`address`, `businessName`, `email`, `firstName`, `lastName`, `latitude`, `longitude`, `type`) VALUES ('Tirane', 'Cola', 'cola@email.com', 'Coca', 'Cola', '48', '48', 'S')
INSERT INTO `agent` (`address`, `businessName`, `email`, `firstName`, `lastName`, `latitude`, `longitude`, `type`) VALUES ('Tirane', 'Bravo', 'bravo@email.com', 'Bravo', 'Drink', '48', '48', 'C')