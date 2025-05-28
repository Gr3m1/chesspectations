package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.*;
import mate.check.chesspectations.repository.ChessMatchRepository;
import mate.check.chesspectations.repository.LeaderboardRepository;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.LeaderboardService;
import mate.check.chesspectations.service.RankingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final ChessMatchRepository chessMatchRepository;
    private final RankingRepository rankingRepository;
    private final PlayerDetailRepository playerDetailRepository;
    private final RankingService rankingService;

    @Override
    public List<Leaderboard> getLeaderboard() throws GenericException {
        log.info("About the retrieve chess leaderboard");
        List<Leaderboard> leaderboardList = leaderboardRepository.getLeaderboard();

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
        Optional<String> playerName = leaderboardRepository.getPlayerNameByRank(rank);

        if (playerName.isPresent()) {
            return playerName.get();
        } else {
            log.error("Unable to retrieve chess player at rank [{}]", rank);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    @Transactional
    public List<Leaderboard> addChessMatch(ChessMatch chessMatch) throws GenericException {
        log.info("Adding match between [{}] and [{}]", chessMatch.getEbonyPlayerId(), chessMatch.getIvoryPlayerId());

        PlayerDetails ebonyPlayer;
        PlayerDetails ivoryPlayer;

        try {
            Optional<PlayerDetails> ebonyPlayerDetail = playerDetailRepository.getPlayerById(chessMatch.getEbonyPlayerId());
            Optional<PlayerDetails> ivoryPlayerDetail = playerDetailRepository.getPlayerById(chessMatch.getIvoryPlayerId());

            if (ebonyPlayerDetail.isPresent()) {
                ebonyPlayer = ebonyPlayerDetail.get();
                ebonyPlayer.setGamesPlayed(ebonyPlayer.getGamesPlayed() + 1);
                playerDetailRepository.save(ebonyPlayer);
            } else {
                log.error("Unable to find ebony player with ID [{}]", chessMatch.getEbonyPlayerId());
                throw new GenericException("Uh oh! Something went wrong.");
            }

            if (ivoryPlayerDetail.isPresent()) {
                ivoryPlayer = ivoryPlayerDetail.get();
                ivoryPlayer.setGamesPlayed(ivoryPlayer.getGamesPlayed() + 1);
                playerDetailRepository.save(ivoryPlayer);
            } else {
                log.error("Unable to find ivory player with ID [{}]", chessMatch.getIvoryPlayerId());
                throw new GenericException("Uh oh! Something went wrong.");
            }
            chessMatch.setId(UUID.randomUUID().toString());
            chessMatchRepository.saveNewMatch(
                    chessMatch.getId(),
                    chessMatch.getEbonyPlayerId(),
                    chessMatch.getEbonyRank(),
                    chessMatch.getIvoryPlayerId(),
                    chessMatch.getIvoryRank(),
                    chessMatch.getDatePlayed(),
                    chessMatch.getWinner());

            rankingService.updateRankings(chessMatch);
            return getLeaderboard();

        } catch (Exception e) {
            log.error("Unable to add match. Error: [{}]", e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public PlayerRank addNewPlayer(String playerId) throws GenericException {
        log.info("Adding new player to leaderboard");

        try {
            int maxRank = rankingRepository.getMaxRank();
            Ranking newPlayerRanking = new Ranking();
            newPlayerRanking.setId(UUID.randomUUID().toString());
            newPlayerRanking.setPlayerId(playerId);
            newPlayerRanking.setRanking(maxRank + 1);

            rankingRepository.saveNewRanking(newPlayerRanking.getId(), newPlayerRanking.getPlayerId(), newPlayerRanking.getRanking());

            return leaderboardRepository.getPlayerRank(playerId);

        } catch (Exception e) {
            log.error("Unable to add new player with ID [{}]. Error: [{}]", playerId, e.getMessage(), e);
            throw new GenericException("Unable to add new player with ID [{" + playerId + "}]. Error: [{" +  e.getMessage() + "}]", e);
        }
    }
}
