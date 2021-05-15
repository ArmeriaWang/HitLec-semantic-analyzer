package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

public class S_StatementsBlock extends S_AnyStatement {

    private final int resetIdNum;

    public S_StatementsBlock(IdentifierTable identifierTable) {
        super(identifierTable);
        this.resetIdNum = identifierTable.getSize();
    }

    public int getResetIdNum() {
        return resetIdNum;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENTS_BLOCK;
    }

}
