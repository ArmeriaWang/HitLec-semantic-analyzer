package wang.armeria.common;

public class Label {

    private int label;

    public Label() {
        label = -1;
    }

    public Label(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label == -1 ? "/?/" : String.valueOf(label);
    }
}
