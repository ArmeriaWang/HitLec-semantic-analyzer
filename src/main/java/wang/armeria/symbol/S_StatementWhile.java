package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

public class S_StatementWhile extends S_AnyStatement{

    public S_StatementWhile(IdentifierTable identifierTable) {
        super(identifierTable);
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENT_WHILE;
    }

}
