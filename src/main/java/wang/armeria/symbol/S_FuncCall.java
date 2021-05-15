package wang.armeria.symbol;

import wang.armeria.whkas.IdentifierTable;
import wang.armeria.whkas.Manager;

import java.util.ArrayList;
import java.util.List;

public class S_FuncCall extends S_AnyStatement{

    private final List<Integer> paramRegList;
    private int regId;

    public S_FuncCall(IdentifierTable identifierTable) {
        super(identifierTable);
        paramRegList = new ArrayList<>();
        regId = -1;
    }

    public int getRegId() {
        if (regId == -1) {
            regId = Manager.applyForNewReg();
        }
        return regId;
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

}
