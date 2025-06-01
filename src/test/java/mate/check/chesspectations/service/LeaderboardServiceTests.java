package mate.check.chesspectations.service;

import mate.check.chesspectations.TestConstants;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.repository.ChessMatchRepository;
import mate.check.chesspectations.repository.LeaderboardRepository;
import mate.check.chesspectations.repository.PlayerDetailRepository;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.impl.LeaderboardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(mockLeaderboard.size(), resultList.size());
        Assertions.assertEquals(mockLeaderboard, resultList);
    }

    @Test
    void testGetLeaderboardFailure() {
        List<Leaderboard> emptyLeaderboard = new ArrayList<>();
        when(leaderboardRepository.getLeaderboard()).thenReturn(emptyLeaderboard);

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            leaderboardService.getLeaderboard();
        });

        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }

    // add match
//    @Test
//    void testAddMatchSuccess() {
//        // test save successful
//        // test ranking update successful
//    }
//
//    @Test
//    void testAddMatchFailure() {
//        // test save failure
//        // test ranking update failure...
//    }

    // update rankings high winner - no ranking change

    // update rankings draw - lower move up

    // update rankings draw - no move

    // update rankings low winner

    // update rankings low winner adjacent

    // add new player
    @Test
    void testAddNewPlayerSuccess() throws GenericException {
        String id = "159zdy";
        List<Leaderboard> initialMockLeaderboard = TestConstants.getLeaderboard();
        List<Leaderboard> expectedLeaderboard = TestConstants.getUpdatedLeaderboard();

        when(rankingRepository.getMaxRank()).thenReturn(3);
        when(leaderboardRepository.getLeaderboard()).thenReturn(expectedLeaderboard);

        List<Leaderboard> resultList = leaderboardService.addNewPlayer(id);

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(initialMockLeaderboard.size() + 1, resultList.size());
        Assertions.assertEquals(expectedLeaderboard.size(), resultList.size());
        Assertions.assertEquals(expectedLeaderboard, resultList);
    }

    @Test
    void testAddNewPlayerFailure() {
        String id = "519zdy";

        when(rankingRepository.getMaxRank()).thenReturn(3);
        doThrow(new RuntimeException("Simulated DB failure")).when(rankingRepository).saveNewRanking(anyString(), anyString(), anyInt());

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            leaderboardService.addNewPlayer(id);
        });

        Assertions.assertTrue(exception.getMessage().contains("Unable to add new player with ID [{" + id + "}]"));
    }
}
