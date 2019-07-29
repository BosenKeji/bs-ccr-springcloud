drop table if exists oauth_client_details;
create table oauth_client_details (
  id int (11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  client_id VARCHAR(255) ,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);
 
drop table if exists oauth_client_token;
create table oauth_client_token (
  id int (11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);
 
drop table if exists oauth_access_token;
create table oauth_access_token (
  id int (11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(255)
);
 
drop table if exists oauth_refresh_token;
create table oauth_refresh_token (
  id int (11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication LONG VARBINARY
);
 
drop table if exists oauth_code;
create table oauth_code (
  id int (11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(255),
  authentication LONG VARBINARY
);
 
drop table if exists oauth_approvals;
create table oauth_approvals (
    id int (11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP default '1970-01-01 08:00:01'
);
 
drop table if exists ClientDetails;
create table ClientDetails (
  id int (11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  appId VARCHAR(255),
  resourceIds VARCHAR(255),
  appSecret VARCHAR(255),
  scope VARCHAR(255),
  grantTypes VARCHAR(255),
  redirectUrl VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(255)
);