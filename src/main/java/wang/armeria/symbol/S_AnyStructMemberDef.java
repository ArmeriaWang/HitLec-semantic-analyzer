package wang.armeria.symbol;

import wang.armeria.type.StructType;
import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

import java.util.*;

public class S_AnyStructMemberDef implements Symbol, HasStructType, HasIdTable {

    private final StructType structType;
    private final IdentifierTable identifierTable;

    public S_AnyStructMemberDef(IdentifierTable identifierTable, StructType structType) {
        this.structType = structType;
        this.identifierTable = identifierTable;
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
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
