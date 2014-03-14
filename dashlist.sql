-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Ven 14 Mars 2014 à 17:04
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Contenu de la table `board`
--

INSERT INTO `board` (`id`, `name`) VALUES
(1, 'devoirs'),
(2, 'vacances'),
(3, 'courses'),
(4, 'travaux'),
(5, 'bugs'),
(6, 'test'),
(13, 'lol'),
(14, 'pip'),
(15, 'mlki'),
(16, 'sdvdsv'),
(17, 'Test'),
(18, 'devoirs'),
(19, 'fzef'),
(20, 'pop'),
(21, 'ertfgyh'),
(22, 'aeztegrh'),
(23, 'hb$');

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
(6, 3, 1, 0),
(17, 2, 0, 0),
(16, 3, 1, 0),
(18, 2, 0, 0),
(15, 2, 0, 0),
(13, 3, 0, 0),
(16, 3, 0, 0),
(14, 3, 0, 0),
(2, 3, 0, 0),
(1, 3, 1, 0),
(23, 1, 1, 0);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=73 ;

--
-- Contenu de la table `item`
--

INSERT INTO `item` (`id`, `id_list`, `name`, `date`, `description`, `position`, `cover`) VALUES
(3, 1, 'Exo 10 et 11 page 45', '2014-02-25', '', 1, ''),
(4, 1, 'DM trigo', '2014-02-28', 'Exo 48 page 75', 2, ''),
(5, 1, 'Exo 48', '2014-03-02', NULL, 3, NULL),
(21, 2, 'Contrôle 24/01', '2014-03-02', NULL, 1, NULL),
(22, 27, 'Dissert 28/01', '2014-03-02', NULL, 1, NULL),
(23, 27, 'Test', '2014-03-02', NULL, 2, NULL),
(24, 28, 'dsds', '2014-03-09', NULL, 1, NULL),
(25, 28, '', '2014-03-09', NULL, 2, NULL),
(26, 28, '', '2014-03-09', NULL, 3, NULL),
(27, 28, 'dsc', '2014-03-09', NULL, 4, NULL),
(28, 1, '', '2014-03-09', NULL, 4, NULL),
(29, 1, '', '2014-03-09', NULL, 5, NULL),
(30, 1, '', '2014-03-09', NULL, 6, NULL),
(31, 1, 'Lol', '2014-03-09', NULL, 7, NULL),
(32, 28, 'e', '2014-03-09', NULL, 5, NULL),
(33, 28, 'e', '2014-03-09', NULL, 6, NULL),
(34, 28, 'r', '2014-03-09', NULL, 7, NULL),
(35, 28, 'r', '2014-03-09', NULL, 8, NULL),
(36, 30, 'svdsv', '2014-03-09', NULL, 1, NULL),
(37, 30, 'dsdf', '2014-03-09', NULL, 2, NULL),
(38, 28, 'sddsf', '2014-03-09', NULL, 9, NULL),
(39, 28, 'dsf', '2014-03-09', NULL, 10, NULL),
(40, 31, 'fzeg', '2014-03-09', NULL, 1, NULL),
(41, 31, 'dsv', '2014-03-09', NULL, 2, NULL),
(42, 31, 'sdv', '2014-03-09', NULL, 3, NULL),
(43, 28, 'u', '2014-03-09', NULL, 11, NULL),
(44, 28, 'u', '2014-03-09', NULL, 12, NULL),
(45, 28, 'u', '2014-03-09', NULL, 13, NULL),
(46, 28, 'u', '2014-03-09', NULL, 14, NULL),
(47, 28, 'yu', '2014-03-09', NULL, 15, NULL),
(48, 28, 'g', '2014-03-09', NULL, 16, NULL),
(49, 28, 'u', '2014-03-09', NULL, 17, NULL),
(50, 28, 'ju', '2014-03-09', NULL, 18, NULL),
(51, 28, 'ujk', '2014-03-09', NULL, 19, NULL),
(52, 32, 'static', '2014-03-09', NULL, 1, NULL),
(53, 33, 'const', '2014-03-09', NULL, 1, NULL),
(54, 35, 'pop', '2014-03-09', NULL, 1, NULL),
(55, 36, 'vdsvdsv', '2014-03-09', NULL, 1, NULL),
(56, 38, 'gsdg', '2014-03-11', NULL, 1, NULL),
(57, 39, 'fb', '2014-03-11', NULL, 1, NULL),
(58, 40, 'dfbsd', '2014-03-11', NULL, 1, NULL),
(59, 40, 'fbh', '2014-03-11', NULL, 2, NULL),
(60, 11, 'dfbdfb', '2014-03-11', NULL, 1, NULL),
(61, 11, 'dsv', '2014-03-11', NULL, 2, NULL),
(62, 45, 'dsgsdrhtf', '2014-03-11', NULL, 1, NULL),
(63, 32, 'Element', '2014-03-12', NULL, 2, NULL),
(64, 32, 'EZtezt', '2014-03-12', NULL, 3, NULL),
(65, 47, 'zrgrzg', '2014-03-12', NULL, 1, NULL),
(66, 47, 'zrg', '2014-03-12', NULL, 2, NULL),
(67, 46, 'zrg', '2014-03-12', NULL, 1, NULL),
(68, 2, 'Controle lol', '2014-03-12', NULL, 2, NULL),
(69, 27, 'Truc', '2014-03-12', NULL, 3, NULL),
(70, 27, 'Bidule', '2014-03-12', NULL, 4, NULL),
(71, 53, 'qdfds', '2014-03-14', NULL, 1, NULL),
(72, 2, 'qdsf4', '2014-03-14', NULL, 3, NULL);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=55 ;

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
(27, 1, 'Philo', 3),
(28, 1, 'Test', 4),
(29, 1, 'Test', 5),
(30, 1, 'Kek', 6),
(31, 1, 'fzefzf', 7),
(32, 6, 'Foo', 1),
(33, 6, 'Bar', 2),
(35, 13, 'kik', 1),
(36, 13, 'berbr', 2),
(37, 15, 'jn', 1),
(38, 1, 'tfhdfhf', 8),
(39, 16, 'dsbs', 1),
(40, 16, 'dfb', 2),
(41, 3, 'dvs', 3),
(42, 3, 'dsv', 4),
(43, 3, 'df', 5),
(44, 3, 'f4', 6),
(45, 3, 'dgj', 7),
(46, 6, 'Liste3', 3),
(47, 6, 'Liste4', 4),
(48, 3, 'rgeg', 8),
(49, 3, 'ergerg', 9),
(50, 1, 'Philo', 9),
(51, 18, 'ZEf', 1),
(52, 18, 'zef', 2),
(53, 23, 'sdfsd', 1),
(54, 23, 'qdsfds', 2);

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
  ADD CONSTRAINT `board_members_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `board_members_ibfk_2` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`);

--
-- Contraintes pour la table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

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
