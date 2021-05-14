package wang.armeria.type;

import java.util.*;

public class StructType extends Type {

    public static class Member {
        private final Type type;
        private final String id;

        public Member(Type type, String id) {
            this.type = type;
            this.id = id;
        }

        public Type getType() {
            return type;
        }

        public String getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Member member = (Member) o;
            return Objects.equals(id, member.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    private static final Map<String, StructType> id2Struct = new HashMap<>();
    private String structName;
    private final List<Member> memberList = new ArrayList<>();

    public StructType() {
        super(TypeName.STRUCT);
    }

    public Member addMember(Type type, String id) {
        Member member = new Member(type, id);
        if (addMember(member)) {
            return member;
        } else {
            return null;
        }
    }

    public boolean addMember(Member member) {
        if (memberList.contains(member)) {
            return false;
        }
        memberList.add(member);
        return true;
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
            try {
                ret += member.getType().getWidth();
                while (ret % 4 > 0) {
                    ret++;
                }
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Member member : memberList) {
            builder.append(member.getType()).append("*");
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
}
