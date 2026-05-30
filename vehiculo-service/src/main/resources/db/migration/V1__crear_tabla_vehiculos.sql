CREATE TABLE VEHICULO (
                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        PATENTE VARCHAR(20) NOT NULL UNIQUE,
                        MARCA VARCHAR(100) NOT NULL,
                        MODELO VARCHAR(100) NOT NULL,
                        ANIO INT NOT NULL,
                        COLOR VARCHAR(50) NOT NULL,
                        TIPO_VEHICULO VARCHAR(50) NOT NULL
);