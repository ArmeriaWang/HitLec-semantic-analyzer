package wang.armeria.type;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return typeName == type.typeName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }
}
