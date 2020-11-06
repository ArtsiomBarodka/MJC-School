

CREATE TABLE `gift_certificate_tag` (
  gift_certificate_id int NOT NULL,
  tag_id int NOT NULL,
  PRIMARY KEY (`gift_certificate_id`,`tag_id`),
  CONSTRAINT `fk_gift_sertificate` FOREIGN KEY (`gift_certificate_id`) REFERENCES `certificate` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `gift_certificate_tag` VALUES (1,1),(2,1),(3,1),(1,2),(2,2),(1,3),(2,3),(3,3),(4,3),(1,4),(2,4),(3,5),(4,5),(1,6);

