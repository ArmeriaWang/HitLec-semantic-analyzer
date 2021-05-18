package wang.armeria.ast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

/**
 * 抽象语法树AST类
 */
public class ASTree {

    private ASTreeNode root;

    /**
     * 新建AST
     */
    public ASTree() {
        root = null;
    }

    public ASTreeNode getRoot() {
        return root;
    }

    public static ASTree parseASTree(String xmlPathName) {
        File file = new File(xmlPathName);
        ASTree asTree = new ASTree();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            Element ast = doc.getDocumentElement();
            asTree.root = ASTreeNode.parseASTreeNode(ast.getElementsByTagName("astNode").item(0));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return asTree;
    }

    private void printSubtree(ASTreeNode node, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        System.out.println(node);
        List<ASTreeNode> children = node.getChildren();
        if (children.size() > 0) {
            for (ASTreeNode child : children) {
                printSubtree(child, depth + 1);
            }
        }
    }

    /**
     * 按格式打印AST。仅当全部解析完成后才能调用
     *
     * @throws RuntimeException 根节点为null
     */
    public void printTree() {
        if (root == null) {
            throw new RuntimeException("Root of the tree is now null!");
        }
        printSubtree(root, 0);
    }

}
