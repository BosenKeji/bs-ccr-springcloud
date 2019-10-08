CREATE TABLE job(
    id int (11) NOT NULL AUTO_INCREMENT,
    job_id varchar (24) NOT NULL DEFAULT '' ,
    job_name varchar (50) not null DEFAULT '' ,
    status tinyint not null default 1 ,
    created_at timestamp not null,
    updated_at timestamp not null default '1980-01-01 00:00:01',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
