package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.model.PlayerDetail;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.service.PlayerDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerDetailsServiceImpl implements PlayerDetailsService {

    private final PlayerDetailRepository playerDetailRepository;

    @Override
    public List<PlayerDetail> getAllPlayers() {
        return List.of();
    }

    @Override
    public PlayerRank getPlayerByName(String name) {
        return null;
    }

    @Override
    public PlayerRank getPlayerByEmail(String name) {
        return null;
    }

    @Override
    public PlayerRank addNewPlayer(PlayerDetail playerDetail) {
        return null;
    }

    @Override
    public PlayerDetail updatePlayer(PlayerDetail playerDetail) {
        return null;
    }

    @Override
    public void removePlayerByName(String name) {

    }

    @Override
    public void removePlayerByEmail(String email) {

    }
}