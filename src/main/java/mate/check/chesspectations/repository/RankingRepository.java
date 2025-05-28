package mate.check.chesspectations.repository;

import mate.check.chesspectations.model.Ranking;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RankingRepository extends CrudRepository<Ranking, String> {

    List<Ranking> findAllByOrderByRankingAsc();

    @Modifying
    @Query("""
            DELETE FROM chessdata.ranking
            WHERE player_id = :playerId
            """)
    void deleteByPlayerId(String playerId);

    @Query("""
            SELECT MAX(ranking) from chessdata.ranking
            """)
    int getMaxRank();

    @Modifying
    @Transactional
    @Query ("""
            INSERT INTO chessdata.ranking (id, player_id, ranking)
            VALUES (:id, :playerId, :ranking)
            """)
    void saveNewRanking(String id, String playerId, int ranking);
}
