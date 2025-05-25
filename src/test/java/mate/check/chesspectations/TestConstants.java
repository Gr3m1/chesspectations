package mate.check.chesspectations;

import mate.check.chesspectations.model.Leaderboard;

import java.util.Arrays;
import java.util.List;

public abstract class TestConstants {

    public static List<Leaderboard> getLeaderboard() {
        Leaderboard leader1 = new Leaderboard(1, "James Jeffreys", 15);
        Leaderboard leader2 = new Leaderboard(2, "Linda Lowes", 24);
        Leaderboard leader3 = new Leaderboard(3, "Lil Timmy", 5);

        return Arrays.asList(leader1,leader2,leader3);
    }
}
