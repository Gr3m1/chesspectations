package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.PlayerDetail;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.service.LeaderboardService;
import mate.check.chesspectations.service.PlayerDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerDetailsServiceImpl implements PlayerDetailsService {

    private final PlayerDetailRepository playerDetailRepository;
    private final LeaderboardService leaderboardService;

    @Override
    public List<PlayerDetail> getAllPlayers() throws GenericException {
        log.info("About to get all players");

        Iterable<PlayerDetail> iterPlayers = playerDetailRepository.findAll();

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
    @Transactional
    public PlayerRank addNewPlayer(PlayerDetail playerDetail) throws GenericException {
        log.info("About to add new player with name [{}]", playerDetail.getPlayerName());

        try {
            PlayerDetail playerDetail1 = playerDetailRepository.save(playerDetail);
            return leaderboardService.addNewPlayer(playerDetail1.getId());

        } catch (Exception e) {
            log.error("Unable to add new player with name, [{}]. Error: [{}]", playerDetail.getPlayerName(), e.getMessage(), e);
            throw new GenericException("Uh oh! Something went wrong.");
        }
    }

    @Override
    public PlayerDetail updatePlayer(PlayerDetail playerDetail) throws GenericException {
        log.info("About to update player with ID [{}]", playerDetail.getId());

        return null;
    }

    @Override
    public void removePlayerByName(String name) throws GenericException {
        log.info("About to remove player with name [{}]", name);

    }

    @Override
    public void removePlayerByEmail(String email) throws GenericException {
        log.info("About to remove player with email [{}]", email);

    }
}