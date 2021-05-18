package wang.armeria.type;

public class FloatType extends Type{

    private static final int width = 4;

    public FloatType() {
        super(TypeName.FLOAT);
    }

    @Override
    public String toString() {
        return "float";
    }

    @Override
    public int getWidth() {
        return width;
    }

}
