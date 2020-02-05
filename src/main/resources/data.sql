INSERT IGNORE INTO role VALUES ("ROLE_STUDENT"), ("ROLE_FACULTY"), ("ROLE_ADMIN");
INSERT IGNORE INTO user (bar_code_id, email, enabled, first_name, last_name, password, username) VALUES
("000-XX-YYYY", "admin@miu.edu", TRUE, "Admin", "Super Privileged", "admin", "admin"),
("001-XX-YYYY", "faculty@miu.edu", TRUE, "Faculty", "Medium privileged", "faculty", "faculty"),
("002-XX-YYYY", "user@miu.edu", TRUE, "User", "Low Privileged", "user", "user");

REPLACE INTO user_roles VALUES (1, "ROLE_ADMIN"), (2, "ROLE_FACULTY"), (3, "ROLE_STUDENT");

REPLACE INTO course (id, description, name) VALUES (1, "EA", "Enterprise Architecture"),
(2, "MPP", "Modern Programming Practices"),
(3, "Algorithms", "Damn Algorithms");

REPLACE INTO location (id, description) VALUES (1, "Location A"),
(2, "Location B"),
(3, "Location C");

REPLACE INTO timeslot (abbreviation, description, begin_time, end_time) VALUES
("AM", "Morning", "00:00:00", "11:59:59"),
("PM", "Afternoon", "12:00:00", "23:59:59");

REPLACE INTO course_offering(id, course_id, start_date, end_date) VALUES
(1, 1, "2020-05-01", "2020-06-01"),
(2, 2, "2020-06-01", "2020-06-01")