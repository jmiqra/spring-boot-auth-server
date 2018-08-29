INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER');

INSERT INTO `user` VALUES (1,'ratul','13ratul@gmail.com',/*123*/'$2a$08$Zkp0R01vI6U5dDDv52DgXeIXmQE29gej4456R44iTWM6xng/trVWm','active',NULL,0,NULL,'2018-08-28 12:39:01','2018-08-28 12:39:01');
INSERT INTO `user` VALUES (2,'asraf','asraf@dginnovationlab.com',/*123*/'$2a$08$Zkp0R01vI6U5dDDv52DgXeIXmQE29gej4456R44iTWM6xng/trVWm','active',NULL,0,NULL,'2018-08-28 12:39:01','2018-08-28 12:39:01');

INSERT INTO `user_role` VALUES (1,1),(1,2);
INSERT INTO `user_role` VALUES (2,2);
