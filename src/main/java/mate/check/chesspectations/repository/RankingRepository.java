package mate.check.chesspectations.repository;

import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<PlayerRank, String> {

@Query(value = """
        SELECT r.ranking, p.player_name AS playerName, p.games_played AS gamesPlayed
        from chessdata.ranking r
        INNER JOIN chessdata.player_details p
        ON r.player_id = p.id
        ORDER BY r.ranking
        """, nativeQuery = true)
List<Leaderboard> getLeaderboard();
}
