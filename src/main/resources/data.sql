-- Interns Management - seed data (users + demo records)
USE l3_pfe;

-- Categories
INSERT INTO categorie (id, nom) VALUES
  (1, 'Informatique'),
  (2, 'Réseaux'),
  (3, 'Gestion');

-- Directions
INSERT INTO direction (id, nom) VALUES
  (1, 'Direction Technique'),
  (2, 'Direction RH'),
  (3, 'Direction Commerciale');

-- Internship levels
INSERT INTO niveaustage (id, nom) VALUES
  (1, 'Licence 3'),
  (2, 'Master 1'),
  (3, 'Master 2');

-- Administrators (DB login — plain text passwords for local dev only)
-- Super-admin admin/admin is built into the application and does not need a row here.
INSERT INTO admin (id, nom, prenom, username, password, idcat, iddir) VALUES
  (1, 'Benali',   'Karim',  'kbenali',  'password123', 1, 1),
  (2, 'Mansouri', 'Sara',   'smansouri','password123', 2, 1),
  (3, 'Dupont',   'Marie',  'mdupont',  'password123', 3, 2);

-- Supervisors
INSERT INTO encadreur (id, nom, prenom, email, idcat, iddir, suivi) VALUES
  (1, 'Alaoui',  'Hassan', 'h.alaoui@example.com',  1, 1, 0),
  (2, 'Idrissi', 'Fatima', 'f.idrissi@example.com', 1, 1, 0),
  (3, 'Martin',  'Paul',   'p.martin@example.com',  2, 1, 0);

-- Internship topics
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

-- Interns
INSERT INTO stagiare (id, nom, prenom, email, idcat, idniv, iddir, etabalissement, etat) VALUES
  (1, 'Tazi',     'Youssef', 'y.tazi@univ.example.com',     1, 3, 1, 'Université Hassan II',       'attendre l affectation'),
  (2, 'Berrada',  'Nadia',   'n.berrada@univ.example.com',  1, 2, 1, 'Université Mohammed V',      'attendre l affectation'),
  (3, 'Chraibi',  'Omar',    'o.chraibi@univ.example.com',  2, 2, 1, 'ENSIAS',                     'attendre l affectation'),
  (4, 'Leroy',    'Julie',   'j.leroy@univ.example.com',    3, 3, 2, 'IAE Paris',                  'attendre l affectation');
