package mate.check.chesspectations.repository;

import mate.check.chesspectations.model.PlayerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerDetailRepository extends JpaRepository<PlayerDetail, String> {

}
