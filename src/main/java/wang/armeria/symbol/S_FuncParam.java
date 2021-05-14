package wang.armeria.symbol;

import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;
import wang.armeria.whkas.IdentifierTable.Entry;

public class S_FuncParam implements Symbol, HasIdTable{

    private Type type;  // S
    private String id;
    private final IdentifierTable identifierTable;  // I

    public S_FuncParam(IdentifierTable identifierTable) {
        this.identifierTable = identifierTable;
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
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_ANY_ID_DEF;
    }

}
