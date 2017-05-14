package midianet.cond.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 80)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 10)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 15)
    private String password;
}