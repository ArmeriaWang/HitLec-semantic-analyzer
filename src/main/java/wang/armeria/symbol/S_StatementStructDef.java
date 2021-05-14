package wang.armeria.symbol;

import wang.armeria.type.StructType;
import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

public class S_StatementStructDef implements Symbol, HasStructType {

    private final StructType structType;  // I S

    public S_StatementStructDef() {
        this.structType = new StructType();
    }

    @Override
    public StructType getStructType() {
        return structType;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENT_STRUCT_DEF;
    }

}
