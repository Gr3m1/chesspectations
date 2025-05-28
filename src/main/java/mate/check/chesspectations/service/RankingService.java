package mate.check.chesspectations.service;

import mate.check.chesspectations.model.ChessMatch;

public interface RankingService {

    void updateRankings(ChessMatch newMatch);

    public void recalculateLeaderboard(String ebonyId, int newEbonyRank, String ivoryId, int newIvoryRank);
}
