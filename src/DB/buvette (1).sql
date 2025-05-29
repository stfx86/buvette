-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 29 mai 2025 à 15:10
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `buvette`
--

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `order_date` datetime NOT NULL,
  `total_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `orders`
--

INSERT INTO `orders` (`order_id`, `order_date`, `total_price`) VALUES
(1, '2025-05-10 16:03:16', 68687),
(2, '2025-05-10 16:03:16', 67787),
(3, '2025-05-10 16:24:01', 67787);

-- --------------------------------------------------------

--
-- Structure de la table `order_items`
--

CREATE TABLE `order_items` (
  `order_id` int(11) NOT NULL,
  `plat_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `order_items`
--

INSERT INTO `order_items` (`order_id`, `plat_name`, `quantity`, `price`) VALUES
(1, 'BURGGER', 1, 900),
(1, 'GGG', 1, 67787),
(2, 'GGG', 1, 67787),
(3, 'GGG', 1, 67787);

-- --------------------------------------------------------

--
-- Structure de la table `plat`
--

CREATE TABLE `plat` (
  `nom` varchar(255) NOT NULL,
  `prix` double NOT NULL,
  `descrp` text DEFAULT NULL,
  `cat` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `plat`
--

INSERT INTO `plat` (`nom`, `prix`, `descrp`, `cat`, `image`) VALUES
('&', 600, 'JKDJJKJK', 'pizza', 'C:\\Users\\dell\\Desktop\\java beans project\\buvette\\src\\images\\pizza2.jpeg'),
('Bacon Burger', 38, 'Beef patty with crispy bacon and BBQ sauce.', 'Burgers', 'src/images/burger2.jpeg'),
('BszRGGER', 900, 'HDHDJJJHDJHJH', 'CAT', 'C:\\Users\\dell\\Desktop\\java beans project\\buvette\\src\\images\\logo.jpeg'),
('BURGGER', 900, 'HDHDJJJHDJHJH', 'CAT', 'C:\\Users\\dell\\Desktop\\java beans project\\buvette\\src\\images\\logo.jpeg'),
('BURGGERd', 900, 'HDHDJJJHDJHJH', 'CAT', 'C:\\Users\\dell\\Desktop\\java beans project\\buvette\\src\\images\\logo.jpeg'),
('Cheeseburger', 35, 'Beef patty with cheddar, lettuce, and tomato.', 'Burgers', 'src/images/burger1.jpeg'),
('CocaCola', 10, 'Refreshing carbonated soft drink.', 'Drinks', 'src/images/drink1.jpeg'),
('Crème Brûlée', 28, 'Rich custard with a caramelized sugar crust.', 'Desserts', 'src/images/dessert2.jpeg'),
('FF', 600, 'JKDJJKJK', 'pizza', 'C:\\Users\\dell\\Desktop\\java beans project\\buvette\\src\\images\\pizza2.jpeg'),
('GGG', 67787, 'JJJJHH', 'CAT', 'C:\\Users\\dell\\Desktop\\java beans project\\buvette\\src\\images\\button.jpg'),
('ITALI', 500, 'JUST EAT', 'pizza', 'C:\\Users\\dell\\Desktop\\java beans project\\buvette\\src\\images\\pizza1.jpeg'),
('Orange Juice', 12, 'Freshly squeezed orange juice.', 'Drinks', 'src/images/drink2.jpeg'),
('Pizza Margherita', 30, 'Classic pizza with tomato, mozzarella, and basil.', 'Pizzas', 'src/images/pizza1.jpeg'),
('Pizza Pepperoni', 40, 'Spicy pepperoni with mozzarella and tomato sauce.', 'Pizzas', 'src/images/pizza2.jpeg'),
('test', 600, 'JKDJJKJK', 'pizza', 'C:\\Users\\dell\\Desktop\\java beans project\\buvette\\src\\images\\pizza2.jpeg'),
('Tiramisu', 25, 'Coffee-flavored Italian dessert with mascarpone.', 'Desserts', 'src/images/dessert1.jpeg'),
('YOU', 600, 'JKDJJKJK', 'pizza', 'C:\\Users\\dell\\Desktop\\java beans project\\buvette\\src\\images\\fanta.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `name` varchar(60) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `type` varchar(60) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`name`, `password`, `type`, `email`) VALUES
('admin', 'admin', 'admin', 'najiy@gmail.com'),
('youssef', '123', 'client', NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- Index pour la table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`order_id`,`plat_name`),
  ADD KEY `plat_name` (`plat_name`);

--
-- Index pour la table `plat`
--
ALTER TABLE `plat`
  ADD PRIMARY KEY (`nom`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`name`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`plat_name`) REFERENCES `plat` (`nom`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
