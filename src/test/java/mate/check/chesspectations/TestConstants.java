package mate.check.chesspectations;

import mate.check.chesspectations.enumeration.Winner;
import mate.check.chesspectations.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class TestConstants {

    public static List<Leaderboard> getLeaderboard() {
        Leaderboard leader1 = new Leaderboard(1, "123abc", "James Jeffreys", 15);
        Leaderboard leader2 = new Leaderboard(2, "456def", "Linda Lowes", 24);
        Leaderboard leader3 = new Leaderboard(3, "789ghi", "Lil Timmy", 5);

        List<Leaderboard> leaderboard = new ArrayList<>();
        leaderboard.add(leader1);
        leaderboard.add(leader2);
        leaderboard.add(leader3);

        return leaderboard;
    }

    public static List<Leaderboard> getUpdatedLeaderboard() {
        List<Leaderboard> leaderboard = getLeaderboard();

        Leaderboard newPlayer = new Leaderboard(4, "159zdy", "John Jones", 0);
        leaderboard.add(newPlayer);

        return leaderboard;
    }

    public static List<Leaderboard> getReducedLeaderboard() {
        List<Leaderboard> leaderboard = getLeaderboard();
        leaderboard.remove(leaderboard.get(1));

        return leaderboard;
    }

    public static List<Leaderboard> getEditedLeaderboard() {
        List<Leaderboard> leaderboard = getLeaderboard();
        PlayerRank updatedPlayer = getUpdatedPlayer();
        int rankIndex = updatedPlayer.getRanking() - 1;

        leaderboard.get(rankIndex).setPlayerName(updatedPlayer.getPlayerName());
        return leaderboard;
    }

    public static List<Leaderboard> getLeaderboardAfterMatch() {
        Leaderboard leader1 = new Leaderboard(1, "123abc", "James Jeffreys", 16);
        Leaderboard leader3 = new Leaderboard(2, "789ghi", "Lil Timmy", 6);
        Leaderboard leader2 = new Leaderboard(3, "456def", "Linda Lowes", 24);

        List<Leaderboard> leaderboard = new ArrayList<>();
        leaderboard.add(leader1);
        leaderboard.add(leader2);
        leaderboard.add(leader3);

        return leaderboard;
    }

    public static PlayerRank getPlayerRank() {
        PlayerRank playerRank = new PlayerRank();

        playerRank.setId("123abc");
        playerRank.setPlayerName("James Jeffreys");
        playerRank.setEmailAddress("jamesj@example.com");
        playerRank.setDateOfBirth(LocalDate.of(1978, 5, 12));
        playerRank.setGamesPlayed(15);
        playerRank.setRanking(1);

        return playerRank;
    }

    public static PlayerDetails getPlayer() {
        PlayerDetails playerDetails = new PlayerDetails();
        playerDetails.setPlayerName("John Jones");
        playerDetails.setEmailAddress("jjones@example.com");
        playerDetails.setDateOfBirth(LocalDate.of(1990, 1, 5));

        return playerDetails;
    }

    public static PlayerRank getUpdatedPlayer() {
        PlayerRank playerRank = new PlayerRank();

        playerRank.setId("123abc");
        playerRank.setPlayerName("Jameson Jeffreys");
        playerRank.setEmailAddress("jjeffreys@example.com");
        playerRank.setDateOfBirth(LocalDate.of(1978, 5, 12));
        playerRank.setGamesPlayed(15);
        playerRank.setRanking(1);

        return playerRank;
    }

    public static ChessMatch getChessMatch() {
        ChessMatch chessMatch = new ChessMatch();

        chessMatch.setEbonyPlayerId("123abc");
        chessMatch.setEbonyRank(1);
        chessMatch.setIvoryPlayerId("789ghi");
        chessMatch.setIvoryRank(3);
        chessMatch.setDatePlayed(LocalDate.now());
        chessMatch.setWinner(Winner.IVORY);

        return chessMatch;
    }

    public static PlayerRank getPlayerRank1() {
        PlayerRank playerRank = new PlayerRank();

        playerRank.setId("123abc");
        playerRank.setPlayerName("James Jeffreys");
        playerRank.setEmailAddress("jamesj@example.com");
        playerRank.setDateOfBirth(LocalDate.of(1978, 5, 12));
        playerRank.setGamesPlayed(15);
        playerRank.setRanking(1);

        return playerRank;
    }

    public static PlayerRank getPlayerRank2() {
        PlayerRank playerRank = new PlayerRank();

        playerRank.setId("789ghi");
        playerRank.setPlayerName("Lil Timmy");
        playerRank.setEmailAddress("timmy_the_great@example.com");
        playerRank.setDateOfBirth(LocalDate.of(2015, 11, 15));
        playerRank.setGamesPlayed(5);
        playerRank.setRanking(3);

        return playerRank;
    }

    public static List<PlayerNameRank> getAllPlayers() {
        List<PlayerNameRank> players = new ArrayList<>();

        PlayerNameRank player1 = new PlayerNameRank();
        player1.setPlayerId("123abc");
        player1.setPlayerName("James Jeffreys");
        player1.setRanking(1);
        players.add(player1);

        PlayerNameRank player2 = new PlayerNameRank();
        player2.setPlayerId("456def");
        player2.setPlayerName("Linda Lowes");
        player2.setRanking(2);
        players.add(player2);

        PlayerNameRank player3 = new PlayerNameRank();
        player3.setPlayerId("789ghi");
        player3.setPlayerName("Lil Timmy");
        player3.setRanking(3);
        players.add(player3);

        return players;
    }
}
