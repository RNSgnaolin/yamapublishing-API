create table books(

    id bigint not null auto_increment,
    title varchar(100) not null,
    release_date varchar(100) not null,
    pages bigint not null,
    price bigint not null,
    genre varchar(100) not null,
    author_name varchar(100) not null,
    author_phone varchar(100) not null,
    author_email varchar(100) not null,

    primary key(id)

);

create table users(

    id bigint not null auto_increment,
    login varchar(100) not null,
    password varchar(255) not null,

    primary key(id)

);