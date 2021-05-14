package wang.armeria.whkas;

import wang.armeria.type.Type;

import java.util.*;

public class IdentifierTable {

    public static class Entry {
        private final Type type;
        private final String id;
        private final int offset;

        private Entry(Type type, String id, int offset) {
            this.type = type;
            this.id = id;
            this.offset = offset;
        }

        public Type getType() {
            return type;
        }

        public String getId() {
            return id;
        }

        public int getOffset() {
            return offset;
        }

        public int getWidth() {
            try {
                return type.getWidth();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(id, entry.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    private static final Map<String, Entry> globalMap = new HashMap<>();
    private static final List<Entry> allEntries = new ArrayList<>();

    private final Stack<Entry> tempStack;
    private final Map<String, Entry> tempMap;
    private static int topAddr = 0;

    public IdentifierTable() {
        tempStack = new Stack<>();
        tempMap = new HashMap<>();
    }

    /**
     * 添加全局标识符
     *
     * @param type 类型
     * @param id 标识符字符串
     * @return 标识符的<code>Entry</code>；如果标识符已存在，返回null
     */
    public static Entry addGlobalEntry(Type type, String id) {
        if (globalMap.containsKey(id)) {
            return null;
        }
        Entry entry = new Entry(type, id, topAddr);
        allEntries.add(entry);
        topAddr += entry.getWidth();
        globalMap.put(id, entry);
        return entry;
    }

    /**
     * 添加局部标识符
     *
     * @param type 类型
     * @param id 标识符字符串
     * @return 标识符的<code>Entry</code>；如果标识符已存在，返回null
     */
    public Entry addTempEntry(Type type, String id) {
        if (tempMap.containsKey(id) || globalMap.containsKey(id)) {
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
     * @param entryNum 作用域中的局部标识符数目
     */
    public void leaveZone(int entryNum) {
        for (int i = 0; i < entryNum; i++) {
            Entry entry = tempStack.pop();
            tempMap.remove(entry.id);
            topAddr -= entry.getWidth();
        }
    }

    public static void printTable() {
        System.out.println("\nIdentifier Table");
        System.out.printf("%-10s %-20s %s%n", "Id", "Type", "Offset");
        for (Entry entry : allEntries) {
            System.out.printf("%-10s %-20s %-5d%n", entry.id, entry.type, entry.offset);
        }
    }
}
