package wang.armeria.symbol;

import wang.armeria.type.StructType;
import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

public class S_StructSingleMember implements Symbol, HasStructType, HasVarType, HasIdTable {

    private Type varType;  // I - brother
    private final StructType structType;  // I
    private Type type;  // S
    private String id;  // S
    private final IdentifierTable identifierTable;

    public S_StructSingleMember(IdentifierTable identifierTable, StructType structType, Type varType) {
        this.structType = structType;
        this.identifierTable = identifierTable;
        this.varType = varType;
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    public void setTypeId(Type type, String id) {
        this.type = type;
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getVarType() {
        return varType;
    }


    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    @Override
    public StructType getStructType() {
        return structType;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_ANY_ID_DEF;
    }

}
