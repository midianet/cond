package midianet.cond.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: Implement Internationalization
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Usuário e ou senha inválida")
public class BadCredentialsException extends BussinesException {

    public BadCredentialsException() {
        super("Usuário e ou senha inválida.");
    }

    public static BadCredentialsException raise() {
        return new BadCredentialsException();
    }

}