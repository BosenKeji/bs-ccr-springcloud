CREATE TABLE user(
    id int (11) NOT NULL AUTO_INCREMENT,
		username varchar(64) NOT NULL,
		tel VARCHAR(16) NOT NULL,
		password VARCHAR(150) NOT NULL,
		status TINYINT NOT NULL default 1,
    created_at timestamp not null,
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;