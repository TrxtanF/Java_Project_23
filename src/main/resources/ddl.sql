CREATE TABLE `company` (
  `company_id` int NOT NULL AUTO_INCREMENT,
  `company_name` varchar(45) NOT NULL,
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `company_name_UNIQUE` (`company_name`)
);

CREATE TABLE `room` (
  `room_id` int NOT NULL AUTO_INCREMENT,
  `room_name` varchar(50) NOT NULL,
  PRIMARY KEY (`room_id`),
  UNIQUE KEY `room_name_UNIQUE` (`room_name`)
);


CREATE TABLE `course` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `subject` varchar(50) NOT NULL,
  `room_fk` int NOT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `subject_UNIQUE` (`subject`),
  INDEX `room_fk_idx` (`room_fk`),
  CONSTRAINT `room_fk` FOREIGN KEY (`room_fk`) REFERENCES `room` (`room_id`)
);




CREATE TABLE `student` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `company_fk` int NOT NULL,
  `java_skills` int NOT NULL,
  PRIMARY KEY (`student_id`),
  KEY `company_fk_idx` (`company_fk`),
  CONSTRAINT `company_fk` FOREIGN KEY (`company_fk`) REFERENCES `company` (`company_id`)
);

CREATE TABLE `allocation` (
  `allocation_id` int NOT NULL AUTO_INCREMENT,
  `student_fk` int NOT NULL,
  `course_fk` int NOT NULL,
  PRIMARY KEY (`allocation_id`),
  KEY `student_fk_idx` (`student_fk`),
  KEY `course_fk_idx` (`course_fk`),
  CONSTRAINT `course_fk` FOREIGN KEY (`course_fk`) REFERENCES `course` (`course_id`),
  CONSTRAINT `student_fk` FOREIGN KEY (`student_fk`) REFERENCES `student` (`student_id`)
);

CREATE TABLE `test` (
  `idtest` int NOT NULL,
  `testcol` varchar(45) NOT NULL,
  PRIMARY KEY (`idtest`)
);