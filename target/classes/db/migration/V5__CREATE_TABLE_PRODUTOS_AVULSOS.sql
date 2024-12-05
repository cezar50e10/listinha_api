CREATE TABLE IF NOT EXISTS produtos_avulsos(

    id bigint not null auto_increment,
    id_usuario_dono bigint not null,
    nome varchar(200) not null,
    descricao varchar(999) not null,
    tipo_medida varchar(999) not null,
	status varchar(1) not null,

    primary key(id)

);