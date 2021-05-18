package wang.armeria.symbol;

import wang.armeria.common.Label;
import wang.armeria.whkas.IdentifierTable;

public class S_StatementIf extends S_AnyStatement {

    public S_StatementIf(IdentifierTable identifierTable) {
        super(identifierTable);
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENT_IF;
    }

}
