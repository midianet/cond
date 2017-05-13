package midianet.cond.exception;

public class InfraException extends RuntimeException {

    public InfraException(final String message) {
        super(message);
    }

    public InfraException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InfraException(final Throwable cause) {
        super(cause);
    }

    public static InfraException raise(final String message) {
        return new InfraException(message);
    }

    public static InfraException raise(final String message, final Throwable cause) {
        return new InfraException(message, cause);
    }

    public static InfraException raise(final Throwable cause) {
        return new InfraException(cause);
    }

}