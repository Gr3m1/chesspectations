package mate.check.chesspectations.service;

import mate.check.chesspectations.model.PlayerDetail;
import mate.check.chesspectations.model.PlayerRank;

import java.util.List;

public interface PlayerDetailsService {

    List<PlayerDetail> getAllPlayers();

    PlayerRank getPlayerByName(String name);

    PlayerRank getPlayerByEmail(String name);

    PlayerRank addNewPlayer(PlayerDetail playerDetail);

    PlayerDetail updatePlayer(PlayerDetail playerDetail);

    void removePlayerByName(String name);

    void removePlayerByEmail(String email);
}
