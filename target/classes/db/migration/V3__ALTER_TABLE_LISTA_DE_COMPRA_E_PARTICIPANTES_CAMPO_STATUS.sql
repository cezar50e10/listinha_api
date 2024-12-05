ALTER TABLE listas_de_compras
MODIFY COLUMN status VARCHAR(100) NOT NULL;

ALTER TABLE participante_lista_compra
MODIFY COLUMN status VARCHAR(100) NOT NULL;