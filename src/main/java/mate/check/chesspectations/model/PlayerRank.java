package mate.check.chesspectations.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerRank extends PlayerDetail {

    private int ranking;
}
