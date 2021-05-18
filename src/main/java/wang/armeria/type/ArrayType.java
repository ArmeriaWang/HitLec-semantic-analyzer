package wang.armeria.type;

import java.util.Objects;

public class ArrayType extends Type {

    private final Type contentType;
    private final int length;

    public ArrayType(Type contentType, int length) {
        super(TypeName.ARRAY);
        this.contentType = contentType;
        this.length = length;
    }

    public Type getContentType() {
        return contentType;
    }

    public int getLength() {
        return length;
    }

    public Type getBasicType() {
        ArrayType arrayType;
        do {
            arrayType = (ArrayType) this.contentType;
        } while (arrayType.contentType.getTypeName() == TypeName.ARRAY);
        return arrayType.contentType;
    }

    @Override
    public String toString() {
        return String.format("array(%d, %s)", length, contentType);
    }

    @Override
    public int getWidth() {
        return length * contentType.getWidth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ArrayType arrayType = (ArrayType) o;
        return length == arrayType.length && Objects.equals(contentType, arrayType.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contentType, length);
    }

}
