package mate.check.chesspectations.service;

import mate.check.chesspectations.TestConstants;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.impl.PlayerDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerDetailsTests {

    @Mock
    private PlayerDetailRepository playerDetailRepository;

    @Mock
    private RankingRepository rankingRepository;

    @Mock
    private LeaderboardService leaderboardService;

    @Mock
    private RankingService rankingService;

    @InjectMocks
    private PlayerDetailsServiceImpl playerDetailsService;

    // get player by id
    @Test
    void testGetPlayerByIdSuccess() throws GenericException {
        String id = "123abc";
        PlayerRank playerRank = TestConstants.getPlayerRank();
        when(playerDetailRepository.getPlayerById(anyString())).thenReturn(Optional.of(playerRank));

        PlayerRank result = playerDetailsService.getPlayerById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(playerRank.getPlayerName(), result.getPlayerName());
        Assertions.assertEquals(playerRank.getGamesPlayed(), result.getGamesPlayed());
        Assertions.assertEquals(playerRank, result);
    }

    @Test
    void testGetPlayerByIdFailure() {
        String id = "123";
        when(playerDetailRepository.getPlayerById(anyString())).thenReturn(Optional.empty());

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.getPlayerById(id);
        });

        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    // add new player
    @Test
    void testAddNewPlayerSuccess() throws GenericException {
        PlayerDetails newPlayer = TestConstants.getPlayer();
        List<Leaderboard> newLeaderboard = TestConstants.getUpdatedLeaderboard();

        when(leaderboardService.addNewPlayer(anyString())).thenReturn(newLeaderboard);

        List<Leaderboard> result = playerDetailsService.addNewPlayer(newPlayer);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(newPlayer.getPlayerName(), result.get(result.size() - 1).getPlayerName());
        Assertions.assertEquals(0, result.get(result.size() - 1).getGamesPlayed());
    }

    @Test
    void testAddNewPlayerFailure() {
        PlayerDetails newPlayer = TestConstants.getPlayer();

        doThrow(new RuntimeException("Simulated DB failure"))
                .when(playerDetailRepository).saveNewPlayer(anyString(), eq(newPlayer.getPlayerName()), eq(newPlayer.getEmailAddress()), eq(newPlayer.getDateOfBirth()), eq(0));

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.addNewPlayer(newPlayer);
        });

        verifyNoInteractions(leaderboardService);
        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    // update player details
    @Test
    void testUpdatePlayerSuccess() throws GenericException {
        PlayerRank existingPlayer = TestConstants.getPlayerRank();
        PlayerRank updatedPlayer = TestConstants.getUpdatedPlayer();

        when(playerDetailRepository.getPlayerById(anyString())).thenReturn(Optional.of(TestConstants.getPlayerRank()));
        when(playerDetailRepository.save(any(PlayerRank.class))).thenReturn(updatedPlayer);

        PlayerDetails result = playerDetailsService.updatePlayer(updatedPlayer);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedPlayer.getId(), result.getId());
        Assertions.assertEquals(updatedPlayer.getPlayerName(), result.getPlayerName());
        Assertions.assertEquals(existingPlayer.getPlayerName(), updatedPlayer.getPlayerName());
        Assertions.assertEquals(updatedPlayer.getGamesPlayed(), result.getGamesPlayed());
        Assertions.assertEquals(existingPlayer.getGamesPlayed(), updatedPlayer.getGamesPlayed());

        Assertions.assertNotEquals(existingPlayer.getEmailAddress(), result.getEmailAddress());
    }

    @Test
    void testUpdatePlayerFindFails() {
        PlayerRank updatedPlayer = TestConstants.getUpdatedPlayer();

        when(playerDetailRepository.getPlayerById(anyString())).thenThrow(new RuntimeException("Simulated DB failure"));

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.updatePlayer(updatedPlayer);
        });

        verify(playerDetailRepository, never()).save(any(PlayerRank.class));
        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    @Test
    void testUpdatePlayerSaveFails() {
        PlayerRank updatedPlayer = TestConstants.getUpdatedPlayer();

        when(playerDetailRepository.getPlayerById(anyString())).thenReturn(Optional.of(updatedPlayer));
        doThrow(new RuntimeException("Simulated DB failure")).when(playerDetailRepository).save(any(PlayerRank.class));

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.updatePlayer(updatedPlayer);
        });

        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    // remove player
    @Test
    void testRemovePlayerSuccess() throws GenericException {
        String id = "456def";
        List<Leaderboard> newLeaderboard = TestConstants.getReducedLeaderboard();

        when(leaderboardService.getLeaderboard()).thenReturn(newLeaderboard);

        List<Leaderboard> result = playerDetailsService.removePlayerById(id);

        boolean containsId = result.stream()
                .noneMatch(item -> "456def".equals(item.getPlayerId()));

        Assertions.assertTrue(containsId);
    }

    @Test
    void testRemovePlayerRankingDeleteFails() {
        String id = "456def";

        doThrow(new RuntimeException("Simulated DB failure")).when(rankingRepository).deleteByPlayerId(id);

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.removePlayerById(id);
        });

        verifyNoInteractions(playerDetailRepository);
        verifyNoInteractions(leaderboardService);
        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    @Test
    void testRemovePlayerPlayerDeleteFails() {
        String id = "456def";

        doThrow(new RuntimeException("Simulated DB failure")).when(playerDetailRepository).deleteById(id);

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.removePlayerById(id);
        });

        verifyNoInteractions(leaderboardService);
        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    @Test
    void testRemovePlayerLeaderboardGetFails() throws GenericException {
        String id = "456def";

        when(leaderboardService.getLeaderboard()).thenThrow(new GenericException("Simulated get failure"));

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.removePlayerById(id);
        });

        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }
}
