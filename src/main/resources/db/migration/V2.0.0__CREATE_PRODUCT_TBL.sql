create table product (
        product_id int8 not null,
        product_description text,
        product_name varchar(255),
        product_price varchar(255),
        primary key (product_id)
    );

create table product_extra_info (
       product_id int8 not null,
        item_detail varchar(255),
        item_name varchar(255) not null,
        primary key (product_id, item_name)
    );

alter table product_extra_info
       add constraint FKosb696umf4dotitpnbxqjcx92
       foreign key (product_id)
       references product;

CREATE SEQUENCE sq_product
    CYCLE
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 999999999999
    CACHE 10;