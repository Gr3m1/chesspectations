package mate.check.chesspectations.service;

import mate.check.chesspectations.exception.LeaderboardException;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.Leaderboard;

import java.util.List;

public interface LeaderboardService {

    List<Leaderboard> getLeaderboard() throws LeaderboardException;

    String getPlayerByRank(int rank);

    ChessMatch addChessMatch(ChessMatch chessMatch);
}
