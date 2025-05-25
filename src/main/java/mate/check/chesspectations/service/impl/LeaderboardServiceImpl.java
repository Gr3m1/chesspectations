package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.exception.LeaderboardException;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.LeaderboardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final RankingRepository rankingRepository;

    @Override
    public List<Leaderboard> getLeaderboard() throws LeaderboardException {
        log.info("About the retrieve chess leaderboard");
        List<Leaderboard> leaderboardList = rankingRepository.getLeaderboard();

        if (!leaderboardList.isEmpty()) {
            return leaderboardList;
        } else {
            log.error("Unable to retrieve chess leaderboard");
            throw new LeaderboardException("Uh oh! Something went wrong.");
        }
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
