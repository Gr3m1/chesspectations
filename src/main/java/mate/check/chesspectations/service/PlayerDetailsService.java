package mate.check.chesspectations.service;

import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.model.PlayerRank;

import java.util.List;

public interface PlayerDetailsService {

    List<PlayerDetails> getAllPlayers() throws GenericException;

    PlayerRank getPlayerByName(String name) throws GenericException;

    PlayerRank getPlayerByEmail(String email) throws GenericException;

    PlayerRank addNewPlayer(PlayerDetails playerDetails) throws GenericException;

    PlayerDetails updatePlayer(PlayerDetails playerDetails) throws GenericException;

    void removePlayerByName(String name) throws GenericException;

    void removePlayerByEmail(String email) throws GenericException;
}
