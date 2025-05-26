package mate.check.chesspectations.service;

import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.PlayerDetail;
import mate.check.chesspectations.model.PlayerRank;

import java.util.List;

public interface PlayerDetailsService {

    List<PlayerDetail> getAllPlayers() throws GenericException;

    PlayerRank getPlayerByName(String name) throws GenericException;

    PlayerRank getPlayerByEmail(String email) throws GenericException;

    PlayerRank addNewPlayer(PlayerDetail playerDetail) throws GenericException;

    PlayerDetail updatePlayer(PlayerDetail playerDetail) throws GenericException;

    void removePlayerByName(String name) throws GenericException;

    void removePlayerByEmail(String email) throws GenericException;
}
