CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    firstName character varying,
    lastName character varying,
	email character varying,
	password character varying
);

SELECT * FROM users;