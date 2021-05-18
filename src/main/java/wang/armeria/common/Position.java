package wang.armeria.common;

/**
 * 程序位置类（Immutable）
 */
public class Position {

    private final int lineNum;
    private final int columnNum;

    /**
     * 新建程序位置实例
     * @param lineNum 行号（第一行编号为1）
     * @param columnNum 列号（第一列编号为0）
     */
    public Position(int lineNum, int columnNum) {
        this.lineNum = lineNum;
        this.columnNum = columnNum;
    }

    /**
     * 从字符串 <code>&lt;%d, %d&gt;</code> 解析程序位置
     * @param str 字符串
     * @return 从参数字符串解析而来的位置
     * @throws IllegalArgumentException 格式错误，无法解析
     */
    public static Position parsePosition(String str) {
        if (str.length() < 2) {
            throw new IllegalArgumentException();
        }
        String[] numbers = str.substring(1, str.length() - 1).split(",");
        return new Position(Integer.parseInt(numbers[0].trim()), Integer.parseInt(numbers[1].trim()));
    }

    /**
     * 获取行号
     * @return 行号
     */
    public int getLineNum() {
        return lineNum;
    }

    /**
     * 获取列号
     * @return 列号
     */
    public int getColumnNum() {
        return columnNum;
    }

    /**
     * 获取程序位置的格式化字符串
     * @return 程序位置的格式化字符串
     */
    @Override
    public String toString() {
        return String.format("<%d, %d>", lineNum, columnNum);
    }
}
