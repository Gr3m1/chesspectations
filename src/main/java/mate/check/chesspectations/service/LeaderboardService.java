package mate.check.chesspectations.service;

import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.Leaderboard;

import java.util.List;

public interface LeaderboardService {

    List<Leaderboard> getLeaderboard() throws GenericException;

    List<Leaderboard> addNewPlayer(String playerId) throws GenericException;
}
