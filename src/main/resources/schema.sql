-- Interns Management - database schema
-- MySQL 8+ / MariaDB 10.4+

CREATE DATABASE IF NOT EXISTS l3_pfe
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE l3_pfe;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS stagiare;
DROP TABLE IF EXISTS stage;
DROP TABLE IF EXISTS encadreur;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS niveaustage;
DROP TABLE IF EXISTS direction;
DROP TABLE IF EXISTS categorie;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE categorie (
  id   INT AUTO_INCREMENT PRIMARY KEY,
  nom  VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE direction (
  id   INT AUTO_INCREMENT PRIMARY KEY,
  nom  VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE niveaustage (
  id   INT AUTO_INCREMENT PRIMARY KEY,
  nom  VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE admin (
  id        INT AUTO_INCREMENT PRIMARY KEY,
  nom       VARCHAR(100) NOT NULL,
  prenom    VARCHAR(100) NOT NULL,
  username  VARCHAR(50)  NOT NULL UNIQUE,
  password  VARCHAR(255) NOT NULL,
  idcat     INT NOT NULL,
  iddir     INT NOT NULL,
  CONSTRAINT fk_admin_categorie FOREIGN KEY (idcat) REFERENCES categorie(id) ON DELETE CASCADE,
  CONSTRAINT fk_admin_direction FOREIGN KEY (iddir) REFERENCES direction(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE encadreur (
  id      INT AUTO_INCREMENT PRIMARY KEY,
  nom     VARCHAR(100) NOT NULL,
  prenom  VARCHAR(100) NOT NULL,
  email   VARCHAR(150) NOT NULL,
  idcat   INT NOT NULL,
  iddir   INT NOT NULL,
  suivi   INT NOT NULL DEFAULT 0,
  CONSTRAINT fk_encadreur_categorie FOREIGN KEY (idcat) REFERENCES categorie(id) ON DELETE CASCADE,
  CONSTRAINT fk_encadreur_direction FOREIGN KEY (iddir) REFERENCES direction(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE stage (
  id              INT AUTO_INCREMENT PRIMARY KEY,
  titre           VARCHAR(255) NOT NULL,
  idcat           INT NOT NULL,
  idniv           INT NOT NULL,
  description     TEXT NOT NULL,
  etat            VARCHAR(50) NOT NULL DEFAULT 'en attente',
  date_debut      DATE NULL,
  date_fin        DATE NULL,
  travail_livrer  LONGBLOB NULL,
  CONSTRAINT fk_stage_categorie FOREIGN KEY (idcat) REFERENCES categorie(id) ON DELETE CASCADE,
  CONSTRAINT fk_stage_niveau    FOREIGN KEY (idniv) REFERENCES niveaustage(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE stagiare (
  id               INT AUTO_INCREMENT PRIMARY KEY,
  nom              VARCHAR(100) NOT NULL,
  prenom           VARCHAR(100) NOT NULL,
  email            VARCHAR(150) NOT NULL,
  idcat            INT NOT NULL,
  idniv            INT NOT NULL,
  iddir            INT NOT NULL,
  etabalissement   VARCHAR(200) NOT NULL,
  etat             VARCHAR(50) NOT NULL DEFAULT 'attendre l affectation',
  travail_demander INT NULL,
  idencad          INT NULL,
  evaluation       TEXT NULL,
  CONSTRAINT fk_stagiare_categorie FOREIGN KEY (idcat) REFERENCES categorie(id) ON DELETE CASCADE,
  CONSTRAINT fk_stagiare_niveau    FOREIGN KEY (idniv) REFERENCES niveaustage(id),
  CONSTRAINT fk_stagiare_direction FOREIGN KEY (iddir) REFERENCES direction(id) ON DELETE CASCADE,
  CONSTRAINT fk_stagiare_stage     FOREIGN KEY (travail_demander) REFERENCES stage(id) ON DELETE SET NULL,
  CONSTRAINT fk_stagiare_encadreur FOREIGN KEY (idencad) REFERENCES encadreur(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
