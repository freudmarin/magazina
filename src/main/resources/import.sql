insert into role (name) values ('ROLE_USER')

insert into user (username, enabled, password, role_id) values ('user', true, '$2a$10$L4ghC4bWiMG69Pt0dYrjfOPlHBVw46L0daaY7lrJ8pAdgwOvMsPd2', 1)
insert into user (username, enabled, password, role_id) values ('user2', true, '$2a$10$L4ghC4bWiMG69Pt0dYrjfOPlHBVw46L0daaY7lrJ8pAdgwOvMsPd2', 1)

insert into warehouse (name, city, address) values ('asdf', 'Tirane', 'Home')