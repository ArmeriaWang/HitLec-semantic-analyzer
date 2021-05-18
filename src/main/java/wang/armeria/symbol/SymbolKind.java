package wang.armeria.symbol;

public enum SymbolKind {
    S_YYEOF(0),                    /* "end of file"  */
    S_YYerror(1),                  /* error  */
    S_YYUNDEF(2),                  /* "invalid token"  */
    S_DT_INTEGER(3),               /* DT_INTEGER  */
    S_DT_BOOLEAN(4),               /* DT_BOOLEAN  */
    S_DT_FLOAT(5),                 /* DT_FLOAT  */
    S_STRUCT(6),                   /* STRUCT  */
    S_WHILE(7),                    /* WHILE  */
    S_IF(8),                       /* IF  */
    S_ELSE(9),                     /* ELSE  */
    S_RETURN(10),                  /* RETURN  */
    S_FUNCTION(11),                /* FUNCTION  */
    S_CONST_INTEGER(12),           /* CONST_INTEGER  */
    S_CONST_FLOAT(13),             /* CONST_FLOAT  */
    S_CONST_STRING(14),            /* CONST_STRING  */
    S_CONST_BOOLEAN(15),           /* CONST_BOOLEAN  */
    S_ID(16),                      /* ID  */
    S_SEMICOLON(17),               /* SEMICOLON  */
    S_COMMA(18),                   /* COMMA  */
    S_DOT(19),                     /* DOT  */
    S_ROUND_LEFT(20),              /* ROUND_LEFT  */
    S_ROUND_RIGHT(21),             /* ROUND_RIGHT  */
    S_SQUARE_LEFT(22),             /* SQUARE_LEFT  */
    S_SQUARE_RIGHT(23),            /* SQUARE_RIGHT  */
    S_BEGIN(24),                   /* BEGIN  */
    S_END(25),                     /* END  */
    S_ASSIGN(26),                  /* ASSIGN  */
    S_LOR(27),                     /* LOR  */
    S_LAND(28),                    /* LAND  */
    S_LNOT(29),                     /* LNOT  */
    S_BXOR(30),                    /* BXOR  */
    S_APSAND(31),                  /* APSAND  */
    S_EQ(32),                      /* EQ  */
    S_NE(33),                      /* NE  */
    S_LE(34),                      /* LE  */
    S_GE(35),                      /* GE  */
    S_LT(36),                      /* LT  */
    S_GT(37),                      /* GT  */
    S_PLUS(38),                    /* PLUS  */
    S_MINUS(39),                   /* MINUS  */
    S_STAR(40),                    /* STAR  */
    S_DIVIDE(41),                  /* DIVIDE  */
    S_MOD(42),                     /* MOD  */
    S_GET_ADDR(43),                /* GET_ADDR  */
    S_DEREF(44),                   /* DEREF  */
    S_POSITIVE(45),                /* POSITIVE  */
    S_NEGATIVE(46),                /* NEGATIVE  */
    S_YYACCEPT(47),                /* $accept  */
    S_PROGRAM(48),                 /* PROGRAM  */
    S_TOP_STATEMENTS(49),          /* TOP_STATEMENTS  */
    S_STATEMENTS_BLOCK(50),        /* STATEMENTS_BLOCK  */
    S_STATEMENTS(51),              /* STATEMENTS  */
    S_STATEMENT(52),               /* STATEMENT  */
    S_EXP_R(53),                   /* EXP_R  */
    S_EXP_L(54),                   /* EXP_L  */
    S_HD_ARRAY(55),                /* HD_ARRAY  */
    S_STATEMENT_EXP_R(56),          /* MORE_ARRAY_DIM  */
    S_NUMBER(57),                  /* NUMBER  */
    S_STATEMENT_IF(58),            /* STATEMENT_IF  */
    S_STATEMENT_ELSE(59),          /* STATEMENT_ELSE  */
    S_STATEMENT_WHILE(60),         /* STATEMENT_WHILE  */
    S_WHILE_BODY(61),              /* WHILE_BODY  */
    S_STATEMENT_VAR_DEF(62),       /* STATEMENT_VAR_DEF  */
    S_DECLARE_MORE(63),            /* DECLARE_MORE  */
    S_DECLARE_NON_INITIALIZE(64),  /* DECLARE_NON_INITIALIZE  */
    S_DECLARE_INITIALIZE(65),      /* DECLARE_INITIALIZE  */
    S_STATEMENT_ASSIGN(66),        /* STATEMENT_ASSIGN  */
    S_STATEMENT_STRUCT_DEF(67),    /* STATEMENT_STRUCT_DEF  */
    S_MORE_STRUCT_MEMBER_DEF(68),  /* MORE_STRUCT_MEMBER_DEF  */
    S_STRUCT_MEMBER_DEF(69),       /* STRUCT_MEMBER_DEF  */
    S_DECLARE_MORE_NON_INITIALIZE(70), /* DECLARE_MORE_NON_INITIALIZE  */
    S_STATEMENT_RETURN(71),        /* STATEMENT_RETURN  */
    S_FUNC_CALL(72),               /* FUNC_CALL  */
    S_SEND_FUNC_ARGS(73),          /* SEND_FUNC_ARGS  */
    S_STATEMENT_FUNC_DEF(74),      /* STATEMENT_FUNC_DEF  */
    S_RECV_FUNC_ARGS(75),          /* RECV_FUNC_ARGS  */
    S_SINGLE_RECV_FUNC_ARG(76),    /* SINGLE_RECV_FUNC_ARG  */
    S_RECV_HD_ARRAY(77),           /* RECV_HD_ARRAY  */
    S_DT_STRUCT(78),               /* DT_STRUCT  */
    S_DT_POINTER(79),              /* DT_POINTER  */
    S_VAR_DEF_TYPE(80),            /* VAR_DEF_TYPE  */
    S_FUNC_DEF_TYPE(81),           /* FUNC_DEF_TYPE  */

