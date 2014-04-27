-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Dim 27 Avril 2014 à 18:02
-- Version du serveur: 5.1.41-community-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `dashlist`
--
CREATE DATABASE IF NOT EXISTS `dashlist` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `dashlist`;

-- --------------------------------------------------------

--
-- Structure de la table `board`
--

CREATE TABLE IF NOT EXISTS `board` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Contenu de la table `board`
--

INSERT INTO `board` (`id`, `name`) VALUES
(12, 'Test1'),
(13, 'Mathématiques'),
(14, 'SQL');

-- --------------------------------------------------------

--
-- Structure de la table `board_members`
--

CREATE TABLE IF NOT EXISTS `board_members` (
  `board_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_admin` tinyint(1) NOT NULL,
  KEY `board_id` (`board_id`,`user_id`),
  KEY `user_id` (`user_id`),
  KEY `board_id_2` (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `board_members`
--

INSERT INTO `board_members` (`board_id`, `user_id`, `is_admin`) VALUES
(12, 1, 1),
(13, 1, 1),
(14, 1, 0),
(12, 2, 0),
(12, 7, 0),
(12, 3, 0),
(12, 4, 0);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=123 ;

--
-- Contenu de la table `event`
--

INSERT INTO `event` (`id`, `list_id_old`, `list_id_new`, `item_id_old`, `item_id_new`, `user_id`, `date`) VALUES
(104, NULL, 99, NULL, NULL, 1, '2014-04-23 16:00:18'),
(105, NULL, NULL, NULL, 107, 1, '2014-04-23 16:01:02'),
(106, NULL, 100, NULL, NULL, 1, '2014-04-23 16:01:17'),
(107, NULL, NULL, NULL, 108, 1, '2014-04-23 16:02:31'),
(108, NULL, NULL, NULL, 109, 1, '2014-04-23 16:02:57'),
(109, NULL, NULL, NULL, 110, 1, '2014-04-23 16:03:05'),
(110, NULL, NULL, NULL, 111, 1, '2014-04-23 16:03:53'),
(111, NULL, NULL, NULL, 112, 1, '2014-04-23 16:03:58'),
(112, NULL, NULL, NULL, 113, 1, '2014-04-23 16:04:01'),
(113, NULL, NULL, NULL, 114, 1, '2014-04-23 16:04:06'),
(114, NULL, NULL, NULL, 115, 1, '2014-04-23 16:04:12'),
(115, NULL, NULL, NULL, 116, 1, '2014-04-23 16:04:17'),
(116, NULL, NULL, NULL, 117, 1, '2014-04-23 16:04:20'),
(117, NULL, NULL, NULL, 118, 1, '2014-04-23 16:04:24'),
(118, NULL, NULL, NULL, 119, 1, '2014-04-23 16:04:27'),
(119, NULL, NULL, NULL, 120, 1, '2014-04-23 16:04:30'),
(120, NULL, NULL, NULL, 121, 1, '2014-04-23 16:04:35'),
(121, NULL, NULL, NULL, 122, 1, '2014-04-23 16:04:42'),
(122, NULL, NULL, NULL, 123, 1, '2014-04-23 16:04:47');

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
  `is_deleted` tinyint(1) NOT NULL,
  `cover` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_list` (`id_list`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=133 ;

--
-- Contenu de la table `item`
--

INSERT INTO `item` (`id`, `id_list`, `name`, `date`, `description`, `position`, `is_deleted`, `cover`) VALUES
(107, 99, 'capturer pikachu', '2014-04-23', NULL, 1, 0, NULL),
(108, 100, 'élever gabumon', '2014-04-23', NULL, 1, 0, NULL),
(109, 99, 'lol', '2014-04-23', NULL, 2, 0, NULL),
(110, 99, 'prout', '2014-04-23', NULL, 3, 0, NULL),
(111, 99, 'haha', '2014-04-23', NULL, 4, 0, NULL),
(112, 99, 'slip', '2014-04-23', NULL, 5, 0, NULL),
(113, 99, 'string', '2014-04-23', NULL, 6, 0, NULL),
(114, 99, 'crac', '2014-04-23', NULL, 7, 0, NULL),
(115, 99, 'weep', '2014-04-23', NULL, 8, 0, NULL),
(116, 99, 'node', '2014-04-23', NULL, 9, 0, NULL),
(117, 99, 'js', '2014-04-23', NULL, 10, 1, NULL),
(118, 99, 'php', '2014-04-23', NULL, 11, 0, NULL),
(119, 99, 'html', '2014-04-23', NULL, 12, 1, NULL),
(120, 99, 'css', '2014-04-23', NULL, 13, 1, NULL),
(121, 99, '.net', '2014-04-23', NULL, 14, 1, NULL),
(122, 99, 'crack', '2014-04-23', NULL, 15, 1, NULL),
(123, 99, 'cuir', '2014-04-23', NULL, 16, 1, NULL),
(124, 99, 'pouet', '2014-04-26', NULL, 11, 0, NULL),
(125, 100, 'ama', '2014-04-27', NULL, 2, 0, NULL),
(126, 100, 'matt', '2014-04-27', NULL, 3, 0, NULL),
(127, 100, 'tai', '2014-04-27', NULL, 4, 0, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `list`
--

CREATE TABLE IF NOT EXISTS `list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_board` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `position` int(11) NOT NULL,
  `is_deleted` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_board` (`id_board`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=101 ;

--
-- Contenu de la table `list`
--

INSERT INTO `list` (`id`, `id_board`, `name`, `position`, `is_deleted`) VALUES
(99, 12, 'pokemon', 1, 0),
(100, 12, 'digimon', 2, 0);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `password` varchar(40) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `is_pending` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `name`, `password`, `mail`, `is_pending`) VALUES
(1, 'Pierre', 'azerty', 'pierre@hotmail.fr', 0),
(2, 'Paul', 'azerty', 'paul@free.fr', 0),
(3, 'Jacques', 'azerty', 'jacques@gmail.com', 0),
(4, 'Bernard', 'azerty', 'bernard@pivot.com', 0),
(5, 'Louis', 'azerty', 'louis@labrocante.fr', 0),
(6, 'Robby', 'azerty', 'robby@ybbor.fr', 0),
(7, '_', '_', 'unknow@caller.fr', 1);

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
