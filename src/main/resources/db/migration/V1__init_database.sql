CREATE  TABLE users (
    id varchar(36) NOT NULL  primary key,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL ,
    password VARCHAR(255) NOT NULL ,
    is_verified BOOLEAN DEFAULT FALSE,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE recipes
(
    id           VARCHAR(50)  NOT NULL PRIMARY KEY,
    recipe_name  VARCHAR(255) NOT NULL,
    description  VARCHAR      NOT NULL,
    instructions VARCHAR      NOT NULL,
    author_id    VARCHAR      NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users (id),
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    img_url      VARCHAR(255) NOT NULL
);

CREATE TABLE comments(
    id VARCHAR(50) PRIMARY KEY NOT NULL UNIQUE ,
    message VARCHAR ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    likes INT DEFAULT 0,
    dislikes INT DEFAULT 0 ,
    recipe_id VARCHAR (50) NOT NULL ,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    parent_comment_id VARCHAR(50) NOT NULL ,
    FOREIGN KEY (parent_comment_id) REFERENCES comments(id),
    author_id VARCHAR NOT NULL ,
    FOREIGN KEY (author_id) REFERENCES users(id)
);

