package mate.check.chesspectations.service;

import mate.check.chesspectations.exception.GenericException;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.ChessMatchWithNames;
import mate.check.chesspectations.model.Leaderboard;

import java.util.List;

public interface ChessMatchService {

    List<ChessMatchWithNames> getHistory(String playerId) throws GenericException;

    List<Leaderboard> addChessMatch(ChessMatch chessMatch) throws GenericException;
}
