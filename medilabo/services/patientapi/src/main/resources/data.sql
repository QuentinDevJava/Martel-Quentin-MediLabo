-- Création de la table seulement si elle n'existe pas
CREATE TABLE IF NOT EXISTS `patient` (
  `date` DATE DEFAULT NULL,
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `adresse` VARCHAR(255) DEFAULT NULL,
  `genre` VARCHAR(255) NOT NULL,
  `nom` VARCHAR(255) NOT NULL,
  `prenom` VARCHAR(255) NOT NULL,
  `telephone` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Insertion de données, on ignore les doublons d'ID
INSERT IGNORE INTO `patient` (`id`, `date`, `adresse`, `genre`, `nom`, `prenom`, `telephone`) VALUES
(1, '1966-12-31', '1 Brookside St', 'F', 'TestNone', 'Test', '100-222-3333'),
(2, '1945-06-24', '2 High St', 'M', 'TestBorderline', 'Test', '200-333-4444'),
(3, '2004-06-18', '3 Club Road', 'M', 'TestInDanger', 'Test', '300-444-5555'),
(4, '2002-06-28', '4 Valley Dr', 'F', 'TestEarlyOnset', 'Test', '400-555-6666');
