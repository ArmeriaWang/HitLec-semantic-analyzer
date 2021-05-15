package wang.armeria.whkas;

import wang.armeria.ast.ASTree;
import wang.armeria.ast.ASTreeNode;
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

    private boolean doWithArithmeticExpR(ASTreeNode node, String op) {
        S_ExpR child0 = (S_ExpR) node.childAt(0).getSymbol();
        S_ExpR child2 = (S_ExpR) node.childAt(2).getSymbol();
        if (!child0.isArithmetic() || !child2.isArithmetic()) {
            return false;
        }
        if (!child0.getType().equals(child2.getType())) {
            return false;
        }
        S_ExpR_Ari symbol = (S_ExpR_Ari) node.getSymbol();
        symbol.setType(child0.getType());
        int reg1 = ((S_ExpR_Ari) child0).getRegId();
        int reg2 = ((S_ExpR_Ari) child2).getRegId();
        int reg3 = ((S_ExpR_Ari) node.getSymbol()).getRegId();
        Manager.addTetrad(op, Manager.reg2String(reg1),
                Manager.reg2String(reg2),
                Manager.reg2String(reg3));
        return true;
    }

    private boolean preDo(ASTreeNode node) {
        switch (node.getProducer()) {
            case -1:   // Terminal
                return true;
            case 14:   // STATEMENT -> STATEMENT_STRUCT_DEF
            case 49:   // MORE_ARRAY_DIM -> PLACEHOLDER
            case 51:   // NUMBER -> CONST_BOOLEAN
            case 59:   // WHILE_BODY -> STATEMENT
            case 60:   // STATEMENT_VAR_DEF -> VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
            case 63:   // DECLARE_MORE -> COMMA DECLARE_INITIALIZE DECLARE_MORE
            case 69:   // DECLARE_INITIALIZE -> ID ASSIGN EXP_R
            case 70:   // DECLARE_INITIALIZE -> STAR ID ASSIGN EXP_R
            case 95:   // VAR_DEF_TYPE -> DT_BOOLEAN
            case 99:   // FUNC_DEF_TYPE -> DT_BOOLEAN
                reportSemanticError(String.format("Unsupported producer %s!", node.getProducer()));
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
                    Manager.addTetrad("j", "/*/", "/*/", fatherSymbol.getNextLabel());
                    S_ExpR_Log uncle2 = (S_ExpR_Log) node.getFather().getFather().childAt(2).getSymbol();
                    uncle2.setGoFalseLabel(Manager.nextLabel());
                    symbol.inherentNextLabel(fatherSymbol.getNextLabel());
                } else if (node.getFather().getProducer() == 58) {
                    // WHILE_BODY -> {?} STATEMENTS_BLOCK
                    S_StatementWhile grandpaSymbol = (S_StatementWhile) node.getFather().getFather().getSymbol();
                    S_ExpR_Log uncle2 = (S_ExpR_Log) node.getFather().getFather().childAt(2).getSymbol();
                    uncle2.setGoTrueLabel(Manager.nextLabel());
                    symbol.inherentNextLabel(grandpaSymbol.getStartLabel());
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
            case 16:   // STATEMENT -> EXP_R SEMICOLON
            case 17:   // STATEMENT -> SEMICOLON
                node.setSymbol(new S_AnyStatement(getIdTableOfFather(node)));
                break;
            case 18:   // EXP_R -> EXP_R LT EXP_R
            case 19:   // EXP_R -> EXP_R LE EXP_R
            case 20:   // EXP_R -> EXP_R GT EXP_R
            case 21:   // EXP_R -> EXP_R GE EXP_R
            case 22:   // EXP_R -> EXP_R NE EXP_R
            case 23:   // EXP_R -> EXP_R EQ EXP_R
            {
                S_ExpR_Log symbol = new S_ExpR_Log(getIdTableOfFather(node));
                node.setSymbol(symbol);
                if (node.getFather().getProducer() == 57) {
                    // STATEMENT_WHILE: WHILE ROUND_LEFT {?} EXP_R ROUND_RIGHT WHILE_BODY
                    S_StatementWhile fatherSymbol = (S_StatementWhile) node.getFather().getSymbol();
                    symbol.inherentGoFalseLabel(fatherSymbol.getNextLabel());
                }
                break;
            }
            case 24:   // EXP_R -> EXP_R PLUS EXP_R
            case 25:   // EXP_R -> EXP_R MINUS EXP_R
            case 26:   // EXP_R -> EXP_R STAR EXP_R
            case 27:   // EXP_R -> EXP_R DIVIDE EXP_R
            case 28:   // EXP_R -> EXP_R MOD EXP_R
                node.setSymbol(new S_ExpR_Ari(getIdTableOfFather(node)));
                break;
            case 36:   // EXP_R -> ROUND_LEFT EXP_R ROUND_RIGHT
                node.setSymbol(new S_ExpR(getIdTableOfFather(node)));
                break;
            case 37:   // EXP_R -> EXP_L
            case 38:   // EXP_R -> APSAND EXP_L
            case 39:   // EXP_R -> STAR EXP_L
                node.setSymbol(new S_ExpR_Ari(getIdTableOfFather(node)));
                break;
            case 40:   // EXP_R -> CONST_STRING
                break;
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
            case 50:   // NUMBER -> CONST_INTEGER
            case 52:   // NUMBER -> CONST_FLOAT
                break;
            case 53:   // STATEMENT_IF -> IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE
                node.setSymbol(new S_StatementIf(getIdTableOfFather(node)));
                break;
            case 54:   // STATEMENT_ELSE -> ELSE STATEMENTS_BLOCK
            {
                S_AnyStatement symbol = new S_AnyStatement(getIdTableOfFather(node));
                node.setSymbol(symbol);
                S_StatementIf fatherSymbol = (S_StatementIf) node.getFather().getSymbol();
                symbol.inherentNextLabel(fatherSymbol.getNextLabel());
                break;
            }
            case 55:   // STATEMENT_ELSE -> ELSE STATEMENT_IF
            case 56:   // STATEMENT_ELSE -> %empty
                break;
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
                node.setSymbol(new S_AnyStructMemberDef(getStructTypeOfFather(node)));
                break;
            }
            case 76:   // DECLARE_MORE_NON_INITIALIZE -> COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE
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
            case 78:  // STATEMENT_RETURN -> RETURN EXP_R SEMICOLON
            {
                node.setSymbol(new S_AnyStatement(getIdTableOfFather(node)));
                break;
            }
            case 83:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
            case 84:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
                node.setSymbol(new S_StatementFuncDef());
                break;
            case 85:   // RECV_FUNC_ARGS -> COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
            case 86:   // RECV_FUNC_ARGS -> %empty
                node.setSymbol(new S_FuncParamList(getIdTableOfFather(node)));
                break;
            case 87:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE ID
            case 88:   // SINGLE_RECV_FUNC_ARG -> FUNC_DEF_TYPE RECV_HD_ARRAY
                node.setSymbol(new S_FuncParam(getIdTableOfFather(node)));
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
                reportSemanticError();
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
            case 49:   // MORE_ARRAY_DIM -> PLACEHOLDER
            case 51:   // NUMBER -> CONST_BOOLEAN
            case 59:   // WHILE_BODY -> STATEMENT
            case 60:   // STATEMENT_VAR_DEF -> VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
            case 63:   // DECLARE_MORE -> COMMA DECLARE_INITIALIZE DECLARE_MORE
            case 69:   // DECLARE_INITIALIZE -> ID ASSIGN EXP_R
            case 70:   // DECLARE_INITIALIZE -> STAR ID ASSIGN EXP_R
            case 95:   // VAR_DEF_TYPE -> DT_BOOLEAN
            case 99:   // FUNC_DEF_TYPE -> DT_BOOLEAN
                reportSemanticError(String.format("Unsupported producer %s!", node.getProducer()));
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
                    reportSemanticError();
                    return false;
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
            case 16:   // STATEMENT -> EXP_R SEMICOLON
            {
                S_AnyStatement symbol = (S_AnyStatement) node.getSymbol();
                S_AnyStatement child0 = (S_AnyStatement) node.childAt(0).getSymbol();
                symbol.inherentNextLabel(child0.getNextLabel());
                break;
            }
            case 17:   // STATEMENT -> SEMICOLON
                break;
            case 18:   // EXP_R -> EXP_R LT EXP_R
            case 19:   // EXP_R -> EXP_R LE EXP_R
            {
                S_ExpR_Log symbol = (S_ExpR_Log) node.getSymbol();
                S_ExpR_Ari child0 = (S_ExpR_Ari) node.childAt(0).getSymbol();
                S_ExpR_Ari child2 = (S_ExpR_Ari) node.childAt(2).getSymbol();
                int reg1 = child0.getRegId();
                int reg2 = child2.getRegId();
                Manager.addTetrad("j<=", Manager.reg2String(reg1), Manager.reg2String(reg2), symbol.getGoTrue());
                Manager.addTetrad("j", "/*/", "/*/", symbol.getGoFalse());
                break;
            }
            case 20:   // EXP_R -> EXP_R GT EXP_R
            case 21:   // EXP_R -> EXP_R GE EXP_R
            case 22:   // EXP_R -> EXP_R NE EXP_R
            case 23:   // EXP_R -> EXP_R EQ EXP_R
            {
                S_ExpR_Log symbol = (S_ExpR_Log) node.getSymbol();
                S_ExpR_Ari child0 = (S_ExpR_Ari) node.childAt(0).getSymbol();
                S_ExpR_Ari child2 = (S_ExpR_Ari) node.childAt(2).getSymbol();
                int reg1 = child0.getRegId();
                int reg2 = child2.getRegId();
                Manager.addTetrad("j==", Manager.reg2String(reg1), Manager.reg2String(reg2), symbol.getGoTrue());
                Manager.addTetrad("j", "/*/", "/*/", symbol.getGoFalse());
                break;
            }
            case 24:   // EXP_R -> EXP_R PLUS EXP_R
            {
                if (!doWithArithmeticExpR(node, "+")) {
                    reportSemanticError("Type not match!");
                    return false;
                }
                break;
            }
            case 25:   // EXP_R -> EXP_R MINUS EXP_R
            {
                if (!doWithArithmeticExpR(node, "-")) {
                    reportSemanticError("Type not match!");
                    return false;
                }
                break;
            }
            case 26:   // EXP_R -> EXP_R STAR EXP_R
            {
                if (!doWithArithmeticExpR(node, "*")) {
                    reportSemanticError("Type not match!");
                    return false;
                }
                break;
            }
            case 27:   // EXP_R -> EXP_R DIVIDE EXP_R
            {
                if (!doWithArithmeticExpR(node, "/")) {
                    reportSemanticError("Type not match!");
                    return false;
                }
                break;
            }
            case 28:   // EXP_R -> EXP_R MOD EXP_R
            {
                if (!doWithArithmeticExpR(node, "%")) {
                    reportSemanticError("Type not match!");
                    return false;
                }
                break;
            }
            case 36:   // EXP_R -> ROUND_LEFT EXP_R ROUND_RIGHT
            {
                S_ExpR child1 = (S_ExpR) node.getFather().childAt(1).getSymbol();
                if (child1.isLogical()) {

                } else if (child1.isArithmetic()) {
                    S_ExpR_Ari child1_ari = (S_ExpR_Ari) node.getFather().childAt(1).getSymbol();
                    S_ExpR_Ari symbol_ari = (S_ExpR_Ari) node.getSymbol();
                    node.setSymbol(symbol_ari, true);
                    symbol_ari.setType(child1.getType());
                    Manager.addTetrad("=", Manager.reg2String(child1_ari.getRegId()), "/*/",
                            Manager.reg2String(symbol_ari.getRegId()));
                } else {

                }
                break;
            }
            case 37:   // EXP_R -> EXP_L
            {
                S_ExpR_Ari symbol = (S_ExpR_Ari) node.getSymbol();
                S_ExpL child0 = (S_ExpL) node.childAt(0).getSymbol();
                int regId = symbol.getRegId();
                symbol.setType(child0.getEntry().getType());
                Manager.addTetrad("=", child0.getFullRef(), "/*/", Manager.reg2String(regId));
            }
            case 38:   // EXP_R -> APSAND EXP_L
            case 39:   // EXP_R -> STAR EXP_L
            case 40:   // EXP_R -> CONST_STRING
                break;
            case 41:   // EXP_R -> NUMBER
            {
                S_ExpR_Ari symbol = (S_ExpR_Ari) node.getSymbol();
                S_Number child0 = (S_Number) node.childAt(0).getSymbol();
                int regId = symbol.getRegId();
                symbol.setType(child0.getType());
                symbol.setValue(child0.getValue());
                Manager.addTetrad("=", child0.getValue().toString(), "/*/", Manager.reg2String(regId));
                break;
            }
            case 42:   // EXP_R -> FUNC_CALL
                break;
            case 43:   // EXP_L -> ID
            {
                S_ExpL symbol = (S_ExpL) node.getSymbol();
                String id = node.childAt(0).getValue().toString();
                Entry entry = symbol.getIdentifierTable().getEntryById(id);
                if (entry == null) {
                    reportSemanticError(String.format("Use undefined identifier '%s'!", id));
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
                Entry memberEntry = symbol.getIdentifierTable().getStructMemberEntry(leftEntry, rightId);
                if (memberEntry == null) {
                    reportSemanticError(String.format("Struct member %s of %s not defined!", rightId, leftEntry.getId()));
                    return false;
                }
                symbol.setFullRef(child0.getFullRef() + "." + rightId);
                symbol.setEntry(memberEntry);
                break;
            }
            case 45:   // EXP_L -> HD_ARRAY
            {
                S_ExpL symbol = (S_ExpL) node.getSymbol();
                S_HDArray_Ref child0 = (S_HDArray_Ref) node.childAt(0).getSymbol();
                ArrayRefEntry entry = ArrayRefEntry.getArrayRefEntry(
                        symbol.getIdentifierTable(),
                        child0.getId(),
                        child0.getIndexRegList());
                if (entry == null) {
                    reportSemanticError(String.format("Use undefined array or not matched reference! array id is '%s'",
                            child0.getId()));
                    return false;
                }
                symbol.setEntry(entry);
                symbol.setFullRef(entry.getFullRef());
                break;
            }
            case 46:   // EXP_L -> EXP_L DOT HD_ARRAY
                break;
            case 47:   // HD_ARRAY -> ID SQUARE_LEFT EXP_R SQUARE_RIGHT
            case 48:   // HD_ARRAY -> HD_ARRAY SQUARE_LEFT EXP_R SQUARE_RIGHT
            {
                boolean isRef = ((S_HDArray) node.getSymbol()).isRef();
                if (isRef) {
                    S_HDArray_Ref symbol = (S_HDArray_Ref) node.getSymbol();
                    S_ExpR child2 = (S_ExpR) node.childAt(2).getSymbol();
                    if (child2.getType().getTypeName() != Type.TypeName.INTEGER) {
                        reportSemanticError("Array reference should use integers!");
                        return false;
                    }
                    if (node.getProducer() == 48) {
                        S_HDArray_Ref child0 = (S_HDArray_Ref) node.childAt(0).getSymbol();
                        symbol.addIndexRegList(child0.getIndexRegList());
                        symbol.setId(child0.getId());
                    }
                    else {
                        symbol.setId(node.childAt(0).getValue().toString());
                    }
                    symbol.addIndexReg(((S_ExpR_Ari) child2).getRegId());
                } else {
                    S_HDArray_Def symbol = (S_HDArray_Def) node.getSymbol();
                    S_ExpR child2 = (S_ExpR) node.childAt(2).getSymbol();
                    if (child2.getType().getTypeName() != Type.TypeName.INTEGER) {
                        reportSemanticError("Array dim definition should use integers!");
                        return false;
                    }
                    Number num = ((S_ExpR_Ari) child2).getValue();
                    if (num == null) {
                        reportSemanticError("Array dim definition should use constants!");
                        return false;
                    }
                    if (node.getProducer() == 48) {
                        S_HDArray_Def child0 = (S_HDArray_Def) node.childAt(0).getSymbol();
                        symbol.addDimSizeList(child0.getDimSizeList());
                        symbol.setId(child0.getId());
                    }
                    else {
                        symbol.setId(node.childAt(0).getValue().toString());
                    }
                    symbol.addDimSize((Integer) num);
                }
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
                Manager.addTetrad("j", "/*/", "/*/", symbol.getStartLabel());
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
                        reportSemanticError();
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    symbol.setTypeId(symbol.getVarType(), node.childAt(0).getValue().toString());
                    Entry entry = symbol.getIdentifierTable().addEntry(symbol.getType(), symbol.getId());
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
                    String id = node.childAt(1).getValue().toString();
                    symbol.setTypeId(type, id);
                    if (symbol.getStructType().addMember(type, id) == null) {
                        reportSemanticError();
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    symbol.setTypeId(new PointerType(symbol.getVarType()),
                            node.childAt(1).getValue().toString());
                    Entry entry = symbol.getIdentifierTable().addEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError();
                        return false;
                    }
                }
                break;
            case 67:   // DECLARE_NON_INITIALIZE -> HD_ARRAY
                if (node.getSymbol() instanceof S_StructSingleMember) {
                    S_StructSingleMember symbol = (S_StructSingleMember) node.getSymbol();
                    S_HDArray_Def child0 = (S_HDArray_Def) node.childAt(0).getSymbol();
                    symbol.setTypeId(child0.convert2ArrayType(symbol.getVarType()), child0.getId());
                    if (symbol.getStructType().addMember(symbol.getType(), symbol.getId()) == null) {
                        reportSemanticError();
                        return false;
                    }
                } else if (node.getSymbol() instanceof S_DefSingleVar) {
                    S_DefSingleVar symbol = (S_DefSingleVar) node.getSymbol();
                    S_HDArray_Def child0 = (S_HDArray_Def) node.childAt(0).getSymbol();
                    symbol.setTypeId(child0.convert2ArrayType(symbol.getVarType()), child0.getId());
                    Entry entry = symbol.getIdentifierTable().addEntry(symbol.getType(), symbol.getId());
                    if (entry == null) {
                        reportSemanticError();
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
                        reportSemanticError();
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
                        reportSemanticError();
                        return false;
                    }
                }
                break;
            case 71:   // STATEMENT_ASSIGN -> EXP_L ASSIGN EXP_R SEMICOLON
            {
                S_ExpL expL = (S_ExpL) node.childAt(0).getSymbol();
                S_ExpR expR = (S_ExpR) node.childAt(2).getSymbol();
                if (!expR.isArithmetic()) {
                    reportSemanticError("Assign right value not match!");
                    return false;
                }
                S_ExpR_Ari expR_ari = (S_ExpR_Ari) expR;
                Manager.addTetrad("=", Manager.reg2String(expR_ari.getRegId()), "/*/", expL.getFullRef());
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
                    reportSemanticError();
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
                S_ExpR child1 = (S_ExpR) node.childAt(1).getSymbol();
                if (!child1.isArithmetic()) {
                    reportSemanticError("Return type not match!");
                    return false;
                }
                S_ExpR_Ari child1_ari = (S_ExpR_Ari) child1;
                Manager.addTetrad("ret", Manager.reg2String(child1_ari.getRegId()), "/*/", "/*/");
                break;
            }
            case 83:   // STATEMENT_FUNC_DEF -> FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
            {
                S_StatementFuncDef symbol = (S_StatementFuncDef) node.getSymbol();
                S_AnyType child1 = (S_AnyType) node.childAt(1).getSymbol();
                symbol.setFunctionType(new FunctionType(List.of(), child1.getType()));
                IdentifierTable.getGlobalTable().addEntry(symbol.getFunctionType(),
                        node.childAt(2).getValue().toString());
                Manager.addTetrad("ret", "/*/", "/*/", "/*/");
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
                IdentifierTable.getGlobalTable().addEntry(
                        symbol.getFunctionType(), node.childAt(2).getValue().toString());
                break;
            }
            case 85:   // RECV_FUNC_ARGS -> COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
            {
                S_FuncParamList symbol = (S_FuncParamList) node.getSymbol();
                S_FuncParam child1 = (S_FuncParam) node.childAt(1).getSymbol();
                S_FuncParamList child2 = (S_FuncParamList) node.childAt(2).getSymbol();
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
                symbol.getIdentifierTable().addEntry(symbol.getType(), symbol.getId());
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
        throw new RuntimeException();
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
