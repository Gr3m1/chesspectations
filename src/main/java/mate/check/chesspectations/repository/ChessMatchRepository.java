package mate.check.chesspectations.repository;

import mate.check.chesspectations.enumeration.Winner;
import mate.check.chesspectations.model.ChessMatch;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface ChessMatchRepository extends CrudRepository<ChessMatch, String> {

    @Modifying
    @Transactional
    @Query ("""
            INSERT INTO chessdata.matches (id, ebony_player_id, ebony_rank, ivory_player_id, ivory_rank, date_played, winner)
            VALUES (:id, :ebonyPlayerId, :ebonyRank, :ivoryPlayerId, :ivoryRank, :datePlayed, :winner)
            """)
    void saveNewMatch(String id, String ebonyPlayerId, int ebonyRank, String ivoryPlayerId, int ivoryRank, LocalDate datePlayed, Winner winner);
}
