package mate.check.chesspectations.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.service.LeaderboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/leaderboard")
@Slf4j
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    // get Leaderboard
    @GetMapping
    public ResponseEntity<Leaderboard> getLeaderboard() {
        log.info("Starting call to get all player details");
        Leaderboard leaderboard = leaderboardService.getLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }

    // get player name by rank
    @GetMapping("/getByRank/{rank}")
    public ResponseEntity<String> getPlayerByRank(@PathVariable("rank") int rank) {
        log.info("Starting call to get player name at rank [{}]", rank);
        String playerName = leaderboardService.getPlayerByRank(rank);
        return ResponseEntity.ok(playerName);
    }

    // add match
    @PostMapping("/matches")
    public ResponseEntity<ChessMatch> addChessMatch(@RequestBody @Valid ChessMatch chessMatch) {
        log.info("Starting call to add new chess match");
        ChessMatch addedChessMatch = leaderboardService.addChessMatch(chessMatch);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedChessMatch);
    }
}
