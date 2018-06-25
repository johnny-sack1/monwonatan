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