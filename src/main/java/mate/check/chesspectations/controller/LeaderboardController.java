package mate.check.chesspectations.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.service.LeaderboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/leaderboard")
@Slf4j
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping
    public ResponseEntity<List<Leaderboard>> getLeaderboard() throws Exception {
        log.info("Starting call to get all player details");
        List<Leaderboard> leaderboard = leaderboardService.getLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }

    @GetMapping("/getByRank/{rank}")
    public ResponseEntity<String> getPlayerByRank(@PathVariable("rank") int rank) throws Exception {
        log.info("Starting call to get player name at rank [{}]", rank);

        if (rank <= 0) {
            return ResponseEntity.badRequest().body("Rank must be a positive number");
        }

        String playerName = leaderboardService.getPlayerByRank(rank);
        return ResponseEntity.ok(playerName);
    }

    @PostMapping("/matches")
    public ResponseEntity<List<Leaderboard>> addChessMatch(@RequestBody @Valid ChessMatch chessMatch) throws Exception {
        log.info("Starting call to add new chess match");
        List<Leaderboard> newLeaderboard = leaderboardService.addChessMatch(chessMatch);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLeaderboard);
    }
}
