package mate.check.chesspectations.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class PlayerRank extends PlayerDetail{

    private int ranking;
}
