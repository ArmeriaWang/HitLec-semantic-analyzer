package wang.armeria.symbol;

import wang.armeria.common.Label;
import wang.armeria.whkas.IdentifierTable;

public class S_ExpR_Log extends S_ExpR
        implements Symbol, HasControl {

    private Label goTrue;
    private Label goFalse;

    public S_ExpR_Log(IdentifierTable identifierTable) {
        super(identifierTable, true);
        goTrue = new Label();
        goFalse = new Label();
    }

    @Override
    public Label getGoTrueLabel() {
        return goTrue;
    }

    @Override
    public Label getGoFalseLabel() {
        return goFalse;
    }

    @Override
    public void inherentGoTrueLabel(Label goTrue) {
        this.goTrue = goTrue;
    }

    @Override
    public void inherentGoFalseLabel(Label goFalse) {
        this.goFalse = goFalse;
    }

    @Override
    public void setGoTrueLabel(int goTrue) {
        this.goTrue.setLabel(goTrue);
    }

    @Override
    public void setGoFalseLabel(int goFalse) {
        this.goFalse.setLabel(goFalse);
    }

    @Override
    public SymbolKind getSymbolKind() {
        return SymbolKind.S_EXP_R;
    }

}
