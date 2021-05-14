package wang.armeria.symbol;

import wang.armeria.type.StructType;
import wang.armeria.type.Type;

import java.util.ArrayList;
import java.util.List;

public class S_StructLineMemberList implements Symbol, HasStructType, HasVarType {

    private final StructType structType;  // I
    private final Type varType;  // I - brother
    private final List<Type> typeList = new ArrayList<>();  // S
    private final List<String> idList = new ArrayList<>();  // S

    public S_StructLineMemberList(StructType structType, Type varType) {
        this.structType = structType;
        this.varType = varType;
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
