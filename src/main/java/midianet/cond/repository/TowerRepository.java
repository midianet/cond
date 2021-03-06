package midianet.cond.repository;

import midianet.cond.domain.Tower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TowerRepository extends JpaRepository<Tower, Long>, JpaSpecificationExecutor {

}