package wang.armeria.whkas;

import wang.armeria.common.Label;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private static int regId = 0;
    private static int icLineLabel = 0;
    private static final List<Tetrad> tetradList = new ArrayList<>();

    private static class Tetrad {
        private final int icLineLabel;
        private final String op;
        private final String obj1;
        private final String obj2;
        private final Label label;
        private final String target;

        /**
         * jump
         */
        public Tetrad(int icLineLabel, String op, String obj1, String obj2, Label label) {
            this.icLineLabel = icLineLabel;
            this.op = op;
            this.obj1 = obj1;
            this.obj2 = obj2;
            this.label = label;
            this.target = null;
        }

        /**
         * arithmetic, assign, return, call, param
         */
        public Tetrad(int icLineLabel, String op, String obj1, String obj2, String target) {
            this.icLineLabel = icLineLabel;
            this.op = op;
            this.obj1 = obj1;
            this.obj2 = obj2;
            this.label = null;
            this.target = target;
        }

        @Override
        public String toString() {
            String s1, s2;
            if (label != null) {
                s1 = String.format("%-3d:\t( %-15s, %-15s, %-15s, %-15s )", icLineLabel, op, obj1, obj2, label);
                if (op.equals("j")) {
                    s2 = String.format("goto %s", label);
                } else if (op.startsWith("j")) {
                    s2 = String.format("if %s %s %s goto %s", obj1, op.substring(1), obj2, label);
                } else {
                    s2 = "unknown code";
                }
            }
            else {  // target != null
                assert target != null;
                s1 = String.format("%-3d:\t( %-15s, %-15s, %-15s, %-15s )", icLineLabel, op, obj1, obj2, target);
                if (op.equals("=")) {
                    s2 = String.format("%s = %s", target, obj1);
                } else if (op.equals("ret")) {
                    s2 = String.format("ret %s", target.equals("/#/") ? "" : target);
                } else if (op.equals("call")) {
                    s2 = String.format("call %s", obj1);
                } else if (op.equals("param")) {
                    s2 = String.format("param %s", obj1);
                } else if (op.length() == 1 && "+-*/%".contains(op)) {
                    s2 = String.format("%s = %s %s %s", target, obj1, op, obj2);
                } else {
                    s2 = "unknown code";
                }
            }
            return s1 + "\t\t\t" + s2;
        }
    }

    public static int applyForNewReg() {
        regId++;
        return regId - 1;
    }

    public static String reg2String(int regId) {
        if (regId >= Manager.regId) {
            throw new IllegalArgumentException();
        }
        return String.format("#%d", regId);
    }

    public static int nextLabel() {
        return icLineLabel;
    }

    public static void addTetrad(String op, String obj1, String obj2, Label label) {
        tetradList.add(new Tetrad(icLineLabel, op, obj1, obj2, label));
        icLineLabel++;
    }

    public static void addTetrad(String op, String obj1, String obj2, String target) {
        tetradList.add(new Tetrad(icLineLabel, op, obj1, obj2, target));
        icLineLabel++;
    }

    public static void printTetradList() {
        for (Tetrad tetrad : tetradList) {
            System.out.println(tetrad);
        }
    }

}
