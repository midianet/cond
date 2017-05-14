package midianet.cond.exception;


//TODO: Implement Internationalization
public abstract class BussinesException extends RuntimeException {

    public BussinesException() {
        super();
    }

    public BussinesException(String message) {
        super(message);
    }

    public BussinesException(String message, Throwable cause) {
        super(message, cause);
    }

    public BussinesException(Throwable cause) {
        super(cause);
    }

    protected BussinesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
