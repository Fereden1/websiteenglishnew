
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(100) NOT NULL,
role VARCHAR(10) CHECK (role IN ('USER', 'ADMIN')) DEFAULT 'USER',
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    course_type VARCHAR(20) CHECK (course_type IN ('CONVERSATIONAL', 'AVIATION')) NOT NULL,
    price DECIMAL(10,2),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_courses (
                              user_id INT REFERENCES users(id) ON DELETE CASCADE,
                              course_id INT REFERENCES courses(id) ON DELETE CASCADE,
                              enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (user_id, course_id)
);

CREATE TABLE applications (
                              id SERIAL PRIMARY KEY,
                              user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              course_type VARCHAR(20) CHECK (course_type IN ('CONVERSATIONAL', 'AVIATION')) NOT NULL,
                              student_name VARCHAR(100) NOT NULL,
                              email VARCHAR(255) NOT NULL,
                              phone VARCHAR(20),
                              message TEXT,
                              status VARCHAR(20) CHECK (status IN ('NEW', 'CONTACTED', 'CLOSED')) DEFAULT 'NEW',
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          text TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          course_type VARCHAR(20) NOT NULL DEFAULT 'BASIC'
);