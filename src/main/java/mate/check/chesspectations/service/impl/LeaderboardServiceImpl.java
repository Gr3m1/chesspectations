package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.service.LeaderboardService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    @Override
    public Leaderboard getLeaderboard() {
        return null;
    }

    @Override
    public String getPlayerByRank(int rank) {
        return "";
    }

    @Override
    public ChessMatch addChessMatch(ChessMatch chessMatch) {
        return null;
    }
}