    // symbols for analysis here
    S_ANY_ID_DEF(82),
    S_ANY_TYPE(83),
    S_ANY_ID_DEF_LIST(84),
    S_ANY_STRUCT_MEMBER_DEF(85);

    private final int yycode_;

    SymbolKind(int n) {
        this.yycode_ = n;
    }

    private static final SymbolKind[] values_ = {
            SymbolKind.S_YYEOF,
            SymbolKind.S_YYerror,
            SymbolKind.S_YYUNDEF,
            SymbolKind.S_DT_INTEGER,
            SymbolKind.S_DT_BOOLEAN,
            SymbolKind.S_DT_FLOAT,
            SymbolKind.S_STRUCT,
            SymbolKind.S_WHILE,
            SymbolKind.S_IF,
            SymbolKind.S_ELSE,
            SymbolKind.S_RETURN,
            SymbolKind.S_FUNCTION,
            SymbolKind.S_CONST_INTEGER,
            SymbolKind.S_CONST_FLOAT,
            SymbolKind.S_CONST_STRING,
            SymbolKind.S_CONST_BOOLEAN,
            SymbolKind.S_ID,
            SymbolKind.S_SEMICOLON,
            SymbolKind.S_COMMA,
            SymbolKind.S_DOT,
            SymbolKind.S_ROUND_LEFT,
            SymbolKind.S_ROUND_RIGHT,
            SymbolKind.S_SQUARE_LEFT,
            SymbolKind.S_SQUARE_RIGHT,
            SymbolKind.S_BEGIN,
            SymbolKind.S_END,
            SymbolKind.S_ASSIGN,
            SymbolKind.S_LOR,
            SymbolKind.S_LAND,
            SymbolKind.S_LNOT,
            SymbolKind.S_BXOR,
            SymbolKind.S_APSAND,
            SymbolKind.S_EQ,
            SymbolKind.S_NE,
            SymbolKind.S_LE,
            SymbolKind.S_GE,
            SymbolKind.S_LT,
            SymbolKind.S_GT,
            SymbolKind.S_PLUS,
            SymbolKind.S_MINUS,
            SymbolKind.S_STAR,
            SymbolKind.S_DIVIDE,
            SymbolKind.S_MOD,
            SymbolKind.S_GET_ADDR,
            SymbolKind.S_DEREF,
            SymbolKind.S_POSITIVE,
            SymbolKind.S_NEGATIVE,
            SymbolKind.S_YYACCEPT,
            SymbolKind.S_PROGRAM,
            SymbolKind.S_TOP_STATEMENTS,
            SymbolKind.S_STATEMENTS_BLOCK,
            SymbolKind.S_STATEMENTS,
            SymbolKind.S_STATEMENT,
            SymbolKind.S_EXP_R,
            SymbolKind.S_EXP_L,
            SymbolKind.S_HD_ARRAY,
            SymbolKind.S_STATEMENT_EXP_R,
            SymbolKind.S_NUMBER,
            SymbolKind.S_STATEMENT_IF,
            SymbolKind.S_STATEMENT_ELSE,
            SymbolKind.S_STATEMENT_WHILE,
            SymbolKind.S_WHILE_BODY,
            SymbolKind.S_STATEMENT_VAR_DEF,
            SymbolKind.S_DECLARE_MORE,
            SymbolKind.S_DECLARE_NON_INITIALIZE,
            SymbolKind.S_DECLARE_INITIALIZE,
            SymbolKind.S_STATEMENT_ASSIGN,
            SymbolKind.S_STATEMENT_STRUCT_DEF,
            SymbolKind.S_MORE_STRUCT_MEMBER_DEF,
            SymbolKind.S_STRUCT_MEMBER_DEF,
            SymbolKind.S_DECLARE_MORE_NON_INITIALIZE,
            SymbolKind.S_STATEMENT_RETURN,
            SymbolKind.S_FUNC_CALL,
            SymbolKind.S_SEND_FUNC_ARGS,
            SymbolKind.S_STATEMENT_FUNC_DEF,
            SymbolKind.S_RECV_FUNC_ARGS,
            SymbolKind.S_SINGLE_RECV_FUNC_ARG,
            SymbolKind.S_RECV_HD_ARRAY,
            SymbolKind.S_DT_STRUCT,
            SymbolKind.S_DT_POINTER,
            SymbolKind.S_VAR_DEF_TYPE,
            SymbolKind.S_FUNC_DEF_TYPE,
            SymbolKind.S_ANY_ID_DEF,
            SymbolKind.S_ANY_ID_DEF_LIST,
            SymbolKind.S_ANY_TYPE,
            SymbolKind.S_ANY_STRUCT_MEMBER_DEF
    };

