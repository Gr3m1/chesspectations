package mate.check.chesspectations.service;

import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.model.PlayerNameRank;
import mate.check.chesspectations.model.PlayerRank;

import java.util.List;

public interface PlayerDetailsService {

    List<PlayerNameRank> getAllPlayers() throws GenericException;

    PlayerRank getPlayerById(String id) throws GenericException;

    List<Leaderboard> addNewPlayer(PlayerDetails playerDetails) throws GenericException;

    List<Leaderboard> updatePlayer(PlayerDetails playerDetails) throws GenericException;

    List<Leaderboard> removePlayerById(String id) throws GenericException;
}
