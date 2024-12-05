CREATE TABLE IF NOT EXISTS produtos_lista_de_compra(

    id bigint not null auto_increment,
    id_produto bigint not null,
    id_lista_de_compra bigint not null,
    qtd_desejada varchar(200) not null,
    qtd_estoque varchar(999) not null,
	status varchar(1) not null,

    primary key(id)

);