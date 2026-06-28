package cl.duoc.pago_service.exception;

public class PagoNotExistException extends RuntimeException {
    public PagoNotExistException(String message) {
        super(message);
    }
}
