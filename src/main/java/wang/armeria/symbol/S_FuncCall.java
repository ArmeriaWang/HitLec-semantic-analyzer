package wang.armeria.symbol;

import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;
import wang.armeria.whkas.Manager;

import java.util.ArrayList;
import java.util.List;

public class S_FuncCall extends S_AnyStatement{

    private final List<Integer> paramRegList;
    private final List<Type> paramTypeList;
    private int retRegId;

    public S_FuncCall(IdentifierTable identifierTable) {
        super(identifierTable);
        paramRegList = new ArrayList<>();
        paramTypeList = new ArrayList<>();
        retRegId = -1;
    }

    public int getRetRegId() {
        if (retRegId == -1) {
            retRegId = Manager.applyForNewReg();
        }
        return retRegId;
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

}
