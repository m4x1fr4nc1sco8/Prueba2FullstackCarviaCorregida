CREATE TABLE SUCURSALES (

                            ID BIGINT AUTO_INCREMENT PRIMARY KEY,

                            NOMBRE_SUCURSAL VARCHAR(150) NOT NULL,

                            DIRECCION VARCHAR(255) NOT NULL,

                            CIUDAD VARCHAR(100) NOT NULL,

                            TELEFONO VARCHAR(50) NOT NULL,

                            HORARIO_ATENCION VARCHAR(100) NOT NULL,

                            ESTADO_SUCURSAL VARCHAR(50) NOT NULL

);