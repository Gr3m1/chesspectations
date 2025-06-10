package mate.check.chesspectations.service;

import mate.check.chesspectations.TestConstants;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.repository.LeaderboardRepository;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.impl.LeaderboardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceTests {

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @Mock
    private RankingRepository rankingRepository;

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
