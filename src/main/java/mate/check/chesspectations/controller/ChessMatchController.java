package mate.check.chesspectations.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.ChessMatchWithNames;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.service.ChessMatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/chessMatch")
@Slf4j
@RequiredArgsConstructor
public class ChessMatchController {

    private final ChessMatchService chessMatchService;

    // no auth, everybody can view match history
    @GetMapping("/history/{playerId}")
    public ResponseEntity<List<ChessMatchWithNames>> getHistory(@PathVariable("playerId") String playerId) throws Exception {
        log.info("Starting call to get match history for playerID [{}]", playerId);
        List<ChessMatchWithNames> matchHistory = chessMatchService.getHistory(playerId);
        return ResponseEntity.ok(matchHistory);
    }

    // only admin can add matches
    @PostMapping("/addMatch")
    public ResponseEntity<List<Leaderboard>> addChessMatch(@RequestBody @Valid ChessMatch chessMatch) throws Exception {
        log.info("Starting call to add new chess match");
        List<Leaderboard> newLeaderboard = chessMatchService.addChessMatch(chessMatch);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLeaderboard);
    }
}
