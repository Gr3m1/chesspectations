package mate.check.chesspectations.service;

import mate.check.chesspectations.TestConstants;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.PlayerDetail;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.service.impl.PlayerDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerDetailsTests {

    @Mock
    private PlayerDetailRepository playerDetailRepository;

    @InjectMocks
    private PlayerDetailsServiceImpl playerDetailsService;

    // get all players
    @Test
    void testGetAllPlayersSuccess() throws GenericException {
        List<PlayerDetail> playerList = TestConstants.getPlayerDetails();
        when(playerDetailRepository.findAll()).thenReturn(playerList);

        List<PlayerDetail> resultList = playerDetailsService.getAllPlayers();

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(playerList, resultList);
    }

    @Test
    void testGetAllPlayersFailure() {
        List<PlayerDetail> emptyPlayerList = new ArrayList<>();
        when(playerDetailRepository.findAll()).thenReturn(emptyPlayerList);

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.getAllPlayers();
        });

        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    // get player by name
    @Test
    void testGetPlayerNameSuccess() throws GenericException {
        String name = "James Jeffreys";
        PlayerRank playerRank = TestConstants.getPlayerRank();
        when(playerDetailRepository.getPlayerByName(anyString())).thenReturn(Optional.of(playerRank));

        PlayerRank result = playerDetailsService.getPlayerByName(name);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(playerRank.getPlayerName(), result.getPlayerName());
        Assertions.assertEquals(playerRank.getGamesPlayed(), result.getGamesPlayed());
        Assertions.assertEquals(playerRank, result);
    }

    @Test
    void testGetPlayerNameFailure() {
        String name = "Jameson Jeffreys";
        when(playerDetailRepository.getPlayerByName(anyString())).thenReturn(Optional.empty());

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.getPlayerByName(name);
        });

        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    // get player by email
    @Test
    void testGetPlayerEmailSuccess() throws GenericException {
        String email = "jamesj@example.com";
        PlayerRank playerRank = TestConstants.getPlayerRank();
        when(playerDetailRepository.getPlayerByEmail(email)).thenReturn(Optional.of(playerRank));

        PlayerRank result = playerDetailsService.getPlayerByEmail(email);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(playerRank.getPlayerName(), result.getPlayerName());
        Assertions.assertEquals(playerRank.getGamesPlayed(), result.getGamesPlayed());
        Assertions.assertEquals(playerRank, result);
    }

    @Test
    void testGetPlayerEmailFailure() {
        String email = "jamesj@example.com";
        when(playerDetailRepository.getPlayerByEmail(email)).thenReturn(Optional.empty());

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            playerDetailsService.getPlayerByEmail(email);
        });

        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

//    // add new player
//    @Test
//    void testAddNewPlayerSuccess() {
//
//    }
//
//    @Test
//    void testAddNewPlayerFailure() {
//
//    }
//
//    // update player details
//    @Test
//    void testUpdatePlayerSuccess() {
//
//    }
//
//    @Test
//    void testUpdatePlayerFailure() {
//
//    }
//
//    // remove player
//    @Test
//    void testRemovePlayerSuccess() {
//
//    }
//
//    @Test
//    void testRemovePlayerFailure() {
//
//    }
}
