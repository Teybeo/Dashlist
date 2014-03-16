-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Dim 16 Mars 2014 à 13:23
-- Version du serveur: 5.5.16
-- Version de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `dashlist`
--

-- --------------------------------------------------------

--
-- Structure de la table `board`
--

CREATE TABLE IF NOT EXISTS `board` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Contenu de la table `board`
--

INSERT INTO `board` (`id`, `name`) VALUES
(1, 'devoirs'),
(2, 'vacances'),
(3, 'courses'),
(4, 'travaux'),
(5, 'bugs'),
(6, 'test');

-- --------------------------------------------------------

--
-- Structure de la table `board_members`
--

CREATE TABLE IF NOT EXISTS `board_members` (
  `board_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_admin` tinyint(1) NOT NULL,
  `is_pending` tinyint(1) NOT NULL,
  KEY `board_id` (`board_id`,`user_id`),
  KEY `user_id` (`user_id`),
  KEY `board_id_2` (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `board_members`
--

INSERT INTO `board_members` (`board_id`, `user_id`, `is_admin`, `is_pending`) VALUES
(1, 1, 1, 0),
(2, 1, 0, 0),
(3, 2, 0, 0),
(4, 3, 0, 0),
(5, 1, 0, 0),
(6, 3, 1, 0);

-- --------------------------------------------------------

--
-- Structure de la table `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `content` varchar(2048) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `item_id` (`item_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `list_id_old` int(11) DEFAULT NULL,
  `list_id_new` int(11) DEFAULT NULL,
  `item_id_old` int(11) DEFAULT NULL,
  `item_id_new` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `list_id_new` (`list_id_new`),
  KEY `item_id_old` (`item_id_old`),
  KEY `item_id_new` (`item_id_new`),
  KEY `list_id_old` (`list_id_old`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

-- --------------------------------------------------------

--
-- Structure de la table `item`
--

CREATE TABLE IF NOT EXISTS `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_list` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `date` date NOT NULL,
  `description` text,
  `position` int(11) NOT NULL,
  `cover` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_list` (`id_list`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=76 ;

--
-- Contenu de la table `item`
--

INSERT INTO `item` (`id`, `id_list`, `name`, `date`, `description`, `position`, `cover`) VALUES
(3, 1, 'Exo 10 et 11 page 45', '2014-02-25', '', 1, ''),
(4, 1, 'DM trigo', '2014-02-28', 'Exo 48 page 75', 2, ''),
(5, 1, 'Exo 48', '2014-03-02', NULL, 3, NULL),
(21, 2, 'Contrôle 24/01', '2014-03-02', NULL, 1, NULL),
(22, 27, 'Dissert 28/01', '2014-03-02', NULL, 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `list`
--

CREATE TABLE IF NOT EXISTS `list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_board` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_board` (`id_board`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=86 ;

--
-- Contenu de la table `list`
--

INSERT INTO `list` (`id`, `id_board`, `name`, `position`) VALUES
(1, 1, 'Maths', 1),
(2, 1, 'SVT', 2),
(5, 2, 'Transport', 1),
(6, 2, 'Finances', 2),
(7, 2, 'Choses à faires', 3),
(10, 3, 'Bouffe', 1),
(11, 3, 'Autre', 2),
(12, 4, 'Salle de bains', 1),
(13, 4, 'Grenier', 2),
(14, 4, 'Cuisine', 3),
(15, 4, 'Peinture', 4),
(27, 1, 'Philo', 3);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `password` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `name`, `password`) VALUES
(1, 'Pierre', ''),
(2, 'Paul', ''),
(3, 'Jacques', '');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `board_members`
--
ALTER TABLE `board_members`
  ADD CONSTRAINT `board_members_ibfk_2` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`),
  ADD CONSTRAINT `board_members_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `event_ibfk_12` FOREIGN KEY (`list_id_old`) REFERENCES `list` (`id`),
  ADD CONSTRAINT `event_ibfk_13` FOREIGN KEY (`list_id_new`) REFERENCES `list` (`id`),
  ADD CONSTRAINT `event_ibfk_14` FOREIGN KEY (`item_id_old`) REFERENCES `item` (`id`),
  ADD CONSTRAINT `event_ibfk_15` FOREIGN KEY (`item_id_new`) REFERENCES `item` (`id`),
  ADD CONSTRAINT `event_ibfk_16` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`id_list`) REFERENCES `list` (`id`);

--
-- Contraintes pour la table `list`
--
ALTER TABLE `list`
  ADD CONSTRAINT `list_ibfk_1` FOREIGN KEY (`id_board`) REFERENCES `board` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
