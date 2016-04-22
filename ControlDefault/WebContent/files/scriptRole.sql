INSERT INTO `controldefault`.`role`
(`id`,
`name`,
`remark`)
VALUES
(
1, 'ROLE_DESENVOLVEDOR', 'ROLE_DESENVOLVEDOR');

INSERT INTO `controldefault`.`user`
(`id`,`role_id`,`name`,`cpf`,`remark`,`login`,`passwd`)
VALUES(1,1,'Desenvolvedor','34867332810','','desenv','b4e883efe3b956d74d28f520dbf4a6f8');




CREATE TABLE user (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  role_id INTEGER UNSIGNED NOT NULL,
  name VARCHAR(50) NOT NULL,
  cpf VARCHAR(11) NULL,
  remark VARCHAR(500) NULL,
  login VARCHAR(10) NOT NULL,
  passwd VARCHAR(100) NOT NULL,
  PRIMARY KEY(id),
  INDEX user_FKIndex1(role_id),
  FOREIGN KEY(role_id)
    REFERENCES role(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE role (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(25) NOT NULL,
  remark VARCHAR(500) NULL,
  PRIMARY KEY(id)
);