package mate.check.chesspectations.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.model.PlayerNameRank;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.service.PlayerDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/players")
@Slf4j
@RequiredArgsConstructor
public class PlayerDetailsController {

    private final PlayerDetailsService playerDetailsService;

    // only admin user can call this
    @GetMapping
    public ResponseEntity<List<PlayerNameRank>> getAllPlayers() throws Exception {
        log.info("Starting call to get all players");
        List<PlayerNameRank> players = playerDetailsService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    // only admin can call this
    @GetMapping("/byId/{id}")
    public ResponseEntity<PlayerRank> getPlayerById(@PathVariable("id") String id) throws Exception {
        log.info("Starting call to get player details for ID [{}]", id);
        PlayerRank playerDetail = playerDetailsService.getPlayerById(id);
        return ResponseEntity.ok(playerDetail);
    }

    // only admin can call this
    @PostMapping("/newPlayer")
    public ResponseEntity<List<Leaderboard>> addNewPlayer(@RequestBody @Valid PlayerDetails playerDetails) throws Exception {
        log.info("Starting call to add a new player with name [{}]", playerDetails.getPlayerName());
        List<Leaderboard> newPlayerAddedList = playerDetailsService.addNewPlayer(playerDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayerAddedList);
    }

    // only admin can call this
    @PutMapping("/update")
    public ResponseEntity<List<Leaderboard>> updatePlayerDetail(@RequestBody @Valid PlayerDetails playerDetails) throws Exception {
        log.info("Starting call to update details for player with id [{}]", playerDetails.getId());

        if (playerDetails.getId() == null || playerDetails.getId().isEmpty()) {
            throw new IllegalArgumentException("Player ID is required");
        }
        List<Leaderboard> updatedPlayer = playerDetailsService.updatePlayer(playerDetails);
        return ResponseEntity.ok(updatedPlayer);
    }

    // only admin can call this
    @DeleteMapping("/removePlayer/{id}")
    public ResponseEntity<List<Leaderboard>> removePlayerById(@PathVariable("id") String id) throws Exception {
        log.info("Starting call to remove player with id [{}]", id);
        List<Leaderboard> removedPlayerList = playerDetailsService.removePlayerById(id);
        return ResponseEntity.ok(removedPlayerList);
    }
}
