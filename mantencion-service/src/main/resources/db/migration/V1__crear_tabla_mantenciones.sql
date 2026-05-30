CREATE TABLE MANTENCIONES (

                              ID BIGINT AUTO_INCREMENT PRIMARY KEY,

                              VEHICULO_ID BIGINT NOT NULL,

                              DESCRIPCION VARCHAR(255) NOT NULL,

                              FECHA_MANTENCION DATE NOT NULL,

                              COSTO DOUBLE NOT NULL,

                              TIPO_MANTENCION VARCHAR(100) NOT NULL,

                              ESTADO_MANTENCION VARCHAR(100) NOT NULL

);