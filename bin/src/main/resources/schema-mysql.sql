--DROP TABLE people IF EXISTS;
--
--CREATE TABLE people  (
--    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
--    first_name VARCHAR(20),
--    last_name VARCHAR(20)
--);
--Drop table if exists `person`;

create table person
(
`personId`  int(11) NOT NULL AUTO_INCREMENT,
`personName`  varchar(20),
`personAge`  varchar(3) ,
`personSex`  varchar(20),
PRIMARY KEY (`personId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8
;