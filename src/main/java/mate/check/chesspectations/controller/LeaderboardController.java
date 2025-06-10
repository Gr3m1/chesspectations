package mate.check.chesspectations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.service.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/leaderboard")
@Slf4j
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    // no auth, everybody can see
    @GetMapping
    public ResponseEntity<List<Leaderboard>> getLeaderboard() throws Exception {
        log.info("Starting call to get all player details");
        List<Leaderboard> leaderboard = leaderboardService.getLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }
}
