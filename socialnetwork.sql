CREATE TABLE users (
    id bigint PRIMARY KEY,
    first_name character varying,
    last_name character varying
);

SELECT * FROM users;

INSERT INTO users (id, first_name, last_name) VALUES (1, 'Maria', 'Dancu');
INSERT INTO users (id, first_name, last_name) VALUES (2, 'Oana', 'Marcu');
INSERT INTO users (id, first_name, last_name) VALUES (3, 'Gabriel', 'Popa');
