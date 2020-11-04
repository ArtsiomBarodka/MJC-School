
CREATE TABLE `certificate` (
  id int AUTO_INCREMENT,
  name varchar(20) ,
  description tinytext ,
  price decimal(10,2) ,
  create_date timestamp DEFAULT CURRENT_TIMESTAMP,
  last_update_date timestamp  DEFAULT CURRENT_TIMESTAMP,
  duration int ,
  PRIMARY KEY (`id`)
);

INSERT INTO certificate(`name`,`description`,`price`,`duration`) VALUES ('dream best','The operatiy Michael Faraday. The principle, later called Faradays law, is that an electromotive force is generated in an electrical conductor which encircles a varying magnetic',122.99,7);
INSERT INTO certificate(`name`,`description`,`price`,`duration`) VALUES ('power star','And induced waste heating of the copper disc. Later homopolar generators would solve this problem by using an array of magnets arranged around the disc perimeter ',50.99,2);
INSERT INTO certificate(`name`,`description`,`price`,`duration`) VALUES ('magic store','Independently of Faraday, Ányos Jedlik started experimenting in 1827 with the electromagnetic rotating devices which he called electromagnetic self-rotors.',125.50,13);
INSERT INTO certificate(`name`,`description`,`price`,`duration`) VALUES ('quality mall','Was invented independently by Sir Charles Wheatstone, Werner von Siemens and Samuel Alfred Varley. Varley took out a patent on 24 December 1866, while Siemens and Wheatstone both announced their discoveries on 17 January 1867.',100.00,9);

/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `gift_sertificate_BEFORE_UPDATE` BEFORE UPDATE ON `certificate` FOR EACH ROW BEGIN
set new.last_update_date = CURRENT_TIMESTAMP;
END */;;
