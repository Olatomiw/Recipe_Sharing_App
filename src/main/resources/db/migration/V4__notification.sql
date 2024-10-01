CREATE TABLE notifications (
    id VARCHAR (50) PRIMARY KEY NOT NULL UNIQUE ,
    message VARCHAR (255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    user_id VARCHAR NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users(id),
    recipe_id VARCHAR NOT NULL ,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id)

);