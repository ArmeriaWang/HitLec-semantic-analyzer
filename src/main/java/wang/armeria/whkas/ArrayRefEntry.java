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

    /**
     * 将高维数组的原表条目转化为引用条目，并自动生成计算索引的中间代码
     *
     * @param entry 高维数组的原表条目
     * @param indexRegList 存储各维度索引值的寄存器的列表
     * @return 该高维数组的引用条目；若引用维度数和定义维度数不匹配，返回null
     */
    public static ArrayRefEntry getArrayRefEntry(Entry entry, List<Integer> indexRegList) {
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
        return new ArrayRefEntry(type, entry.getId(), entry.getOffset(), addResReg);
    }

    public String getFullRef() {
        return getId() + "[" + Manager.reg2String(regId) + "]";
    }

    public String getFullRef(boolean needId) {
        return (needId ? getId() : "") + "[" + Manager.reg2String(regId) + "]";
    }

    public int getRegId() {
        return regId;
    }
}
