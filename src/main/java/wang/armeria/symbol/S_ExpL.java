package wang.armeria.symbol;

import wang.armeria.whkas.Entry;
import wang.armeria.whkas.IdentifierTable;

public class S_ExpL implements Symbol, HasIdTable {

    private final IdentifierTable identifierTable;  // I
    private String fullRef;  // S
    private Entry entry;  // S

    public S_ExpL(IdentifierTable identifierTable) {
        this.identifierTable = identifierTable;
    }

    public String getFullRef() {
        return fullRef;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public void setFullRef(String fullId) {
        this.fullRef = fullId;
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_EXP_L;
    }
}
