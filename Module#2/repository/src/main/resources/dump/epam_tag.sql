
DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `id` int NOT NULL ,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`,`name`),
  UNIQUE KEY `tag_id_UNIQUE` (`id`),
  UNIQUE KEY `tag_name_UNIQUE` (`name`)
);


--
-- Dumping data for table `tag`
--


INSERT INTO `tag` (id, name) VALUES (1,'one'),(2,'two'),(3,'three'),(4,'four'),(5,'five'),(6,'six'),(7,'seven');


