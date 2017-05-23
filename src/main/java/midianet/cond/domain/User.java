package midianet.cond.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 80)
    @Column(name = "user_name", length = 80, nullable = false)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 10)
    @Column(name = "user_username", length = 10, nullable = false, unique = true)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 15)
    @Column(name = "user_password", length = 15, nullable = false)
    private String password;

    @NotNull
    @NotEmpty
    @Size(max = 10)
    @Column(name = "user_profile", length = 10, nullable = false)
    private String profile;

    public static Specification<User> id(final Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.<Long>get("id"), id);
    }

    public static Specification<User> nameStart(final String nome) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), nome.toUpperCase() + "%");
    }

    public static Specification<User> usernameStart(final String username) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username);
    }

}