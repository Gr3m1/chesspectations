package mate.check.chesspectations.service;

import mate.check.chesspectations.TestConstants;
import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.repository.LeaderboardRepository;
import mate.check.chesspectations.service.impl.LeaderboardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceTests {

    @Mock
    private LeaderboardRepository leaderboardRepository;

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

    // get player by rank
    @Test
    void getPlayerRankSuccess() throws GenericException {
        String playerName = "Jenny Queen";
        int rank = 4;
        when(leaderboardRepository.getPlayerNameByRank(rank)).thenReturn(Optional.of(playerName));

        String result = leaderboardService.getPlayerByRank(rank);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(playerName, result);
    }

    @Test
    void testGetPlayerRankFailure() {
        int rank = 4;
        when(leaderboardRepository.getPlayerNameByRank(rank)).thenReturn(Optional.empty());

        GenericException exception = Assertions.assertThrows(GenericException.class, () -> {
            leaderboardService.getPlayerByRank(rank);
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
}
