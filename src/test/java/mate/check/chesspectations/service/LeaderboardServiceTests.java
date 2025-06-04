package mate.check.chesspectations.service;

import mate.check.chesspectations.TestConstants;
import mate.check.chesspectations.enumeration.Winner;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.repository.ChessMatchRepository;
import mate.check.chesspectations.repository.LeaderboardRepository;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.impl.LeaderboardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceTests {

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @Mock
    private ChessMatchRepository chessMatchRepository;

    @Mock
    private RankingRepository rankingRepository;

    @Mock
    private PlayerDetailRepository playerDetailRepository;

    @Mock
    private RankingService rankingService;

    @InjectMocks
    private LeaderboardServiceImpl leaderboardService;

    // get Leaderboard
    @Test
    void testGetLeaderboardSuccess() throws GenericException {
        List<Leaderboard> mockLeaderboard = TestConstants.getLeaderboard();
        when(leaderboardRepository.getLeaderboard()).thenReturn(mockLeaderboard);

        List<Leaderboard> resultList = leaderboardService.getLeaderboard();

        assertNotNull(resultList);
        assertEquals(mockLeaderboard.size(), resultList.size());
        assertEquals(mockLeaderboard, resultList);
    }

    @Test
    void testGetLeaderboardFailure() {
        List<Leaderboard> emptyLeaderboard = new ArrayList<>();
        when(leaderboardRepository.getLeaderboard()).thenReturn(emptyLeaderboard);

        GenericException exception = assertThrows(GenericException.class, () -> {
            leaderboardService.getLeaderboard();
        });

        assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    // add match
    @Test
    void testAddMatchSuccess() throws GenericException {
        ChessMatch chessMatch = TestConstants.getChessMatch();

        when(playerDetailRepository.getPlayerById(anyString()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank1()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank2()));
        when(leaderboardRepository.getLeaderboard()).thenReturn(TestConstants.getLeaderboardAfterMatch());

        leaderboardService.addChessMatch(chessMatch);

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
            leaderboardService.addChessMatch(chessMatch);
        });

        assertEquals("Uh oh! Something went wrong.", exception.getMessage());
        verify(playerDetailRepository, atMostOnce()).updateGamesPlayed(anyString(), anyInt());
        verifyNoInteractions(chessMatchRepository);
        verifyNoInteractions(rankingService);
        verifyNoInteractions(leaderboardRepository);
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
            leaderboardService.addChessMatch(chessMatch);
        });

        assertEquals("Uh oh! Something went wrong.", exception.getMessage());
        verify(playerDetailRepository).updateGamesPlayed("123abc", 16);
        verify(playerDetailRepository).updateGamesPlayed("789ghi", 6);
        verifyNoInteractions(rankingService);
        verifyNoInteractions(leaderboardRepository);
    }

    @Test
    void testAddMatchFailToRetrieveLeaderboard() {
        ChessMatch chessMatch = TestConstants.getChessMatch();

        when(playerDetailRepository.getPlayerById(anyString()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank1()))
                .thenReturn(Optional.of(TestConstants.getPlayerRank2()));
        when(leaderboardRepository.getLeaderboard()).thenThrow(new RuntimeException("Simulated DB failure"));

        GenericException exception = assertThrows(GenericException.class, () -> {
            leaderboardService.addChessMatch(chessMatch);
        });

        assertEquals("Uh oh! Something went wrong.", exception.getMessage());
        verify(playerDetailRepository).updateGamesPlayed("123abc", 16);
        verify(playerDetailRepository).updateGamesPlayed("789ghi", 6);
        verify(chessMatchRepository).saveNewMatch(anyString(), eq("123abc"), anyInt(), eq("789ghi"), anyInt(), any(LocalDate.class), eq(Winner.IVORY));
        verify(rankingService).updateRankings(any(ChessMatch.class));
    }

    // add new player
    @Test
    void testAddNewPlayerSuccess() throws GenericException {
        String id = "159zdy";
        List<Leaderboard> initialMockLeaderboard = TestConstants.getLeaderboard();
        List<Leaderboard> expectedLeaderboard = TestConstants.getUpdatedLeaderboard();

        when(rankingRepository.getMaxRank()).thenReturn(3);
        when(leaderboardRepository.getLeaderboard()).thenReturn(expectedLeaderboard);

        List<Leaderboard> resultList = leaderboardService.addNewPlayer(id);

        assertNotNull(resultList);
        assertEquals(initialMockLeaderboard.size() + 1, resultList.size());
        assertEquals(expectedLeaderboard.size(), resultList.size());
        assertEquals(expectedLeaderboard, resultList);
    }

    @Test
    void testAddNewPlayerFailure() {
        String id = "519zdy";

        when(rankingRepository.getMaxRank()).thenReturn(3);
        doThrow(new RuntimeException("Simulated DB failure")).when(rankingRepository).saveNewRanking(anyString(), anyString(), anyInt());

        GenericException exception = assertThrows(GenericException.class, () -> {
            leaderboardService.addNewPlayer(id);
        });

        assertTrue(exception.getMessage().contains("Unable to add new player with ID [{" + id + "}]"));
    }
}
