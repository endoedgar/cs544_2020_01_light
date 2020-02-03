INSERT IGNORE INTO role VALUES ("ROLE_STUDENT"), ("ROLE_FACULTY"), ("ROLE_ADMIN");
INSERT IGNORE INTO user (bar_code_id, email, enabled, first_name, last_name, password, username) VALUES
("000-XX-YYYY", "admin@miu.edu", TRUE, "Admin", "Super Privileged", "admin", "admin"),
("001-XX-YYYY", "faculty@miu.edu", TRUE, "Faculty", "Medium privileged", "faculty", "faculty"),
("002-XX-YYYY", "user@miu.edu", TRUE, "User", "Low Privileged", "user", "user");

REPLACE INTO user_roles VALUES (1, "ROLE_ADMIN"), (2, "ROLE_FACULTY"), (3, "ROLE_STUDENT");