package wang.armeria.whkas;

import wang.armeria.type.Type;

import java.util.*;

public class IdentifierTable {

    private static final List<Entry> allEntries = new ArrayList<>();
    private static final IdentifierTable globalTable = new IdentifierTable(true);

    private final Stack<Entry> tempStack;
    private final Map<String, Entry> tempMap;
    private static int topAddr = 0;
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
    public Entry addTempEntry(Type type, String id) {
        if (tempMap.containsKey(id) || globalTable.tempMap.containsKey(id)) {
            return null;
        }
        Entry entry = new Entry(type, id, topAddr);
        allEntries.add(entry);
        topAddr += entry.getWidth();
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

    public static void printTable() {
        System.out.println("\nIdentifier Table");
        System.out.printf("%-10s %-20s %s%n", "Id", "Type", "Offset");
        for (Entry entry : allEntries) {
            System.out.printf("%-10s %-20s %-5d%n", entry.getId(), entry.getType(), entry.getOffset());
        }
    }

    public static IdentifierTable getGlobalTable() {
        return globalTable;
    }

}
