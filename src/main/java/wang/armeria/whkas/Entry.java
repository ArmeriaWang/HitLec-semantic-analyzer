package wang.armeria.whkas;

import wang.armeria.type.Type;

import java.util.Objects;

public class Entry {
    private final Type type;
    private final String id;
    private final int offset;

    public Entry(Type type, String id, int offset) {
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
        return type.getWidth();
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
