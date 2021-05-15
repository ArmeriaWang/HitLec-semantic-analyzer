package wang.armeria.whkas;

import wang.armeria.type.StructType;
import wang.armeria.type.Type;

import java.util.*;

public class IdentifierTable {

    private static final List<Entry> allEntries = new ArrayList<>();
    private static final IdentifierTable globalTable = new IdentifierTable(true);

    private final Stack<Entry> tempStack;
    private final Map<String, Entry> tempMap;
    private static int topAddr = 0;
    private static int globalTopAddr = -4;
    private final boolean isGlobal;

    private IdentifierTable(boolean isGlobal) {
        tempStack = new Stack<>();
        tempMap = new HashMap<>();
        this.isGlobal = isGlobal;
    }

    public IdentifierTable() {
        tempStack = new Stack<>();
        tempMap = new HashMap<>();
        isGlobal = false;
    }

    /**
     * 添加局部标识符
     *
     * @param type 类型
     * @param id 标识符字符串
     * @return 标识符的<code>Entry</code>；如果标识符已存在，返回null
     */
    public Entry addEntry(Type type, String id) {
        if (tempMap.containsKey(id) || globalTable.tempMap.containsKey(id)) {
            return null;
        }
        Entry entry = new Entry(type, id, isGlobal ? globalTopAddr : topAddr);
        allEntries.add(entry);
        if (isGlobal) {
            globalTopAddr -= entry.getWidth();
        } else {
            topAddr += entry.getWidth();
        }
        tempMap.put(id, entry);
        tempStack.push(entry);
        return entry;
    }

    /**
     * 离开作用域
     *
     * @param resetIdNum pop Ids until size of table equals resetIdNum
     */
    public void leaveZone(int resetIdNum) throws IllegalAccessException {
        if (this.isGlobal) {
            throw new IllegalAccessException("Global table cannot leave the zone!");
        }
        while (tempStack.size() > resetIdNum) {
            Entry entry = tempStack.pop();
            tempMap.remove(entry.getId());
            topAddr -= entry.getWidth();
        }
    }

    public int getSize() {
        return tempStack.size();
    }

    public Entry getEntryById(String id) {
        Entry entry = globalTable.tempMap.getOrDefault(id, null);
        if (entry == null) {
            entry = tempMap.getOrDefault(id, null);
        }
        return entry;
    }

    private Entry getEntryByOffset(int offset) {
        if (offset < 0) {
            for (Entry entry : globalTable.tempStack) {
                if (entry.getOffset() == offset) {
                    return entry;
                }
            }
        } else {
            for (Entry entry : tempStack) {
                if (entry.getOffset() == offset) {
                    return entry;
                }
            }
        }
        return null;
    }

    public Entry getStructMemberEntry(Entry structVarEntry, String id) {
        StructType structType = (StructType) structVarEntry.getType();
        StructType.Member member = structType.getMemberById(id);
        if (member == null) {
            return null;
        }
        return new Entry(member.getType(), member.getId(), structVarEntry.getOffset() + member.getOffset());
    }

    public static void printTable() {
        System.out.println("\nIdentifier Table");
        System.out.printf("%-10s %-25s %s%n", "Id", "Type", "Offset");
        for (Entry entry : allEntries) {
            System.out.printf("%-10s %-25s %-5d%n", entry.getId(), entry.getType(), entry.getOffset());
        }
    }

    public static IdentifierTable getGlobalTable() {
        return globalTable;
    }

}
