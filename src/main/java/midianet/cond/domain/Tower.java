package midianet.cond.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_tower")
public class Tower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tower_id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    @Column(name = "tower_name", length = 80, nullable = false)
    private String name;

    public static Specification<Tower> id(final Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.<Long>get("id"), id);
    }

    public static Specification<Tower> nameStart(final String nome) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), nome.toUpperCase() + "%");
    }

}