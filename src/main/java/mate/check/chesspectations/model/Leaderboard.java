package mate.check.chesspectations.model;

import lombok.Data;

@Data
public class Leaderboard {

    private String playerName;
    private int gamesPlayed;
    private int ranking;
}
