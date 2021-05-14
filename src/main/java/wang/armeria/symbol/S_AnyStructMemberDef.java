package wang.armeria.symbol;

import wang.armeria.type.StructType;
import wang.armeria.type.Type;

import java.util.*;

public class S_AnyStructMemberDef implements Symbol, HasStructType {

    private final StructType structType;
    private Type varType;
    private final List<StructType.Member> memberList = new ArrayList<>();

    public S_AnyStructMemberDef(StructType structType) {
        this.structType = structType;
    }

    public void addMembers(Collection<StructType.Member> members) {
        memberList.addAll(members);
    }

    public void addMembers(List<Type> typeList, List<String> idList) {
        if (typeList.size() != idList.size()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < typeList.size(); i++) {
            memberList.add(new StructType.Member(typeList.get(i), idList.get(i)));
        }
    }

    public void addMember(StructType.Member member) {
        memberList.add(member);
    }

    public List<StructType.Member> getMemberList() {
        return new ArrayList<>(memberList);
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STRUCT_MEMBER_DEF;
    }

    @Override
    public StructType getStructType() {
        return structType;
    }

}
