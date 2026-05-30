package cl.duoc.sucursal_service.exception;

public class SucursalNotExistException extends RuntimeException {
    public SucursalNotExistException(String message) {
        super(message);
    }
}
