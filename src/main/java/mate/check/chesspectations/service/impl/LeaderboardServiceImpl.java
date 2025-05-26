package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.repository.ChessMatchRepository;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.LeaderboardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final RankingRepository rankingRepository;
    private final ChessMatchRepository chessMatchRepository;

    @Override
    public List<Leaderboard> getLeaderboard() throws GenericException {
        log.info("About the retrieve chess leaderboard");
        List<Leaderboard> leaderboardList = rankingRepository.getLeaderboard();

        if (!leaderboardList.isEmpty()) {
            return leaderboardList;
        } else {
            log.error("Unable to retrieve chess leaderboard");
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public String getPlayerByRank(int rank) throws GenericException {
        log.info("About to retrieve chess player at rank [{}]", rank);
        Optional<String> playerName = rankingRepository.getPlayerNameByRank(rank);

        if (playerName.isPresent()) {
            return playerName.get();
        } else {
            log.error("Unable to retrieve chess player at rank [{}]", rank);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public ChessMatch addChessMatch(ChessMatch chessMatch) throws GenericException {
        log.info("Adding match between [{}] and [{}]", chessMatch.getEbonyPlayerId(), chessMatch.getIvoryPlayerId());

        try {
            ChessMatch savedMatch = chessMatchRepository.save(chessMatch);
            updateRankings(savedMatch);
            return savedMatch;
        } catch (Exception e) {
            log.error("Unable to add match. Error: [{}]", e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public PlayerRank addNewPlayer(String playerId) throws GenericException {
        log.info("Adding new player to leaderboard");

        // get max rank
        // add new entry in rankings table
        // retrieve PlayerRank

        return null;
    }

    private void updateRankings(ChessMatch chessMatch) {
        // find winner/tie

        // compare ranks

        // magically update table
    }
}
