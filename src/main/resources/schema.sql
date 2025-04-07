DROP TABLE IF EXISTS courses;

CREATE TABLE IF NOT EXISTS courses (
    id INT NOT NULL PRIMARY KEY,
    title VARCHAR(128),
    description VARCHAR(255),
    is_full BOOLEAN
);

-- insert into courses values (1, 'CS101', 'An Intro to Computing', FALSE);