-- Acessa+ | Script de criação do banco (MySQL / MariaDB — compatível com o XAMPP)
-- Como executar: phpMyAdmin > aba "Importar" > escolher este arquivo,
-- ou: mysql -u root < database/schema.sql

CREATE DATABASE IF NOT EXISTS acessamais CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE acessamais;

CREATE TABLE IF NOT EXISTS usuarios (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    nome          VARCHAR(150) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,
    tipo_usuario  VARCHAR(20)  NOT NULL,   -- ESTUDANTE | MEMBRO_NUCLEO | EQUIPE_MANUTENCAO
    matricula     VARCHAR(30),             -- Estudante
    cargo         VARCHAR(50),             -- MembroNucleo
    setor         VARCHAR(50)              -- EquipeManutencao
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS manifestacoes (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    estudante_id  INT          NOT NULL,
    tipo_barreira VARCHAR(30)  NOT NULL,   -- ARQUITETONICA | PEDAGOGICA | ATITUDINAL
    `local`       VARCHAR(150) NOT NULL,
    descricao     TEXT         NOT NULL,
    status        VARCHAR(20)  NOT NULL DEFAULT 'RECEBIDA',
    data_registro DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_manifestacao_estudante FOREIGN KEY (estudante_id) REFERENCES usuarios(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS encaminhamentos (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    manifestacao_id     INT         NOT NULL,
    tipo_encaminhamento VARCHAR(20) NOT NULL,  -- ESTRUTURAL | PEDAGOGICO | ATITUDINAL
    setor_responsavel   VARCHAR(50),
    sla_prazo_dias      INT,
    data_conclusao      DATETIME NULL,
    CONSTRAINT fk_encaminhamento_manifestacao FOREIGN KEY (manifestacao_id)
        REFERENCES manifestacoes(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS anexos (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    manifestacao_id INT          NOT NULL,
    url_arquivo     VARCHAR(255) NOT NULL,
    CONSTRAINT fk_anexo_manifestacao FOREIGN KEY (manifestacao_id)
        REFERENCES manifestacoes(id) ON DELETE CASCADE
) ENGINE=InnoDB;
