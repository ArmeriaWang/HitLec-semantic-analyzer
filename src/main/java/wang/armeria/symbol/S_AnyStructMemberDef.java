package wang.armeria.symbol;

import wang.armeria.type.StructType;
import wang.armeria.type.Type;

import java.util.*;

public class S_AnyStructMemberDef implements Symbol, HasStructType {

    private final StructType structType;
    private Type varType;

    public S_AnyStructMemberDef(StructType structType) {
        this.structType = structType;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STRUCT_MEMBER_DEF;
    }

    @Override
    public StructType getStructType() {
        return structType;
    }

}
