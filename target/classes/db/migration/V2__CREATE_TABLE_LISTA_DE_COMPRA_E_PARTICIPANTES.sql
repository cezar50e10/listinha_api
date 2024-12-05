CREATE TABLE IF NOT EXISTS listas_de_compras(

    id bigint not null auto_increment,
    nome varchar(200) not null,
    descricao varchar(999) not null,
	status varchar(1) not null,

    primary key(id)

);

CREATE TABLE IF NOT EXISTS participante_lista_compra(

    id bigint not null auto_increment,
	id_lista_de_compra bigint not null,
    id_usuario_participante bigint not null,
    cargo varchar(100) not null,
	status varchar(1) not null,

    primary key(id)

);