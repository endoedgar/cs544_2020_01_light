INSERT IGNORE INTO role VALUES ("ROLE_STUDENT"), ("ROLE_FACULTY"), ("ROLE_ADMIN");
INSERT IGNORE INTO user (bar_code_id, email, enabled, first_name, last_name, password, username) VALUES
("000-XX-YYYY", "admin@miu.edu", TRUE, "Admin", "Super Privileged", "admin", "admin"), /* id: 1 */
("001-XX-YYYY", "faculty@miu.edu", TRUE, "Faculty", "Medium privileged", "faculty", "faculty"), /* id: 2 */
("002-XX-YYYY", "user@miu.edu", TRUE, "User", "Low Privileged", "user", "user"), /* id: 3 */
("003-XX-YYYY", "eauser1@miu.edu", TRUE, "EA Student 1", "Low Privileged", "eauser1", "eauser1"), /* id: 4 */
("004-XX-YYYY", "eauser2@miu.edu", TRUE, "EA Student 2", "Low Privileged", "eauser2", "eauser2"), /* id: 5 */
("005-XX-YYYY", "eauser3@miu.edu", TRUE, "EA Student 3", "Low Privileged", "eauser3", "eauser3"), /* id: 6 */
("006-XX-YYYY", "alguser1@miu.edu", TRUE, "Algorithms Student 1", "Low Privileged", "alguser1", "alguser1"); /* id: 7 */

REPLACE INTO user_roles VALUES
(1, "ROLE_ADMIN"),
(2, "ROLE_FACULTY"),
(3, "ROLE_STUDENT"),
(4, "ROLE_STUDENT"),
(5, "ROLE_STUDENT"),
(6, "ROLE_STUDENT"),
(7, "ROLE_STUDENT");

REPLACE INTO course (id, name, description) VALUES (1, "EA", "Enterprise Architecture"),
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
(2, 2, "2020-06-01", "2020-06-01"),
(3, 3, "2020-05-01", "2020-06-01");

REPLACE INTO `session`(id, course_offering_id, timeslot_abbreviation, `date`) VALUES
(1, 1, "AM", "2020-05-10"),
(2, 1, "AM", "2020-05-11");

INSERT IGNORE INTO user_course_offerings (user_id, course_offerings_id) VALUES
(4, 1), /* id: 4 */
(5, 1), /* id: 5 */
(6, 1), /* id: 6 */
(7, 3); /* id: 7 */