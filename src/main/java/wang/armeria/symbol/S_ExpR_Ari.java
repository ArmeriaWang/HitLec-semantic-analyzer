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

    /**
     * 获取算术运算结果
     *
     * @param that 另一个操作数（算数右值结点）
     * @param op 操作符
     * @return 算术运算结果；如果被整数0除或被整数0模，返回null
     */
    public Number doArithmeticCalWith(S_ExpR_Ari that, String op) {
        if (that.getType().getTypeName() != this.getType().getTypeName()) {
            throw new IllegalArgumentException("Type not match!");
        }
        if (type.getTypeName() == Type.TypeName.INTEGER) {
            switch (op) {
                case "+":
                    return this.value.intValue() + that.value.intValue();
                case "-":
                    return this.value.intValue() - that.value.intValue();
                case "*":
                    return this.value.intValue() * that.value.intValue();
                case "/":
                    if (that.value.intValue() == 0) {
                        return null;
                    }
                    return this.value.intValue() / that.value.intValue();
                case "%":
                    if (that.value.intValue() == 0) {
                        return null;
                    }
                    return this.value.intValue() % that.value.intValue();
                default:
                    throw new IllegalArgumentException("Unsupported operation " + op);
            }
        } else if (type.getTypeName() == Type.TypeName.FLOAT) {
            switch (op) {
                case "+":
                    return this.value.floatValue() + that.value.floatValue();
                case "-":
                    return this.value.floatValue() - that.value.floatValue();
                case "*":
                    return this.value.floatValue() * that.value.floatValue();
                case "/":
                    return this.value.floatValue() / that.value.floatValue();
                case "%":
                    return this.value.floatValue() % that.value.floatValue();
                default:
                    throw new IllegalArgumentException("Unsupported operation " + op);
            }
        }
        return null;
    }

}
