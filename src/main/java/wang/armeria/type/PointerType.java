package wang.armeria.type;

public class PointerType extends Type {

    private final Type pointsToType;

    public PointerType(Type pointsTo) {
        super(TypeName.POINTER);
        this.pointsToType = pointsTo;
    }

    @Override
    public String toString() {
        return "↑" + pointsToType;
    }

    public Type getPointsToType() {
        return pointsToType;
    }

}
