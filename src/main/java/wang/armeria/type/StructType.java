package wang.armeria.type;

import wang.armeria.whkas.Entry;

import java.util.*;

public class StructType extends Type {

    public static class Member extends Entry {
        protected Member(Type type, String id, int offset) {
            super(type, id, offset);
        }
    }

    private static final Map<String, StructType> id2Struct = new HashMap<>();
    private String structName;
    private final List<Member> memberList = new ArrayList<>();
    private int offset;

    public StructType() {
        super(TypeName.STRUCT);
        offset = 0;
    }

    public Member addMember(Type type, String id) {
        for (Member member : memberList) {
            if (member.getId().equals(id)) {
                return null;
            }
        }
        Member member = new Member(type, id, offset);
        memberList.add(member);
        offset += member.getWidth();
        while (offset % 4 > 0) {
            offset++;
        }
        return member;
    }

    public Member getMemberById(String id) {
        for (Member member : memberList) {
            if (member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }

    public String getStructName() {
        return structName;
    }

    public List<Member> getMemberList() {
        return new ArrayList<>(memberList);
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }

    @Override
    public int getWidth() {
        int ret = 0;
        for (Member member : memberList) {
            ret += member.getType().getWidth();
            while (ret % 4 > 0) {
                ret++;
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Member member : memberList) {
            if (member.getType().getTypeName() == TypeName.STRUCT) {
                StructType structType = (StructType) member.getType();
                builder.append("struct-").append(structType.getStructName()).append("*");
            } else {
                builder.append(member.getType()).append("*");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static boolean addStructType(StructType structType) {
        if (id2Struct.containsKey(structType.structName)) {
            return false;
        }
        id2Struct.put(structType.structName, structType);
        return true;
    }

    public static StructType getStructTypeByName(String structName) {
        return id2Struct.getOrDefault(structName, null);
    }

    public static void printStructTypeTable() {
        System.out.println("\nStruct Type Table");
        for (StructType structType : id2Struct.values()) {
            structType.printStructType();
            System.out.println();
        }
    }

    public void printStructType() {
        System.out.printf("struct %s\n", structName);
        for (Member member : memberList) {
            System.out.printf("%-10s\t%-20s\n", member.getId(), member.getType());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StructType that = (StructType) o;
        return Objects.equals(structName, that.structName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), structName);
    }

}
