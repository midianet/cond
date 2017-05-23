package midianet.cond.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import midianet.cond.vo.Convertible;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_resident")
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resident_id", nullable = false)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 80)
    @Column(name = "resident_name", length = 80, nullable = false)
    private String name;

    @Column(name = "resident_telegram", unique = true)
    private Long telegram;

    @NotNull
    @Column(name = "resident_apartment", nullable = false)
    private Integer apartment;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tower_id", nullable = false)
    private Tower tower;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "resident_begin", nullable = false)
    private Date begin;

    @Temporal(TemporalType.DATE)
    @Column(name = "resident_end")
    private Date end;

    public static Specification<Resident> id(final Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.<Long>get("id"), id);
    }

    public static Specification<Resident> nameStart(final String nome) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), nome.toUpperCase() + "%");
    }

}