package cl.duoc.seguro_service.exception;

public class SeguroNotExistException extends RuntimeException {
    public SeguroNotExistException(String message) {
        super(message);
    }
}
