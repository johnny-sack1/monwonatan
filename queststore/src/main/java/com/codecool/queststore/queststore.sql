DROP TABLE User_type, Student_type, Mentor_type, Admin_type, Artifact, Backpack, Quest, Experience_Level, Classroom;

CREATE TABLE Experience_Level(
    experience_level_id SERIAL PRIMARY KEY,
    description VARCHAR(200) NOT NULL,
    required_coins INT NOT NULL
);

CREATE TABLE User_type (
    login VARCHAR(20) NOT NULL,
    password VARCHAR(200) NOT NULL,
    first_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    classroom_id VARCHAR(20) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('student', 'mentor', 'admin')),
    PRIMARY KEY (login)
);

CREATE TABLE Student_type (
    login VARCHAR(20) NOT NULL REFERENCES User_type(login) PRIMARY KEY,
    coins_current INT DEFAULT 0 NOT NULL,
    coins_total INT DEFAULT 0 NOT NULL,
    exp_lvl int REFERENCES Experience_Level(experience_level_id) ON DELETE CASCADE
);

CREATE TABLE Mentor_type (
    login VARCHAR(20) NOT NULL REFERENCES User_type(login) ON DELETE CASCADE,
    email VARCHAR(200) NOT NULL,
    address VARCHAR(200) NOT NULL
);

CREATE TABLE Admin_type (
    login VARCHAR(20) NOT NULL REFERENCES User_type(login) ON DELETE CASCADE,
    email VARCHAR(200) NOT NULL
);

CREATE TABLE Artifact (
    artifact_id SERIAL PRIMARY KEY,
    available_for_groups BOOLEAN DEFAULT FALSE,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    price INT NOT NULL
);

CREATE TABLE Backpack (
    student_login VARCHAR(20) NOT NULL REFERENCES User_type(login) ON DELETE CASCADE,
    artifact_id INT NOT NULL REFERENCES Artifact(artifact_id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL CHECK (status IN ('unused', 'pending', 'done'))
);

CREATE TABLE Quest (
    quest_id SERIAL PRIMARY KEY,
    available_for_groups BIT DEFAULT '0',
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    value INT NOT NULL
);

CREATE TABLE Classroom (
    classroom_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    description TEXT NOT NULL
);

INSERT INTO classroom (name, description) VALUES ('2017.1', 'Module advanced');
INSERT INTO artifact (available_for_groups, name, description, price) VALUES (default, 'artifact', 'description', 200);
INSERT INTO quest (name, description, value) VALUES ('Stuff', 'Do some stuff', 200);
INSERT INTO Experience_Level (description, required_coins) VALUES ('PHP programmer', 0);

INSERT INTO user_type (first_name, last_name, login, password, classroom_id, type) VALUES ('Jan', 'Kowalski', 'admin',
'1000:5b424037613932393232:7c5527f5d552085ab5ba1be337633649625070821585f031f71957963df8459217039609e17cec9b2508848e1244fd5770186b807ca7d8dd6cb474ccd3e045ff', 1, 'admin');
INSERT INTO admin_type (login, email) VALUES ('admin', 'admin@codecool.com');

INSERT INTO user_type (first_name, last_name, login, password, classroom_id, type) VALUES ('Marcin', 'Kowalski', 'mentor',
'1000:5b424037613932393232:7c5527f5d552085ab5ba1be337633649625070821585f031f71957963df8459217039609e17cec9b2508848e1244fd5770186b807ca7d8dd6cb474ccd3e045ff', 1, 'mentor');
INSERT INTO mentor_type (login, email, address) VALUES ('mentor', 'mentor@codecool.com', 'Ślusarska 9');

INSERT INTO user_type (first_name, last_name, login, password, classroom_id, type) VALUES ('Filip', 'Brzozowski', 'student',
'1000:5b424037613932393232:7c5527f5d552085ab5ba1be337633649625070821585f031f71957963df8459217039609e17cec9b2508848e1244fd5770186b807ca7d8dd6cb474ccd3e045ff', 1, 'student');
INSERT INTO student_type (login, coins_current, coins_total) VALUES ('student', default, default);