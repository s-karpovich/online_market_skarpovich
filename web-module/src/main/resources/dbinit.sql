DROP TABLE IF EXISTS feedback;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;

CREATE TABLE IF NOT EXISTS role (
id BIGINT UNSIGNED      NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30)        NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user (
id BIGINT UNSIGNED      NOT NULL AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(70)    NOT NULL UNIQUE,
password VARCHAR(72)    NOT NULL,
firstname VARCHAR(20)   NOT NULL,
middlename VARCHAR(40)  NOT NULL,
surname VARCHAR(40)     NOT NULL,
deleted  TINYINT(1)     NOT NULL,
role_id BIGINT UNSIGNED NOT NULL, FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT INTO role (name) VALUES ('ADMINISTRATOR');
INSERT INTO role (name) VALUES ('SALE');
INSERT INTO role (name) VALUES ('CUSTOMER');
INSERT INTO role (name) VALUES ('SECURE API');

INSERT INTO user (username,password,firstname,middlename,surname,deleted,role_id) VALUES (
'admin@email.com',
'$2a$12$L9eDNHoC6pGJuQAdg4aL6OB1DlCwMObWUPqcpSFoxnah9nxTZbr7m',
'Admin',
'Adminovich',
'Adminov',
'0',
(SELECT id FROM role WHERE name='ADMINISTRATOR')
);

INSERT INTO user (username,password,firstname,middlename,surname,deleted,role_id) VALUES (
'user@email.com',
'$2a$10$l6wRkqfxG9xtKLDBunBtt.xgQ8VTmSKXa4HzRImvB03tGo/xzmuwG',
'User',
'Userovich',
'Userov',
'0',
(SELECT id FROM role WHERE name='CUSTOMER')
);

INSERT INTO user (username,password,firstname,middlename,surname,deleted,role_id) VALUES (
'user2@email.com',
'$2a$10$l6wRkqfxG9xtKLDBunBtt.xgQ8VTmSKXa4HzRImvB03tGo/xzmuwG',
'User2',
'Userovich2',
'Userov2',
'0',
(SELECT id FROM role WHERE name='CUSTOMER')
);

CREATE TABLE IF NOT EXISTS feedback (
id BIGINT UNSIGNED      NOT NULL AUTO_INCREMENT PRIMARY KEY,
date VARCHAR(70)        NOT NULL,
message VARCHAR(200)    NOT NULL,
deleted  TINYINT(1)     NOT NULL,
user_id BIGINT UNSIGNED NOT NULL, FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO feedback (date,message,deleted,user_id) VALUES (
'05/05/2019',
'This is second feedback message #1. Your company is the BEST in the world! I will recommend your company to all of friends and relatives. Thank you for the great service your provided!',
'0',
(SELECT id FROM user WHERE username='admin@email.com')
);
INSERT INTO feedback (date,message,deleted,user_id) VALUES (
'06/05/2019',
'This is second feedback message #2. Your company is the WORST in the world! I will NOT recommend your company to all of friends and relatives. Thank you for the great service your provided!',
'1',
(SELECT id FROM user WHERE username='user@email.com')
);

INSERT INTO feedback (date,message,deleted,user_id) VALUES (
'06/05/2019',
'This is second feedback message #3. Your company is the BEST in the world! I will recommend your company to all of friends and relatives. Thank you for the great service your provided!',
'0',
(SELECT id FROM user WHERE username='user@email.com')
);

INSERT INTO feedback (date,message,deleted,user_id) VALUES (
'06/05/2019',
'This is second feedback message #4. Your company is the BEST in the world! I will recommend your company to all of friends and relatives. Thank you for the great service your provided!',
'0',
(SELECT id FROM user WHERE username='user@email.com')
);

INSERT INTO feedback (date,message,deleted,user_id) VALUES (
'06/05/2019',
'This is second feedback message #5. Your company is the BEST in the world! I will recommend your company to all of friends and relatives. Thank you for the great service your provided!',
'0',
(SELECT id FROM user WHERE username='user@email.com')
);
