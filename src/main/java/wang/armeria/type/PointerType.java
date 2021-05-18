package wang.armeria.type;

public class PointerType extends Type {

    private final Type pointsToType;
    private static final int width = 4;

    public PointerType(Type pointsTo) {
        super(TypeName.POINTER);
        this.pointsToType = pointsTo;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "@" + pointsToType;
    }

    public Type getPointsToType() {
        return pointsToType;
    }

}
