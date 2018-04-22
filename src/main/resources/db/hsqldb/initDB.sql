DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE roles IF EXISTS;

CREATE TABLE users (
	id							INTEGER IDENTITY PRIMARY KEY,
	username					VARCHAR(100) NOT NULL,
	password					VARCHAR(100) NOT NULL,
	first_name					VARCHAR(30),
	last_name					VARCHAR(30),
	email						VARCHAR(255),
	enabled						INTEGER NOT NULL,
	last_password_reset_date	DATE NOT NULL
);
CREATE INDEX users_name ON users (username);

CREATE TABLE roles (
	id							INTEGER IDENTITY PRIMARY KEY,
	name						VARCHAR(30) NOT NULL
);

CREATE TABLE user_roles (
	user_id						INTEGER NOT NULL,
	role_id						INTEGER NOT NULL
);
ALTER TABLE user_roles ADD CONSTRAINT fk_user_roles_users FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE user_roles ADD CONSTRAINT fk_user_roles_roles FOREIGN KEY (role_id) REFERENCES roles (id);