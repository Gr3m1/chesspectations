package mate.check.chesspectations.repository;

import mate.check.chesspectations.model.PlayerDetail;
import mate.check.chesspectations.model.PlayerRank;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerDetailRepository extends CrudRepository<PlayerDetail, String> {

    @Query("""
            SELECT p.id, p.player_name AS playerName, p.email_address AS emailAddress, p.date_of_birth AS dateOfBirth, p.games_played AS gamesPlayed, r.ranking
            FROM chessdata.playerDetail p
            INNER JOIN chessdata.ranking r on p.id = r.player_id
            WHERE p.player_name = :name
            """)
    Optional<PlayerRank> getPlayerByName(String name);

    @Query("""
            SELECT p.id, p.player_name AS playerName, p.email_address AS emailAddress, p.date_of_birth AS dateOfBirth, p.games_played AS gamesPlayed, r.ranking
            FROM chessdata.playerDetail p
            INNER JOIN chessdata.ranking r on p.id = r.player_id
            WHERE p.email_address = :email
            """)
    Optional<PlayerRank> getPlayerByEmail(String email);
}
