package midianet.cond.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: Implement Internationalization
@ResponseStatus(value = HttpStatus.IM_USED, reason = "Usuário já em uso")
public class UsernameUsedException extends BussinesException {

    public UsernameUsedException(final String username) {
        super("Usuário [".concat(username).concat("] já existe"));
    }

    public static UsernameUsedException raise(final String username) {
        return new UsernameUsedException(username);
    }

}