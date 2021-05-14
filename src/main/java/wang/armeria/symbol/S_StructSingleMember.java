package wang.armeria.symbol;

import wang.armeria.type.StructType;
import wang.armeria.type.Type;

public class S_StructSingleMember implements Symbol, HasStructType, HasVarType {

    private Type varType;  // I - brother
    private final StructType structType;  // I
    private Type type;  // S
    private String id;  // S

    public S_StructSingleMember(StructType structType, Type varType) {
        this.structType = structType;
        this.varType = varType;
    }

    public void setTypeId(Type type, String id) {
        this.varType = type;
        this.id = id;
    }

    public Type getVarType() {
        return varType;
    }


    public String getId() {
        return id;
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
