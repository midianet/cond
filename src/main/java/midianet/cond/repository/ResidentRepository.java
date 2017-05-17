package midianet.cond.repository;

import midianet.cond.domain.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long>, JpaSpecificationExecutor {

    List<Resident> findByTelegram(final Long telegram);
}