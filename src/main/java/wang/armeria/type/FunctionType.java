package wang.armeria.type;

import java.util.ArrayList;
import java.util.List;

public class FunctionType extends Type {

    private final List<Type> paramTypeList;
    private final Type returnType;
    private final static int width = 4;

    public FunctionType(List<Type> paramTypeList, Type returnType) {
        super(TypeName.FUNCTION);
        this.paramTypeList = paramTypeList;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Type type : paramTypeList) {
            builder.append(type).append("*");
        }
        if (paramTypeList.size() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("->").append(returnType);
        return builder.toString();
    }

    public List<Type> getParamTypeList() {
        return new ArrayList<>(paramTypeList);
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public int getWidth() {
        return width;
    }

}
