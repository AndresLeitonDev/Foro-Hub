INSERT INTO perfiles (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO perfiles (nombre) VALUES ('ROLE_USER');

INSERT INTO usuarios (email, password, nombre) VALUES ('admin@alura.com', '$2a$10$0/R5g8ErCu5oFDZInPl2RePh4FjdcGIHl8/fgqOAqK5O/e0OGDp2y', 'admin');

INSERT INTO usuario_join_perfil (usuario_id, perfil_id) VALUES (1, 1);
INSERT INTO usuario_join_perfil (usuario_id, perfil_id) VALUES (1, 2);



