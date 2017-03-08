USE `meganelc`;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
	user_id VARCHAR(25) PRIMARY KEY,
	PASSWORD VARCHAR(12),
	user_type VARCHAR(9)
);

INSERT INTO Users VALUES
('admin', 'admin', 'admin'),
('sally', 'password', 'counselor'),
('jane', 'password', 'patient');

DROP TABLE IF EXISTS Patients;

CREATE TABLE Patients (
	patient_id INT PRIMARY KEY,
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	email VARCHAR(256)
);

INSERT INTO Patients VALUES
(1, 'Tim', 'Tompson', 'tt4ever@email.com'),
(2, 'Jane', 'Smith', 'jane@smith.com'),
(3, 'John', 'Parker', 'jp@uw.edu');

DROP TABLE IF EXISTS Counselors;

CREATE TABLE Counselors (
	counselor_id INT PRIMARY KEY,
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	email VARCHAR(256)
);

INSERT INTO Counselors VALUES
(1, 'Sally', 'Seashell', 'sally@counselor.com'),
(2, 'Amy', 'Anderson', 'amy@counselor.com'),
(3, 'Arthur', 'Anderson', 'arthur@counselor.com');

DROP TABLE IF EXISTS Rooms;

CREATE TABLE Rooms (
	location VARCHAR(10) PRIMARY KEY,
	capacity INT,
	equipment VARCHAR(200)
);

INSERT INTO Rooms VALUES
('101', 20, 'White Board'),
('201', 15, 'White Board, Projector'),
('202', 10, '');

DROP TABLE IF EXISTS Meetings;

CREATE TABLE Meetings (
	meeting_id INT PRIMARY KEY,
	counselor_id INT,
	location VARCHAR(10),
	start_time DATETIME,
	end_time DATETIME,
	title VARCHAR(300)
);

INSERT INTO Meetings VALUES
(1, 1, 201, '2017-03-15 13:00:00', '2017-03-15 14:00:00', 'Coping with Stress'),
(2, 1, 101, '2017-03-17 09:00:00', '2017-03-15 10:00:00', 'Mindfulness'),
(3, 2, 202, '2017-03-25 11:00:00', '2017-03-25 12:30:00', 'Eating Clean for a Happy Mind'),
(4, 3, 101, '2017-03-25 11:00:00', '2017-03-25 12:00:00', 'Weekly Check-in'),
(5, 2, 201, '2017-04-02 09:00:00', '2017-04-02 11:30:00', 'Intake session'),
(6, 3, 201, '2017-04-02 12:30:00', '2017-04-02 13:30:00', 'Nutrition and Wellness'),
(7, 1, 101, '2017-04-03 09:00:00', '2017-04-03 10:30:00', 'Finding Balance'); 

DROP TABLE IF EXISTS Attendees;

CREATE TABLE Attendees (
	meeting_id INT,
	patient_id INT,
	attend_status VARCHAR(9)
	PRIMARY KEY(meeting_id, patient_id)
);

INSERT INTO Attendees VALUES 
(1, 1, 'confirmed'),
(1, 2, 'confirmed'),
(1, 3, 'unconfirmed'),
(2, 1, 'confirmed'),
(3, 1, 'unconfirmed'),
(3, 2, 'confirmed'),
(4, 2, 'confirmed'),
(4, 3, 'confirmed');