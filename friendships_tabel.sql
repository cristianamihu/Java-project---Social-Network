CREATE TABLE friendships (
    id SERIAL PRIMARY KEY,
    idUser1 bigint,
    idUser2 bigint,
    friendsFrom character varying
);

ALTER TABLE friendships
ADD CONSTRAINT fk_user1 FOREIGN KEY(idUser1) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE friendships
ADD CONSTRAINT fk_user2 FOREIGN KEY(idUser2) REFERENCES users(id) ON DELETE CASCADE;

INSERT INTO friendships(idUser1, idUser2, friendsFrom) VALUES (1, 2, '2023-12-13');
INSERT INTO friendships(idUser1, idUser2, friendsFrom) VALUES (3, 1, '2023-12-19');

SELECT * FROM friendships;
