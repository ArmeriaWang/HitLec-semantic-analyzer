package wang.armeria.symbol;

import wang.armeria.type.IntegerType;
import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;
import wang.armeria.whkas.Manager;

public class S_ExpR_Ari extends S_ExpR implements Symbol, HasIdTable {

    private int regId;  // S
    private Type type;  // S
    private Number value = null;  // S

    public S_ExpR_Ari(IdentifierTable identifierTable) {
        super(identifierTable, new IntegerType());
        this.regId = -1;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * 获取右值表达式的值。如果不是常数，返回null
     *
     * @return 右值表达式的值
     */
    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public int getRegId() {
        if (regId == -1) {
            regId = Manager.applyForNewReg();
        }
        return regId;
    }

}
