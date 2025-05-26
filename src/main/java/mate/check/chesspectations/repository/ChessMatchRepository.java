package mate.check.chesspectations.repository;

import mate.check.chesspectations.model.ChessMatch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChessMatchRepository extends CrudRepository<ChessMatch, String> {

}
