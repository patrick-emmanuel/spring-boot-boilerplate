CREATE TABLE role(
    role_id BIGSERIAL,
    name VARCHAR(15) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    modified_at  TIMESTAMP,
    CONSTRAINT roles_pkey PRIMARY KEY(role_id),
    UNIQUE(name)
);

CREATE TABLE users(
    user_id BIGSERIAL,
    role_id BIGINT,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    last_login TIMESTAMP,
    last_password_reset_data timestamp,
    enabled BOOLEAN DEFAULT true,
    deleted BOOLEAN DEFAULT false,
    CONSTRAINT FK_users_roles
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    CONSTRAINT users_pkey PRIMARY KEY(user_id),
    UNIQUE(email)
);

create table user_role(
    user_role_id bigserial,
    role_id bigint,
    user_id bigint,
    primary key (user_role_id),
    constraint UK_user_id_role_id unique (user_id, role_id),
    constraint FK_user_role_role foreign key (role_id) references role(role_id),
    constraint FK_user_role_user foreign key (user_id) references users(user_id)
);

CREATE TABLE password_reset_token(
    password_reset_token_id BIGSERIAL,
    token VARCHAR(255) NOT NULL,
    expiry_date  TIMESTAMP,
    user_id BIGINT,
    CONSTRAINT FK_password_reset_tokens_users
        FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT password_reset_token_pkey PRIMARY KEY(password_reset_token_id)
);
