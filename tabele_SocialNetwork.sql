CREATE TABLE users
(
	id SERIAL PRIMARY KEY,
    firstname character varying,
    lastname character varying,
	password character varying
)

CREATE TABLE friendships
(
	id_user1 bigint,
	id_user2 bigint,
	friendsfrom timestamp
)

INSERT INTO friendships ( id_user1, id_user2) VALUES ( 1, 0);

SELECT * FROM friendships;

ALTER TABLE friendships
add constraint fk_user1 foreign key(id_user1) references users(id) on delete cascade;

ALTER TABLE friendships
add constraint fk_user2 foreign key(id_user2) references users(id) on delete cascade


alter table friendships
add constraint pk_friendships primary key(id_user1, id_user2);