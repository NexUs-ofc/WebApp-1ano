CREATE TABLE tb_endereco (
    id_endereco SERIAL PRIMARY KEY,
    rua VARCHAR(100) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    cep VARCHAR(10) NOT NULL
);

CREATE TABLE tb_telefone (
    id_telefone SERIAL PRIMARY KEY,
    numero VARCHAR(20) NOT NULL
);

CREATE TABLE tb_usuario (
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    preferencias_consumo TEXT,
    configuracao_alerta VARCHAR(20) NOT NULL DEFAULT 'Ativado',
    data_criacao DATE NOT NULL DEFAULT CURRENT_DATE,
    telefone VARCHAR(20),
    senha VARCHAR(100)
);

CREATE TABLE tb_restricao_alimentar (
    id_restricao SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE tb_usuario_telefone (
    id_usuario INT NOT NULL,
    id_telefone INT NOT NULL,
    PRIMARY KEY (id_usuario, id_telefone),
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_telefone) REFERENCES tb_telefone(id_telefone)
);

CREATE TABLE tb_usuario_restricao (
    id_usuario INT NOT NULL,
    id_restricao INT NOT NULL,
    PRIMARY KEY (id_usuario, id_restricao),
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_restricao) REFERENCES tb_restricao_alimentar(id_restricao)
);

CREATE TABLE tb_categoria (
    id_categoria SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE tb_alimento (
    id_alimento SERIAL PRIMARY KEY,
    codigo_barras VARCHAR(50),
    nome VARCHAR(100) NOT NULL,
    marca VARCHAR(100),
    id_categoria INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES tb_categoria(id_categoria)
);

CREATE TABLE tb_item_despensa (
    id_item SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_alimento INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    data_validade DATE,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_alimento) REFERENCES tb_alimento(id_alimento)
);

CREATE TABLE tb_alerta (
    id_alerta SERIAL PRIMARY KEY,
    tipo_notificacao VARCHAR(20) NOT NULL
        CHECK (tipo_notificacao IN ('Validade', 'Reposicao', 'Promocao')),
    envio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_item INT NOT NULL,
    FOREIGN KEY (id_item) REFERENCES tb_item_despensa(id_item) ON DELETE CASCADE
);

CREATE TABLE tb_lista_compra (
    id_lista SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_criacao DATE NOT NULL DEFAULT CURRENT_DATE,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE tb_item_lista_compra (
    id_item_lista SERIAL PRIMARY KEY,
    id_lista INT NOT NULL,
    id_alimento INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    FOREIGN KEY (id_lista) REFERENCES tb_lista_compra(id_lista) ON DELETE CASCADE,
    FOREIGN KEY (id_alimento) REFERENCES tb_alimento(id_alimento)
);

CREATE TABLE tb_nutricao (
    id_nutricao SERIAL PRIMARY KEY,
    tabela_nutricional TEXT
);

CREATE TABLE tb_ia_evento (
    id_evento SERIAL PRIMARY KEY,
    auxilio_evento TEXT NOT NULL
);

CREATE TABLE tb_ia_receita (
    id_receita SERIAL PRIMARY KEY,
    sugestao_receita TEXT NOT NULL,
    tipo VARCHAR(30) CHECK (tipo IN ('Receita', 'Dica', 'Sugestao'))
);

CREATE TABLE tb_administrador (
    id_administrador SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    url_foto VARCHAR(500)
);

CREATE TABLE tb_loja (
    id_loja SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200),
    telefone VARCHAR(20)
);

CREATE TABLE tb_funcionario (
    id_funcionario SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    cargo VARCHAR(50),
    salario DECIMAL(10,2) CHECK (salario >= 0),
    telefone VARCHAR(20),
    email VARCHAR(100),
    id_loja INT,
    FOREIGN KEY (id_loja) REFERENCES tb_loja(id_loja)
);

CREATE TABLE tb_fornecedor (
    id_fornecedor SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18) UNIQUE,
    endereco VARCHAR(200),
    telefone VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE tb_fornece (
    id_fornecedor INT NOT NULL,
    id_categoria INT NOT NULL,
    PRIMARY KEY (id_fornecedor, id_categoria),
    FOREIGN KEY (id_fornecedor) REFERENCES tb_fornecedor(id_fornecedor),
    FOREIGN KEY (id_categoria) REFERENCES tb_categoria(id_categoria)
);

CREATE TABLE tb_produto (
    id_produto SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL CHECK (preco > 0),
    estoque INT DEFAULT 0 CHECK (estoque >= 0),
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES tb_categoria(id_categoria)
);

CREATE TABLE tb_empresa (
    id_empresa SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18) UNIQUE,
    id_endereco INT NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    FOREIGN KEY (id_endereco) REFERENCES tb_endereco(id_endereco)
);

CREATE TABLE tb_venda (
    id_venda SERIAL PRIMARY KEY,
    data_venda DATE DEFAULT CURRENT_DATE,
    valor_total DECIMAL(10,2) DEFAULT 0 CHECK (valor_total >= 0),
    forma_pagamento VARCHAR(50) DEFAULT 'Dinheiro',
    status VARCHAR(30) DEFAULT 'Pendente'
        CHECK (status IN ('Pendente', 'Pago', 'Cancelado')),
    id_empresa INT,
    id_funcionario INT,
    FOREIGN KEY (id_empresa) REFERENCES tb_empresa(id_empresa),
    FOREIGN KEY (id_funcionario) REFERENCES tb_funcionario(id_funcionario)
);

CREATE TABLE tb_item_venda (
    id_item_venda SERIAL PRIMARY KEY,
    id_venda INT,
    id_produto INT,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    preco_unitario DECIMAL(10,2) CHECK (preco_unitario > 0),
    subtotal DECIMAL(10,2) CHECK (subtotal >= 0),
    FOREIGN KEY (id_venda) REFERENCES tb_venda(id_venda),
    FOREIGN KEY (id_produto) REFERENCES tb_produto(id_produto)
);

CREATE VIEW view_item_despensa AS
SELECT
    item.id_item,
    item.id_usuario,
    item.id_alimento,
    usuario.nome AS usuario,
    alimento.nome AS alimento,
    categoria.nome AS categoria,
    item.quantidade,
    item.data_validade
FROM tb_item_despensa AS item
JOIN tb_usuario AS usuario
    ON usuario.id_usuario = item.id_usuario
JOIN tb_alimento AS alimento
    ON alimento.id_alimento = item.id_alimento
JOIN tb_categoria AS categoria
    ON categoria.id_categoria = alimento.id_categoria;

CREATE VIEW view_alimento AS
SELECT
    alimento.id_alimento,
    alimento.codigo_barras,
    alimento.nome,
    alimento.marca,
    categoria.nome AS categoria
FROM tb_alimento AS alimento
JOIN tb_categoria AS categoria
    ON categoria.id_categoria = alimento.id_categoria;

CREATE VIEW view_empresa AS
SELECT
    empresa.id_empresa,
    empresa.nome,
    empresa.cnpj,
    endereco.cep,
    empresa.telefone
FROM tb_empresa AS empresa
JOIN tb_endereco AS endereco
    ON endereco.id_endereco = empresa.id_endereco;
