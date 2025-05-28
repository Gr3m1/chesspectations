package mate.check.chesspectations;

import mate.check.chesspectations.model.Leaderboard;
import mate.check.chesspectations.model.PlayerDetails;
import mate.check.chesspectations.model.PlayerRank;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public abstract class TestConstants {

    public static List<Leaderboard> getLeaderboard() {
        Leaderboard leader1 = new Leaderboard(1, "James Jeffreys", 15);
        Leaderboard leader2 = new Leaderboard(2, "Linda Lowes", 24);
        Leaderboard leader3 = new Leaderboard(3, "Lil Timmy", 5);

        return Arrays.asList(leader1,leader2,leader3);
    }

    public static List<PlayerDetails> getPlayerDetails() {
        PlayerDetails player1 = new PlayerDetails();
        player1.setId("123");
        player1.setPlayerName("James Jeffreys");
        player1.setEmailAddress("jamesj@example.com");
        player1.setDateOfBirth(LocalDate.of(1978, 5, 12));
        player1.setGamesPlayed(15);

        PlayerDetails player2 = new PlayerDetails();
        player2.setId("456");
        player2.setPlayerName("Linda Lowes");
        player2.setEmailAddress("llowes@example.com");
        player2.setDateOfBirth(LocalDate.of(1985, 2, 2));
        player2.setGamesPlayed(24);

        PlayerDetails player3 = new PlayerDetails();
        player3.setId("789");
        player3.setPlayerName("Lil Timmy");
        player3.setEmailAddress("timmydamonster@example.com");
        player3.setDateOfBirth(LocalDate.of(2015, 10, 13));
        player3.setGamesPlayed(5);

        return Arrays.asList(player1,player2,player3);
    }

    public static PlayerRank getPlayerRank() {
        PlayerRank playerRank = new PlayerRank();

        playerRank.setId("123");
        playerRank.setPlayerName("James Jeffreys");
        playerRank.setEmailAddress("jamesj@example.com");
        playerRank.setDateOfBirth(LocalDate.of(1978, 5, 12));
        playerRank.setGamesPlayed(15);
        playerRank.setRanking(1);

        return playerRank;
    }
}
