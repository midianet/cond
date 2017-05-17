package midianet.cond.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: Implement Internationalization
@ResponseStatus(value = HttpStatus.IM_USED, reason = "Telegram já em uso")
public class TelegramUsedException extends BussinesException {

    public TelegramUsedException(final Long telegram) {
        super("Telegram [".concat(String.valueOf(telegram)).concat("] já existe"));
    }

    public static TelegramUsedException raise(final Long telegram) {
        return new TelegramUsedException(telegram);
    }

}