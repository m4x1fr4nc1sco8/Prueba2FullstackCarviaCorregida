CREATE TABLE SEGUROS (

                         ID BIGINT AUTO_INCREMENT PRIMARY KEY,

                         VEHICULO_ID BIGINT NOT NULL,

                         NOMBRE_SEGURO VARCHAR(150) NOT NULL,

                         TIPO_COBERTURA VARCHAR(150) NOT NULL,

                         COSTO_SEGURO DOUBLE NOT NULL,

                         FECHA_INICIO DATE NOT NULL,

                         FECHA_FIN DATE NOT NULL,

                         ESTADO_SEGURO VARCHAR(100) NOT NULL

);