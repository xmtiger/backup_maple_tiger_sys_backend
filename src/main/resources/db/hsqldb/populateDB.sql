INSERT INTO users VALUES (1, 'user01', '$2a$10$ZtmDHUsbVuE0yE8se6.MNe7opzme/1HMPe5OdiVPVaJpnfVT7/29a', 'james', 'Carter', 'james.carter@gmail.com', 1, '2010-09-07');
INSERT INTO users VALUES (2, 'user02', '$2a$10$wFVdHriJk3bcLSd4vjV5Wux6mMvMiXfY4Ge4ixEQctBO16S5lgp0W', 'helen', 'Leary', 'helen.leary@gmail.com', 1, '2011-03-11');
INSERT INTO users VALUES (3, 'user03', '$2a$10$6bnBuSr7poS/vKF/aPb3pu8RVXts3H6VvcUwTCCGA0SW5f75.oq8C', 'Linda', 'Douglas', 'linda.douglas@gmail.com', 1, '2012-11-12');

INSERT INTO roles VALUES (1, 'ADMIN');
INSERT INTO roles VALUES (2, 'USER');
INSERT INTO roles VALUES (3, 'ACCOUNTANT');

INSERT INTO user_roles VALUES (1, 1);
INSERT INTO user_roles VALUES (2, 2);
INSERT INTO user_roles VALUES (2, 3);
INSERT INTO user_roles VALUES (3, 2);

/*The upper is for initiation of security tables*/
INSERT INTO departments VALUES (1, 'Management');
INSERT INTO departments VALUES (2, 'Engineering');
INSERT INTO departments VALUES (3, 'Accounting');

INSERT INTO department_relationship VALUES (1, 3);

INSERT INTO department_histories VALUES (1, 1, 'ACTIVE', '2001-09-01', '1900-01-01', '');
INSERT INTO department_histories VALUES (2, 2, 'ACTIVE', '2003-08-01', '1900-01-01', '');
INSERT INTO department_histories VALUES (3, 3, 'ACTIVE', '2011-03-01', '1900-01-01', '');

INSERT INTO department_phones VALUES (1, 1, 'office', '416', '8966699');
INSERT INTO department_phones VALUES (2, 2, 'office', '416', '5666656');
INSERT INTO department_phones VALUES (3, 3, 'office', '416', '7866678');

INSERT INTO department_emails VALUES (1, 1, 'office', 'managerment@yamax.com');
INSERT INTO department_emails VALUES (2, 2, 'office', 'engineering@yamax.com');
INSERT INTO department_emails VALUES (3, 3, 'office', 'accounting@yamax.com');

INSERT INTO department_addresses VALUES (1, 1, 'office', 'Dollard', '1899', '', '201', 'Montreal', 'Quebec', 'Canada', 'H8N 3H6'); 
INSERT INTO department_addresses VALUES (2, 2, 'office', 'Dollard', '1899', '', '201', 'Montreal', 'Quebec', 'Canada', 'H8N 3H7'); 
INSERT INTO department_addresses VALUES (3, 3, 'office', 'Dollard', '1899', '', '201', 'Montreal', 'Quebec', 'Canada', 'H8N 3H8'); 

INSERT INTO employees VALUES (1, 1, 1, 'james', '', 'Carter', '1977-09-07', 'MALE', 'ACTIVE');
INSERT INTO employees VALUES (2, 2, 2, 'helen', '', 'Leary', '1978-08-16', 'FEMALE', 'ACTIVE');
INSERT INTO employees VALUES (3, 3, 3, 'Linda', '', 'Douglas', '1982-05-31', 'FEMALE', 'ACTIVE');

INSERT INTO employee_files VALUES (1, 'ID Photo', 1, 'ACTIVE', 'jpg', 'files/employee/employee_1.jpg', '2003-09-10', 'This is for personal ID photo used internally');
INSERT INTO employee_files VALUES (2, 'ID Photo', 2, 'ACTIVE', 'jpg', 'files/employee/employee_2.jpg', '2006-07-07', 'This is for personal ID photo used internally');
INSERT INTO employee_files VALUES (3, 'ID Photo', 3, 'ACTIVE', 'jpg', 'files/employee/employee_3.jpg', '2012-06-09', 'This is for personal ID photo used internally');

INSERT INTO employee_histories VALUES (1, 1, 'ACTIVE', '2003-09-01', '1900-01-01', '');
INSERT INTO employee_histories VALUES (2, 2, 'ACTIVE', '2006-08-01', '1900-01-01', '');
INSERT INTO employee_histories VALUES (3, 3, 'ACTIVE', '2012-03-01', '1900-01-01', '');

INSERT INTO employee_phones VALUES (1, 1, 'office', '780', '6667799');
INSERT INTO employee_phones VALUES (2, 2, 'office', '780', '7896556');
INSERT INTO employee_phones VALUES (3, 3, 'office', '780', '9796559');

INSERT INTO employee_emails VALUES (1, 1, 'office', 'test01@yamax.com');
INSERT INTO employee_emails VALUES (2, 2, 'office', 'test02@yamax.com');
INSERT INTO employee_emails VALUES (3, 3, 'office', 'test03@yamax.com');

INSERT INTO employee_addresses VALUES (1, 1, 'living', 'springland', '191', '', '', 'Montreal', 'Quebec', 'Canada', 'H8N 3H6');
INSERT INTO employee_addresses VALUES (2, 2, 'living', 'westland', '8827', '', '', 'Montreal', 'Quebec', 'Canada', 'H8N 3H7');
INSERT INTO employee_addresses VALUES (3, 3, 'living', 'eastside', '2356', '', '', 'Montreal', 'Quebec', 'Canada', 'H8N 3H8');









