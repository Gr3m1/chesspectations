package mate.check.chesspectations.service;

import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.Leaderboard;

public interface LeaderboardService {

    // get Leaderboard
    Leaderboard getLeaderboard();

    // get player by rank
    String getPlayerByRank(int rank);

    // add match
    ChessMatch addChessMatch(ChessMatch chessMatch);
}
