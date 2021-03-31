DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS cathedra CASCADE;
DROP TABLE IF EXISTS subjects CASCADE;
DROP TABLE IF EXISTS audience CASCADE;
DROP TABLE IF EXISTS faculty CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS schedules CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
CREATE TABLE Faculty(
	id SERIAL,
	name CHAR(20),
	PRIMARY KEY (id)
);
CREATE TABLE Cathedra(
    id INTEGER SERIAL,
	name CHAR(35),
	facultyId int,
	PRIMARY KEY (id),
	FOREIGN KEY (facultyId) REFERENCES Faculty(id)
);
CREATE TABLE Teachers(
	id SERIAL,
	firstName VARCHAR (15) NOT NULL,
	lastName VARCHAR(15) NOT NULL,
	sex VARCHAR(5),
	age int,
	degree VARCHAR(15),
	scheduleId int,
	PRIMARY KEY (id)
);
CREATE TABLE subjects(
	id SERIAL,
	name character(35) NOT NULL,
	description CHARACTER(35),
	teacherId int,
	PRIMARY KEY (id),
	FOREIGN KEY (teacherId) REFERENCES Teachers(id)
);
CREATE TABLE Audience(
	id SERIAL,
	number INTEGER,
	PRIMARY KEY (id)
);
CREATE TABLE Schedules(
    id SERIAL,
   name char(15),
    PRIMARY KEY (id)
);

CREATE TABLE Items(
	id SERIAL,
	date DATE,
	duration INTEGER,
	subjectId int,
	audienceId int,
	scheduleId int,
	FOREIGN KEY (subjectId) REFERENCES subjects(id),
	FOREIGN KEY (audienceId) REFERENCES Audience(id),
	FOREIGN KEY (scheduleId) REFERENCES Schedules(id),
	PRIMARY KEY (id)
);
CREATE TABLE groups(
	id SERIAL,
	name CHAR(5),
	cathedraId int,
	scheduleId int,
	PRIMARY KEY (id),
	FOREIGN KEY (cathedraId) REFERENCES Cathedra(id),
	FOREIGN  KEY (scheduleId) REFERENCES Schedules(id)
);

CREATE TABLE students(
	id SERIAL,
	firstName VARCHAR (15),
	lastName VARCHAR(15),
	sex VARCHAR(5),
	age int,
	course int,
	PRIMARY KEY (id)
);