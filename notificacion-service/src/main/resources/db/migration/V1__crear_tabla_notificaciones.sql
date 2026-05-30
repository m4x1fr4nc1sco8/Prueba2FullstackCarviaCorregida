CREATE TABLE NOTIFICACIONES (

                                ID BIGINT AUTO_INCREMENT PRIMARY KEY,

                                USUARIO_ID BIGINT NOT NULL,

                                MENSAJE VARCHAR(255) NOT NULL,

                                TIPO_NOTIFICACION VARCHAR(100) NOT NULL,

                                FECHA_ENVIO DATE NOT NULL,

                                ESTADO_NOTIFICACION VARCHAR(100) NOT NULL

);