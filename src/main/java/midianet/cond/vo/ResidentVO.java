package midianet.cond.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import midianet.cond.domain.Tower;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResidentVO implements Serializable {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 80)
    private String name;

    private Long telegram;

    @NotNull
    private Integer apartment;

    @NotNull
    private Tower tower;

    @NotNull
    private Date begin;

    private Date end;

}