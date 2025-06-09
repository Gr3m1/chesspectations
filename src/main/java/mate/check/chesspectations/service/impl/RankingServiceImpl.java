package mate.check.chesspectations.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mate.check.chesspectations.enumeration.Winner;
import mate.check.chesspectations.model.ChessMatch;
import mate.check.chesspectations.model.Ranking;
import mate.check.chesspectations.repository.RankingRepository;
import mate.check.chesspectations.service.RankingService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;

    @Override
    public void updateRankings(ChessMatch newMatch) {
        int ebonyRank = newMatch.getEbonyRank();
        System.out.println("ebonyRank " + ebonyRank);
        int ivoryRank = newMatch.getIvoryRank();
        System.out.println("ivoryRank " + ivoryRank);
        Winner winner = newMatch.getWinner();
        System.out.println("Winner " + winner);

        int newEbonyRank = ebonyRank;
        int newIvoryRank = ivoryRank;

        switch (winner) {
            case EBONY -> {
                if (ivoryRank < ebonyRank) {
                    // lower rank moves half difference up
                    // lower = lower + (higher - lower)/2
                    int move = (ebonyRank - ivoryRank) / 2;
                    newEbonyRank = ebonyRank - move;

                    // higher rank loses 1 rank
                    newIvoryRank = ivoryRank + 1;
                }
            }
            case IVORY -> {
                if (ebonyRank < ivoryRank) {
                    // lower rank moves half difference up
                    // lower = lower + (higher - lower)/2
                    int move = (ivoryRank - ebonyRank) / 2;
                    newIvoryRank = ivoryRank - move;

                    // higher rank loses 1 rank
                    newEbonyRank = ebonyRank + 1;
                }
            }
            case DRAW -> {
                boolean areAdjacent = Math.abs(ebonyRank - ivoryRank) == 1;
                if (!areAdjacent) {
                    if (ivoryRank > ebonyRank) {
                        newIvoryRank = ivoryRank - 1;
                    } else {
                        newEbonyRank = ebonyRank - 1;
                    }
                }
            }
        }

        recalculateLeaderboard(newMatch.getEbonyPlayerId(), newEbonyRank, newMatch.getIvoryPlayerId(), newIvoryRank);
    }

    public void recalculateLeaderboard(String ebonyId, int newEbonyRank, String ivoryId, int newIvoryRank) {
        log.info("Recalculating leaderboard");
        List<Ranking> rankings = rankingRepository.findAllByOrderByRankingAsc();
        rankings.sort(Comparator.comparing(Ranking::getRanking));

        if (ebonyId != null && ivoryId != null) {
            Ranking ebony = new Ranking();
            Ranking ivory = new Ranking();
            for (Ranking rank : rankings) {
                if (rank.getPlayerId().equals(ebonyId)) {
                    ebony = rank;
                } else if (rank.getPlayerId().equals(ivoryId)) {
                    ivory = rank;
                }
            }

            rankings.removeIf(r -> r.getPlayerId().equals(ebonyId));
            rankings.removeIf(r -> r.getPlayerId().equals(ivoryId));

            // insert highest rank first
            if (newEbonyRank < newIvoryRank) {
                rankings.add(newEbonyRank - 1, ebony);
                rankings.add(newIvoryRank - 1, ivory);
            } else {
                rankings.add(newIvoryRank - 1, ivory);
                rankings.add(newEbonyRank - 1, ebony);
            }
        }

        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setRanking(i + 1);
        }

        rankingRepository.saveAll(rankings);
    }
}
