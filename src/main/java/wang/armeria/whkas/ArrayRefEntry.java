package wang.armeria.whkas;

import wang.armeria.common.Label;
import wang.armeria.type.ArrayType;
import wang.armeria.type.Type;

import java.util.List;

public class ArrayRefEntry extends Entry {

    private final int regId;

    private ArrayRefEntry(Type type, String id, int offset, int regId) {
        super(type, id, offset);
        this.regId = regId;
    }

    public static ArrayRefEntry getArrayRefEntry(IdentifierTable identifierTable,
                                        String id, List<Integer> indexRegList) {
        Entry entry = identifierTable.getEntryById(id);
        Type type = entry.getType();
        if (type.getTypeName() != Type.TypeName.ARRAY || indexRegList.size() < 1) {
            return null;
        }
        int curLen = 1, addResReg = -1;
        for (int i = indexRegList.size() - 1; i >= 0; i--) {
            if (type.getTypeName() != Type.TypeName.ARRAY) {
                return null;
            }
            ArrayType arrayType = (ArrayType) type;
            int multiplyResReg = Manager.applyForNewReg();
            Manager.addTetrad("*", String.valueOf(curLen), Manager.reg2String(indexRegList.get(i)),
                    Manager.reg2String(multiplyResReg));
            if (addResReg != -1) {
                int oldAddResReg = addResReg;
                addResReg = Manager.applyForNewReg();
                Manager.addTetrad("+", Manager.reg2String(multiplyResReg),
                        Manager.reg2String(oldAddResReg),
                        Manager.reg2String(addResReg));
            } else {
                addResReg = multiplyResReg;
            }
            curLen *= arrayType.getLength();
            type = arrayType.getContentType();
        }
        if (type.getTypeName() == Type.TypeName.ARRAY) {
            return null;
        }
        return new ArrayRefEntry(entry.getType(), entry.getId(), entry.getOffset(), addResReg);
    }

    public String getFullRef() {
        return getId() + "[" + Manager.reg2String(regId) + "]";
    }

    public int getRegId() {
        return regId;
    }
}
