package wang.armeria.symbol;

import wang.armeria.type.StructType;
import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

import java.util.ArrayList;
import java.util.List;

public class S_StructLineMemberList implements Symbol, HasStructType, HasVarType, HasIdTable {

    private final StructType structType;  // I
    private final Type varType;  // I - brother
    private final List<Type> typeList = new ArrayList<>();  // S
    private final List<String> idList = new ArrayList<>();  // S
    private final IdentifierTable identifierTable;

    public S_StructLineMemberList(IdentifierTable identifierTable, StructType structType, Type varType) {
        this.structType = structType;
        this.identifierTable = identifierTable;
        this.varType = varType;
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    public void addTypeId(Type type, String id) {
        typeList.add(type);
        idList.add(id);
    }

    public void addTypeIdList(List<Type> typeList, List<String> idList) {
        this.typeList.addAll(typeList);
        this.idList.addAll(idList);
    }

    public List<Type> getTypeList() {
        return new ArrayList<>(typeList);
    }

    public List<String> getIdList() {
        return new ArrayList<>(idList);
    }

    public int getLength() {
        return typeList.size();
    }

    @Override
    public StructType getStructType() {
        return structType;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_ANY_ID_DEF_LIST;
    }

    @Override
    public Type getVarType() {
        return this.varType;
    }

}
