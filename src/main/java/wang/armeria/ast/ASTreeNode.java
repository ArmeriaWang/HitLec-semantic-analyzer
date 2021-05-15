package wang.armeria.ast;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import wang.armeria.common.Position;
import wang.armeria.symbol.Symbol;
import wang.armeria.symbol.SymbolKind;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象语法树AST节点类
 */
public class ASTreeNode {

    private final SymbolKind symbolKind;
    private final Position position;
    private final Object value;
    private final List<ASTreeNode> children = new ArrayList<>();
    private final int producer;
    private ASTreeNode father;
    private Symbol symbol = null;
    private final int sonRank;

    /**
     * 新建AST节点
     * @param symbolKind 符号类型
     * @param position 程序位置
     * @param value 符号语义值
     * @param producer 产生式编号（终结符为-1）
     */
    public ASTreeNode(SymbolKind symbolKind, Position position, Object value, List<ASTreeNode> children,
                      int producer, int sonRank) {
        this.symbolKind = symbolKind;
        this.position = position;
        this.value = value;
        this.producer = producer;
        this.sonRank = sonRank;
        setChildren(children);
        father = null;
    }

    public int getSonRank() {
        return sonRank;
    }

    /**
     * 获取本节点的符号类型
     * @return 本节点的符号类型
     */
    public SymbolKind getSymbolKind() {
        return symbolKind;
    }

    /**
     * 获取本节点的程序位置
     * @return 本节点程序位置
     */
    public Position getPosition() {
        return position;
    }

    /**
     * 获取本节点的符号语义值
     * @return 本节点的符号语义值（字面值、标识符为非null值，其他类型为null）
     */
    public Object getValue() {
        return value;
    }

    /**
     * 获取本节点的父亲节点
     * @return 本节点的父亲节点（若本节点为根，返回null）
     */
    public ASTreeNode getFather() {
        return father;
    }

    public int getProducer() {
        return producer;
    }

    /**
     * 获取本节点的子节点列表
     * @return 本节点的子节点列表（若无子节点，返回空列表）
     */
    public List<ASTreeNode> getChildren() {
        return new ArrayList<>(children);
    }

    public ASTreeNode childAt(int index) {
        return children.get(index);
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        if (this.symbol != null) {
            throw new RuntimeException("Symbol repeatedly assigned!");
        }
        this.symbol = symbol;
    }

    public void setSymbol(Symbol symbol, boolean force) {
        if (this.symbol != null && !force) {
            throw new RuntimeException("Symbol repeatedly assigned!");
        }
        this.symbol = symbol;
    }

    /**
     * 为本节点设置子节点，并将子节点列表中节点的父节点全部设置为本节点
     * @param children 子节点列表
     * @throws RuntimeException 本节点已存在非空的子节点列表
     */
    private void setChildren(List<ASTreeNode> children) {
        if (this.children.size() > 0) {
            throw new RuntimeException("This node already have children");
        }
        for (ASTreeNode child : children) {
            if (child.father != null) {
                throw new IllegalArgumentException();
            }
            child.father = this;
        }
        this.children.addAll(children);
    }

    /**
     * 按格式生成本节点的输出字符串
     * @return 本节点的输出字符串
     */
    @Override
    public String toString() {
        switch (symbolKind) {
            case S_CONST_BOOLEAN:
            case S_CONST_FLOAT:
            case S_CONST_INTEGER:
            case S_CONST_STRING:
            case S_ID:
                return symbolKind.getName() + ": " + value + " " + position;
        }
        return symbolKind.getName() + " " + position + (producer == -1 ? "" : " [" + producer + "]");
    }

    public static ASTreeNode parseASTreeNode(Node domNode) {
        SymbolKind symbolKind = SymbolKind.valueOf(domNode.getChildNodes().item(0).getTextContent());
        Position position = Position.parsePosition(domNode.getChildNodes().item(1).getTextContent());
        Object value = domNode.getChildNodes().item(2).getTextContent();
        int producer = Integer.parseInt(domNode.getChildNodes().item(3).getTextContent());
        int sonRank = Integer.parseInt(domNode.getChildNodes().item(4).getTextContent());
        NodeList domChildren = domNode.getChildNodes().item(5).getChildNodes();
        List<ASTreeNode> children = new ArrayList<>();
        for (int i = 0; i < domChildren.getLength(); i++) {
            Node domChild = domChildren.item(i);
            children.add(parseASTreeNode(domChild));
        }
        return new ASTreeNode(symbolKind, position, value, children, producer, sonRank);
    }
}
