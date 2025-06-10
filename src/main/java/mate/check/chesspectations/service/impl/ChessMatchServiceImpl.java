package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.ChessMatchWithNames;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.repository.ChessMatchRepository;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.service.ChessMatchService;
import mate.check.chesspectations.service.LeaderboardService;
import mate.check.chesspectations.service.RankingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChessMatchServiceImpl implements ChessMatchService {

    public final PlayerDetailRepository playerDetailRepository;
    public final ChessMatchRepository chessMatchRepository;
    public final RankingService rankingService;
    public final LeaderboardService leaderboardService;

    @Override
    public List<ChessMatchWithNames> getHistory(String playerId) throws GenericException {
        try {
            return chessMatchRepository.getHistoryById(playerId);

        } catch (Exception e) {
            log.error("Unable to retrieve match history for playerID [{}]. Error: [{}]", playerId, e.getMessage(), e);
            throw new GenericException("No history to show.");
        }
    }

    @Override
    @Transactional
    public List<Leaderboard> addChessMatch(ChessMatch chessMatch) throws GenericException {
        log.info("Adding match between [{}] and [{}]", chessMatch.getEbonyPlayerId(), chessMatch.getIvoryPlayerId());

        PlayerRank ebonyPlayer;
        PlayerRank ivoryPlayer;

        try {
            Optional<PlayerRank> ebonyPlayerDetail = playerDetailRepository.getPlayerById(chessMatch.getEbonyPlayerId());
            Optional<PlayerRank> ivoryPlayerDetail = playerDetailRepository.getPlayerById(chessMatch.getIvoryPlayerId());

            if (ebonyPlayerDetail.isPresent()) {
                ebonyPlayer = ebonyPlayerDetail.get();
                ebonyPlayer.setGamesPlayed(ebonyPlayer.getGamesPlayed() + 1);
                playerDetailRepository.updateGamesPlayed(ebonyPlayer.getId(), ebonyPlayer.getGamesPlayed());
            } else {
                log.error("Unable to find ebony player with ID [{}]", chessMatch.getEbonyPlayerId());
                throw new GenericException("Uh oh! Something went wrong.");
            }

            if (ivoryPlayerDetail.isPresent()) {
                ivoryPlayer = ivoryPlayerDetail.get();
                ivoryPlayer.setGamesPlayed(ivoryPlayer.getGamesPlayed() + 1);
                playerDetailRepository.updateGamesPlayed(ivoryPlayer.getId(), ivoryPlayer.getGamesPlayed());
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
            return leaderboardService.getLeaderboard();

        } catch (Exception e) {
            log.error("Unable to add match. Error: [{}]", e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }
}
