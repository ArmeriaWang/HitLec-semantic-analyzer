package wang.armeria.symbol;

import wang.armeria.type.ArrayType;
import wang.armeria.type.Type;
import wang.armeria.whkas.IdentifierTable;

import java.util.ArrayList;
import java.util.List;

public class S_HDArray_Def extends S_HDArray {

    private final List<Integer> dimSizeList;
    private String id;

    public S_HDArray_Def(IdentifierTable identifierTable) {
        super(identifierTable, false);
        dimSizeList = new ArrayList<>();
    }

    public void addDimSize(int size) {
        dimSizeList.add(size);
    }

    public void addDimSizeList(List<Integer> dimSizeList) {
        this.dimSizeList.addAll(dimSizeList);
    }

    public List<Integer> getDimSizeList() {
        return new ArrayList<>(dimSizeList);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayType convert2ArrayType(Type varType) {
        if (dimSizeList.size() < 1) {
            throw new RuntimeException("Dimension of dimList is less than 1!");
        }
        ArrayType arrayType = new ArrayType(varType, dimSizeList.get(0));
        for (int i = 1; i < dimSizeList.size(); i++) {
            arrayType = new ArrayType(arrayType, dimSizeList.get(i));
        }
        return arrayType;
    }

}
