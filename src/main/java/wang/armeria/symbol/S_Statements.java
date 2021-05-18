package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

public class S_Statements extends S_AnyStatement {

    public S_Statements(IdentifierTable identifierTable) {
        super(identifierTable);
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENTS;
    }
}
