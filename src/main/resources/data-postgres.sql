INSERT INTO roles (role_id, name) values (1, 'USER');
INSERT INTO roles (role_id, name) values (2, 'ADMIN');
INSERT INTO users (role_id, firstname, lastname, password, email) values (2, 'Emmanuel', 'Patrick', 'c9ad541b0a63a5828429e1466f48dd0ad9ff2c146c6415f306035dfc2c7900ef1ac00bb2998cf1f5', 'admin@email.com');
INSERT INTO users (role_id, firstname, lastname, password, email) values (1, 'Inem', 'Patrick', 'c9ad541b0a63a5828429e1466f48dd0ad9ff2c146c6415f306035dfc2c7900ef1ac00bb2998cf1f5', 'user@email.com');