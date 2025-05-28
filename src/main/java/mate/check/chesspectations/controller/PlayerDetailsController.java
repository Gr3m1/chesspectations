package mate.check.chesspectations.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.service.PlayerDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/players")
@Slf4j
@RequiredArgsConstructor
public class PlayerDetailsController {

    private final PlayerDetailsService playerDetailsService;

    @GetMapping
    public ResponseEntity<List<PlayerDetails>> getAllPlayers() throws Exception {
        log.info("Starting call to get all player details");
        List<PlayerDetails> allPlayers = playerDetailsService.getAllPlayers();
        return ResponseEntity.ok(allPlayers);
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<PlayerRank> getPlayerByName(@PathVariable ("name") String name) throws Exception {
        log.info("Starting call to get details for player [{}]", name);
        PlayerRank playerDetail = playerDetailsService.getPlayerByName(name);
        return ResponseEntity.ok(playerDetail);
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<PlayerRank> getPlayerByEmail(@PathVariable ("email") String email) throws Exception {
        log.info("Starting call to get details for player with email address [{}]", email);
        PlayerRank playerDetail = playerDetailsService.getPlayerByEmail(email);
        return ResponseEntity.ok(playerDetail);
    }

    @PostMapping("/newPlayer")
    public ResponseEntity<PlayerRank> addNewPlayer(@RequestBody @Valid PlayerDetails playerDetails) throws Exception {
        log.info("Starting call to add a new player with name [{}]", playerDetails.getPlayerName());
        PlayerRank newPlayer = playerDetailsService.addNewPlayer(playerDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }

    @PutMapping("/update")
    public ResponseEntity<PlayerDetails> updatePlayerDetail(@RequestBody @Valid PlayerDetails playerDetails) throws Exception {
        log.info("Starting call to update details for player with id [{}]", playerDetails.getId());

        if (playerDetails.getId() == null || playerDetails.getId().isEmpty()) {
            throw new IllegalArgumentException("Player ID is required");
        }
        PlayerDetails updatedPlayer = playerDetailsService.updatePlayer(playerDetails);
        return ResponseEntity.ok(updatedPlayer);
    }

    @DeleteMapping("/removeByName/{name}")
    public ResponseEntity<Void> removePlayerByName(@PathVariable ("name") String name) throws Exception {
        log.info("Starting call to remove details for player with name [{}]", name);
        playerDetailsService.removePlayerByName(name);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeByEmail/{email}")
    public ResponseEntity<Void> removePlayerByEmail(@PathVariable ("email") String email) throws Exception {
        log.info("Starting call to remove details for player with email address [{}]", email);
        playerDetailsService.removePlayerByEmail(email);
        return ResponseEntity.ok().build();
    }
}
