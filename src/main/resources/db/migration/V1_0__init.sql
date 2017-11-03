CREATE TABLE roles(
    id BIGSERIAL,
    name VARCHAR(15) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    modified_at  TIMESTAMP,
    CONSTRAINT roles_pkey PRIMARY KEY(id),
    UNIQUE(name)
);

--CREATE sequence hibernate_sequence;
CREATE TABLE users(
    id BIGSERIAL,
    role_id BIGINT,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    last_login TIMESTAMP,
    enabled BOOLEAN DEFAULT true,
    deleted BOOLEAN DEFAULT false,
    CONSTRAINT FK_users_roles
    FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT users_pkey PRIMARY KEY(id),
    UNIQUE(email)
);