package midianet.cond.repository;

import midianet.cond.domain.Ground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroundRepository extends JpaRepository<Ground, Long>, JpaSpecificationExecutor {

}