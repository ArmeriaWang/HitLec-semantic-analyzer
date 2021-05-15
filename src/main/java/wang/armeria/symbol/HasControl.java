package wang.armeria.symbol;

import wang.armeria.common.Label;

public interface HasControl {

    Label getGoTrue();
    Label getGoFalse();

    void inherentGoTrueLabel(Label goTrue);
    void inherentGoFalseLabel(Label goFalse);
    void setGoTrueLabel(int goTrue);
    void setGoFalseLabel(int goFalse);

}
