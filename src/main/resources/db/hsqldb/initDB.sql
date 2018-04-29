DROP TABLE employee_addresses IF EXISTS;
DROP TABLE employee_emails IF EXISTS;
DROP TABLE employee_phones IF EXISTS;
DROP TABLE employee_histories IF EXISTS;
DROP TABLE employees IF EXISTS;

DROP TABLE department_addresses IF EXISTS;
DROP TABLE department_emails IF EXISTS;
DROP TABLE department_phones IF EXISTS;
DROP TABLE department_histories IF EXISTS;
DROP TABLE department_relationship IF EXISTS;
DROP TABLE departments IF EXISTS;

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

/*-----------------------------The upper tables are for security---------------------------------------*/
CREATE TABLE employee_histories (
	id							INTEGER IDENTITY PRIMARY KEY,
	owner_id					INTEGER NOT NULL,
	
	status						VARCHAR(10),
	
	begin_time					DATE,
	end_time					DATE,
	
	description					VARCHAR(255)
);

CREATE TABLE employee_phones (
	id							INTEGER IDENTITY PRIMARY KEY,
	owner_id					INTEGER NOT NULL,
	
	phone_type					VARCHAR(30),
	area_code					VARCHAR(10),
	phone_number				VARCHAR(30)	
);

CREATE TABLE employee_emails (
	id							INTEGER IDENTITY PRIMARY KEY,
	owner_id					INTEGER NOT NULL,
	
	email_type					VARCHAR(30),
	email_address				VARCHAR(60),
);

CREATE TABLE employee_addresses (
	id							INTEGER IDENTITY PRIMARY KEY,
	owner_id					INTEGER NOT NULL,
	
	address_type				VARCHAR(10),
	
	street_name					VARCHAR(60),
	street_number				VARCHAR(10),
	
	building_number				VARCHAR(10),
	apartment_number			VARCHAR(10),
	
	city						VARCHAR(60),
	province_state				VARCHAR(60),
	
	country						VARCHAR(60),	
	
	postcode_zip				VARCHAR(10)
);

CREATE TABLE employees (
	id							INTEGER IDENTITY PRIMARY KEY,
	department_id         		INTEGER NOT NULL,
	user_id						INTEGER,
	
	first_name      			VARCHAR_IGNORECASE(30),
    middle_name     			VARCHAR_IGNORECASE(30),
    last_name       			VARCHAR_IGNORECASE(30),
	
	birth_date      			DATE,
    gender          			VARCHAR(10),
	
	status						VARCHAR(10)
);

CREATE TABLE department_histories (
	id							INTEGER IDENTITY PRIMARY KEY,
	owner_id					INTEGER NOT NULL,
	
	status						VARCHAR(10),
	
	begin_time					DATE,
	end_time					DATE,
	
	description					VARCHAR(255)
);

CREATE TABLE department_phones (
	id							INTEGER IDENTITY PRIMARY KEY,
	owner_id					INTEGER NOT NULL,
	
	phone_type					VARCHAR(30),
	area_code					VARCHAR(10),
	phone_number				VARCHAR(30)	
);

CREATE TABLE department_emails (
	id							INTEGER IDENTITY PRIMARY KEY,
	owner_id					INTEGER NOT NULL,
	
	email_type					VARCHAR(30),
	email_address				VARCHAR(60),
);

CREATE TABLE department_addresses (
	id							INTEGER IDENTITY PRIMARY KEY,
	owner_id					INTEGER NOT NULL,
	
	address_type				VARCHAR(10),
	
	street_name					VARCHAR(60),
	street_number				VARCHAR(10),
	
	building_number				VARCHAR(10),
	apartment_number			VARCHAR(10),
	
	city						VARCHAR(60),
	province_state				VARCHAR(60),
	
	country						VARCHAR(60),	
	
	postcode_zip				VARCHAR(10)
);


CREATE TABLE departments (
	id							INTEGER IDENTITY PRIMARY KEY,
	name						VARCHAR(30)
);

CREATE TABLE department_relationship(
    id_father       INTEGER NOT NULL,
    id_child        INTEGER NOT NULL
);

ALTER TABLE department_relationship ADD CONSTRAINT fk_dept_father FOREIGN KEY (id_father) REFERENCES departments (id);
ALTER TABLE department_relationship ADD CONSTRAINT fk_dept_child FOREIGN KEY (id_child) REFERENCES departments (id);

ALTER TABLE employees ADD CONSTRAINT fk_dept_employee FOREIGN KEY (department_id) REFERENCES departments (id); 
ALTER TABLE employees ADD CONSTRAINT fk_user_employee FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE employee_histories ADD CONSTRAINT fk_employee_history FOREIGN KEY (owner_id) REFERENCES employees (id);
ALTER TABLE employee_phones ADD CONSTRAINT fk_employee_phone FOREIGN KEY (owner_id) REFERENCES employees (id);
ALTER TABLE employee_emails ADD CONSTRAINT fk_employee_email FOREIGN KEY (owner_id) REFERENCES employees (id);
ALTER TABLE employee_addresses ADD CONSTRAINT fk_employee_address FOREIGN KEY (owner_id) REFERENCES employees (id);

ALTER TABLE department_phones ADD CONSTRAINT fk_department_phone FOREIGN KEY (owner_id) REFERENCES departments (id);
ALTER TABLE department_emails ADD CONSTRAINT fk_department_email FOREIGN KEY (owner_id) REFERENCES departments (id);
ALTER TABLE department_addresses ADD CONSTRAINT fk_department_address FOREIGN KEY (owner_id) REFERENCES departments (id);
ALTER TABLE department_histories ADD CONSTRAINT fk_dept_history FOREIGN KEY (owner_id) REFERENCES departments (id);

 
