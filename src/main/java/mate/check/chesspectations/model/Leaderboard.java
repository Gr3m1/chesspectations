package mate.check.chesspectations.model;

import lombok.Data;

@Data
public class Leaderboard {

    private int ranking;
    private String playerId;
    private String playerName;
    private Integer gamesPlayed;

    public Leaderboard(int ranking, String playerId, String playerName, Integer gamesPlayed) {
        this.ranking = ranking;
        this.playerId = playerId;
        this.playerName = playerName;
        this.gamesPlayed = gamesPlayed;
    }
}
