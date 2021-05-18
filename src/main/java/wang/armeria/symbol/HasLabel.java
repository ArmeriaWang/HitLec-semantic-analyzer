package wang.armeria.symbol;

import wang.armeria.common.Label;

public interface HasLabel {

    Label getStartLabel();
    Label getNextLabel();
    void inherentNextLabel(Label label);
    void setNextLabel(int label);

}
