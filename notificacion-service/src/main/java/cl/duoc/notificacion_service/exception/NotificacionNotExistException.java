package cl.duoc.notificacion_service.exception;

public class NotificacionNotExistException extends RuntimeException {
    public NotificacionNotExistException(String message) {
        super(message);
    }
}
