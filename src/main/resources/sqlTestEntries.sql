INSERT INTO Company (company_id, company_name)
VALUES (1,'ABB'), (2,'Cisco'), (3,'Alpha-Test');

INSERT INTO room (room_id, room_name)
VALUES (1, '100D'), (2, '104D'), (3, '102D');

INSERT INTO course (course_id, subject, room_fk)
VALUES (1, 'TINF', 1), (2, 'WVS', 2), (3, 'WIN', 3);

INSERT INTO student (name, company_fk, java_skills)
VALUES ('pippo', 2, 5), ('Paperino', 3, 0), ('Topolino', 2, 6);

INSERT INTO Company (company_name)
VALUES ('AB'), ('Cco'), ('Alpha');

INSERT INTO Allocation (allocation_id, student_fk, course_fk)
Values (1, 2, 2), (2, 2, 3), (3, 1, 3);




