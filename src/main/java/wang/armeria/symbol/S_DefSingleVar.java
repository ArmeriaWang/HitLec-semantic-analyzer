package wang.armeria.symbol;

import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

public class S_DefSingleVar implements Symbol, HasIdTable, HasVarType {


    private final IdentifierTable identifierTable;
    private final Type varType;
    private Type type;  // S
    private String id;  // S

    public S_DefSingleVar(IdentifierTable identifierTable, Type varType) {
        this.identifierTable = identifierTable;
        this.varType = varType;
    }

    public void setTypeId(Type type, String id) {
        this.type = type;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public String getId() {
        return id;
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
