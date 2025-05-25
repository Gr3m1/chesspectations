package mate.check.chesspectations.service;

import mate.check.chesspectations.TestConstants;
import mate.check.chesspectations.exception.LeaderboardException;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.impl.LeaderboardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceTests {

    @Mock
    private RankingRepository rankingRepository;

    @InjectMocks
    private LeaderboardServiceImpl leaderboardService;

    // get Leaderboard
    @Test
    void testGetLeaderboardSuccess() throws LeaderboardException {
        // given
        List<Leaderboard> mockLeaderboard = TestConstants.getLeaderboard();
        // when
        when(rankingRepository.getLeaderboard()).thenReturn(mockLeaderboard);

        List<Leaderboard> resultList = leaderboardService.getLeaderboard();

        // then
        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(mockLeaderboard.size(), resultList.size());
        Assertions.assertEquals(mockLeaderboard, resultList);
    }

    @Test
    void testGetLeaderboardFailure() {
        // given
        List<Leaderboard> emptyLeaderboard = new ArrayList<>();
        // when
        when(rankingRepository.getLeaderboard()).thenReturn(emptyLeaderboard);

        LeaderboardException exception = Assertions.assertThrows(LeaderboardException.class, () -> {
            leaderboardService.getLeaderboard();
        });

        Assertions.assertEquals("Uh oh! Something went wrong.", exception.getMessage());
    }
//
//    // get player by rank
//    @Test
//    void getPlayerRankSuccess() {
//
//    }
//
//    @Test
//    void testGetPlayerRankFailure() {
//
//    }
//
//    // add match
//    @Test
//    void testAddMatchSuccess() {
//
//    }
//
//    @Test
//    void testAddMatchFailure() {
//
//    }

    // update rankings high winner - no ranking change

    // update rankings draw - lower move up

    // update rankings draw - no move

    // update rankings low winner

    // update rankings low winner adjacent
}
