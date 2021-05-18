package wang.armeria.whkas;

import wang.armeria.type.ArrayType;
import wang.armeria.type.StructType;
import wang.armeria.type.Type;

public class MemberRefEntry extends Entry {

    private final int relOffset;

    public MemberRefEntry(Type type, String memberId, int offset, int relOffset) {
        super(type, memberId, offset);
        this.relOffset = relOffset;
    }

    public String getFullRef() {
        int width = getWidth();
        if (getType().getTypeName() == Type.TypeName.ARRAY) {
            width = ((ArrayType) getType()).getBasicType().getWidth();
        }
        return getId() + "(" + relOffset + ")<" + width + ">";
    }

    public int getRelOffset() {
        return relOffset;
    }
}
