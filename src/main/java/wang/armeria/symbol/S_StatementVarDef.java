package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

public class S_StatementVarDef implements Symbol, HasIdTable {

    private final IdentifierTable identifierTable;

    public S_StatementVarDef(IdentifierTable identifierTable) {
        this.identifierTable = identifierTable;
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENT_VAR_DEF;
    }
}
