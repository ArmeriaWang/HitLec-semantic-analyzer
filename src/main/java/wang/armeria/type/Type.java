package wang.armeria.type;

public abstract class Type {

    public enum TypeName {
        INTEGER,
        FLOAT,
        BOOLEAN,
        ARRAY,
        STRING,
        STRUCT,
        FUNCTION,
        POINTER
    }

    protected final TypeName typeName;

    public Type(TypeName typeName) {
        this.typeName = typeName;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public int getWidth() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

}
