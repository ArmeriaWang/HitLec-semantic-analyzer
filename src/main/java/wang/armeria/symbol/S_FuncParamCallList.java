package wang.armeria.symbol;

import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

import java.util.ArrayList;
import java.util.List;

public class S_FuncParamCallList extends S_AnyStatement {

    private final List<Integer> paramRegList;
    private final List<Type> paramTypeList;

    public S_FuncParamCallList(IdentifierTable identifierTable) {
        super(identifierTable);
        this.paramRegList = new ArrayList<>();
        this.paramTypeList = new ArrayList<>();
    }

    public void addParam(Type type, int regId) {
        paramTypeList.add(type);
        paramRegList.add(regId);
    }

    public List<Type> getParamTypeList() {
        return new ArrayList<>(paramTypeList);
    }

    public void addParamList(List<Type> typeList, List<Integer> regIdList) {
        paramTypeList.addAll(typeList);
        paramRegList.addAll(regIdList);
    }
    public List<Integer> getParamRegList() {
        return new ArrayList<>(paramRegList);
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_ANY_ID_DEF;
    }

}
