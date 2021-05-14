package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

public class S_StatementsBlock implements Symbol, HasIdTable {

    private final IdentifierTable identifierTable;
    private final int resetIdNum;

    public S_StatementsBlock(IdentifierTable identifierTable) {
        this.identifierTable = identifierTable;
        this.resetIdNum = identifierTable.getSize();
    }

    public int getResetIdNum() {
        return resetIdNum;
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENTS_BLOCK;
    }

}
