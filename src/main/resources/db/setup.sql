-- One-shot setup: schema + seed data
-- Usage: mysql -u root -p < src/main/resources/db/setup.sql

-- ========== SCHEMA ==========

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

-- ========== SEED DATA ==========

INSERT INTO categorie (id, nom) VALUES
  (1, 'Informatique'),
  (2, 'Réseaux'),
  (3, 'Gestion');

INSERT INTO direction (id, nom) VALUES
  (1, 'Direction Technique'),
  (2, 'Direction RH'),
  (3, 'Direction Commerciale');

INSERT INTO niveaustage (id, nom) VALUES
  (1, 'Licence 3'),
  (2, 'Master 1'),
  (3, 'Master 2');

INSERT INTO admin (id, nom, prenom, username, password, idcat, iddir) VALUES
  (1, 'Benali',   'Karim',  'kbenali',   'password123', 1, 1),
  (2, 'Mansouri', 'Sara',   'smansouri', 'password123', 2, 1),
  (3, 'Dupont',   'Marie',  'mdupont',   'password123', 3, 2);

INSERT INTO encadreur (id, nom, prenom, email, idcat, iddir, suivi) VALUES
  (1, 'Alaoui',  'Hassan', 'h.alaoui@example.com',  1, 1, 0),
  (2, 'Idrissi', 'Fatima', 'f.idrissi@example.com', 1, 1, 0),
  (3, 'Martin',  'Paul',   'p.martin@example.com',  2, 1, 0);

INSERT INTO stage (id, titre, idcat, idniv, description, etat) VALUES
  (1, 'Application web de gestion des stages',
   1, 3,
   'Conception et développement d''une application JEE/Spring pour le suivi des stagiaires.',
   'Valide'),
  (2, 'Audit de sécurité réseau',
   2, 2,
   'Analyse des vulnérabilités et proposition d''un plan de durcissement.',
   'en attente'),
  (3, 'Automatisation des processus RH',
   3, 3,
   'Digitalisation du workflow d''accueil des stagiaires.',
   'Valide');

INSERT INTO stagiare (id, nom, prenom, email, idcat, idniv, iddir, etabalissement, etat) VALUES
  (1, 'Tazi',     'Youssef', 'y.tazi@univ.example.com',     1, 3, 1, 'Université Hassan II',  'attendre l affectation'),
  (2, 'Berrada',  'Nadia',   'n.berrada@univ.example.com',  1, 2, 1, 'Université Mohammed V', 'attendre l affectation'),
  (3, 'Chraibi',  'Omar',    'o.chraibi@univ.example.com',  2, 2, 1, 'ENSIAS',                'attendre l affectation'),
  (4, 'Leroy',    'Julie',   'j.leroy@univ.example.com',    3, 3, 2, 'IAE Paris',             'attendre l affectation');
