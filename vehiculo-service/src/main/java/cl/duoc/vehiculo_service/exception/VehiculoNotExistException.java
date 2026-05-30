package cl.duoc.vehiculo_service.exception;

public class VehiculoNotExistException extends RuntimeException {
    public VehiculoNotExistException(String message) {
        super(message);
    }
}
