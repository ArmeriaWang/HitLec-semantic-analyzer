package wang.armeria.type;

import java.util.ArrayList;
import java.util.List;

public class FunctionType extends Type {

    private final List<Type> paramList;
    private final Type returnType;
    private final static int width = 4;

    public FunctionType(List<Type> paramList, Type returnType) {
        super(TypeName.FUNCTION);
        this.paramList = paramList;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Type type : paramList) {
            builder.append(type).append("*");
        }
        if (paramList.size() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("->").append(returnType);
        return builder.toString();
    }

    public List<Type> getParamList() {
        return new ArrayList<>(paramList);
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public int getWidth() {
        return width;
    }

}
