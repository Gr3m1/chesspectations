package mate.check.chesspectations.repository;

import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.model.PlayerRank;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PlayerDetailRepository extends CrudRepository<PlayerDetails, String> {

   @Query("""
            SELECT p.id, p.player_name, p.email_address, p.date_of_birth, p.games_played, r.ranking
            FROM chessdata.player_details p
            INNER JOIN chessdata.ranking r on p.id = r.player_id
            WHERE p.id = :id
            """)
    Optional<PlayerRank> getPlayerById(String id);

    @Modifying
    @Transactional
    @Query ("""
            INSERT INTO chessdata.player_details (id, player_name, email_address, date_of_birth, games_played)
            VALUES (:id, :name, :email, :dateOfBirth, :gamesPlayed)
            """)
    void saveNewPlayer(String id, String name, String email, LocalDate dateOfBirth, int gamesPlayed);
}
