package wang.armeria.common;

/**
 * token类型
 */
public enum TokenType {
    IDENTIFIER,

    CONST_INTEGER,
    CONST_FLOAT,
    CONST_BOOLEAN,
    CONST_STRING,

    KW_DO,
    KW_WHILE,
    KW_FOR,
    KW_IF,
    KW_ELSE,
    KW_RETURN,
    KW_STRUCT,
    KW_FUNCTION,

    DT_INTEGER,
    DT_FLOAT,
    DT_BOOLEAN,

    EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    NOT_EQUAL,

    PLUS,
    MINUS,
    STAR,
    DIVIDE,
    MOD,
    LOGIC_AND,
    LOGIC_OR,
    LOGIC_NOT,
    AMPERSAND,
    BIT_OR,
    BIT_NOT,
    BIT_XOR,

    ASSIGNMENT,
    ASN_PLUS,
    ASN_MINUS,
    ASN_MULTIPLY,
    ASN_DIVIDE,
    ASN_MOD,
    ASN_AND,
    ASN_OR,
    ASN_XOR,
    ASN_NOT,
    SELF_INCREASE,
    SELF_DECREASE,

    BEGIN,
    END,
    COMMA,
    SEMICOLON,
    DOT,
    ARROW,
    SQUARE_LEFT,
    SQUARE_RIGHT,
    ROUND_LEFT,
    ROUND_RIGHT,

    COMMENT,

    ERROR,

    UNDETERMINED
}
