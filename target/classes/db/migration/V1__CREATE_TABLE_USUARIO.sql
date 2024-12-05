CREATE TABLE IF NOT EXISTS usuarios(

    id bigint not null auto_increment,
    email varchar(200) not null unique,
    senha varchar(100) not null,

    primary key(id)

);