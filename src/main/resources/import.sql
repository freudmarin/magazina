insert into role (name) values ('ROLE_USER')
insert into role (name) values ('ROLE_ADMIN')

insert into user (username, enabled, password, role_id) values ('user', true, '$2a$10$L4ghC4bWiMG69Pt0dYrjfOPlHBVw46L0daaY7lrJ8pAdgwOvMsPd2', 1)
insert into user (username, enabled, password, role_id) values ('user2', true, '$2a$10$L4ghC4bWiMG69Pt0dYrjfOPlHBVw46L0daaY7lrJ8pAdgwOvMsPd2', 1)
insert into user (username, enabled, password, role_id) values ('admin', true, '$2a$10$L4ghC4bWiMG69Pt0dYrjfOPlHBVw46L0daaY7lrJ8pAdgwOvMsPd2', 2)

insert into warehouse (name, city, address) values ('Tirane', 'Tirane', 'Home')
insert into warehouse (name, city, address) values ('Vlore', 'Vlore', 'Home')
insert into warehouse (name, city, address) values ('Shkoder', 'Shkoder', 'Home')

insert into category (name) values ('Fruta')
insert into category (name) values ('Perime')
insert into category (name) values ('Pije Alkoolike')
insert into category (name) values ('Pije Freskuese')
insert into category (name) values ('Te Konservuara')
insert into measuringunit (name,symbol) values ('Kilogram', 'kg')
insert into measuringunit (name,symbol) values ('Liter', 'l')
insert into measuringunit (name,symbol) values ('Cope', 'cope')
