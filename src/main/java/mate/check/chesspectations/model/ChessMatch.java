package mate.check.chesspectations.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mate.check.chesspectations.enumeration.Winner;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table(name = "matches", schema = "chessdata")
public class ChessMatch {

    @Id
    private String id;

    @NotBlank(message = "Ebony player ID is required")
    private String ebonyPlayerId;

    @NotNull(message = "Ebony rank is required")
    private int ebonyRank;

    @NotBlank(message = "Ivory player ID is required")
    private String ivoryPlayerId;

    @NotNull(message = "Ivory rank is required")
    private int ivoryRank;

    @NotNull(message = "Match date is required")
    private LocalDate datePlayed;

    @NotNull(message = "Winning colour or 'Tie' is required")
    private Winner winner;
}
