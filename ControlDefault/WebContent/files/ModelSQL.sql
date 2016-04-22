CREATE TABLE cliente (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR NULL,
  endRua VARCHAR NULL,
  endNumero SMALLINT UNSIGNED NULL,
  endComplemento VARCHAR NULL,
  endBairro VARCHAR NULL,
  endCidade VARCHAR NULL,
  endUF VARCHAR NULL,
  endCEP INTEGER UNSIGNED NULL,
  contato01 INTEGER UNSIGNED NULL,
  contato02 INTEGER UNSIGNED NULL,
  email VARCHAR NULL,
  observacao VARCHAR NULL,
  PRIMARY KEY(id)
);

CREATE TABLE clienteFisico (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  cliente_id INTEGER UNSIGNED NOT NULL,
  cpf INTEGER UNSIGNED NULL,
  rg VARCHAR NULL,
  dataNascimento DATE NULL,
  PRIMARY KEY(id, cliente_id),
  INDEX clienteFisico_FKIndex1(cliente_id)
);

CREATE TABLE clienteJuridico (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  cliente_id INTEGER UNSIGNED NOT NULL,
  cnpj INTEGER UNSIGNED NULL,
  ie INTEGER UNSIGNED NULL,
  razaoSocial VARCHAR NULL,
  PRIMARY KEY(id, cliente_id),
  INDEX clienteJuridico_FKIndex1(cliente_id)
);

CREATE TABLE employee (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  role_id INTEGER UNSIGNED NOT NULL,
  name VARCHAR(50) NOT NULL,
  cpf VARCHAR(11) NULL,
  remark VARCHAR(500) NULL,
  login VARCHAR(10) NOT NULL,
  passwd VARCHAR(100) NOT NULL,
  PRIMARY KEY(id),
  INDEX employee_FKIndex1(role_id),
  FOREIGN KEY(role_id)
    REFERENCES role(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE equipamento (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  fabricante_id INTEGER UNSIGNED NOT NULL,
  tipoEquipamento_id INTEGER UNSIGNED NOT NULL,
  cliente_id INTEGER UNSIGNED NOT NULL,
  numeroSerie VARCHAR NULL,
  estado VARCHAR NULL,
  observacao VARCHAR NULL,
  modelo VARCHAR NULL,
  acessorios VARCHAR NULL,
  garantia BOOL NULL,
  garantiaVencimento DATE NULL,
  PRIMARY KEY(id),
  INDEX equipamento_FKIndex1(cliente_id),
  INDEX equipamento_FKIndex2(tipoEquipamento_id),
  INDEX equipamento_FKIndex3(fabricante_id)
);

CREATE TABLE fabricante (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR NULL,
  PRIMARY KEY(id)
);

CREATE TABLE os (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  employee_id INTEGER UNSIGNED NOT NULL,
  equipamento_id INTEGER UNSIGNED NOT NULL,
  dataInicio DATE NULL,
  dataTermino DATE NULL,
  problema VARCHAR NULL,
  valorTotal DOUBLE NULL,
  observacao VARCHAR NULL,
  PRIMARY KEY(id),
  INDEX os_FKIndex1(equipamento_id),
  INDEX os_FKIndex2(employee_id)
);

CREATE TABLE pecas (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  os_id INTEGER UNSIGNED NOT NULL,
  descricao VARCHAR NULL,
  valor DOUBLE NULL,
  PRIMARY KEY(id),
  INDEX pecas_FKIndex1(os_id)
);

CREATE TABLE role (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(25) NOT NULL,
  remark VARCHAR(500) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE tipoEquipamento (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  descricao VARCHAR NULL,
  PRIMARY KEY(id)
);


