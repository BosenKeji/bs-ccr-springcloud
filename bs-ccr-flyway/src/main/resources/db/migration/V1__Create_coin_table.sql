CREATE TABLE coin (
    id int auto_increment primary key not null ,
    name varchar(50) not null ,
    status tinyint not null default 1,
    created_at timestamp not null ,
    updated_at timestamp not null

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;