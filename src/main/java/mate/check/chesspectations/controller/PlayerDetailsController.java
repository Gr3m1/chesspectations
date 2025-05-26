package mate.check.chesspectations.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.model.PlayerRank;
import mate.check.chesspectations.model.PlayerDetail;
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

    // get all players
    @GetMapping
    public ResponseEntity<List<PlayerDetail>> getAllPlayers() throws Exception {
        log.info("Starting call to get all player details");
        List<PlayerDetail> allPlayers = playerDetailsService.getAllPlayers();
        return ResponseEntity.ok(allPlayers);
    }

    // get player by name
    @GetMapping("/byName/{name}")
    public ResponseEntity<PlayerRank> getPlayerByName(@PathVariable ("name") String name) throws Exception {
        log.info("Starting call to get details for player [{}]", name);
        PlayerRank playerDetail = playerDetailsService.getPlayerByName(name);
        return ResponseEntity.ok(playerDetail);
    }

    // get player by email
    @GetMapping("/byEmail/{email}")
    public ResponseEntity<PlayerRank> getPlayerByEmail(@PathVariable ("email") String email) throws Exception {
        log.info("Starting call to get details for player with email address [{}]", email);
        PlayerRank playerDetail = playerDetailsService.getPlayerByEmail(email);
        return ResponseEntity.ok(playerDetail);
    }

    // add new player
    @PostMapping("/newPlayer")
    public ResponseEntity<PlayerRank> addNewPlayer(@RequestBody @Valid PlayerDetail playerDetail) throws Exception {
        log.info("Starting call to add a new player with name [{}]", playerDetail.getPlayerName());
        PlayerRank newPlayer = playerDetailsService.addNewPlayer(playerDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }

    // update player details
    @PutMapping("/update")
    public ResponseEntity<PlayerDetail> updatePlayerDetail(@RequestBody @Valid PlayerDetail playerDetail) throws Exception {
        log.info("Starting call to update details for player with id [{}]", playerDetail.getId());
        PlayerDetail updatedPlayer = playerDetailsService.updatePlayer(playerDetail);
        return ResponseEntity.ok(updatedPlayer);
    }

    // remove player by name
    @DeleteMapping("/removeByName/{name}")
    public ResponseEntity<Void> removePlayerByName(@PathVariable ("name") String name) throws Exception {
        log.info("Starting call to remove details for player with name [{}]", name);
        playerDetailsService.removePlayerByName(name);
        return ResponseEntity.ok().build();
    }

    // remove player by email
    @DeleteMapping("/removeByEmail/{email}")
    public ResponseEntity<Void> removePlayerByEmail(@PathVariable ("email") String email) throws Exception {
        log.info("Starting call to remove details for player with email address [{}]", email);
        playerDetailsService.removePlayerByEmail(email);
        return ResponseEntity.ok().build();
    }
}
