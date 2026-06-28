package cl.duoc.cliente_service.exception;

public class ClienteNotExistException extends RuntimeException {
    public ClienteNotExistException(String message) {
        super(message);
    }
}
