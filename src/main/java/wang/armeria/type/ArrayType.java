package wang.armeria.type;

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

    @Override
    public String toString() {
        return String.format("array(%d, %s)", length, contentType);
    }

    @Override
    public int getWidth() throws IllegalAccessException {
        return length * contentType.getWidth();
    }

}
