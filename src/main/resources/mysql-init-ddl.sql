create table if not exists person
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