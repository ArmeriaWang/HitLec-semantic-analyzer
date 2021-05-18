package wang.armeria.whkas;

import wang.armeria.ast.ASTree;
import wang.armeria.ast.ASTreeNode;
import wang.armeria.common.Position;
import wang.armeria.symbol.*;
import wang.armeria.type.*;

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

    private boolean doPostWithArithmeticExpR(ASTreeNode node, String op) {
        S_ExpR child0 = (S_ExpR) node.childAt(0).getSymbol();
        S_ExpR child2 = (S_ExpR) node.childAt(2).getSymbol();
        if (!child0.isArithmetic() || !child2.isArithmetic()) {
            return false;
        }
        S_ExpR_Ari child0_ari = (S_ExpR_Ari) child0;
        S_ExpR_Ari child2_ari = (S_ExpR_Ari) child2;
        if (!child0_ari.getType().equals(child2_ari.getType())) {
            if (child0_ari.getType().getTypeName() == Type.TypeName.INTEGER
                    && child2_ari.getType().getTypeName() == Type.TypeName.FLOAT) {
                child0_ari.setType(new FloatType());
            } else if (child0_ari.getType().getTypeName() == Type.TypeName.FLOAT
                    && child2_ari.getType().getTypeName() == Type.TypeName.INTEGER) {
                child2_ari.setType(new FloatType());
            } else {
                return false;
            }
        }
        S_ExpR_Ari symbol = (S_ExpR_Ari) node.getSymbol();
        symbol.setType(child0_ari.getType());
        if (child0_ari.getValue() != null && child2_ari.getValue() != null) {
            Number opValue = child0_ari.doArithmeticCalWith(child2_ari, op);
            if (opValue == null) {
                reportSemanticError("Divide or mod by zero!", node.childAt(2).getPosition());
                return false;
            }
            symbol.setValue(opValue);
        }
        int reg1 = child0_ari.getRegId();
        int reg2 = child2_ari.getRegId();
        int reg3 = symbol.getRegId();
        Manager.addTetrad(op, Manager.reg2String(reg1),
                Manager.reg2String(reg2),
                Manager.reg2String(reg3));
        return true;
    }

    private boolean doPostWithRelOpExpR(ASTreeNode node, String op) {
        S_ExpR child0 = (S_ExpR) node.childAt(0).getSymbol();
        S_ExpR child2 = (S_ExpR) node.childAt(2).getSymbol();
        if (!child0.isArithmetic() || !child2.isArithmetic()) {
            return false;
        }
        S_ExpR_Log symbol = (S_ExpR_Log) node.getSymbol();
        int reg1 = ((S_ExpR_Ari) child0).getRegId();
        int reg2 = ((S_ExpR_Ari) child2).getRegId();
        Manager.addTetrad("j" + op, Manager.reg2String(reg1), Manager.reg2String(reg2), symbol.getGoTrueLabel());
        Manager.addTetrad("j", "/#/", "/#/", symbol.getGoFalseLabel());
        return true;
    }

    /**
     * 递归时访问到Exp_R
     *
     * @param node
     * @return
     */
    private boolean doPreWithExpRLog(ASTreeNode node) {
        S_ExpR_Log symbol = new S_ExpR_Log(getIdTableOfFather(node));
        if (node.getFather().getProducer() == 29) {
            // EXP_R -> {?} EXP_R LOR {?} EXP_R
            S_ExpR_Log fatherSymbol = (S_ExpR_Log) node.getFather().getSymbol();
            if (node.getSonRank() == 0) {
                symbol.inherentGoTrueLabel(fatherSymbol.getGoTrueLabel());
            } else if (node.getSonRank() == 2) {
                S_ExpR_Log brother0 = (S_ExpR_Log) node.getFather().childAt(0).getSymbol();
                brother0.setGoFalseLabel(Manager.nextLabel());
                symbol.inherentGoTrueLabel(fatherSymbol.getGoTrueLabel());
                symbol.inherentGoFalseLabel(fatherSymbol.getGoFalseLabel());
            }
        } else if (node.getFather().getProducer() == 30) {
            // EXP_R -> {?} EXP_R LAND {?} EXP_R
            S_ExpR_Log fatherSymbol = (S_ExpR_Log) node.getFather().getSymbol();
            if (node.getSonRank() == 0) {
                symbol.inherentGoFalseLabel(fatherSymbol.getGoFalseLabel());
            } else if (node.getSonRank() == 2) {
                S_ExpR_Log brother0 = (S_ExpR_Log) node.getFather().childAt(0).getSymbol();
                brother0.setGoTrueLabel(Manager.nextLabel());
                symbol.inherentGoTrueLabel(fatherSymbol.getGoTrueLabel());
                symbol.inherentGoFalseLabel(fatherSymbol.getGoFalseLabel());
            }
        } else if (node.getFather().getProducer() == 31) {
            // EXP_R -> NOT {?} EXP_R
            S_ExpR_Log fatherSymbol = (S_ExpR_Log) node.getFather().getSymbol();
            symbol.inherentGoTrueLabel(fatherSymbol.getGoFalseLabel());
            symbol.inherentGoFalseLabel(fatherSymbol.getGoTrueLabel());
        } else if (node.getFather().getProducer() == 37) {
            // EXP_R -> ROUND_LEFT {?} EXP_R ROUND_RIGHT
            S_ExpR_Log fatherSymbol = (S_ExpR_Log) node.getFather().getSymbol();
            symbol.inherentGoTrueLabel(fatherSymbol.getGoTrueLabel());
            symbol.inherentGoFalseLabel(fatherSymbol.getGoFalseLabel());
        } else if (node.getFather().getProducer() == 53) {
            // STATEMENT_IF -> IF ROUND_LEFT {?} EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE
            S_StatementIf fatherSymbol = (S_StatementIf) node.getFather().getSymbol();
            symbol.inherentGoFalseLabel(fatherSymbol.getNextLabel());
        } else if (node.getFather().getProducer() == 57) {
            // STATEMENT_WHILE: WHILE ROUND_LEFT {?} EXP_R ROUND_RIGHT WHILE_BODY
            S_StatementWhile fatherSymbol = (S_StatementWhile) node.getFather().getSymbol();
            symbol.inherentGoFalseLabel(fatherSymbol.getNextLabel());
        }
        node.setSymbol(symbol);
        return true;
    }

    private boolean preDo(ASTreeNode node) {
        switch (node.getProducer()) {
            case -1:   // Terminal
                return true;
            case 14:   // STATEMENT -> STATEMENT_STRUCT_DEF
            case 51:   // NUMBER -> CONST_BOOLEAN
            case 59:   // WHILE_BODY -> STATEMENT
            case 60:   // STATEMENT_VAR_DEF -> VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
            case 63:   // DECLARE_MORE -> COMMA DECLARE_INITIALIZE DECLARE_MORE
            case 69:   // DECLARE_INITIALIZE -> ID ASSIGN EXP_R
            case 70:   // DECLARE_INITIALIZE -> STAR ID ASSIGN EXP_R
            case 89:   // RECV_HD_ARRAY -> ID SQUARE_LEFT SQUARE_RIGHT MORE_ARRAY_DIM
            case 90:   // RECV_HD_ARRAY -> ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM            case 95:   // VAR_DEF_TYPE -> DT_BOOLEAN
            case 99:   // FUNC_DEF_TYPE -> DT_BOOLEAN
                reportSemanticError(String.format("Unsupported producer %s!", node.getProducer()), node.getPosition());
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
                S_StatementsBlock symbol = new S_StatementsBlock(getIdTableOfFather(node));
                node.setSymbol(symbol);
                if (node.getFather().getProducer() == 53) {
                    // STATEMENT_IF -> IF ROUND_LEFT EXP_R ROUND_RIGHT {?} STATEMENTS_BLOCK STATEMENT_ELSE
                    S_StatementIf fatherSymbol = (S_StatementIf) node.getFather().getSymbol();
                    S_ExpR_Log brother2 = (S_ExpR_Log) node.getFather().childAt(2).getSymbol();
                    brother2.setGoTrueLabel(Manager.nextLabel());
                    symbol.inherentNextLabel(fatherSymbol.getNextLabel());
                } else if (node.getFather().getProducer() == 54) {
                    // STATEMENT_ELSE -> ELSE {?} STATEMENTS_BLOCK
                    S_AnyStatement fatherSymbol = (S_AnyStatement) node.getFather().getSymbol();
                    Manager.addTetrad("j", "/#/", "/#/", fatherSymbol.getNextLabel());
                    S_ExpR_Log uncle2 = (S_ExpR_Log) node.getFather().getFather().childAt(2).getSymbol();
                    uncle2.setGoFalseLabel(Manager.nextLabel());
                    symbol.inherentNextLabel(fatherSymbol.getNextLabel());
                } else if (node.getFather().getProducer() == 58) {
                    // WHILE_BODY -> {?} STATEMENTS_BLOCK
                    S_StatementWhile grandpaSymbol = (S_StatementWhile) node.getFather().getFather().getSymbol();
                    S_ExpR_Log uncle2 = (S_ExpR_Log) node.getFather().getFather().childAt(2).getSymbol();
                    uncle2.setGoTrueLabel(Manager.nextLabel());
                    symbol.inherentNextLabel(grandpaSymbol.getStartLabel());
                } else if (node.getFather().getProducer() == 83) {
                    // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT {?} STATEMENTS_BLOCK
                    S_StatementFuncDef fatherSymbol = (S_StatementFuncDef) node.getFather().getSymbol();
                    S_AnyType brother1 = (S_AnyType) node.getFather().childAt(1).getSymbol();
                    fatherSymbol.setFunctionType(new FunctionType(List.of(), brother1.getType()));
                    String functionId = node.getFather().childAt(2).getValue().toString();
                    IdentifierTable.getGlobalTable().addEntry(fatherSymbol.getFunctionType(), functionId);
                    fatherSymbol.getIdentifierTable().setFunctionId(functionId);
                } else if (node.getFather().getProducer() == 84) {
                    // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ...  {?} STATEMENTS_BLOCK
                    S_StatementFuncDef fatherSymbol = (S_StatementFuncDef) node.getFather().getSymbol();
                    S_AnyType brother1 = (S_AnyType) node.getFather().childAt(1).getSymbol();
                    S_FuncParamDef brother4 = (S_FuncParamDef) node.getFather().childAt(4).getSymbol();
                    S_FuncParamDefList brother5 = (S_FuncParamDefList) node.getFather().childAt(5).getSymbol();
                    List<Type> typeList = new ArrayList<>(brother5.getTypeList());
                    typeList.add(brother4.getType());
                    fatherSymbol.setFunctionType(new FunctionType(typeList, brother1.getType()));
                    String functionId = node.getFather().childAt(2).getValue().toString();
                    IdentifierTable.getGlobalTable().addEntry(fatherSymbol.getFunctionType(), functionId);
                    fatherSymbol.getIdentifierTable().setFunctionId(functionId);
                }
                break;
            }
            case 8:    // STATEMENTS -> STATEMENT STATEMENTS
            case 9:    // STATEMENTS -> STATEMENT
                node.setSymbol(new S_Statements(getIdTableOfFather(node)));
                break;
            case 10:   // STATEMENT -> STATEMENT_VAR_DEF
            case 11:   // STATEMENT -> STATEMENT_ASSIGN
            case 12:   // STATEMENT -> STATEMENT_IF
            case 13:   // STATEMENT -> STATEMENT_WHILE
            case 15:   // STATEMENT -> STATEMENT_RETURN
            case 16:   // STATEMENT -> STATEMENT_EXP_R
            case 17:   // STATEMENT -> SEMICOLON
                node.setSymbol(new S_AnyStatement(getIdTableOfFather(node)));
                break;
            case 18:   // EXP_R -> EXP_R LT EXP_R
            case 19:   // EXP_R -> EXP_R LE EXP_R
            case 20:   // EXP_R -> EXP_R GT EXP_R
            case 21:   // EXP_R -> EXP_R GE EXP_R
            case 22:   // EXP_R -> EXP_R NE EXP_R
            case 23:   // EXP_R -> EXP_R EQ EXP_R
                doPreWithExpRLog(node);
                break;
            case 24:   // EXP_R -> EXP_R PLUS EXP_R
            case 25:   // EXP_R -> EXP_R MINUS EXP_R
            case 26:   // EXP_R -> EXP_R STAR EXP_R
            case 27:   // EXP_R -> EXP_R DIVIDE EXP_R
            case 28:   // EXP_R -> EXP_R MOD EXP_R
                node.setSymbol(new S_ExpR_Ari(getIdTableOfFather(node)));
                break;
            case 29:   // EXP_R -> EXP_R LOR EXP_R
            case 30:   // EXP_R -> EXP_R LAND EXP_R
            case 31:   // EXP_R -> LNOT EXP_R
                doPreWithExpRLog(node);
                break;
            case 37:   // EXP_R -> ROUND_LEFT EXP_R ROUND_RIGHT
            {
                int faProducer = node.getFather().getProducer();
                if (faProducer == 29 || faProducer == 30 || faProducer == 31 ||
                        (faProducer == 37 && ((S_ExpR) node.getFather().getSymbol()).isLogical())) {
                    doPreWithExpRLog(node);
                } else {
                    node.setSymbol(new S_ExpR_Ari(getIdTableOfFather(node)));
                }
                break;
            }
            case 38:   // EXP_R -> EXP_L
            case 41:   // EXP_R -> NUMBER
            case 42:   // EXP_R -> FUNC_CALL
                node.setSymbol(new S_ExpR_Ari(getIdTableOfFather(node)));
                break;
            case 43:   // EXP_L -> ID
            case 44:   // EXP_L -> ID DOT EXP_L
            case 45:   // EXP_L -> HD_ARRAY
            case 46:   // EXP_L -> HD_ARRAY DOT EXP_L
                node.setSymbol(new S_ExpL(getIdTableOfFather(node)));
                break;
            case 47:   // HD_ARRAY -> ID SQUARE_LEFT EXP_R SQUARE_RIGHT
            case 48:   // HD_ARRAY -> HD_ARRAY SQUARE_LEFT EXP_R SQUARE_RIGHT
            {
                IdentifierTable idTbl = getIdTableOfFather(node);
                if (node.getFather().getSymbolKind() == SymbolKind.S_HD_ARRAY) {
                    S_HDArray fatherSymbol = (S_HDArray) node.getFather().getSymbol();
                    node.setSymbol(fatherSymbol.isRef() ? new S_HDArray_Ref(idTbl) : new S_HDArray_Def(idTbl));
                } else if (node.getFather().getSymbolKind() == SymbolKind.S_DECLARE_NON_INITIALIZE) {
                    node.setSymbol(new S_HDArray_Def(idTbl));
                } else if (node.getFather().getSymbolKind() == SymbolKind.S_EXP_L) {
                    node.setSymbol(new S_HDArray_Ref(idTbl));
                }
                break;
            }
            case 49:   // STATEMENT_EXP_R -> EXP_R SEMICOLON
                node.setSymbol(new S_AnyStatement(getIdTableOfFather(node)));
                break;
            case 50:   // NUMBER -> CONST_INTEGER
            case 52:   // NUMBER -> CONST_FLOAT
                break;
            case 53:   // STATEMENT_IF -> IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE
                node.setSymbol(new S_StatementIf(getIdTableOfFather(node)));
                break;
            case 54:   // STATEMENT_ELSE -> ELSE STATEMENTS_BLOCK
            case 55:   // STATEMENT_ELSE -> ELSE STATEMENT_IF
            case 56:   // STATEMENT_ELSE -> %empty
            {
                S_AnyStatement symbol = new S_AnyStatement(getIdTableOfFather(node));
                node.setSymbol(symbol);
                S_StatementIf fatherSymbol = (S_StatementIf) node.getFather().getSymbol();
                symbol.inherentNextLabel(fatherSymbol.getNextLabel());
                break;
            }
            case 57:   // STATEMENT_WHILE: WHILE ROUND_LEFT EXP_R ROUND_RIGHT WHILE_BODY
                node.setSymbol(new S_StatementWhile(getIdTableOfFather(node)));
                break;
            case 58:   // WHILE_BODY -> STATEMENTS_BLOCK
            {
                S_AnyStatement symbol = new S_AnyStatement(getIdTableOfFather(node));
                S_AnyStatement fatherSymbol = (S_AnyStatement) node.getFather().getSymbol();
                node.setSymbol(symbol);
                symbol.inherentNextLabel(fatherSymbol.getNextLabel());
                break;
            }
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
                    node.setSymbol(new S_StructSingleMember(
                            getIdTableOfFather(node),
                            fatherSymbol.getStructType(), varType));
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
            case 71:   // STATEMENT_ASSIGN: EXP_L ASSIGN EXP_R SEMICOLON
                node.setSymbol(new S_AnyStatement(getIdTableOfFather(node)));
                break;
            case 72:   // STATEMENT_STRUCT_DEF -> STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
                node.setSymbol(new S_StatementStructDef());
                break;
            case 73:   // MORE_STRUCT_MEMBER_DEF -> STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF
            case 74:   // MORE_STRUCT_MEMBER_DEF -> %empty
            case 75:   // STRUCT_MEMBER_DEF -> VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON
            {
                node.setSymbol(new S_AnyStructMemberDef(getIdTableOfFather(node),
                        getStructTypeOfFather(node)));
                break;
            }
            case 76:   // DECLARE_MORE_NON_INITIALIZE -> COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE
            case 77:   // DECLARE_MORE_NON_INITIALIZE: %empty
            {
                Symbol fatherSymbol = node.getFather().getSymbol();
                if (node.getFather().getProducer() == 75) {  // VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE ...
                    Symbol brother0 = node.getFather().childAt(0).getSymbol();
                    S_StructLineMemberList symbol = new S_StructLineMemberList(
                            getIdTableOfFather(node),
                            ((HasStructType) fatherSymbol).getStructType(),
                            ((S_AnyType) brother0).getType());
                    node.setSymbol(symbol);
                } else {  // father is DECLARE_MORE_NON_INITIALIZE
                    S_StructLineMemberList symbol = new S_StructLineMemberList(
                            getIdTableOfFather(node),
                            ((HasStructType) fatherSymbol).getStructType(),
                            ((HasVarType) fatherSymbol).getVarType());
                    node.setSymbol(symbol);
                }
                break;
            }
            case 78:  // STATEMENT_RETURN -> RETURN EXP_R SEMICOLON
            {
                node.setSymbol(new S_AnyStatement(getIdTableOfFather(node)));
                break;
            }
            case 79:   // FUNC_CALL -> ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
            case 80:   // FUNC_CALL -> ID ROUND_LEFT ROUND_RIGHT
                node.setSymbol(new S_FuncCall(getIdTableOfFather(node)));
                break;
            case 81:   // SEND_FUNC_ARGS -> COMMA EXP_R SEND_FUNC_ARGS
            case 82:   // SEND_FUNC_ARGS -> %empty
                node.setSymbol(new S_FuncParamCallList(getIdTableOfFather(node)));
                break;
            case 83:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
            case 84:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
                node.setSymbol(new S_StatementFuncDef());
                break;
            case 85:   // RECV_FUNC_ARGS -> COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
            case 86:   // RECV_FUNC_ARGS -> %empty
                node.setSymbol(new S_FuncParamDefList(getIdTableOfFather(node)));
                break;
            case 87:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE ID
            case 88:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE RECV_HD_ARRAY
                node.setSymbol(new S_FuncParamDef(getIdTableOfFather(node)));
                break;
            case 91:   // DT_STRUCT -> STRUCT ID
            case 92:   // DT_POINTER -> VAR_DEF_TYPE STAR
            case 93:   // VAR_DEF_TYPE -> DT_INTEGER
            case 94:   // VAR_DEF_TYPE -> DT_FLOAT
            case 96:   // VAR_DEF_TYPE -> DT_STRUCT
            case 97:   // FUNC_DEF_TYPE -> DT_INTEGER
            case 98:   // FUNC_DEF_TYPE -> DT_FLOAT
            case 100:  // FUNC_DEF_TYPE -> DT_POINTER
                break;
            default:
                if (!finished) {
                    break;
                }
                reportSemanticError("Unknown producer " + node.getProducer() + "!", node.getPosition());
                return false;
        }
        return true;
    }

    private boolean postDo(ASTreeNode node) {
//        System.err.println(node.getPosition());
        switch (node.getProducer()) {
            case -1:   // Terminal
                return true;
            case 14:   // STATEMENT -> STATEMENT_STRUCT_DEF
            case 51:   // NUMBER -> CONST_BOOLEAN
            case 59:   // WHILE_BODY -> STATEMENT
            case 60:   // STATEMENT_VAR_DEF -> VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
            case 63:   // DECLARE_MORE -> COMMA DECLARE_INITIALIZE DECLARE_MORE
            case 69:   // DECLARE_INITIALIZE -> ID ASSIGN EXP_R
            case 70:   // DECLARE_INITIALIZE -> STAR ID ASSIGN EXP_R
            case 89:   // RECV_HD_ARRAY -> ID SQUARE_LEFT SQUARE_RIGHT MORE_ARRAY_DIM
            case 90:   // RECV_HD_ARRAY -> ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
            case 95:   // VAR_DEF_TYPE -> DT_BOOLEAN
            case 99:   // FUNC_DEF_TYPE -> DT_BOOLEAN
                reportSemanticError(String.format("Unsupported producer %s!", node.getProducer()), node.getPosition());
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
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
                break;
            }
            case 8:    // STATEMENTS -> STATEMENT STATEMENTS
            {
                S_Statements symbol = (S_Statements) node.getSymbol();
                S_AnyStatement child1 = (S_AnyStatement) node.childAt(1).getSymbol();
                symbol.inherentNextLabel(child1.getNextLabel());
                break;
            }
            case 9:    // STATEMENTS -> STATEMENT
            case 10:   // STATEMENT -> STATEMENT_VAR_DEF
            case 11:   // STATEMENT -> STATEMENT_ASSIGN
            case 12:   // STATEMENT -> STATEMENT_IF
            case 13:   // STATEMENT -> STATEMENT_WHILE
            case 15:   // STATEMENT -> STATEMENT_RETURN
            case 16:   // STATEMENT -> STATEMENT_EXP_R
            {
                S_AnyStatement symbol = (S_AnyStatement) node.getSymbol();
                S_AnyStatement child0 = (S_AnyStatement) node.childAt(0).getSymbol();
                symbol.inherentNextLabel(child0.getNextLabel());
                break;
            }
            case 17:   // STATEMENT -> SEMICOLON
                break;
            case 18:   // EXP_R -> EXP_R LT EXP_R
            {
                if (!doPostWithRelOpExpR(node, "<")) {
                    reportSemanticError("Expression next to the relative op < is not arithmetic!",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 19:   // EXP_R -> EXP_R LE EXP_R
            {
                if (!doPostWithRelOpExpR(node, "<=")) {
                    reportSemanticError("Expression next to the relative op <= is not arithmetic!",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 20:   // EXP_R -> EXP_R GT EXP_R
            {
                if (!doPostWithRelOpExpR(node, ">")) {
                    reportSemanticError("Expression next to the relative op > is not arithmetic!",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 21:   // EXP_R -> EXP_R GE EXP_R
            {
                if (!doPostWithRelOpExpR(node, ">=")) {
                    reportSemanticError("Expression next to the relative op >= is not arithmetic!",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 22:   // EXP_R -> EXP_R NE EXP_R
            {
                if (!doPostWithRelOpExpR(node, "!=")) {
                    reportSemanticError("Expression next to the relative op != is not arithmetic!",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 23:   // EXP_R -> EXP_R EQ EXP_R
            {
                if (!doPostWithRelOpExpR(node, "==")) {
                    reportSemanticError("Expression next to the relative op == is not arithmetic!",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 24:   // EXP_R -> EXP_R PLUS EXP_R
            {
                if (!doPostWithArithmeticExpR(node, "+")) {
                    reportSemanticError("Type not match next to arithmetic op + !",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 25:   // EXP_R -> EXP_R MINUS EXP_R
            {
                if (!doPostWithArithmeticExpR(node, "-")) {
                    reportSemanticError("Type not match next to arithmetic op - !",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 26:   // EXP_R -> EXP_R STAR EXP_R
            {
                if (!doPostWithArithmeticExpR(node, "*")) {
                    reportSemanticError("Type not match next to arithmetic op * !",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 27:   // EXP_R -> EXP_R DIVIDE EXP_R
            {
                if (!doPostWithArithmeticExpR(node, "/")) {
                    reportSemanticError("Type not match next to arithmetic op / !",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 28:   // EXP_R -> EXP_R MOD EXP_R
            {
                if (!doPostWithArithmeticExpR(node, "%")) {
                    reportSemanticError("Type not match next to arithmetic op % !",
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 29:   // EXP_R -> EXP_R LOR EXP_R
            case 30:   // EXP_R -> EXP_R LAND EXP_R
            case 31:   // EXP_R -> LNOT EXP_R
                break;
            case 37:   // EXP_R -> ROUND_LEFT EXP_R ROUND_RIGHT
            {
                S_ExpR child1 = (S_ExpR) node.childAt(1).getSymbol();
                if (child1.isArithmetic()) {
                    S_ExpR_Ari child1_ari = (S_ExpR_Ari) node.childAt(1).getSymbol();
                    S_ExpR_Ari symbol_ari = (S_ExpR_Ari) node.getSymbol();
                    node.setSymbol(symbol_ari, true);
                    symbol_ari.setType(child1_ari.getType());
                    Manager.addTetrad("=", Manager.reg2String(child1_ari.getRegId()), "/#/",
                            Manager.reg2String(symbol_ari.getRegId()));
                }
                break;
            }
            case 38:   // EXP_R -> EXP_L
            {
                S_ExpR_Ari symbol = (S_ExpR_Ari) node.getSymbol();
                S_ExpL child0 = (S_ExpL) node.childAt(0).getSymbol();
                int regId = symbol.getRegId();
                symbol.setType(child0.getEntry().getType());
                Manager.addTetrad("=", child0.getFullRef(), "/#/", Manager.reg2String(regId));
                break;
            }
            case 41:   // EXP_R -> NUMBER
            {
                S_ExpR_Ari symbol = (S_ExpR_Ari) node.getSymbol();
                S_Number child0 = (S_Number) node.childAt(0).getSymbol();
                int regId = symbol.getRegId();
                symbol.setType(child0.getType());
                symbol.setValue(child0.getValue());
                Manager.addTetrad("=", child0.getValue().toString(), "/#/", Manager.reg2String(regId));
                break;
            }
            case 42:   // EXP_R -> FUNC_CALL
            {
                S_ExpR_Ari symbol = (S_ExpR_Ari) node.getSymbol();
                S_FuncCall child0 = (S_FuncCall) node.childAt(0).getSymbol();
                symbol.setType(child0.getIdentifierTable().getReturnType());
                int regId = symbol.getRegId();
                Manager.addTetrad("=", Manager.reg2String(child0.getRetRegId()), "/#/",
                        Manager.reg2String(regId));
                break;
            }
            case 43:   // EXP_L -> ID
            {
                S_ExpL symbol = (S_ExpL) node.getSymbol();
                String id = node.childAt(0).getValue().toString();
                Entry entry = symbol.getIdentifierTable().getEntryById(id);
                if (entry == null) {
                    reportSemanticError(String.format("Use undefined identifier '%s'!", id),
                            node.childAt(0).getPosition());
                    return false;
                }
                symbol.setEntry(entry);
                symbol.setFullRef(id);
                break;
            }
            case 44:   // EXP_L -> EXP_L DOT ID
            {
                S_ExpL symbol = (S_ExpL) node.getSymbol();
                S_ExpL child0 = (S_ExpL) node.childAt(0).getSymbol();
                Entry leftEntry = child0.getEntry();
                String rightId = node.childAt(2).getValue().toString();
                MemberRefEntry memberRefEntry = symbol.getIdentifierTable().getStructMemberEntry(leftEntry, rightId);
                if (memberRefEntry == null) {
                    reportSemanticError(
                            String.format("Struct member %s of %s not defined!", rightId, leftEntry.getId()),
                            node.childAt(2).getPosition());
                    return false;
                }
                symbol.setFullRef(child0.getFullRef() + "." + memberRefEntry.getFullRef());
                symbol.setEntry(memberRefEntry);
                break;
            }
            case 45:   // EXP_L -> HD_ARRAY
            {
                S_ExpL symbol = (S_ExpL) node.getSymbol();
                S_HDArray_Ref child0 = (S_HDArray_Ref) node.childAt(0).getSymbol();
                Entry entry = symbol.getIdentifierTable().getEntryById(child0.getId());
                if (entry == null) {
                    reportSemanticError(String.format("Use undefined array! array id is '%s'",
                            child0.getId()), node.childAt(0).getPosition());
                    return false;
                }
                ArrayRefEntry arrayRefEntry = ArrayRefEntry.getArrayRefEntry(entry,
                        child0.getIndexRegList());
                if (arrayRefEntry == null) {
                    reportSemanticError(String.format("Array reference does not match! array id is '%s'",
                            child0.getId()), node.childAt(0).getPosition());
                    return false;
                }
                symbol.setEntry(arrayRefEntry);
                symbol.setFullRef(arrayRefEntry.getFullRef());
                break;
            }
            case 46:   // EXP_L -> EXP_L DOT HD_ARRAY
            {
                S_ExpL symbol = (S_ExpL) node.getSymbol();
                S_ExpL child0 = (S_ExpL) node.childAt(0).getSymbol();
                S_HDArray_Ref child2 = (S_HDArray_Ref) node.childAt(2).getSymbol();
                Entry leftEntry = child0.getEntry();
                String rightId = child2.getId();
                MemberRefEntry memberRefEntry = symbol.getIdentifierTable().getStructMemberEntry(leftEntry, rightId);
                if (memberRefEntry == null) {
                    reportSemanticError(String.format("Use undefined array! array id is '%s'", rightId),
                            node.childAt(2).getPosition());
                    return false;
                }
                ArrayRefEntry arrayRefEntry = ArrayRefEntry.getArrayRefEntry(memberRefEntry, child2.getIndexRegList());
                if (arrayRefEntry == null) {
                    reportSemanticError(String.format("Array reference does not match! array id is '%s'", rightId),
                            node.childAt(2).getPosition());
                    return false;
                }
                symbol.setEntry(arrayRefEntry);
                symbol.setFullRef(child0.getFullRef() + "." +
                        memberRefEntry.getFullRef() + arrayRefEntry.getFullRef(false));
                break;
            }
            case 47:   // HD_ARRAY -> ID SQUARE_LEFT EXP_R SQUARE_RIGHT
            case 48:   // HD_ARRAY -> HD_ARRAY SQUARE_LEFT EXP_R SQUARE_RIGHT
            {
                boolean isRef = ((S_HDArray) node.getSymbol()).isRef();
                if (isRef) {
                    S_HDArray_Ref symbol = (S_HDArray_Ref) node.getSymbol();
                    S_ExpR child2 = (S_ExpR) node.childAt(2).getSymbol();
                    if (!child2.isArithmetic() ||
                            ((S_ExpR_Ari) child2).getType().getTypeName() != Type.TypeName.INTEGER) {
                        reportSemanticError("Array reference should use integers!",
                                node.childAt(2).getPosition());
                        return false;
                    }
                    if (node.getProducer() == 48) {
                        S_HDArray_Ref child0 = (S_HDArray_Ref) node.childAt(0).getSymbol();
                        symbol.addIndexRegList(child0.getIndexRegList());
                        symbol.setId(child0.getId());
                    } else {
                        symbol.setId(node.childAt(0).getValue().toString());
                    }
                    symbol.addIndexReg(((S_ExpR_Ari) child2).getRegId());
                } else {
                    S_HDArray_Def symbol = (S_HDArray_Def) node.getSymbol();
                    S_ExpR child2 = (S_ExpR) node.childAt(2).getSymbol();
                    if (!child2.isArithmetic() ||
                            ((S_ExpR_Ari) child2).getType().getTypeName() != Type.TypeName.INTEGER) {
                        reportSemanticError("Array dim definition should use integers!",
                                node.childAt(2).getPosition());
                        return false;
                    }
                    Number num = ((S_ExpR_Ari) child2).getValue();
                    if (num == null) {
                        reportSemanticError("Array dim definition should use constants!",
                                node.childAt(2).getPosition());
                        return false;
                    }
                    if (node.getProducer() == 48) {
                        S_HDArray_Def child0 = (S_HDArray_Def) node.childAt(0).getSymbol();
                        symbol.addDimSizeList(child0.getDimSizeList());
                        symbol.setId(child0.getId());
                    } else {
                        symbol.setId(node.childAt(0).getValue().toString());
                    }
                    symbol.addDimSize((Integer) num);
                }
                break;
            }
            case 49:   // STATEMENT_EXP_R -> EXP_R SEMICOLON
            {
                S_AnyStatement symbol = (S_AnyStatement) node.getSymbol();
                symbol.setNextLabel(Manager.nextLabel());
                break;
            }
            case 50:   // NUMBER -> CONST_INTEGER
                node.setSymbol(new S_Number(new IntegerType(), Integer.parseInt(node.childAt(0).getValue().toString())));
                break;
            case 52:   // NUMBER -> CONST_FLOAT
                node.setSymbol(new S_Number(new FloatType(), Float.parseFloat(node.childAt(0).getValue().toString())));
                break;
            case 53:   // STATEMENT_IF -> IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE
            {
                S_StatementIf symbol = (S_StatementIf) node.getSymbol();
                symbol.setNextLabel(Manager.nextLabel());
                break;
            }
            case 54:   // STATEMENT_ELSE -> ELSE STATEMENTS_BLOCK
            case 55:   // STATEMENT_ELSE -> ELSE STATEMENT_IF
            case 56:   // STATEMENT_ELSE -> %empty
                break;
            case 57:   // STATEMENT_WHILE -> WHILE ROUND_LEFT EXP_R ROUND_RIGHT WHILE_BODY
            {
                S_StatementWhile symbol = (S_StatementWhile) node.getSymbol();
                Manager.addTetrad("j", "/#/", "/#/", symbol.getStartLabel());
                symbol.setNextLabel(Manager.nextLabel());
                break;
            }
            case 58:   // WHILE_BODY -> STATEMENTS_BLOCK
            {
                break;
            }
            case 62:   // DECLARE_MORE -> SEMICOLON
            case 64:   // DECLARE_MORE -> COMMA DECLARE_NON_INITIALIZE DECLARE_MORE
                break;
            case 65:   // DECLARE_NON_INITIALIZE -> ID
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    String id = node.childAt(0).getValue().toString();
                    symbol.setTypeId(symbol.getVarType(), id);
                    if (symbol.getStructType().addMember(symbol.getVarType(), id) == null) {
                        reportSemanticError(String.format("Repeatedly define struct member id %s!", id),
                                node.childAt(0).getPosition());
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    symbol.setTypeId(symbol.getVarType(), node.childAt(0).getValue().toString());
                    Entry entry = symbol.getIdentifierTable().addEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError(String.format("Repeatedly define variable id %s!", symbol.getId()),
                                node.childAt(0).getPosition());
                        return false;
                    }
                }
                break;
            case 66:   // DECLARE_NON_INITIALIZE -> STAR ID
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    Type type = new PointerType(symbol.getVarType());
                    String id = node.childAt(1).getValue().toString();
                    symbol.setTypeId(type, id);
                    if (symbol.getStructType().addMember(type, id) == null) {
                        reportSemanticError(String.format("Repeatedly define struct member id %s!", id),
                                node.childAt(1).getPosition());
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    symbol.setTypeId(new PointerType(symbol.getVarType()),
                            node.childAt(1).getValue().toString());
                    Entry entry = symbol.getIdentifierTable().addEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError(String.format("Repeatedly define variable id %s!", symbol.getId()),
                                node.childAt(0).getPosition());
                        return false;
                    }
                }
                break;
            case 67:   // DECLARE_NON_INITIALIZE -> HD_ARRAY
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    S_HDArray_Def child0 = (S_HDArray_Def) node.childAt(0).getSymbol();
                    symbol.setTypeId(child0.convert2ArrayType(symbol.getVarType()), child0.getId());
                    StructType.Member member = symbol.getStructType().addMember(symbol.getType(), symbol.getId());
                    if (member == null) {
                        reportSemanticError(String.format("Repeatedly define struct member id %s!", symbol.getId()),
                                node.childAt(0).getPosition());
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    S_HDArray_Def child0 = (S_HDArray_Def) node.childAt(0).getSymbol();
                    symbol.setTypeId(child0.convert2ArrayType(symbol.getVarType()), child0.getId());
                    Entry entry = symbol.getIdentifierTable().addEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError(String.format("Repeatedly define variable id %s!", symbol.getId()),
                                node.childAt(0).getPosition());
                        return false;
                    }
                }
                break;
            case 68:   // DECLARE_NON_INITIALIZE -> STAR HD_ARRAY
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    S_HDArray_Def child1 = (S_HDArray_Def) node.childAt(1).getSymbol();
                    Type type = child1.convert2ArrayType(new PointerType(symbol.getVarType()));
                    String id = child1.getId();
                    symbol.setTypeId(type, id);
                    if (symbol.getStructType().addMember(type, id) == null) {
                        reportSemanticError(String.format("Repeatedly define struct member id %s!", id),
                                node.childAt(1).getPosition());
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    S_HDArray_Def child1 = (S_HDArray_Def) node.childAt(1).getSymbol();
                    Type type = child1.convert2ArrayType(new PointerType(symbol.getVarType()));
                    String id = child1.getId();
                    symbol.setTypeId(type, id);
                    Entry entry = symbol.getIdentifierTable().addEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError(String.format("Repeatedly define variable id %s!", symbol.getId()),
                                node.childAt(0).getPosition());
                        return false;
                    }
                }
                break;
            case 71:   // STATEMENT_ASSIGN -> EXP_L ASSIGN EXP_R SEMICOLON
            {
                S_ExpL expL = (S_ExpL) node.childAt(0).getSymbol();
                S_ExpR expR = (S_ExpR) node.childAt(2).getSymbol();
                if (!expR.isArithmetic()) {
                    reportSemanticError("On the right of assign symbol there should be an arithmetic value!",
                            node.childAt(1).getPosition());
                    return false;
                }
                S_ExpR_Ari expR_ari = (S_ExpR_Ari) expR;
                Type type1 = expL.getEntry().getType();
                Type type2 = expR_ari.getType();
                if (!type1.equals(type2)) {
                    boolean notMatch = true;
                    if (type1.getTypeName() == Type.TypeName.FLOAT && type2.getTypeName() == Type.TypeName.INTEGER) {
                        notMatch = false;
                    }
                    if (notMatch) {
                        reportSemanticError(
                                String.format("Assign statement: type not match, left: %s, right: %s", type1, type2),
                                node.childAt(1).getPosition());
                        return false;
                    }
                }
                Manager.addTetrad("=", Manager.reg2String(expR_ari.getRegId()), "/#/", expL.getFullRef());
                S_AnyStatement symbol = (S_AnyStatement) node.getSymbol();
                symbol.setNextLabel(Manager.nextLabel());
                break;
            }
            case 72:   // STATEMENT_STRUCT_DEF -> STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
            {
                S_StatementStructDef symbol = (S_StatementStructDef) node.getSymbol();
                StructType structType = symbol.getStructType();
                structType.setStructName(node.childAt(1).getValue().toString());
                if (!StructType.addStructType(structType)) {
                    reportSemanticError(String.format("Repeatedly define struct name id %s!",
                            structType.getStructName()),
                            node.childAt(1).getPosition());
                    return false;
                }
                break;
            }
            case 73:   // MORE_STRUCT_MEMBER_DEF -> STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF
            case 74:   // MORE_STRUCT_MEMBER_DEF -> %empty
            case 75:   // STRUCT_MEMBER_DEF -> VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON
                break;
            case 76:   // DECLARE_MORE_NON_INITIALIZE -> COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE
            case 77:   // DECLARE_MORE_NON_INITIALIZE: %empty
                break;
            case 78:  // STATEMENT_RETURN -> RETURN EXP_R SEMICOLON
            {
                S_AnyStatement symbol = (S_AnyStatement) node.getSymbol();
                S_ExpR child1 = (S_ExpR) node.childAt(1).getSymbol();
                if (!child1.isArithmetic() ||
                        !((S_ExpR_Ari) child1).getType().equals(symbol.getIdentifierTable().getReturnType())) {
                    reportSemanticError(String.format("Return type not match! Expected: %s, given: %s",
                            symbol.getIdentifierTable().getReturnType(), ((S_ExpR_Ari) child1).getType()),
                            node.childAt(1).getPosition());
                    return false;
                }
                S_ExpR_Ari child1_ari = (S_ExpR_Ari) child1;
                Manager.addTetrad("ret", Manager.reg2String(child1_ari.getRegId()), "/#/", "/#/");
                break;
            }
            case 79:   // FUNC_CALL -> ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
            {
                S_FuncCall symbol = (S_FuncCall) node.getSymbol();
                S_ExpR child2 = (S_ExpR) node.childAt(2).getSymbol();
                S_FuncParamCallList child3 = (S_FuncParamCallList) node.childAt(3).getSymbol();
                if (!child2.isArithmetic()) {
                    reportSemanticError("Arg for function call should be arithmetic value!",
                            node.childAt(2).getPosition());
                    return false;
                }
                S_ExpR_Ari child2_ari = (S_ExpR_Ari) child2;
                String functionId = node.childAt(0).getValue().toString();
                Entry entry = symbol.getIdentifierTable().getEntryById(functionId);
                if (entry == null || entry.getType().getTypeName() != Type.TypeName.FUNCTION) {
                    reportSemanticError(String.format("Call undefined function %s", functionId),
                            node.childAt(0).getPosition());
                    return false;
                }
                FunctionType functionType = (FunctionType) entry.getType();
                symbol.addParam(child2_ari.getType(), child2_ari.getRegId());
                symbol.addParamList(child3.getParamTypeList(), child3.getParamRegList());
                List<Type> funcParamDefTypeList = functionType.getParamTypeList();
                for (int i = 0; i < funcParamDefTypeList.size(); i++) {
                    Type expected = funcParamDefTypeList.get(i);
                    Type given = symbol.getParamTypeList().get(i);
                    if (!expected.equals(given)) {
                        reportSemanticError(String.format(
                                "Call for function %s, type not match at param %d. Expected %s, given %s",
                                functionId, i, expected, given),
                                node.getPosition());
                        return false;
                    }
                }
                List<Integer> regIdList = symbol.getParamRegList();
                for (int regId : regIdList) {
                    Manager.addTetrad("param", Manager.reg2String(regId), "/#/", "/#/");
                }
                int regId = symbol.getRetRegId();
                Manager.addTetrad("call", functionId, "/#/", Manager.reg2String(regId));
                break;
            }
            case 80:   // FUNC_CALL -> ID ROUND_LEFT ROUND_RIGHT
            {
                S_FuncCall symbol = (S_FuncCall) node.getSymbol();
                String functionId = node.childAt(0).getValue().toString();
                Entry entry = symbol.getIdentifierTable().getEntryById(functionId);
                if (entry == null || entry.getType().getTypeName() != Type.TypeName.FUNCTION) {
                    reportSemanticError(String.format("Call undefined function %s", functionId),
                            node.childAt(0).getPosition());
                    return false;
                }
                Manager.addTetrad("call", "/#/", "/#/", functionId);
                break;
            }
            case 81:   // SEND_FUNC_ARGS -> COMMA EXP_R SEND_FUNC_ARGS
            {
                S_FuncParamCallList symbol = (S_FuncParamCallList) node.getSymbol();
                S_ExpR child1 = (S_ExpR) node.childAt(1).getSymbol();
                S_FuncParamCallList child2 = (S_FuncParamCallList) node.childAt(2).getSymbol();
                if (!child1.isArithmetic()) {
                    reportSemanticError("Arg for function call should be arithmetic value!",
                            node.childAt(1).getPosition());
                    return false;
                }
                S_ExpR_Ari child0_ari = (S_ExpR_Ari) child1;
                symbol.addParam(child0_ari.getType(), child0_ari.getRegId());
                symbol.addParamList(child2.getParamTypeList(), child2.getParamRegList());
                break;
            }
            case 82:   // SEND_FUNC_ARGS -> %empty
                break;
            case 83:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
            case 84:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
                Manager.addTetrad("ret", "/#/", "/#/", "/#/");
                break;
            case 85:   // RECV_FUNC_ARGS -> COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
            {
                S_FuncParamDefList symbol = (S_FuncParamDefList) node.getSymbol();
                S_FuncParamDef child1 = (S_FuncParamDef) node.childAt(1).getSymbol();
                S_FuncParamDefList child2 = (S_FuncParamDefList) node.childAt(2).getSymbol();
                symbol.addTypeId(child1.getType(), child1.getId());
                symbol.addTypeIdList(child2.getTypeList(), child2.getIdList());
                break;
            }
            case 86:   // RECV_FUNC_ARGS -> %empty
                break;
            case 87:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE ID
            {
                S_FuncParamDef symbol = (S_FuncParamDef) node.getSymbol();
                S_AnyType child0 = (S_AnyType) node.childAt(0).getSymbol();
                symbol.setTypeId(child0.getType(), node.childAt(1).getValue().toString());
                symbol.getIdentifierTable().addEntry(symbol.getType(), symbol.getId());
                break;
            }
            case 88:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE RECV_HD_ARRAY
                break;
            case 91:   // DT_STRUCT -> STRUCT ID
                String id = node.childAt(1).getValue().toString();
                StructType structType = StructType.getStructTypeByName(id);
                if (structType == null) {
                    reportSemanticError(String.format("Use undefined struct name id %s!", id),
                            node.childAt(1).getPosition());
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

    public void reportSemanticError(String msg, Position position) {
        System.err.printf("Semantic error at position %s: %s\n", position, msg);
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
        Manager.printTetradList();
        IdentifierTable.printTable();
        StructType.printStructTypeTable();
    }

}
