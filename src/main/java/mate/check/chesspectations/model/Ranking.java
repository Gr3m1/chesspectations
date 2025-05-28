package mate.check.chesspectations.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "ranking", schema = "chessdata")
public class Ranking {

    @Id
    String id;

    String playerId;
    int ranking;
}
