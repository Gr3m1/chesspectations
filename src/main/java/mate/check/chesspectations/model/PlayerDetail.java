package mate.check.chesspectations.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "personal_details", schema = "chessdata")
public class PlayerDetail {

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
