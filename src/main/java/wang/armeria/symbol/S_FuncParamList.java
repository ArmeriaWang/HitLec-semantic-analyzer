package wang.armeria.symbol;

import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

import java.util.ArrayList;
import java.util.List;

public class S_FuncParamList implements Symbol, HasIdTable {

    private final IdentifierTable identifierTable;  // I
    private final List<Type> typeList = new ArrayList<>();  // S
    private final List<String> idList = new ArrayList<>();  // S

    public S_FuncParamList(IdentifierTable identifierTable) {
        this.identifierTable = identifierTable;
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
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_ANY_ID_DEF_LIST;
    }
}
