-- Create User Table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Post Table
CREATE TABLE posts (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       body TEXT NOT NULL,
                       author_id INTEGER NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Create Like Table
CREATE TABLE likes (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER NOT NULL,
                       post_id INTEGER NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                       FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
                       UNIQUE (user_id, post_id)
);

-- Create User_Following Table (Self-referencing Many-to-Many)
CREATE TABLE user_following (
                                follower_id INTEGER NOT NULL,
                                following_id INTEGER NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                PRIMARY KEY (follower_id, following_id),
                                FOREIGN KEY (follower_id) REFERENCES users (id) ON DELETE CASCADE,
                                FOREIGN KEY (following_id) REFERENCES users (id) ON DELETE CASCADE
);
