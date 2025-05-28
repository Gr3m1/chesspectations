package mate.check.chesspectations.model;

import lombok.Data;

@Data
public class Leaderboard {

    private int ranking;
    private String playerName;
    private Integer gamesPlayed;

    public Leaderboard(int ranking, String playerName, Integer gamesPlayed) {
        this.ranking = ranking;
        this.playerName = playerName;
        this.gamesPlayed = gamesPlayed;
    }
}
