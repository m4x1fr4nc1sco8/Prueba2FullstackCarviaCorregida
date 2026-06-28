package cl.duoc.mantencion_service.exception;

public class MantencionNotExistException extends RuntimeException {
    public MantencionNotExistException(String message) {
        super(message);
    }
}
