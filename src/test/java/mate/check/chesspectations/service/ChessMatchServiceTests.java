package mate.check.chesspectations.service;

import mate.check.chesspectations.TestConstants;
import mate.check.chesspectations.enumeration.Winner;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.repository.ChessMatchRepository;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.service.impl.ChessMatchServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class ChessMatchServiceTests {

    @Mock
    PlayerDetailRepository playerDetailRepository;

    @Mock
    ChessMatchRepository chessMatchRepository;

    @Mock
    RankingService rankingService;

    @Mock
    LeaderboardService leaderboardService;

    @InjectMocks
    ChessMatchServiceImpl chessMatchService;

    // get match history
    @Test
    void testGetMatchHistorySuccess() throws GenericException {
        String playerId = "123abc";
        when(chessMatchRepository.getHistoryById(anyString())).thenReturn(TestConstants.getMatchHistory());
    }

    @Test
    void testGetMatchHistoryFailure() {

    }

    // add match
    @Test
    void testAddMatchSuccess() throws GenericException {
        ChessMatch chessMatch = TestConstants.getChessMatch();

        when(playerDetailRepository.getPlayerById(anyString()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank1()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank2()));
        when(leaderboardService.getLeaderboard()).thenReturn(TestConstants.getLeaderboardAfterMatch());

        chessMatchService.addChessMatch(chessMatch);

        verify(playerDetailRepository).updateGamesPlayed("123abc", 16);
        verify(playerDetailRepository).updateGamesPlayed("789ghi", 6);
        verify(chessMatchRepository).saveNewMatch(anyString(), eq("123abc"), anyInt(), eq("789ghi"), anyInt(), any(LocalDate.class), eq(Winner.IVORY));
        verify(rankingService).updateRankings(any(ChessMatch.class));
    }

    @Test
    void testAddMatchFailToGetPlayer() {
        ChessMatch chessMatch = TestConstants.getChessMatch();

        when(playerDetailRepository.getPlayerById(anyString()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank1()))
                .thenReturn(Optional.empty());

        GenericException exception = assertThrows(GenericException.class, () -> {
            chessMatchService.addChessMatch(chessMatch);
        });

        assertEquals("Uh oh! Something went wrong.", exception.getMessage());
        verify(playerDetailRepository, atMostOnce()).updateGamesPlayed(anyString(), anyInt());
        verifyNoInteractions(chessMatchRepository);
        verifyNoInteractions(rankingService);
        verifyNoInteractions(leaderboardService);
    }

    @Test
    void testAddMatchFailToSaveMatch() {
        ChessMatch chessMatch = TestConstants.getChessMatch();

        when(playerDetailRepository.getPlayerById(anyString()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank1()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank2()));

        doThrow(new RuntimeException("Simulated DB failure"))
                .when(chessMatchRepository).saveNewMatch(anyString(), anyString(), anyInt(), anyString(), anyInt(), any(LocalDate.class), any(Winner.class));

        GenericException exception = assertThrows(GenericException.class, () -> {
            chessMatchService.addChessMatch(chessMatch);
        });

        assertEquals("Uh oh! Something went wrong.", exception.getMessage());
        verify(playerDetailRepository).updateGamesPlayed("123abc", 16);
        verify(playerDetailRepository).updateGamesPlayed("789ghi", 6);
        verifyNoInteractions(rankingService);
        verifyNoInteractions(leaderboardService);
    }

    @Test
    void testAddMatchFailToRetrieveLeaderboard() throws GenericException {
        ChessMatch chessMatch = TestConstants.getChessMatch();

        when(playerDetailRepository.getPlayerById(anyString()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank1()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank2()));
        when(leaderboardService.getLeaderboard()).thenThrow(new RuntimeException("Simulated DB failure"));

        GenericException exception = assertThrows(GenericException.class, () -> {
            chessMatchService.addChessMatch(chessMatch);
        });

        assertEquals("Uh oh! Something went wrong.", exception.getMessage());
        verify(playerDetailRepository).updateGamesPlayed("123abc", 16);
        verify(playerDetailRepository).updateGamesPlayed("789ghi", 6);
        verify(chessMatchRepository).saveNewMatch(anyString(), eq("123abc"), anyInt(), eq("789ghi"), anyInt(), any(LocalDate.class), eq(Winner.IVORY));
        verify(rankingService).updateRankings(any(ChessMatch.class));
    }
}
