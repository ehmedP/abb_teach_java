-- Section 1: DDL Statements

rollback;
drop schema if exists store cascade;

-- Task 1
create schema if not exists store;

-- Task 2
create table if not exists store.categories
(
    id          serial primary key,
    name        varchar(50) not null unique,
    description varchar(200)
);

-- Task 3
create table if not exists store.products
(
    id          serial primary key,
    name        varchar(100)   not null,
    price       decimal(10, 2) not null check ( price >= 0),
    stock       int            not null check ( stock >= 0),
    category_id int            not null,
    constraint fk_category foreign key (category_id) references categories (id) on delete restrict
);

-- Task 4
create table if not exists store.customers
(
    id            serial primary key,
    first_name    varchar(100) not null,
    last_name     varchar(100) not null,
    email         varchar(100) not null unique,
    city          varchar(100) not null,
    registered_at DATe default current_date
);

-- Task 5
create table if not exists store.orders
(
    id          serial primary key,
    customer_id int not null,
    product_id  int not null,
    quantity    int not null check (quantity > 0),
    order_date  date        default current_date,
    status      varchar(20) default 'pending',
    constraint fk_customer foreign key (customer_id) references customers (id) on delete cascade,
    constraint fk_product foreign key (product_id) references products (id) on delete cascade
);

-- Task 6
alter table store.customers
    add column if not exists phone varchar(20);

-- Task 7
alter table store.products
    rename column stock to stock_quantity;

-- Task 8
create index if not exists idx_product_category on store.products (category_id);


-- Section 2: DML Statements


-- Task 9
insert into store.categories (name, description)
values ('Electronics', 'Devices and gadgets'),
       ('Books', 'Printed and digital books'),
       ('Clothing', 'Apparel for men, women, and children'),
       ('Sports', 'Sport equipment and accessories');

-- Task 10
insert into store.products (name, price, stock_quantity, category_id)
values ('Laptop Pro 15', 1250.00, 12, 1),
       ('Smartphone X', 899.99, 25, 1),
       ('Wireless Headphones', 149.50, 0, 1),
       ('Bluetooth Speaker', 79.90, 8, 1),
       ('Smart Watch', 210.00, 5, 1),
       ('SQL Basics', 35.00, 40, 2),
       ('Java for Beginners', 45.50, 18, 2),
       ('History of Baku', 22.00, 0, 2),
       ('Data Structures Guide', 60.00, 7, 2),
       ('Poetry Collection', 15.75, 30, 2),
       ('Winter Jacket', 180.00, 10, 3),
       ('Cotton T-Shirt', 25.00, 50, 3),
       ('Blue Jeans', 95.00, 20, 3),
       ('Leather Sneakers', 130.00, 0, 3),
       ('Wool Scarf', 40.00, 14, 3),
       ('Football Ball', 55.00, 22, 4),
       ('Yoga Mat', 65.00, 16, 4),
       ('Dumbbell Set', 240.00, 4, 4),
       ('Tennis Racket', 175.00, 9, 4),
       ('Mountain Bicycle', 890.00, 3, 4);

-- Task 11
insert into store.customers (first_name, last_name, email, city, registered_at, phone)
values ('Aysel', 'Mammadova', 'aysel.mammadova@gmail.com', 'Baku', '2024-01-15', '+994501112233'),
       ('Anar', 'Huseynov', 'anar.huseynov@gmail.com', 'Baku', '2024-02-20', '+994552223344'),
       ('Leyla', 'Aliyeva', 'leyla.aliyeva@mail.ru', 'Ganja', '2024-03-05', '+994703334455'),
       ('Rashad', 'Guliyev', 'rashad.guliyev@gmail.com', 'Sumgait', '2024-04-11', null),
       ('Nigar', 'Ismayilova', 'nigar.ismayilova@yahoo.com', 'Baku', '2024-05-30', '+994505556677'),
       ('Kamran', 'Aliyev', 'kamran.aliyev@gmail.com', 'Lankaran', '2024-06-18', '+994556667788'),
       ('Aygun', 'Safarova', 'aygun.safarova@mail.ru', 'Ganja', '2024-07-22', null),
       ('Elvin', 'Babayev', 'elvin.babayev@gmail.com', 'Baku', '2024-08-09', '+994708889900'),
       ('Sevinc', 'Karimova', 'sevinc.karimova@yahoo.com', 'Shaki', '2024-09-14', '+994509990011'),
       ('Tural', 'Hasanov', 'tural.hasanov@gmail.com', 'Mingachevir', '2024-10-02', '+994551234567');

