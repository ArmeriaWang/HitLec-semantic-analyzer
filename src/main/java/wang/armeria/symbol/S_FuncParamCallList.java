package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;

import java.util.ArrayList;
import java.util.List;

public class S_FuncParamCallList extends S_AnyStatement {

    private final List<Integer> paramRegList;

    public S_FuncParamCallList(IdentifierTable identifierTable) {
        super(identifierTable);
        this.paramRegList = new ArrayList<>();
    }

    public void addParam(int regId) {
        paramRegList.add(regId);
    }

    public void addParamList(List<Integer> regIdList) {
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
