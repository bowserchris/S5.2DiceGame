DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE users (
id INT AUTO_INCREMENT  PRIMARY KEY,
username VARCHAR(250) NOT NULL,
password VARCHAR(250) NOT NULL,
enabled BOOLEAN
);

CREATE TABLE roles (
id INT AUTO_INCREMENT  PRIMARY KEY,
name VARCHAR(250) NOT NULL
);

INSERT INTO roles (name) VALUES
('USER'),
('ADMIN'),
('MANAGER');

CREATE TABLE users_roles (
user_id INTEGER NOT NULL,
role_id INTEGER NOT NULL,
FOREIGN KEY(user_id) REFERENCES users(id),
FOREIGN KEY(role_id) REFERENCES roles(id),
PRIMARY KEY (user_id, role_id)
);