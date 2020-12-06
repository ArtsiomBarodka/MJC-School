INSERT INTO certificate(`id`, `name`,`description`,`price`,`duration`) VALUES (1,'dream best','The operatiy Michael Faraday. The principle, later called Faradays law, is that an electromotive force is generated in an electrical conductor which encircles a varying magnetic',122.99,7);
INSERT INTO certificate(`id`, `name`,`description`,`price`,`duration`) VALUES (2,'power star','And induced waste heating of the copper disc. Later homopolar generators would solve this problem by using an array of magnets arranged around the disc perimeter ',50.99,2);
INSERT INTO certificate(`id`, `name`,`description`,`price`,`duration`) VALUES (3,'magic store','Independently of Faraday, Ányos Jedlik started experimenting in 1827 with the electromagnetic rotating devices which he called electromagnetic self-rotors.',125.50,13);
INSERT INTO certificate(`id`, `name`,`description`,`price`,`duration`) VALUES (4,'quality mall','Was invented independently by Sir Charles Wheatstone, Werner von Siemens and Samuel Alfred Varley. Varley took out a patent on 24 December 1866, while Siemens and Wheatstone both announced their discoveries on 17 January 1867.',100.00,9);

INSERT INTO `tag` (id,name) VALUES (1,'one'),(2,'two'),(3,'three'),(4,'four'),(5,'five'),(6,'six'),(7,'seven');

INSERT INTO `gift_certificate_tag` VALUES (1,1),(2,1),(3,1),(1,2),(2,2),(1,3),(2,3),(3,3),(4,3),(1,4),(2,4),(3,5),(4,5),(1,6);

INSERT INTO `user`(id, name) VALUES (1, 'Artsiom'), (2, 'Anna'), (3, 'Poul');

INSERT INTO `user_order` (id, fk_user_id) VALUES (1,1),(2,1),(3,1),(4,2);

INSERT INTO `certificate_order` VALUES (1,1),(2,1),(3,1),(1,2),(4,2),(2,3),(2,4),(4,4);