    static final SymbolKind get(int code) {
        return values_[code];
    }

    public final int getCode() {
        return this.yycode_;
    }

    /* Return YYSTR after stripping away unnecessary quotes and
   backslashes, so that it's suitable for yyerror.  The heuristic is
   that double-quoting is unnecessary unless the string contains an
   apostrophe, a comma, or backslash (other than backslash-backslash).
   YYSTR is taken from yytname.  */
    private static String yytnamerr_(String yystr) {
        if (yystr.charAt(0) == '"') {
            StringBuffer yyr = new StringBuffer();
            strip_quotes:
            for (int i = 1; i < yystr.length(); i++)
                switch (yystr.charAt(i)) {
                    case '\'':
                    case ',':
                        break strip_quotes;

                    case '\\':
                        if (yystr.charAt(++i) != '\\')
                            break strip_quotes;
                        /* Fall through.  */
                    default:
                        yyr.append(yystr.charAt(i));
                        break;

                    case '"':
                        return yyr.toString();
                }
        }
        return yystr;
    }

    /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at \a YYNTOKENS_, nonterminals.  */
    private static final String[] yytname_ = yytname_init();

    private static final String[] yytname_init() {
        return new String[]
                {
                        "\"end of file\"", "error", "\"invalid token\"", "DT_INTEGER",
                        "DT_BOOLEAN", "DT_FLOAT", "STRUCT", "WHILE", "IF", "ELSE", "RETURN",
                        "FUNCTION", "CONST_INTEGER", "CONST_FLOAT", "CONST_STRING",
                        "CONST_BOOLEAN", "ID", "SEMICOLON", "COMMA", "DOT", "ROUND_LEFT",
                        "ROUND_RIGHT", "SQUARE_LEFT", "SQUARE_RIGHT", "BEGIN", "END", "ASSIGN",
                        "LOR", "LAND", "LNOT", "BXOR", "APSAND", "EQ", "NE", "LE", "GE", "LT",
                        "GT", "PLUS", "MINUS", "STAR", "DIVIDE", "MOD", "GET_ADDR", "DEREF",
                        "POSITIVE", "NEGATIVE", "$accept", "PROGRAM", "TOP_STATEMENTS",
                        "STATEMENTS_BLOCK", "STATEMENTS", "STATEMENT", "EXP_R", "EXP_L",
                        "HD_ARRAY", "MORE_ARRAY_DIM", "NUMBER", "STATEMENT_IF", "STATEMENT_ELSE",
                        "STATEMENT_WHILE", "WHILE_BODY", "STATEMENT_VAR_DEF", "DECLARE_MORE",
                        "DECLARE_NON_INITIALIZE", "DECLARE_INITIALIZE", "STATEMENT_ASSIGN",
                        "STATEMENT_STRUCT_DEF", "MORE_STRUCT_MEMBER_DEF", "STRUCT_MEMBER_DEF",
                        "DECLARE_MORE_NON_INITIALIZE", "STATEMENT_RETURN", "FUNC_CALL",
                        "SEND_FUNC_ARGS", "STATEMENT_FUNC_DEF", "RECV_FUNC_ARGS",
                        "SINGLE_RECV_FUNC_ARG", "RECV_HD_ARRAY", "DT_STRUCT", "DT_POINTER",
                        "VAR_DEF_TYPE", "FUNC_DEF_TYPE", null
                };
    }

    /* The user-facing name of this symbol.  */
    public final String getName() {
        return yytnamerr_(yytname_[yycode_]);
    }

}