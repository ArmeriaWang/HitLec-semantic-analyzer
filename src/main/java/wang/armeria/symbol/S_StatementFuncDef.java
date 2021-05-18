package wang.armeria.symbol;

import wang.armeria.type.FunctionType;
import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

public class S_StatementFuncDef implements Symbol, HasIdTable {

    private final IdentifierTable identifierTable;  // I
    private FunctionType functionType;  // S

    public S_StatementFuncDef() {
        this.identifierTable = new IdentifierTable();
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    public Type getFunctionType() {
        return functionType;
    }

    public void setFunctionType(FunctionType functionType) {
        this.functionType = functionType;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENT_FUNC_DEF;
    }
}
