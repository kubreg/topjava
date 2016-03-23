DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2016-03-23 02:07:31', 'Burger', 500, 100000);
INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2016-03-24 02:07:31', 'Pizza', 1500, 100000);
INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2016-03-24 02:03:31', 'Pie', 1500, 100000);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2016-03-23 02:07:31', 'Pie', 1300, 100001);
INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2016-03-23 02:07:31', 'Pizza', 1500, 100001);
INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2016-03-24 02:07:31', 'Cola', 1300, 100001);
