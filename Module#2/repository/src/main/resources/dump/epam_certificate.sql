
DROP TABLE IF EXISTS `certificate`;

CREATE TABLE `certificate` (
  `id` int NOT NULL ,
  `name` varchar(20) NOT NULL,
  `description` tinytext NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `duration` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `certificate_name_UNIQUE` (`name`),
  UNIQUE KEY `certificate_id_UNIQUE` (`id`)
);

INSERT INTO certificate(`id`,`name`,`description`,`price`,`duration`) VALUES (1,'dream best','The operatiy Michael Faraday. The principle, later called Faradays law, is that an electromotive force is generated in an electrical conductor which encircles a varying magnetic',122.99,7);
INSERT INTO certificate(`id`,`name`,`description`,`price`,`duration`) VALUES (2,'power star','And induced waste heating of the copper disc. Later homopolar generators would solve this problem by using an array of magnets arranged around the disc perimeter ',50.99,2);
INSERT INTO certificate(`id`,`name`,`description`,`price`,`duration`) VALUES (3,'magic store','Independently of Faraday, Ányos Jedlik started experimenting in 1827 with the electromagnetic rotating devices which he called electromagnetic self-rotors.',125.50,13);
INSERT INTO certificate(`id`,`name`,`description`,`price`,`duration`) VALUES (4,'quality mall','Was invented independently by Sir Charles Wheatstone, Werner von Siemens and Samuel Alfred Varley. Varley took out a patent on 24 December 1866, while Siemens and Wheatstone both announced their discoveries on 17 January 1867.',100.00,9);

/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `gift_sertificate_BEFORE_UPDATE` BEFORE UPDATE ON `certificate` FOR EACH ROW BEGIN
set new.last_update_date = CURRENT_TIMESTAMP;
END */;;
