package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.Ranking;
import mate.check.chesspectations.repository.LeaderboardRepository;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.LeaderboardService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LeaderboardRepository leaderboardRepository;
    private final RankingRepository rankingRepository;

    @Override
    public List<Leaderboard> getLeaderboard() throws GenericException {
        log.info("About to retrieve chess leaderboard");
        List<Leaderboard> leaderboardList = leaderboardRepository.getLeaderboard();

        if (!leaderboardList.isEmpty()) {
            return leaderboardList;
        } else {
            log.error("Unable to retrieve chess leaderboard");
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public List<Leaderboard> addNewPlayer(String playerId) throws GenericException {
        log.info("Adding new player to leaderboard");

        try {
            int maxRank = rankingRepository.getMaxRank();
            Ranking newPlayerRanking = new Ranking();
            newPlayerRanking.setId(UUID.randomUUID().toString());
            newPlayerRanking.setPlayerId(playerId);
            newPlayerRanking.setRanking(maxRank + 1);

            rankingRepository.saveNewRanking(newPlayerRanking.getId(), newPlayerRanking.getPlayerId(), newPlayerRanking.getRanking());

            return leaderboardRepository.getLeaderboard();

        } catch (Exception e) {
            log.error("Unable to add new player with ID [{}]. Error: [{}]", playerId, e.getMessage(), e);
            throw new GenericException("Unable to add new player with ID [{" + playerId + "}]. Error: [{" + e.getMessage() + "}]", e);
        }
    }
}
