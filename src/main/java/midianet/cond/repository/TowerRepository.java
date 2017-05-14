package midianet.cond.repository;

import midianet.cond.domain.Tower;
import midianet.cond.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TowerRepository extends JpaRepository<Tower, Long>, JpaSpecificationExecutor {

    Optional<Tower> findById(final Long id);

}