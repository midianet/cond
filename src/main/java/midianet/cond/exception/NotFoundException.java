package midianet.cond.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: Implement Internationalization
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Registro não encontrado")
public class NotFoundException extends BussinesException {

    public NotFoundException(final String entity, final Object key) {
        super("Não encontrado [".concat(entity).concat("] com a chave [").concat(String.valueOf(key)).concat("]"));
    }

    public static NotFoundException raise(final String entity, final Object key) {
        return new NotFoundException(entity, key);
    }

}