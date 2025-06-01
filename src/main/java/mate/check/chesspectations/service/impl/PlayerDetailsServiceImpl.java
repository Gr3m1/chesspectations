package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.model.PlayerNameRank;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.LeaderboardService;
import mate.check.chesspectations.service.PlayerDetailsService;
import mate.check.chesspectations.service.RankingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerDetailsServiceImpl implements PlayerDetailsService {

    private final PlayerDetailRepository playerDetailRepository;
    private final RankingRepository rankingRepository;
    private final LeaderboardService leaderboardService;
    private final RankingService rankingService;

    @Override
    public List<PlayerNameRank> getAllPlayers() throws GenericException {
        log.info("About to retrieve all players");

        List<PlayerNameRank> players = playerDetailRepository.getAllPlayers();

        if (!players.isEmpty()) {
            return players;
        } else {
            log.error("Unable to retrieve all players");
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public PlayerRank getPlayerById(String id) throws GenericException {
        log.info("About to retrieve player with ID [{}]", id);

        Optional<PlayerRank> player = playerDetailRepository.getPlayerById(id);

        if (player.isPresent()) {
            return player.get();
        } else {
            log.error("Unable to retrieve player with ID [{}]", id);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    @Transactional
    public List<Leaderboard> addNewPlayer(PlayerDetails playerDetails) throws GenericException {
        log.info("About to add new player with name [{}]", playerDetails.getPlayerName());

        try {
            String newPlayerId = UUID.randomUUID().toString();
            playerDetailRepository.saveNewPlayer(newPlayerId, playerDetails.getPlayerName(), playerDetails.getEmailAddress(), playerDetails.getDateOfBirth(), playerDetails.getGamesPlayed());
            return leaderboardService.addNewPlayer(newPlayerId);

        } catch (Exception e) {
            log.error("Unable to add new player with name, [{}]. Error: [{}]", playerDetails.getPlayerName(), e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public List<Leaderboard> updatePlayer(PlayerDetails updatedPlayerDetails) throws GenericException {
        log.info("About to update player with ID [{}]", updatedPlayerDetails.getId());

        try {
            PlayerRank existingPlayer = getPlayerById(updatedPlayerDetails.getId());
            existingPlayer.setPlayerName(updatedPlayerDetails.getPlayerName() != null ? updatedPlayerDetails.getPlayerName() : existingPlayer.getPlayerName());
            existingPlayer.setEmailAddress(updatedPlayerDetails.getEmailAddress() != null ? updatedPlayerDetails.getEmailAddress() : existingPlayer.getEmailAddress());
            existingPlayer.setDateOfBirth(updatedPlayerDetails.getDateOfBirth() != null ? updatedPlayerDetails.getDateOfBirth() : existingPlayer.getDateOfBirth());

            playerDetailRepository.updatePlayerDetails(existingPlayer.getId(), existingPlayer.getPlayerName(), existingPlayer.getEmailAddress(), existingPlayer.getDateOfBirth());
            return leaderboardService.getLeaderboard();

        } catch (Exception e) {
            log.error("Unable to update player with ID [{}]. Error: [{}]", updatedPlayerDetails.getId(), e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    @Transactional
    public List<Leaderboard> removePlayerById(String id) throws GenericException {
        log.info("About to remove player with ID [{}]", id);

        try {
            rankingRepository.deleteByPlayerId(id);
            rankingService.recalculateLeaderboard(null, 0, null, 0);

            playerDetailRepository.deleteById(id);

            return leaderboardService.getLeaderboard();

        } catch (Exception e) {
            log.error("Unable to remove player with ID [{}]. Error: [{}]", id, e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }
}