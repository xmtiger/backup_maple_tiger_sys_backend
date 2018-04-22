INSERT INTO users VALUES (1, 'user01', '$2a$10$ZtmDHUsbVuE0yE8se6.MNe7opzme/1HMPe5OdiVPVaJpnfVT7/29a', 'james', 'Carter', 'james.carter@gmail.com', 1, '2010-09-07');
INSERT INTO users VALUES (2, 'user02', '$2a$10$wFVdHriJk3bcLSd4vjV5Wux6mMvMiXfY4Ge4ixEQctBO16S5lgp0W', 'helen', 'Leary', 'helen.leary@gmail.com', 1, '2011-03-11');
INSERT INTO users VALUES (3, 'user03', '$2a$10$6bnBuSr7poS/vKF/aPb3pu8RVXts3H6VvcUwTCCGA0SW5f75.oq8C', 'Linda', 'Douglas', 'linda.douglas@gmail.com', 0, '2012-11-12');

INSERT INTO roles VALUES (1, 'ADMIN');
INSERT INTO roles VALUES (2, 'USER');
INSERT INTO roles VALUES (3, 'ACCOUNTANT');

INSERT INTO user_roles VALUES (1, 1);
INSERT INTO user_roles VALUES (2, 2);
INSERT INTO user_roles VALUES (2, 3);
INSERT INTO user_roles VALUES (3, 2);