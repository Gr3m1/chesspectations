package mate.check.chesspectations.repository;

import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerRank;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaderboardRepository extends CrudRepository<PlayerRank, String> {

    @Query("""
            SELECT r.ranking, p.id AS player_id, p.player_name, p.games_played
            FROM chessdata.ranking r
            INNER JOIN chessdata.player_details p ON r.player_id = p.id
            ORDER BY r.ranking
            """)
    List<Leaderboard> getLeaderboard();
}
