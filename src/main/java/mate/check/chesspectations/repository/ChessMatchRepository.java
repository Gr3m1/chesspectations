package mate.check.chesspectations.repository;

import mate.check.chesspectations.enumeration.Winner;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.ChessMatchWithNames;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChessMatchRepository extends CrudRepository<ChessMatch, String> {

    @Query("""
            SELECT p.player_name AS ebony_name, c.ebony_rank, 
                   d.player_name AS ivory_name, c.ivory_rank, 
                   c.date_played, c.winner
            FROM chessdata.matches c
            LEFT JOIN chessdata.player_details p ON c.ebony_player_id = p.id
            LEFT JOIN chessdata.player_details d ON c.ivory_player_id = d.id
            WHERE c.ebony_player_id = :playerId
            OR c.ivory_player_id = :playerId
            ORDER BY c.date_played DESC
            """)
    List<ChessMatchWithNames> getHistoryById(String playerId);

    @Modifying
    @Transactional
    @Query("""
            INSERT INTO chessdata.matches (id, ebony_player_id, ebony_rank, ivory_player_id, ivory_rank, date_played, winner)
            VALUES (:id, :ebonyPlayerId, :ebonyRank, :ivoryPlayerId, :ivoryRank, :datePlayed, :winner)
            """)
    void saveNewMatch(String id, String ebonyPlayerId, int ebonyRank, String ivoryPlayerId, int ivoryRank, LocalDate datePlayed, Winner winner);
}
