package wang.armeria.type;

public class BooleanType extends Type {

    private static final int width = 1;

    public BooleanType() {
        super(TypeName.BOOLEAN);
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public int getWidth() {
        return width;
    }

}
