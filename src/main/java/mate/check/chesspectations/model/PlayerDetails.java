package mate.check.chesspectations.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table(name = "player_details", schema = "chessdata")
public class PlayerDetails {

    @Id
    private String id;

    @NotBlank(message = "Player name is required")
    private String playerName;

    @NotBlank(message = "Player email address is required")
    @Email(message = "Please provide a valid email address")
    private String emailAddress;

    @NotNull(message = "Player date of birth is required")
    private LocalDate dateOfBirth;

    private int gamesPlayed = 0;
}
