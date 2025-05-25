package mate.check.chesspectations.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "matches", schema = "chessdata")
public class ChessMatch {

    @Id
    private String id;

    @NotBlank(message = "Ebony player is required")
    private String ebonyPlayer;

    @NotNull(message = "Ebony rank is required")
    private int ebonyRank;

    @NotBlank (message = "Ivory player is required")
    private String ivoryPlayer;

    @NotNull (message = "Ivory rank is required")
    private int ivoryRank;

    @NotNull (message = "Match date is required")
    private LocalDate datePlayed;

    @NotBlank (message = "Winning colour or 'Tie' is required")
    private String winner;
}
