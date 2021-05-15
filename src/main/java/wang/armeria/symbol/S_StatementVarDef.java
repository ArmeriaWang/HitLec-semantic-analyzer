package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

public class S_StatementVarDef extends S_AnyStatement {

    public S_StatementVarDef(IdentifierTable identifierTable) {
        super(identifierTable);
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENT_VAR_DEF;
    }
}
