package wang.armeria.symbol;

import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

public class S_DefLineVarList implements Symbol, HasIdTable, HasVarType {

    private final IdentifierTable identifierTable;
    private final Type varType;

    public S_DefLineVarList(IdentifierTable identifierTable, Type varType) {
        this.identifierTable = identifierTable;
        this.varType = varType;
    }


    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public Type getVarType() {
        return varType;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return null;
    }
}
