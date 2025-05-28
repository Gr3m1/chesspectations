package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.LeaderboardService;
import mate.check.chesspectations.service.PlayerDetailsService;
import mate.check.chesspectations.service.RankingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerDetailsServiceImpl implements PlayerDetailsService {

    private final PlayerDetailRepository playerDetailRepository;
    private final RankingRepository rankingRepository;
    private final LeaderboardService leaderboardService;
    private final RankingService rankingService;

    @Override
    public List<PlayerDetails> getAllPlayers() throws GenericException {
        log.info("About to get all players");

        Iterable<PlayerDetails> iterPlayers = playerDetailRepository.findAll();

        if (iterPlayers.iterator().hasNext()) {
            return StreamSupport
                    .stream(iterPlayers.spliterator(), false)
                    .collect(Collectors.toList());
        } else {
            log.error("Unable to get all players");
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public PlayerRank getPlayerByName(String name) throws GenericException {
        log.info("About to retrieve player with name [{}]", name);

        Optional<PlayerRank> player = playerDetailRepository.getPlayerByName(name);

        if (player.isPresent()) {
            return player.get();
        } else {
            log.error("Unable to retrieve player with name [{}]", name);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public PlayerRank getPlayerByEmail(String email) throws GenericException {
        log.info("About to retrieve player with email [{}]", email);

        Optional<PlayerRank> player = playerDetailRepository.getPlayerByEmail(email);

        if (player.isPresent()) {
            return player.get();
        } else {
            log.error("Unable to retrieve player with email [{}]", email);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public PlayerRank addNewPlayer(PlayerDetails playerDetails) throws GenericException {
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
    public PlayerDetails updatePlayer(PlayerDetails updatedPlayerDetails) throws GenericException {
        log.info("About to update player with ID [{}]", updatedPlayerDetails.getId());

        try {
            Optional<PlayerDetails> player = playerDetailRepository.getPlayerById(updatedPlayerDetails.getId());

            if (player.isPresent()) {
                PlayerDetails existingPlayer = player.get();
                existingPlayer.setPlayerName(updatedPlayerDetails.getPlayerName() != null ? updatedPlayerDetails.getPlayerName() : existingPlayer.getPlayerName());
                existingPlayer.setEmailAddress(updatedPlayerDetails.getEmailAddress() != null ? updatedPlayerDetails.getEmailAddress() : existingPlayer.getEmailAddress());
                existingPlayer.setDateOfBirth(updatedPlayerDetails.getDateOfBirth() != null ? updatedPlayerDetails.getDateOfBirth() : existingPlayer.getDateOfBirth());
                existingPlayer.setGamesPlayed(updatedPlayerDetails.getGamesPlayed() != 0 ? updatedPlayerDetails.getGamesPlayed() : existingPlayer.getGamesPlayed());

                return playerDetailRepository.save(existingPlayer);
            } else {
                log.error("Unable to retrieve player with ID [{}]", updatedPlayerDetails.getId());
                throw new GenericException("Uh oh! Something went wrong.");
            }

        } catch (Exception e) {
            log.error("Unable to update player with ID [{}]. Error: [{}]", updatedPlayerDetails.getId(), e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public void removePlayerByName(String name) throws GenericException {
        log.info("About to remove player with name [{}]", name);

        try {
            PlayerDetails playerDetails = getPlayerByName(name);
            String playerId = playerDetails.getId();

            rankingRepository.deleteByPlayerId(playerId);
            rankingService.recalculateLeaderboard(null, 0, null, 0);

            playerDetailRepository.deleteById(playerId);

        } catch (Exception e) {
            log.error("Unable to remove player with name [{}]. Error: [{}]", name, e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public void removePlayerByEmail(String email) throws GenericException {
        log.info("About to remove player with email [{}]", email);

        try {
            PlayerDetails playerDetails = getPlayerByEmail(email);
            String playerId = playerDetails.getId();

            rankingRepository.deleteByPlayerId(playerId);
            rankingService.recalculateLeaderboard(null, 0, null, 0);

            playerDetailRepository.deleteById(playerId);

        } catch (Exception e) {
            log.error("Unable to remove player with email address [{}]. Error: [{}]", email, e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }
}