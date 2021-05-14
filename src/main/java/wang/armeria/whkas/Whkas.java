package wang.armeria.whkas;

import wang.armeria.ast.ASTree;
import wang.armeria.ast.ASTreeNode;
import wang.armeria.symbol.*;
import wang.armeria.type.*;
import wang.armeria.type.StructType.Member;

import java.util.ArrayList;
import java.util.List;

public class Whkas {

    private static final boolean finished = false;

    private IdentifierTable getIdTableOfFather(ASTreeNode node) {
        HasIdTable fatherSymbol = (HasIdTable) node.getFather().getSymbol();
        return fatherSymbol.getIdentifierTable();
    }

    private StructType getStructTypeOfFather(ASTreeNode node) {
        HasStructType fatherSymbol = (HasStructType) node.getFather().getSymbol();
        return fatherSymbol.getStructType();
    }

    private boolean preDo(ASTreeNode node) {
        switch (node.getProducer()) {
            case -1:   // Terminal
                return true;
            case 60:   // STATEMENT_VAR_DEF -> VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
            case 63:   // DECLARE_MORE -> COMMA DECLARE_INITIALIZE DECLARE_MORE
            case 69:   // DECLARE_INITIALIZE -> ID ASSIGN EXP_R
            case 70:   // DECLARE_INITIALIZE -> STAR ID ASSIGN EXP_R
                reportSemanticError("Unsupported (initialization)!");
                return false;

            case 1:    // PROGRAM -> TOP_STATEMENTS
                node.setSymbol(new S_Program());
                break;
            case 2:    // TOP_STATEMENTS -> STATEMENT_VAR_DEF TOP_STATEMENTS
            case 3:    // TOP_STATEMENTS -> STATEMENT_FUNC_DEF TOP_STATEMENTS
            case 4:    // TOP_STATEMENTS -> STATEMENT_STRUCT_DEF TOP_STATEMENTS
            case 5:    // TOP_STATEMENTS -> %empty
                node.setSymbol(new S_TopStatements());
                break;

            case 6:    // STATEMENTS_BLOCK -> BEGIN STATEMENTS END
            case 7:    // STATEMENTS_BLOCK -> BEGIN END
            {
                node.setSymbol(new S_StatementsBlock(getIdTableOfFather(node)));
                break;
            }
            case 8:    // STATEMENTS -> STATEMENT STATEMENTS
            case 9:    // STATEMENTS -> STATEMENT
                node.setSymbol(new S_Statements(getIdTableOfFather(node)));
                break;
            case 10:   // STATEMENT -> STATEMENT_VAR_DEF
                node.setSymbol(new S_Statement(getIdTableOfFather(node)));
                break;

            case 24:

            case 61:   // STATEMENT_VAR_DEF -> VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE
                node.setSymbol(new S_StatementVarDef(getIdTableOfFather(node)));
                break;
            case 62:   // DECLARE_MORE -> SEMICOLON
            case 64:   // DECLARE_MORE -> COMMA DECLARE_NON_INITIALIZE DECLARE_MORE
            {
                Symbol fatherSymbol = node.getFather().getSymbol();
                if (node.getFather().getProducer() == 61) {  // VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE
                    node.setSymbol(new S_DefLineVarList(
                            ((HasIdTable) fatherSymbol).getIdentifierTable(),
                            ((S_AnyType) node.getFather().childAt(0).getSymbol()).getType()));
                } else {  // father is DECLARE_MORE
                    node.setSymbol(new S_DefLineVarList(
                            ((HasIdTable) fatherSymbol).getIdentifierTable(),
                            ((HasVarType) fatherSymbol).getVarType()));
                }
                break;
            }
            case 65:   // DECLARE_NON_INITIALIZE -> ID
            case 66:   // DECLARE_NON_INITIALIZE -> STAR ID
            case 67:   // DECLARE_NON_INITIALIZE -> HD_ARRAY
            case 68:   // DECLARE_NON_INITIALIZE -> STAR HD_ARRAY
            {
                if (node.getFather().getSymbol() instanceof HasStructType) {
                    HasStructType fatherSymbol = (HasStructType) node.getFather().getSymbol();
                    Type varType;
                    if (node.getFather().getProducer() == 75) {  // VAR_DEF_TYPE DECLARE_NON_INITIALIZE ...
                        varType = ((S_AnyType) node.getFather().childAt(0).getSymbol()).getType();
                    } else {  // father is DECLARE_MORE_NON_INITIALIZE
                        varType = ((HasVarType) node.getFather().getSymbol()).getVarType();
                    }
                    node.setSymbol(new S_StructSingleMember(fatherSymbol.getStructType(), varType));
                } else if (node.getFather().getSymbol() instanceof HasIdTable) {
                    HasIdTable fatherSymbol = (HasIdTable) node.getFather().getSymbol();
                    Type varType;
                    if (node.getFather().getProducer() == 61) {  // VAR_DEF_TYPE DECLARE_NON_INITIALIZE ...
                        varType = ((S_AnyType) node.getFather().childAt(0).getSymbol()).getType();
                    } else {  // father is DECLARE_MORE_NON_INITIALIZE
                        varType = ((HasVarType) node.getFather().getSymbol()).getVarType();
                    }
                    node.setSymbol(new S_DefSingleVar(fatherSymbol.getIdentifierTable(), varType));
                }
                break;
            }
            case 72:   // STATEMENT_STRUCT_DEF: STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
                node.setSymbol(new S_StatementStructDef());
                break;
            case 73:   // MORE_STRUCT_MEMBER_DEF -> STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF
            case 74:   // MORE_STRUCT_MEMBER_DEF -> %empty
            case 75:   // STRUCT_MEMBER_DEF: VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON
            {
                node.setSymbol(new S_AnyStructMemberDef(getStructTypeOfFather(node)));
                break;
            }
            case 76:   // DECLARE_MORE_NON_INITIALIZE: COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE
            case 77:   // DECLARE_MORE_NON_INITIALIZE: %empty
            {
                Symbol fatherSymbol = node.getFather().getSymbol();
                if (node.getFather().getProducer() == 75) {  // VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE ...
                    Symbol brother0 = node.getFather().childAt(0).getSymbol();
                    S_StructLineMemberList symbol = new S_StructLineMemberList(
                            ((HasStructType) fatherSymbol).getStructType(),
                            ((S_AnyType) brother0).getType());
                    node.setSymbol(symbol);
                } else {  // father is DECLARE_MORE_NON_INITIALIZE
                    S_StructLineMemberList symbol = new S_StructLineMemberList(
                            ((HasStructType) fatherSymbol).getStructType(),
                            ((HasVarType) fatherSymbol).getVarType());
                    node.setSymbol(symbol);
                }
                break;
            }
            case 83:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
            case 84:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
                node.setSymbol(new S_StatementFuncDef());
                break;
            case 85:   // RECV_FUNC_ARGS -> COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
            case 86:   // RECV_FUNC_ARGS -> %empty
            {
                node.setSymbol(new S_FuncParamList(getIdTableOfFather(node)));
                break;
            }
            case 87:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE ID
            case 88:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE RECV_HD_ARRAY
            {
                node.setSymbol(new S_FuncParam(getIdTableOfFather(node)));
                break;
            }
            case 91:   // DT_STRUCT -> STRUCT ID
            case 92:   // DT_POINTER -> VAR_DEF_TYPE STAR
            case 93:   // VAR_DEF_TYPE -> DT_INTEGER
            case 94:   // FUNC_DEF_TYPE -> DT_INTEGER
            case 95:   // VAR_DEF_TYPE -> DT_INTEGER
            case 96:   // FUNC_DEF_TYPE -> DT_FLOAT
            case 97:   // VAR_DEF_TYPE -> DT_FLOAT
            case 98:   // FUNC_DEF_TYPE -> DT_BOOLEAN
            case 99:   // VAR_DEF_TYPE -> DT_STRUCT
            case 100:  // FUNC_DEF_TYPE -> DT_POINTER
                break;
            default:
                if (!finished) {
                    break;
                }
                reportSemanticError();
                return false;
        }
        return true;
    }

