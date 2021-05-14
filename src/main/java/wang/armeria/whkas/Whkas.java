package wang.armeria.whkas;

import wang.armeria.ast.ASTree;
import wang.armeria.ast.ASTreeNode;
import wang.armeria.symbol.*;
import wang.armeria.type.*;
import wang.armeria.whkas.IdentifierTable.Entry;
import wang.armeria.type.StructType.Member;

import java.util.ArrayList;
import java.util.List;

public class Whkas {

    private static final boolean finished = false;

    private boolean preDo(ASTreeNode node) {
        switch (node.getProducer()) {
            case -1:   // Terminal
                return true;
            case 1:    // PROGRAM -> TOP_STATEMENTS
                break;
            case 2:    // TOP_STATEMENTS -> STATEMENT_VAR_DEF TOP_STATEMENTS
                break;
            case 3:    // TOP_STATEMENTS -> STATEMENT_FUNC_DEF TOP_STATEMENTS
                break;
            case 4:    // TOP_STATEMENTS -> STATEMENT_STRUCT_DEF TOP_STATEMENTS
                break;
            case 5:    // TOP_STATEMENTS -> %empty
                break;
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
                    node.setSymbol(new S_DefLineVarList(fatherSymbol.getIdentifierTable(), varType));
                }
                break;
            }
            case 69:   // DECLARE_INITIALIZE: ID ASSIGN EXP_R
            case 70:   // DECLARE_INITIALIZE: STAR ID ASSIGN EXP_R
                reportSemanticError();
                return false;
            case 72:   // STATEMENT_STRUCT_DEF: STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
                node.setSymbol(new S_StatementStructDef());
                break;
            case 73:   // MORE_STRUCT_MEMBER_DEF -> STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF
            case 74:   // MORE_STRUCT_MEMBER_DEF -> %empty
            {
                HasStructType fatherSymbol = (HasStructType) node.getFather().getSymbol();
                node.setSymbol(new S_AnyStructMemberDef(fatherSymbol.getStructType()));
                break;
            }
            case 75:   // STRUCT_MEMBER_DEF: VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON
            {
                StructType structType = ((HasStructType) node.getFather().getSymbol()).getStructType();
                node.setSymbol(new S_AnyStructMemberDef(structType));
                break;
            }
            case 76:   // DECLARE_MORE_NON_INITIALIZE: COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE
            case 77:   // DECLARE_MORE_NON_INITIALIZE: %empty
            {
                HasStructType fatherSymbol = (HasStructType) node.getFather().getSymbol();
                node.setSymbol(new S_AnyStructMemberDef(fatherSymbol.getStructType()));
                break;
            }
            case 83:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
            case 84:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
                node.setSymbol(new S_StatementFuncDef());
                break;
            case 85:   // RECV_FUNC_ARGS -> COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
            case 86:   // RECV_FUNC_ARGS -> %empty
            {
                IdentifierTable identifierTable = ((HasIdTable) node.getFather().getSymbol()).getIdentifierTable();
                node.setSymbol(new S_FuncParamList(identifierTable));
                break;
            }
            case 87:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE ID
            case 88:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE RECV_HD_ARRAY
            {
                IdentifierTable identifierTable = ((HasIdTable) node.getFather().getSymbol()).getIdentifierTable();
                node.setSymbol(new S_FuncParam(identifierTable));
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
            case 1:    // PROGRAM -> TOP_STATEMENTS
                break;
            case 2:    // TOP_STATEMENTS -> STATEMENT_VAR_DEF TOP_STATEMENTS
                break;
            case 3:    // TOP_STATEMENTS -> STATEMENT_FUNC_DEF TOP_STATEMENTS
                break;
            case 65:   // DECLARE_NON_INITIALIZE -> ID
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    Member member = new Member(symbol.getVarType(), node.childAt(0).getValue().toString());
                    symbol.setTypeId(member.getType(), member.getId());
                    if (!symbol.getStructType().addMember(member)) {
                        reportSemanticError();
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    symbol.setTypeId(symbol.getVarType(), node.childAt(0).getSymbol().toString());
                    Entry entry = symbol.getIdentifierTable()
                            .addTempEntry(symbol.getVarType(), node.childAt(0).getSymbol().toString());
                    if (entry == null) {
                        reportSemanticError();
                        return false;
                    }
                }
                break;
            case 66:   // DECLARE_NON_INITIALIZE -> STAR ID
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    Member member = new Member(new PointerType(symbol.getVarType()),
                            node.childAt(0).getValue().toString());
                    symbol.setTypeId(member.getType(), member.getId());
                    if (!symbol.getStructType().addMember(member)) {
                        reportSemanticError();
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    symbol.setTypeId(new PointerType(symbol.getVarType()),
                            node.childAt(0).getSymbol().toString());
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
                    Member member = new Member(child0.getType(), child0.getId());
                    symbol.setTypeId(member.getType(), member.getId());
                    if (!symbol.getStructType().addMember(member)) {
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
                    Member member = new Member(new PointerType(child1.getType()), child1.getId());
                    symbol.setTypeId(member.getType(), member.getId());
                    if (!symbol.getStructType().addMember(member)) {
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
            case 69: // DECLARE_INITIALIZE: ID ASSIGN EXP_R
            case 70: // DECLARE_INITIALIZE: STAR ID ASSIGN EXP_R
                reportSemanticError();
                return false;
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
                IdentifierTable.addGlobalEntry(symbol.getFunctionType(), node.childAt(2).getValue().toString());
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
                reportSemanticError();
                return false;
            case 91:   // DT_STRUCT -> STRUCT ID
                node.setSymbol(new S_AnyType(StructType.getStructTypeByName(node.childAt(1).getValue().toString())));
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
    }

}
