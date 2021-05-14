package wang.armeria.type;

public class IntegerType extends Type {

    private static final int width = 4;

    public IntegerType() {
        super(TypeName.INTEGER);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "int";
    }
}
