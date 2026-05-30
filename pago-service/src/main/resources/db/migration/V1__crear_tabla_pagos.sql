CREATE TABLE PAGOS (

                       ID BIGINT AUTO_INCREMENT PRIMARY KEY,

                       RESERVA_ID BIGINT NOT NULL,

                       USUARIO_ID BIGINT NOT NULL,

                       MONTO DOUBLE NOT NULL,

                       FECHA_PAGO DATE NOT NULL,

                       METODO_PAGO VARCHAR(100) NOT NULL,

                       ESTADO_PAGO VARCHAR(100) NOT NULL

);