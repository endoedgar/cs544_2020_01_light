INSERT IGNORE INTO role VALUES ("Student"), ("Faculty"), ("Admin");
INSERT IGNORE INTO user (bar_code_id, email, enabled, first_name, last_name, password, username) VALUES
("000-XX-YYYY", "admin@miu.edu", TRUE, "Admin", "Super Privileged", "admin", "admin"),
("001-XX-YYYY", "faculty@miu.edu", TRUE, "Faculty", "Medium privileged", "faculty", "faculty"),
("002-XX-YYYY", "user@miu.edu", TRUE, "User", "Low Privileged", "user", "user");

REPLACE INTO user_role VALUES (1, "Admin"), (2, "Faculty"), (3, "Student");