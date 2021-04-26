CREATE DATABASE menagerie;

CREATE TABLE items (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    title varchar(255),
    description varchar(255),
    primary key (id)
);