-- Task 12
insert into store.orders (customer_id, product_id, quantity, order_date, status)
values (1, 1, 1, '2024-03-01', 'catdirildi'),
       (1, 6, 3, '2024-03-12', 'gonderildi'),
       (1, 17, 2, '2024-06-05', 'gozleyir'),
       (2, 2, 1, '2024-03-18', 'catdirildi'),
       (2, 12, 4, '2024-04-02', 'legv edildi'),
       (2, 19, 1, '2024-07-11', 'gozleyir'),
       (3, 7, 2, '2024-04-15', 'catdirildi'),
       (3, 11, 1, '2024-05-20', 'gonderildi'),
       (4, 5, 1, '2024-05-03', 'gozleyir'),
       (4, 16, 3, '2024-06-21', 'legv edildi'),
       (4, 20, 1, '2024-08-01', 'gonderildi'),
       (5, 4, 2, '2024-06-09', 'catdirildi'),
       (5, 10, 5, '2024-06-25', 'gozleyir'),
       (5, 13, 1, '2024-09-03', 'gonderildi'),
       (6, 9, 2, '2024-07-07', 'catdirildi'),
       (6, 15, 3, '2024-08-16', 'legv edildi'),
       (7, 18, 1, '2024-08-22', 'gozleyir'),
       (7, 6, 2, '2024-09-10', 'catdirildi'),
       (8, 2, 1, '2024-09-19', 'gonderildi'),
       (8, 12, 6, '2024-10-05', 'gozleyir'),
       (9, 17, 1, '2024-10-12', 'catdirildi'),
       (9, 19, 2, '2024-11-01', 'legv edildi'),
       (10, 1, 1, '2024-11-14', 'gonderildi'),
       (10, 13, 2, '2024-11-28', 'gozleyir');

-- Task 13
update store.products
set price = price * 1.10
where category_id = 1;

-- Task 14
update store.customers
set phone = '+994500000000'
where customers.phone is null;

-- Task 15
delete
from store.orders
where status = 'legv edildi';


-- Section 3: Queries

-- Task 16
select *
from store.products
where price > 100;
-- currency is not stored in the db

-- Task 17
select *
from store.products
where stock_quantity = 0;

-- Task 18
select *
from store.customers
where city <> 'Baku';

-- Task 18 - 2
select *
from store.customers
where city != 'Baku';

-- Task 19
select *
from store.products
where price between 50 and 200;

-- Task 20
select *
from store.orders
where status in ('gozleyir', 'gonderildi');

-- Task 21
select *
from store.customers
where first_name like 'A%';

-- Task 22
select *
from store.customers
where email like '%gmail%';

-- Task 23
select *
from store.customers
where customers.phone is null;
-- Result like that because of Task 14, all null phones were updated to '+994500000000', so this query will return no results.

-- Task 24
select *
from store.products
where price > 100
  and stock_quantity > 5;


-- Section 4: DQL Statements

-- Task 25
select *
from store.products
order by price;
-- default is ascending order

-- Task 26
select *
from store.customers
order by registered_at desc;

-- Task 27
select *
from store.products
order by category_id, price desc;

-- Task 28
select *
from store.products
order by price desc
limit 3;


-- Section 5: TCL Statements

-- Task 29
begin;

insert into store.products (name, price, stock_quantity, category_id)
VALUES ('Tablet Z', 350.00, 15, 1),
       ('E-Reader', 120.00, 30, 2);

select *
from store.products
order by id desc
limit 2;

-- commit elememis hara dusure
-- commit;

rollback;

-- Task 30
begin;

update store.customers
set city = 'Baku'
where id = 3;

commit;

select *
from store.customers
where id = 3;

-- Task 31
begin;

insert into store.orders (customer_id, product_id, quantity, order_date, status)
VALUES (3, 5, 1, '2024-12-01', 'gozleyir');

savepoint sp1;

insert into store.orders (customer_id, product_id, quantity, order_date, status)
VALUES (3, 6, 2, '2024-12-02', 'gozleyir');

rollback to sp1;

commit;

select *
from store.orders;

-- Task 32
begin;

select stock
from store.products;

select *
from store.products;

rollback;

select *
from store.products;

-- [42703] ERROR: column "stock" does not exist
--   Position: 8