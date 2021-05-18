package wang.armeria.symbol;

import wang.armeria.type.ArrayType;
import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;
import wang.armeria.whkas.Manager;

import java.util.ArrayList;
import java.util.List;

public class S_HDArray_Ref extends S_HDArray implements HasIdTable {

    private final List<Integer> indexRegList;
    private ArrayType arrayType;
    private String id;

    public S_HDArray_Ref(IdentifierTable identifierTable) {
        super(identifierTable, true);
        indexRegList = new ArrayList<>();
    }

    public ArrayType getArrayType() {
        return arrayType;
    }

    public void setArrayType(ArrayType arrayType) {
        this.arrayType = arrayType;
    }

    public void addIndexReg(int indexReg) {
        indexRegList.add(indexReg);
    }

    public void addIndexRegList(List<Integer> indexRegList) {
        this.indexRegList.addAll(indexRegList);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getIndexRegList() {
        return new ArrayList<>(indexRegList);
    }

}
