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
    login VARCHAR(20) NOT NULL REFERENCES User_type(login),
    coins INT NOT NULL
);

CREATE TABLE Mentor_type (
    login VARCHAR(20) NOT NULL REFERENCES User_type(login),
    address VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL
);

CREATE TABLE Admin_type (
    login VARCHAR(20) NOT NULL REFERENCES User_type(login),
    email VARCHAR(200) NOT NULL
);

CREATE TABLE Artifact (
    artifact_id INT NOT NULL,
    available_for_groups BIT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY (artifact_id)
);

CREATE TABLE Wallet (
    student_login VARCHAR(20) NOT NULL REFERENCES User_type(login),
    artifact_id INT NOT NULL REFERENCES Artifact(artifact_id),
    status VARCHAR(20) NOT NULL CHECK (status IN ('unused', 'pending', 'done'))
);

CREATE TABLE Quest (
    quest_id INT NOT NULL,
    available_for_groups BIT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    value INT NOT NULL,
    PRIMARY KEY (quest_id)
);

CREATE TABLE Experience_Level (
    description VARCHAR(200) NOT NULL,
    required_coins INT NOT NULL
);

CREATE TABLE Classroom (
    classroom_id VARCHAR(20) NOT NULL,
    name VARCHAR(100),
    description TEXT NOT NULL,
    PRIMARY KEY (classroom_id)
);