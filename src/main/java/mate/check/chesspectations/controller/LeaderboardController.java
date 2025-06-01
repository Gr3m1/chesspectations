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

@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping("/matches")
    public ResponseEntity<List<Leaderboard>> addChessMatch(@RequestBody @Valid ChessMatch chessMatch) throws Exception {
        log.info("Starting call to add new chess match");
        List<Leaderboard> newLeaderboard = leaderboardService.addChessMatch(chessMatch);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLeaderboard);
    }
}