    private boolean postDo(ASTreeNode node) {
        System.out.println(node.getPosition());
        switch (node.getProducer()) {
            case -1:   // Terminal
                return true;
            case 60:   // STATEMENT_VAR_DEF -> VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
            case 63:   // DECLARE_MORE -> COMMA DECLARE_INITIALIZE DECLARE_MORE
            case 69:   // DECLARE_INITIALIZE -> ID ASSIGN EXP_R
            case 70:   // DECLARE_INITIALIZE -> STAR ID ASSIGN EXP_R
                reportSemanticError();
                return false;
            case 1:    // PROGRAM -> TOP_STATEMENTS
            case 2:    // TOP_STATEMENTS -> STATEMENT_VAR_DEF TOP_STATEMENTS
            case 3:    // TOP_STATEMENTS -> STATEMENT_FUNC_DEF TOP_STATEMENTS
            case 4:    // TOP_STATEMENTS -> STATEMENT_STRUCT_DEF TOP_STATEMENTS
            case 5:    // TOP_STATEMENTS -> %empty
                break;
            case 6:    // STATEMENTS_BLOCK -> BEGIN STATEMENTS END
            case 7:    // STATEMENTS_BLOCK -> BEGIN END
            {
                S_StatementsBlock symbol = (S_StatementsBlock) node.getSymbol();
                IdentifierTable identifierTable = symbol.getIdentifierTable();
                try {
                    identifierTable.leaveZone(symbol.getResetIdNum());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    reportSemanticError();
                    return false;
                }
                break;
            }
            case 8:    // STATEMENTS -> STATEMENT STATEMENTS
            case 9:    // STATEMENTS -> STATEMENT
            case 10:   // STATEMENT -> STATEMENT_VAR_DEF
                break;
            case 62:   // DECLARE_MORE -> SEMICOLON
            case 64:   // DECLARE_MORE -> COMMA DECLARE_NON_INITIALIZE DECLARE_MORE
                break;
            case 65:   // DECLARE_NON_INITIALIZE -> ID
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    String id = node.childAt(0).getValue().toString();
                    symbol.setTypeId(symbol.getVarType(), id);
                    if (symbol.getStructType().addMember(symbol.getVarType(), id) == null) {
                        reportSemanticError();
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    symbol.setTypeId(symbol.getVarType(), node.childAt(0).getValue().toString());
                    Entry entry = symbol.getIdentifierTable().addTempEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError();
                        return false;
                    }
                }
                break;
            case 66:   // DECLARE_NON_INITIALIZE -> STAR ID
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    Type type = new PointerType(symbol.getVarType());
                    String id = node.childAt(0).getValue().toString();
                    symbol.setTypeId(type, id);
                    if (symbol.getStructType().addMember(type, id) == null) {
                        reportSemanticError();
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    symbol.setTypeId(new PointerType(symbol.getVarType()),
                            node.childAt(0).getValue().toString());
                    Entry entry = symbol.getIdentifierTable().addTempEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError();
                        return false;
                    }
                }
                break;
            case 67:   // DECLARE_NON_INITIALIZE -> HD_ARRAY
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    S_HDArray child0 = (S_HDArray) node.childAt(0).getSymbol();
                    symbol.setTypeId(child0.getType(), child0.getId());
                    if (symbol.getStructType().addMember(child0.getType(), child0.getId()) == null) {
                        reportSemanticError();
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    S_HDArray child0 = (S_HDArray) node.childAt(0).getSymbol();
                    symbol.setTypeId(child0.getType(), child0.getId());
                    Entry entry = symbol.getIdentifierTable().addTempEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError();
                        return false;
                    }
                }
                break;
            case 68:   // DECLARE_NON_INITIALIZE -> STAR HD_ARRAY
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    S_HDArray child1 = (S_HDArray) node.childAt(1).getSymbol();
                    Type type = new PointerType(child1.getType());
                    String id = child1.getId();
                    symbol.setTypeId(type, id);
                    if (symbol.getStructType().addMember(type, id) == null) {
                        reportSemanticError();
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    S_HDArray child1 = (S_HDArray) node.childAt(1).getSymbol();
                    symbol.setTypeId(new PointerType(child1.getType()), child1.getId());
                    Entry entry = symbol.getIdentifierTable().addTempEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError();
                        return false;
                    }
                }
                break;
            case 72:   // STATEMENT_STRUCT_DEF -> STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
            {
                S_StatementStructDef symbol = (S_StatementStructDef) node.getSymbol();
                StructType structType = symbol.getStructType();
                structType.setStructName(node.childAt(1).getValue().toString());
                if (!StructType.addStructType(structType)) {
                    reportSemanticError();
                    return false;
                }
                break;
            }
            case 73:   // MORE_STRUCT_MEMBER_DEF -> STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF
            case 74:   // MORE_STRUCT_MEMBER_DEF -> %empty
            case 75:   // STRUCT_MEMBER_DEF: VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON
                break;
            case 76:   // DECLARE_MORE_NON_INITIALIZE: COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE
            case 77:   // DECLARE_MORE_NON_INITIALIZE: %empty
                break;
            case 83:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
            {
                S_StatementFuncDef symbol = (S_StatementFuncDef) node.getSymbol();
                S_AnyType child1 = (S_AnyType) node.childAt(1).getSymbol();
                symbol.setFunctionType(new FunctionType(List.of(), child1.getType()));
                break;
            }
            case 84:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
            {
                S_StatementFuncDef symbol = (S_StatementFuncDef) node.getSymbol();
                S_AnyType child1 = (S_AnyType) node.childAt(1).getSymbol();
                S_FuncParam child4 = (S_FuncParam) node.childAt(4).getSymbol();
                S_FuncParamList child5 = (S_FuncParamList) node.childAt(5).getSymbol();
                List<Type> typeList = new ArrayList<>(child5.getTypeList());
                typeList.add(child4.getType());
                symbol.setFunctionType(new FunctionType(typeList, child1.getType()));
                IdentifierTable.getGlobalTable().addTempEntry(
                        symbol.getFunctionType(), node.childAt(2).getValue().toString());
                break;
            }
            case 85:   // RECV_FUNC_ARGS -> COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
            {
                S_FuncParamList symbol = (S_FuncParamList) node.getSymbol();
                S_FuncParam child1 = (S_FuncParam) node.childAt(1).getSymbol();
                S_FuncParamList child2 = (S_FuncParamList) node.childAt(2).getSymbol();
                Entry entry = child1.getIdentifierTable().addTempEntry(child1.getType(), child1.getId());
                if (entry == null) {
                    reportSemanticError();
                    return false;
                }
                symbol.addTypeId(child1.getType(), child1.getId());
                symbol.addTypeIdList(child2.getTypeList(), child2.getIdList());
                break;
            }
            case 86:   // RECV_FUNC_ARGS -> %empty
                break;
            case 87:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE ID
            {
                S_FuncParam symbol = (S_FuncParam) node.getSymbol();
                S_AnyType child0 = (S_AnyType) node.childAt(0).getSymbol();
                symbol.setTypeId(child0.getType(), node.childAt(1).getValue().toString());
                break;
            }
            case 88:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE RECV_HD_ARRAY
                break;
            case 91:   // DT_STRUCT -> STRUCT ID
                StructType structType = StructType.getStructTypeByName(node.childAt(1).getValue().toString());
                if (structType == null) {
                    reportSemanticError();
                    return false;
                }
                node.setSymbol(new S_AnyType(structType));
                break;
            case 92:   // DT_POINTER -> VAR_DEF_TYPE STAR
            {
                S_AnyType child0 = (S_AnyType) node.childAt(0).getSymbol();
                node.setSymbol(new S_AnyType(new PointerType(child0.getType())));
                break;
            }
            case 93:   // VAR_DEF_TYPE -> DT_INTEGER
            case 97:   // FUNC_DEF_TYPE -> DT_INTEGER
                node.setSymbol(new S_AnyType(new IntegerType()));
                break;
            case 94:   // VAR_DEF_TYPE -> DT_FLOAT
            case 98:   // FUNC_DEF_TYPE -> DT_FLOAT
                node.setSymbol(new S_AnyType(new FloatType()));
                break;
            case 95:   // VAR_DEF_TYPE -> DT_BOOLEAN
            case 99:   // FUNC_DEF_TYPE -> DT_BOOLEAN
                node.setSymbol(new S_AnyType(new BooleanType()));
                break;
            case 96:   // VAR_DEF_TYPE -> DT_STRUCT
            case 100:  // FUNC_DEF_TYPE -> DT_POINTER
                node.setSymbol(node.childAt(0).getSymbol());
                break;
            default:
                if (!finished) {
                    break;
                }
                throw new RuntimeException("Invalid producer number " + node.getProducer() + "!");
        }
        return true;
    }

    public void reportSemanticError(String msg) {
        System.err.println(msg);
    }

    public void reportSemanticError() {
        System.err.println("nmsl!");
    }

    public boolean generateIntermediateCode(ASTreeNode node) {
        if (!preDo(node)) {
            return false;
        }
        List<ASTreeNode> children = node.getChildren();
        for (ASTreeNode child : children) {
            if (!generateIntermediateCode(child)) {
                return false;
            }
        }
        return postDo(node);
    }

    public static void main(String[] args) {
        ASTree asTree = ASTree.parseASTree("test.xml");
//        asTree.printTree();
        Whkas as = new Whkas();
        if (!as.generateIntermediateCode(asTree.getRoot())) {
            System.exit(1);
        }
        IdentifierTable.printTable();
        StructType.printStructTypeTable();
    }

}
