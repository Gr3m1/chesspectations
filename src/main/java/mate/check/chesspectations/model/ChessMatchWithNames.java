package mate.check.chesspectations.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ChessMatchWithNames extends ChessMatch {

    private String ebonyName;
    private String ivoryName;
}
