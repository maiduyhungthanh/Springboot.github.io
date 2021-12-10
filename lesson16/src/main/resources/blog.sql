INSERT INTO user(name,password,email) VALUES ('thanh','123','th@gmail.com');
INSERT INTO user(name,password,email) VALUES ('hoa','456','h@gmail.com');
INSERT INTO user(name,password,email) VALUES ('tung','789','t@gmail.com');

INSERT INTO post(user_id,title,date_post) VALUES (1,'Springboot vs Json','2021-01-01');
INSERT INTO post(user_id,title,date_post) VALUES (1,'Springboot vs SQL','2021-01-01');
INSERT INTO post(user_id,title,date_post) VALUES (1,'Springboot vs CSV','2021-01-01');

INSERT INTO comment(user_id,comment,post_id) VALUES (2,'thanks you very much',1);
INSERT INTO comment(user_id,comment,post_id) VALUES (2,'amazing Good Job',1);
INSERT INTO comment(user_id,comment,post_id) VALUES (3,'wonderful',1);
INSERT INTO comment(user_id,comment,post_id) VALUES (3,'admire you so much',1);

