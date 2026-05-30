CREATE TABLE CLIENTE (
                         ID           BIGINT AUTO_INCREMENT PRIMARY KEY,
                         NOMBRE       VARCHAR(100) NOT NULL,
                         APELLIDO     VARCHAR(100) NOT NULL,
                         EMAIL        VARCHAR(150) NOT NULL UNIQUE,
                         CONTRASENIA  VARCHAR(255) NOT NULL,
                         TELEFONO     VARCHAR(20) NOT NULL
);