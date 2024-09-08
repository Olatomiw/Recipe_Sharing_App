CREATE TABLE ratings(
    id VARCHAR (50) PRIMARY KEY NOT NULL UNIQUE,
    rating INT NOT NULL ,
    recipe_id VARCHAR NOT NULL ,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    user_id VARCHAR NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE saved_recipes(
    id VARCHAR(50) PRIMARY KEY NOT NULL UNIQUE  ,
    user_id VARCHAR NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users(id) ,
    recipe_id VARCHAR NOT NULL ,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id) ,
    saved_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- CREATE TABLE notifications (
--     id VARCHAR (50) PRIMARY KEY NOT NULL UNIQUE ,
--     message VARCHAR (255) NOT NULL,
--     user_id VARCHAR NOT NULL ,
--     FOREIGN KEY (user_id) REFERENCES users(id),
--     type VARCHAR NOT NULL ,
--     recipe_id VARCHAR NOT NULL ,
--     FOREIGN KEY (recipe_id) REFERENCES users(id),
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     is_read BOOLEAN DEFAULT FALSE
-- );

CREATE TABLE tags(
    id VARCHAR NOT NULL PRIMARY KEY ,
    name VARCHAR NOT NULL UNIQUE ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recipe_tags(
    id VARCHAR NOT NULL  PRIMARY KEY ,
    recipe_id VARCHAR NOT NULL ,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    tag_id VARCHAR NOT NULL ,
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);

CREATE TABLE ingredients(
    id VARCHAR NOT NULL PRIMARY KEY UNIQUE ,
    name VARCHAR NOT NULL UNIQUE ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE recipe_ingredient(
    id VARCHAR NOT NULL PRIMARY KEY UNIQUE ,
    recipe_id VARCHAR NOT NULL ,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id) ,
    ingredient_id VARCHAR NOT NULL ,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ,
    quantity VARCHAR NOT NULL
)

