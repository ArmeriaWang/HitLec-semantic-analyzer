package wang.armeria.symbol;

import wang.armeria.common.Label;
import wang.armeria.whkas.IdentifierTable;
import wang.armeria.whkas.Manager;

public class S_AnyStatement implements Symbol, HasIdTable, HasLabel {

    private final IdentifierTable identifierTable;
    private final Label startLabel;
    private Label nextLabel;

    public S_AnyStatement(IdentifierTable identifierTable) {
        this.identifierTable = identifierTable;
        startLabel = new Label();
        nextLabel = new Label();
        startLabel.setLabel(Manager.nextLabel());
    }

    @Override
    public Label getStartLabel() {
        return startLabel;
    }

    @Override
    public Label getNextLabel() {
        return nextLabel;
    }

    @Override
    public void inherentNextLabel(Label nextLabel) {
        this.nextLabel = nextLabel;
    }

    @Override
    public void setNextLabel(int label) {
        this.nextLabel.setLabel(label);
    }

    @Override
    public IdentifierTable getIdentifierTable() {
        return identifierTable;
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_STATEMENT;
    }

}
