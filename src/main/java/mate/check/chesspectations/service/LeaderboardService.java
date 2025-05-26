package mate.check.chesspectations.service;

import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerRank;

import java.util.List;

public interface LeaderboardService {

    List<Leaderboard> getLeaderboard() throws GenericException;

    String getPlayerByRank(int rank) throws GenericException;

    ChessMatch addChessMatch(ChessMatch chessMatch) throws GenericException;

    PlayerRank addNewPlayer(String playerId) throws GenericException;
}
