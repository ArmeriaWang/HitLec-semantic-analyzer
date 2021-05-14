Grammar

    0 $accept: PROGRAM $end

    1 PROGRAM: TOP_STATEMENTS

    2 TOP_STATEMENTS: STATEMENT_VAR_DEF TOP_STATEMENTS
    3               | STATEMENT_FUNC_DEF TOP_STATEMENTS
    4               | STATEMENT_STRUCT_DEF TOP_STATEMENTS
    5               | %empty

    6 STATEMENTS_BLOCK: BEGIN STATEMENTS END
    7                 | BEGIN END

    8 STATEMENTS: STATEMENT STATEMENTS
    9           | STATEMENT

   10 STATEMENT: STATEMENT_VAR_DEF
   11          | STATEMENT_ASSIGN
   12          | STATEMENT_IF
   13          | STATEMENT_WHILE
   14          | STATEMENT_STRUCT_DEF
   15          | STATEMENT_RETURN
   16          | EXP_R SEMICOLON
   17          | SEMICOLON

   18 EXP_R: EXP_R LT EXP_R
   19      | EXP_R LE EXP_R
   20      | EXP_R GT EXP_R
   21      | EXP_R GE EXP_R
   22      | EXP_R NE EXP_R
   23      | EXP_R EQ EXP_R
   24      | EXP_R PLUS EXP_R
   25      | EXP_R MINUS EXP_R
   26      | EXP_R STAR EXP_R
   27      | EXP_R DIVIDE EXP_R
   28      | EXP_R MOD EXP_R
   29      | EXP_R LOR EXP_R
   30      | EXP_R LAND EXP_R
   31      | EXP_R BOR EXP_R
   32      | EXP_R BXOR EXP_R
   33      | EXP_R APSAND EXP_R
   34      | PLUS EXP_R
   35      | MINUS EXP_R
   36      | ROUND_LEFT EXP_R ROUND_RIGHT
   37      | EXP_L
   38      | APSAND EXP_L
   39      | STAR EXP_L
   40      | CONST_STRING
   41      | NUMBER
   42      | FUNC_CALL

   43 EXP_L: ID
   44      | ID DOT EXP_L
   45      | HD_ARRAY
   46      | HD_ARRAY DOT EXP_L

   47 HD_ARRAY: ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

   48 MORE_ARRAY_DIM: SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   49               | %empty

   50 NUMBER: CONST_INTEGER
   51       | CONST_BOOLEAN
   52       | CONST_FLOAT

   53 STATEMENT_IF: IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE

   54 STATEMENT_ELSE: ELSE STATEMENTS_BLOCK
   55               | ELSE STATEMENT_IF
   56               | %empty

   57 STATEMENT_WHILE: WHILE ROUND_LEFT EXP_R ROUND_RIGHT WHILE_BODY

   58 WHILE_BODY: STATEMENTS_BLOCK
   59           | STATEMENT

   60 STATEMENT_VAR_DEF: VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
   61                  | VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE

   62 DECLARE_MORE: SEMICOLON
   63             | COMMA DECLARE_INITIALIZE DECLARE_MORE
   64             | COMMA DECLARE_NON_INITIALIZE DECLARE_MORE

   65 DECLARE_NON_INITIALIZE: ID
   66                       | STAR ID
   67                       | HD_ARRAY
   68                       | STAR HD_ARRAY

   69 DECLARE_INITIALIZE: ID ASSIGN EXP_R
   70                   | STAR ID ASSIGN EXP_R

   71 STATEMENT_ASSIGN: EXP_L ASSIGN EXP_R SEMICOLON

   72 STATEMENT_STRUCT_DEF: STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON

   73 MORE_STRUCT_MEMBER_DEF: STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF
   74                       | %empty

   75 STRUCT_MEMBER_DEF: VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON

   76 DECLARE_MORE_NON_INITIALIZE: COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE
   77                            | %empty

   78 STATEMENT_RETURN: RETURN EXP_R SEMICOLON

   79 FUNC_CALL: ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | ID ROUND_LEFT ROUND_RIGHT

   81 SEND_FUNC_ARGS: COMMA EXP_R SEND_FUNC_ARGS
   82               | %empty

   83 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
   84                   | FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK

   85 RECV_FUNC_ARGS: COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
   86               | %empty

   87 SINGLE_RECV_FUNC_ARG: FUNC_DEF_TYPE ID
   88                     | FUNC_DEF_TYPE RECV_HD_ARRAY

   89 RECV_HD_ARRAY: ID SQUARE_LEFT SQUARE_RIGHT MORE_ARRAY_DIM
   90              | ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

   91 DT_STRUCT: STRUCT ID

   92 DT_POINTER: VAR_DEF_TYPE STAR

   93 VAR_DEF_TYPE: DT_INTEGER
   94             | DT_FLOAT
   95             | DT_BOOLEAN
   96             | DT_STRUCT

   97 FUNC_DEF_TYPE: DT_INTEGER
   98              | DT_FLOAT
   99              | DT_BOOLEAN
  100              | DT_POINTER


Terminals, with rules where they appear

    $end (0) 0
    error (256)
    DT_INTEGER (258) 93 97
    DT_BOOLEAN (259) 95 99
    DT_FLOAT (260) 94 98
    STRUCT (261) 72 91
    WHILE (262) 57
    IF (263) 53
    ELSE (264) 54 55
    RETURN (265) 78
    FUNCTION (266) 83 84
    CONST_INTEGER (267) 50
    CONST_FLOAT (268) 52
    CONST_STRING (269) 40
    CONST_BOOLEAN (270) 51
    ID (271) 43 44 47 65 66 69 70 72 79 80 83 84 87 89 90 91
    SEMICOLON (272) 16 17 62 71 72 75 78
    COMMA (273) 63 64 76 81 85
    DOT (274) 44 46
    ROUND_LEFT (275) 36 53 57 79 80 83 84
    ROUND_RIGHT (276) 36 53 57 79 80 83 84
    SQUARE_LEFT (277) 47 48 89 90
    SQUARE_RIGHT (278) 47 48 89 90
    BEGIN (279) 6 7 72
    END (280) 6 7 72
    ASSIGN (281) 69 70 71
    LOR (282) 29
    LAND (283) 30
    BOR (284) 31
    BXOR (285) 32
    APSAND (286) 33 38
    EQ (287) 23
    NE (288) 22
    LE (289) 19
    GE (290) 21
    LT (291) 18
    GT (292) 20
    PLUS (293) 24 34
    MINUS (294) 25 35
    STAR (295) 26 39 66 68 70 92
    DIVIDE (296) 27
    MOD (297) 28
    GET_ADDR (298)
    DEREF (299)
    POSITIVE (300)
    NEGATIVE (301)


Nonterminals, with rules where they appear

    $accept (47)
        on left: 0
    PROGRAM (48)
        on left: 1
        on right: 0
    TOP_STATEMENTS (49)
        on left: 2 3 4 5
        on right: 1 2 3 4
    STATEMENTS_BLOCK (50)
        on left: 6 7
        on right: 53 54 58 83 84
    STATEMENTS (51)
        on left: 8 9
        on right: 6 8
    STATEMENT (52)
        on left: 10 11 12 13 14 15 16 17
        on right: 8 9 59
    EXP_R (53)
        on left: 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42
        on right: 16 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 47 48 53 57 69 70 71 78 79 81 90
    EXP_L (54)
        on left: 43 44 45 46
        on right: 37 38 39 44 46 71
    HD_ARRAY (55)
        on left: 47
        on right: 45 46 67 68
    MORE_ARRAY_DIM (56)
        on left: 48 49
        on right: 47 48 89 90
    NUMBER (57)
        on left: 50 51 52
        on right: 41
    STATEMENT_IF (58)
        on left: 53
        on right: 12 55
    STATEMENT_ELSE (59)
        on left: 54 55 56
        on right: 53
    STATEMENT_WHILE (60)
        on left: 57
        on right: 13
    WHILE_BODY (61)
        on left: 58 59
        on right: 57
    STATEMENT_VAR_DEF (62)
        on left: 60 61
        on right: 2 10
    DECLARE_MORE (63)
        on left: 62 63 64
        on right: 60 61 63 64
    DECLARE_NON_INITIALIZE (64)
        on left: 65 66 67 68
        on right: 61 64 75 76
    DECLARE_INITIALIZE (65)
        on left: 69 70
        on right: 60 63
    STATEMENT_ASSIGN (66)
        on left: 71
        on right: 11
    STATEMENT_STRUCT_DEF (67)
        on left: 72
        on right: 4 14
    MORE_STRUCT_MEMBER_DEF (68)
        on left: 73 74
        on right: 72 73
    STRUCT_MEMBER_DEF (69)
        on left: 75
        on right: 72 73
    DECLARE_MORE_NON_INITIALIZE (70)
        on left: 76 77
        on right: 75 76
    STATEMENT_RETURN (71)
        on left: 78
        on right: 15
    FUNC_CALL (72)
        on left: 79 80
        on right: 42
    SEND_FUNC_ARGS (73)
        on left: 81 82
        on right: 79 81
    STATEMENT_FUNC_DEF (74)
        on left: 83 84
        on right: 3
    RECV_FUNC_ARGS (75)
        on left: 85 86
        on right: 84 85
    SINGLE_RECV_FUNC_ARG (76)
        on left: 87 88
        on right: 84 85
    RECV_HD_ARRAY (77)
        on left: 89 90
        on right: 88
    DT_STRUCT (78)
        on left: 91
        on right: 96
    DT_POINTER (79)
        on left: 92
        on right: 100
    VAR_DEF_TYPE (80)
        on left: 93 94 95 96
        on right: 60 61 75 92
    FUNC_DEF_TYPE (81)
        on left: 97 98 99 100
        on right: 83 84 87 88


State 0

    0 $accept: • PROGRAM $end
    1 PROGRAM: • TOP_STATEMENTS
    2 TOP_STATEMENTS: • STATEMENT_VAR_DEF TOP_STATEMENTS
    3               | • STATEMENT_FUNC_DEF TOP_STATEMENTS
    4               | • STATEMENT_STRUCT_DEF TOP_STATEMENTS
    5               | • %empty  [$end]
   60 STATEMENT_VAR_DEF: • VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
   61                  | • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE
   72 STATEMENT_STRUCT_DEF: • STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   83 STATEMENT_FUNC_DEF: • FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
   84                   | • FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER  shift, and go to state 1
    DT_BOOLEAN  shift, and go to state 2
    DT_FLOAT    shift, and go to state 3
    STRUCT      shift, and go to state 4
    FUNCTION    shift, and go to state 5

    $default  reduce using rule 5 (TOP_STATEMENTS)

    PROGRAM               go to state 6
    TOP_STATEMENTS        go to state 7
    STATEMENT_VAR_DEF     go to state 8
    STATEMENT_STRUCT_DEF  go to state 9
    STATEMENT_FUNC_DEF    go to state 10
    DT_STRUCT             go to state 11
    VAR_DEF_TYPE          go to state 12


State 1

   93 VAR_DEF_TYPE: DT_INTEGER •

    $default  reduce using rule 93 (VAR_DEF_TYPE)


State 2

   95 VAR_DEF_TYPE: DT_BOOLEAN •

    $default  reduce using rule 95 (VAR_DEF_TYPE)


State 3

   94 VAR_DEF_TYPE: DT_FLOAT •

    $default  reduce using rule 94 (VAR_DEF_TYPE)


State 4

   72 STATEMENT_STRUCT_DEF: STRUCT • ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   91 DT_STRUCT: STRUCT • ID

    ID  shift, and go to state 13


State 5

   83 STATEMENT_FUNC_DEF: FUNCTION • FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
   84                   | FUNCTION • FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
   91 DT_STRUCT: • STRUCT ID
   92 DT_POINTER: • VAR_DEF_TYPE STAR
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT
   97 FUNC_DEF_TYPE: • DT_INTEGER
   98              | • DT_FLOAT
   99              | • DT_BOOLEAN
  100              | • DT_POINTER

    DT_INTEGER  shift, and go to state 14
    DT_BOOLEAN  shift, and go to state 15
    DT_FLOAT    shift, and go to state 16
    STRUCT      shift, and go to state 17

    DT_STRUCT      go to state 11
    DT_POINTER     go to state 18
    VAR_DEF_TYPE   go to state 19
    FUNC_DEF_TYPE  go to state 20


State 6

    0 $accept: PROGRAM • $end

    $end  shift, and go to state 21


State 7

    1 PROGRAM: TOP_STATEMENTS •

    $default  reduce using rule 1 (PROGRAM)


State 8

    2 TOP_STATEMENTS: • STATEMENT_VAR_DEF TOP_STATEMENTS
    2               | STATEMENT_VAR_DEF • TOP_STATEMENTS
    3               | • STATEMENT_FUNC_DEF TOP_STATEMENTS
    4               | • STATEMENT_STRUCT_DEF TOP_STATEMENTS
    5               | • %empty  [$end]
   60 STATEMENT_VAR_DEF: • VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
   61                  | • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE
   72 STATEMENT_STRUCT_DEF: • STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   83 STATEMENT_FUNC_DEF: • FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
   84                   | • FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER  shift, and go to state 1
    DT_BOOLEAN  shift, and go to state 2
    DT_FLOAT    shift, and go to state 3
    STRUCT      shift, and go to state 4
    FUNCTION    shift, and go to state 5

    $default  reduce using rule 5 (TOP_STATEMENTS)

    TOP_STATEMENTS        go to state 22
    STATEMENT_VAR_DEF     go to state 8
    STATEMENT_STRUCT_DEF  go to state 9
    STATEMENT_FUNC_DEF    go to state 10
    DT_STRUCT             go to state 11
    VAR_DEF_TYPE          go to state 12


State 9

    2 TOP_STATEMENTS: • STATEMENT_VAR_DEF TOP_STATEMENTS
    3               | • STATEMENT_FUNC_DEF TOP_STATEMENTS
    4               | • STATEMENT_STRUCT_DEF TOP_STATEMENTS
    4               | STATEMENT_STRUCT_DEF • TOP_STATEMENTS
    5               | • %empty  [$end]
   60 STATEMENT_VAR_DEF: • VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
   61                  | • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE
   72 STATEMENT_STRUCT_DEF: • STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   83 STATEMENT_FUNC_DEF: • FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
   84                   | • FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER  shift, and go to state 1
    DT_BOOLEAN  shift, and go to state 2
    DT_FLOAT    shift, and go to state 3
    STRUCT      shift, and go to state 4
    FUNCTION    shift, and go to state 5

    $default  reduce using rule 5 (TOP_STATEMENTS)

    TOP_STATEMENTS        go to state 23
    STATEMENT_VAR_DEF     go to state 8
    STATEMENT_STRUCT_DEF  go to state 9
    STATEMENT_FUNC_DEF    go to state 10
    DT_STRUCT             go to state 11
    VAR_DEF_TYPE          go to state 12


State 10

    2 TOP_STATEMENTS: • STATEMENT_VAR_DEF TOP_STATEMENTS
    3               | • STATEMENT_FUNC_DEF TOP_STATEMENTS
    3               | STATEMENT_FUNC_DEF • TOP_STATEMENTS
    4               | • STATEMENT_STRUCT_DEF TOP_STATEMENTS
    5               | • %empty  [$end]
   60 STATEMENT_VAR_DEF: • VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
   61                  | • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE
   72 STATEMENT_STRUCT_DEF: • STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   83 STATEMENT_FUNC_DEF: • FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
   84                   | • FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER  shift, and go to state 1
    DT_BOOLEAN  shift, and go to state 2
    DT_FLOAT    shift, and go to state 3
    STRUCT      shift, and go to state 4
    FUNCTION    shift, and go to state 5

    $default  reduce using rule 5 (TOP_STATEMENTS)

    TOP_STATEMENTS        go to state 24
    STATEMENT_VAR_DEF     go to state 8
    STATEMENT_STRUCT_DEF  go to state 9
    STATEMENT_FUNC_DEF    go to state 10
    DT_STRUCT             go to state 11
    VAR_DEF_TYPE          go to state 12


State 11

   96 VAR_DEF_TYPE: DT_STRUCT •

    $default  reduce using rule 96 (VAR_DEF_TYPE)


State 12

   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   60 STATEMENT_VAR_DEF: VAR_DEF_TYPE • DECLARE_INITIALIZE DECLARE_MORE
   61                  | VAR_DEF_TYPE • DECLARE_NON_INITIALIZE DECLARE_MORE
   65 DECLARE_NON_INITIALIZE: • ID
   66                       | • STAR ID
   67                       | • HD_ARRAY
   68                       | • STAR HD_ARRAY
   69 DECLARE_INITIALIZE: • ID ASSIGN EXP_R
   70                   | • STAR ID ASSIGN EXP_R

    ID    shift, and go to state 25
    STAR  shift, and go to state 26

    HD_ARRAY                go to state 27
    DECLARE_NON_INITIALIZE  go to state 28
    DECLARE_INITIALIZE      go to state 29


State 13

   72 STATEMENT_STRUCT_DEF: STRUCT ID • BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   91 DT_STRUCT: STRUCT ID •  [ID, STAR]

    BEGIN  shift, and go to state 30

    $default  reduce using rule 91 (DT_STRUCT)


State 14

   93 VAR_DEF_TYPE: DT_INTEGER •  [STAR]
   97 FUNC_DEF_TYPE: DT_INTEGER •  [ID]

    ID        reduce using rule 97 (FUNC_DEF_TYPE)
    $default  reduce using rule 93 (VAR_DEF_TYPE)


State 15

   95 VAR_DEF_TYPE: DT_BOOLEAN •  [STAR]
   99 FUNC_DEF_TYPE: DT_BOOLEAN •  [ID]

    ID        reduce using rule 99 (FUNC_DEF_TYPE)
    $default  reduce using rule 95 (VAR_DEF_TYPE)


State 16

   94 VAR_DEF_TYPE: DT_FLOAT •  [STAR]
   98 FUNC_DEF_TYPE: DT_FLOAT •  [ID]

    ID        reduce using rule 98 (FUNC_DEF_TYPE)
    $default  reduce using rule 94 (VAR_DEF_TYPE)


State 17

   91 DT_STRUCT: STRUCT • ID

    ID  shift, and go to state 31


State 18

  100 FUNC_DEF_TYPE: DT_POINTER •

    $default  reduce using rule 100 (FUNC_DEF_TYPE)


State 19

   92 DT_POINTER: VAR_DEF_TYPE • STAR

    STAR  shift, and go to state 32


State 20

   83 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE • ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
   84                   | FUNCTION FUNC_DEF_TYPE • ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK

    ID  shift, and go to state 33


State 21

    0 $accept: PROGRAM $end •

    $default  accept


State 22

    2 TOP_STATEMENTS: STATEMENT_VAR_DEF TOP_STATEMENTS •

    $default  reduce using rule 2 (TOP_STATEMENTS)


State 23

    4 TOP_STATEMENTS: STATEMENT_STRUCT_DEF TOP_STATEMENTS •

    $default  reduce using rule 4 (TOP_STATEMENTS)


State 24

    3 TOP_STATEMENTS: STATEMENT_FUNC_DEF TOP_STATEMENTS •

    $default  reduce using rule 3 (TOP_STATEMENTS)


State 25

   47 HD_ARRAY: ID • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   65 DECLARE_NON_INITIALIZE: ID •  [SEMICOLON, COMMA]
   69 DECLARE_INITIALIZE: ID • ASSIGN EXP_R

    SQUARE_LEFT  shift, and go to state 34
    ASSIGN       shift, and go to state 35

    $default  reduce using rule 65 (DECLARE_NON_INITIALIZE)


State 26

   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   66 DECLARE_NON_INITIALIZE: STAR • ID
   68                       | STAR • HD_ARRAY
   70 DECLARE_INITIALIZE: STAR • ID ASSIGN EXP_R

    ID  shift, and go to state 36

    HD_ARRAY  go to state 37


State 27

   67 DECLARE_NON_INITIALIZE: HD_ARRAY •

    $default  reduce using rule 67 (DECLARE_NON_INITIALIZE)


State 28

   61 STATEMENT_VAR_DEF: VAR_DEF_TYPE DECLARE_NON_INITIALIZE • DECLARE_MORE
   62 DECLARE_MORE: • SEMICOLON
   63             | • COMMA DECLARE_INITIALIZE DECLARE_MORE
   64             | • COMMA DECLARE_NON_INITIALIZE DECLARE_MORE

    SEMICOLON  shift, and go to state 38
    COMMA      shift, and go to state 39

    DECLARE_MORE  go to state 40


State 29

   60 STATEMENT_VAR_DEF: VAR_DEF_TYPE DECLARE_INITIALIZE • DECLARE_MORE
   62 DECLARE_MORE: • SEMICOLON
   63             | • COMMA DECLARE_INITIALIZE DECLARE_MORE
   64             | • COMMA DECLARE_NON_INITIALIZE DECLARE_MORE

    SEMICOLON  shift, and go to state 38
    COMMA      shift, and go to state 39

    DECLARE_MORE  go to state 41


State 30

   72 STATEMENT_STRUCT_DEF: STRUCT ID BEGIN • STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   75 STRUCT_MEMBER_DEF: • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER  shift, and go to state 1
    DT_BOOLEAN  shift, and go to state 2
    DT_FLOAT    shift, and go to state 3
    STRUCT      shift, and go to state 17

    STRUCT_MEMBER_DEF  go to state 42
    DT_STRUCT          go to state 11
    VAR_DEF_TYPE       go to state 43


State 31

   91 DT_STRUCT: STRUCT ID •

    $default  reduce using rule 91 (DT_STRUCT)


State 32

   92 DT_POINTER: VAR_DEF_TYPE STAR •

    $default  reduce using rule 92 (DT_POINTER)


State 33

   83 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE ID • ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK
   84                   | FUNCTION FUNC_DEF_TYPE ID • ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK

    ROUND_LEFT  shift, and go to state 44


State 34

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   47         | ID SQUARE_LEFT • EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 55
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 35

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   69 DECLARE_INITIALIZE: ID ASSIGN • EXP_R
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 60
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 36

   47 HD_ARRAY: ID • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   66 DECLARE_NON_INITIALIZE: STAR ID •  [SEMICOLON, COMMA]
   70 DECLARE_INITIALIZE: STAR ID • ASSIGN EXP_R

    SQUARE_LEFT  shift, and go to state 34
    ASSIGN       shift, and go to state 61

    $default  reduce using rule 66 (DECLARE_NON_INITIALIZE)


State 37

   68 DECLARE_NON_INITIALIZE: STAR HD_ARRAY •

    $default  reduce using rule 68 (DECLARE_NON_INITIALIZE)


State 38

   62 DECLARE_MORE: SEMICOLON •

    $default  reduce using rule 62 (DECLARE_MORE)


State 39

   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   63 DECLARE_MORE: COMMA • DECLARE_INITIALIZE DECLARE_MORE
   64             | COMMA • DECLARE_NON_INITIALIZE DECLARE_MORE
   65 DECLARE_NON_INITIALIZE: • ID
   66                       | • STAR ID
   67                       | • HD_ARRAY
   68                       | • STAR HD_ARRAY
   69 DECLARE_INITIALIZE: • ID ASSIGN EXP_R
   70                   | • STAR ID ASSIGN EXP_R

    ID    shift, and go to state 25
    STAR  shift, and go to state 26

    HD_ARRAY                go to state 27
    DECLARE_NON_INITIALIZE  go to state 62
    DECLARE_INITIALIZE      go to state 63


State 40

   61 STATEMENT_VAR_DEF: VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE •

    $default  reduce using rule 61 (STATEMENT_VAR_DEF)


State 41

   60 STATEMENT_VAR_DEF: VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE •

    $default  reduce using rule 60 (STATEMENT_VAR_DEF)


State 42

   72 STATEMENT_STRUCT_DEF: STRUCT ID BEGIN STRUCT_MEMBER_DEF • MORE_STRUCT_MEMBER_DEF END SEMICOLON
   73 MORE_STRUCT_MEMBER_DEF: • STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF
   74                       | • %empty  [END]
   75 STRUCT_MEMBER_DEF: • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER  shift, and go to state 1
    DT_BOOLEAN  shift, and go to state 2
    DT_FLOAT    shift, and go to state 3
    STRUCT      shift, and go to state 17

    $default  reduce using rule 74 (MORE_STRUCT_MEMBER_DEF)

    MORE_STRUCT_MEMBER_DEF  go to state 64
    STRUCT_MEMBER_DEF       go to state 65
    DT_STRUCT               go to state 11
    VAR_DEF_TYPE            go to state 43


State 43

   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   65 DECLARE_NON_INITIALIZE: • ID
   66                       | • STAR ID
   67                       | • HD_ARRAY
   68                       | • STAR HD_ARRAY
   75 STRUCT_MEMBER_DEF: VAR_DEF_TYPE • DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON

    ID    shift, and go to state 66
    STAR  shift, and go to state 67

    HD_ARRAY                go to state 27
    DECLARE_NON_INITIALIZE  go to state 68


State 44

   83 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT • ROUND_RIGHT STATEMENTS_BLOCK
   84                   | FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT • SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
   87 SINGLE_RECV_FUNC_ARG: • FUNC_DEF_TYPE ID
   88                     | • FUNC_DEF_TYPE RECV_HD_ARRAY
   91 DT_STRUCT: • STRUCT ID
   92 DT_POINTER: • VAR_DEF_TYPE STAR
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT
   97 FUNC_DEF_TYPE: • DT_INTEGER
   98              | • DT_FLOAT
   99              | • DT_BOOLEAN
  100              | • DT_POINTER

    DT_INTEGER   shift, and go to state 14
    DT_BOOLEAN   shift, and go to state 15
    DT_FLOAT     shift, and go to state 16
    STRUCT       shift, and go to state 17
    ROUND_RIGHT  shift, and go to state 69

    SINGLE_RECV_FUNC_ARG  go to state 70
    DT_STRUCT             go to state 11
    DT_POINTER            go to state 18
    VAR_DEF_TYPE          go to state 19
    FUNC_DEF_TYPE         go to state 71


State 45

   50 NUMBER: CONST_INTEGER •

    $default  reduce using rule 50 (NUMBER)


State 46

   52 NUMBER: CONST_FLOAT •

    $default  reduce using rule 52 (NUMBER)


State 47

   40 EXP_R: CONST_STRING •

    $default  reduce using rule 40 (EXP_R)


State 48

   51 NUMBER: CONST_BOOLEAN •

    $default  reduce using rule 51 (NUMBER)


State 49

   43 EXP_L: ID •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, ASSIGN, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]
   44      | ID • DOT EXP_L
   47 HD_ARRAY: ID • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   79 FUNC_CALL: ID • ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | ID • ROUND_LEFT ROUND_RIGHT

    DOT          shift, and go to state 72
    ROUND_LEFT   shift, and go to state 73
    SQUARE_LEFT  shift, and go to state 34

    $default  reduce using rule 43 (EXP_L)


State 50

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   36      | ROUND_LEFT • EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 74
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 51

   38 EXP_R: APSAND • EXP_L
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

    ID  shift, and go to state 75

    EXP_L     go to state 76
    HD_ARRAY  go to state 57


State 52

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   34      | PLUS • EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 77
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 53

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   35      | MINUS • EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 78
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 54

   39 EXP_R: STAR • EXP_L
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

    ID  shift, and go to state 75

    EXP_L     go to state 79
    HD_ARRAY  go to state 57


State 55

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   47 HD_ARRAY: ID SQUARE_LEFT EXP_R • SQUARE_RIGHT MORE_ARRAY_DIM

    SQUARE_RIGHT  shift, and go to state 80
    LOR           shift, and go to state 81
    LAND          shift, and go to state 82
    BOR           shift, and go to state 83
    BXOR          shift, and go to state 84
    APSAND        shift, and go to state 85
    EQ            shift, and go to state 86
    NE            shift, and go to state 87
    LE            shift, and go to state 88
    GE            shift, and go to state 89
    LT            shift, and go to state 90
    GT            shift, and go to state 91
    PLUS          shift, and go to state 92
    MINUS         shift, and go to state 93
    STAR          shift, and go to state 94
    DIVIDE        shift, and go to state 95
    MOD           shift, and go to state 96


State 56

   37 EXP_R: EXP_L •

    $default  reduce using rule 37 (EXP_R)


State 57

   45 EXP_L: HD_ARRAY •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, ASSIGN, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]
   46      | HD_ARRAY • DOT EXP_L

    DOT  shift, and go to state 97

    $default  reduce using rule 45 (EXP_L)


State 58

   41 EXP_R: NUMBER •

    $default  reduce using rule 41 (EXP_R)


State 59

   42 EXP_R: FUNC_CALL •

    $default  reduce using rule 42 (EXP_R)


State 60

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   69 DECLARE_INITIALIZE: ID ASSIGN EXP_R •  [SEMICOLON, COMMA]

    LOR     shift, and go to state 81
    LAND    shift, and go to state 82
    BOR     shift, and go to state 83
    BXOR    shift, and go to state 84
    APSAND  shift, and go to state 85
    EQ      shift, and go to state 86
    NE      shift, and go to state 87
    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 69 (DECLARE_INITIALIZE)


State 61

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   70 DECLARE_INITIALIZE: STAR ID ASSIGN • EXP_R
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 98
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 62

   62 DECLARE_MORE: • SEMICOLON
   63             | • COMMA DECLARE_INITIALIZE DECLARE_MORE
   64             | • COMMA DECLARE_NON_INITIALIZE DECLARE_MORE
   64             | COMMA DECLARE_NON_INITIALIZE • DECLARE_MORE

    SEMICOLON  shift, and go to state 38
    COMMA      shift, and go to state 39

    DECLARE_MORE  go to state 99


State 63

   62 DECLARE_MORE: • SEMICOLON
   63             | • COMMA DECLARE_INITIALIZE DECLARE_MORE
   63             | COMMA DECLARE_INITIALIZE • DECLARE_MORE
   64             | • COMMA DECLARE_NON_INITIALIZE DECLARE_MORE

    SEMICOLON  shift, and go to state 38
    COMMA      shift, and go to state 39

    DECLARE_MORE  go to state 100


State 64

   72 STATEMENT_STRUCT_DEF: STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF • END SEMICOLON

    END  shift, and go to state 101


State 65

   73 MORE_STRUCT_MEMBER_DEF: • STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF
   73                       | STRUCT_MEMBER_DEF • MORE_STRUCT_MEMBER_DEF
   74                       | • %empty  [END]
   75 STRUCT_MEMBER_DEF: • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER  shift, and go to state 1
    DT_BOOLEAN  shift, and go to state 2
    DT_FLOAT    shift, and go to state 3
    STRUCT      shift, and go to state 17

    $default  reduce using rule 74 (MORE_STRUCT_MEMBER_DEF)

    MORE_STRUCT_MEMBER_DEF  go to state 102
    STRUCT_MEMBER_DEF       go to state 65
    DT_STRUCT               go to state 11
    VAR_DEF_TYPE            go to state 43


State 66

   47 HD_ARRAY: ID • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   65 DECLARE_NON_INITIALIZE: ID •  [SEMICOLON, COMMA]

    SQUARE_LEFT  shift, and go to state 34

    $default  reduce using rule 65 (DECLARE_NON_INITIALIZE)


State 67

   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   66 DECLARE_NON_INITIALIZE: STAR • ID
   68                       | STAR • HD_ARRAY

    ID  shift, and go to state 103

    HD_ARRAY  go to state 37


State 68

   75 STRUCT_MEMBER_DEF: VAR_DEF_TYPE DECLARE_NON_INITIALIZE • DECLARE_MORE_NON_INITIALIZE SEMICOLON
   76 DECLARE_MORE_NON_INITIALIZE: • COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE
   77                            | • %empty  [SEMICOLON]

    COMMA  shift, and go to state 104

    $default  reduce using rule 77 (DECLARE_MORE_NON_INITIALIZE)

    DECLARE_MORE_NON_INITIALIZE  go to state 105


State 69

    6 STATEMENTS_BLOCK: • BEGIN STATEMENTS END
    7                 | • BEGIN END
   83 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT • STATEMENTS_BLOCK

    BEGIN  shift, and go to state 106

    STATEMENTS_BLOCK  go to state 107


State 70

   84 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG • RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK
   85 RECV_FUNC_ARGS: • COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
   86               | • %empty  [ROUND_RIGHT]

    COMMA  shift, and go to state 108

    $default  reduce using rule 86 (RECV_FUNC_ARGS)

    RECV_FUNC_ARGS  go to state 109


State 71

   87 SINGLE_RECV_FUNC_ARG: FUNC_DEF_TYPE • ID
   88                     | FUNC_DEF_TYPE • RECV_HD_ARRAY
   89 RECV_HD_ARRAY: • ID SQUARE_LEFT SQUARE_RIGHT MORE_ARRAY_DIM
   90              | • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

    ID  shift, and go to state 110

    RECV_HD_ARRAY  go to state 111


State 72

   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   44      | ID DOT • EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

    ID  shift, and go to state 75

    EXP_L     go to state 112
    HD_ARRAY  go to state 57


State 73

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   79          | ID ROUND_LEFT • EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT
   80          | ID ROUND_LEFT • ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    ROUND_RIGHT    shift, and go to state 113
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 114
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 74

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   36      | ROUND_LEFT EXP_R • ROUND_RIGHT

    ROUND_RIGHT  shift, and go to state 115
    LOR          shift, and go to state 81
    LAND         shift, and go to state 82
    BOR          shift, and go to state 83
    BXOR         shift, and go to state 84
    APSAND       shift, and go to state 85
    EQ           shift, and go to state 86
    NE           shift, and go to state 87
    LE           shift, and go to state 88
    GE           shift, and go to state 89
    LT           shift, and go to state 90
    GT           shift, and go to state 91
    PLUS         shift, and go to state 92
    MINUS        shift, and go to state 93
    STAR         shift, and go to state 94
    DIVIDE       shift, and go to state 95
    MOD          shift, and go to state 96


State 75

   43 EXP_L: ID •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, ASSIGN, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]
   44      | ID • DOT EXP_L
   47 HD_ARRAY: ID • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

    DOT          shift, and go to state 72
    SQUARE_LEFT  shift, and go to state 34

    $default  reduce using rule 43 (EXP_L)


State 76

   38 EXP_R: APSAND EXP_L •

    $default  reduce using rule 38 (EXP_R)


State 77

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   34      | PLUS EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]

    $default  reduce using rule 34 (EXP_R)

    Conflict between rule 34 and token LOR resolved as reduce (LOR < POSITIVE).
    Conflict between rule 34 and token LAND resolved as reduce (LAND < POSITIVE).
    Conflict between rule 34 and token BOR resolved as reduce (BOR < POSITIVE).
    Conflict between rule 34 and token BXOR resolved as reduce (BXOR < POSITIVE).
    Conflict between rule 34 and token APSAND resolved as reduce (APSAND < POSITIVE).
    Conflict between rule 34 and token EQ resolved as reduce (EQ < POSITIVE).
    Conflict between rule 34 and token NE resolved as reduce (NE < POSITIVE).
    Conflict between rule 34 and token LE resolved as reduce (LE < POSITIVE).
    Conflict between rule 34 and token GE resolved as reduce (GE < POSITIVE).
    Conflict between rule 34 and token LT resolved as reduce (LT < POSITIVE).
    Conflict between rule 34 and token GT resolved as reduce (GT < POSITIVE).
    Conflict between rule 34 and token PLUS resolved as reduce (PLUS < POSITIVE).
    Conflict between rule 34 and token MINUS resolved as reduce (MINUS < POSITIVE).
    Conflict between rule 34 and token STAR resolved as reduce (STAR < POSITIVE).
    Conflict between rule 34 and token DIVIDE resolved as reduce (DIVIDE < POSITIVE).
    Conflict between rule 34 and token MOD resolved as reduce (MOD < POSITIVE).


State 78

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   35      | MINUS EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]

    $default  reduce using rule 35 (EXP_R)

    Conflict between rule 35 and token LOR resolved as reduce (LOR < NEGATIVE).
    Conflict between rule 35 and token LAND resolved as reduce (LAND < NEGATIVE).
    Conflict between rule 35 and token BOR resolved as reduce (BOR < NEGATIVE).
    Conflict between rule 35 and token BXOR resolved as reduce (BXOR < NEGATIVE).
    Conflict between rule 35 and token APSAND resolved as reduce (APSAND < NEGATIVE).
    Conflict between rule 35 and token EQ resolved as reduce (EQ < NEGATIVE).
    Conflict between rule 35 and token NE resolved as reduce (NE < NEGATIVE).
    Conflict between rule 35 and token LE resolved as reduce (LE < NEGATIVE).
    Conflict between rule 35 and token GE resolved as reduce (GE < NEGATIVE).
    Conflict between rule 35 and token LT resolved as reduce (LT < NEGATIVE).
    Conflict between rule 35 and token GT resolved as reduce (GT < NEGATIVE).
    Conflict between rule 35 and token PLUS resolved as reduce (PLUS < NEGATIVE).
    Conflict between rule 35 and token MINUS resolved as reduce (MINUS < NEGATIVE).
    Conflict between rule 35 and token STAR resolved as reduce (STAR < NEGATIVE).
    Conflict between rule 35 and token DIVIDE resolved as reduce (DIVIDE < NEGATIVE).
    Conflict between rule 35 and token MOD resolved as reduce (MOD < NEGATIVE).


State 79

   39 EXP_R: STAR EXP_L •

    $default  reduce using rule 39 (EXP_R)


State 80

   47 HD_ARRAY: ID SQUARE_LEFT EXP_R SQUARE_RIGHT • MORE_ARRAY_DIM
   48 MORE_ARRAY_DIM: • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   49               | • %empty  [SEMICOLON, COMMA, DOT, ROUND_RIGHT, SQUARE_RIGHT, ASSIGN, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]

    SQUARE_LEFT  shift, and go to state 116

    $default  reduce using rule 49 (MORE_ARRAY_DIM)

    MORE_ARRAY_DIM  go to state 117


State 81

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   29      | EXP_R LOR • EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 118
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 82

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   30      | EXP_R LAND • EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 119
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 83

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   31      | EXP_R BOR • EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 120
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 84

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   32      | EXP_R BXOR • EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 121
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 85

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   33      | EXP_R APSAND • EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 122
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 86

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   23      | EXP_R EQ • EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 123
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 87

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   22      | EXP_R NE • EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 124
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 88

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   19      | EXP_R LE • EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 125
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 89

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   21      | EXP_R GE • EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 126
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 90

   18 EXP_R: • EXP_R LT EXP_R
   18      | EXP_R LT • EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 127
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 91

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   20      | EXP_R GT • EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 128
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 92

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   24      | EXP_R PLUS • EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 129
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 93

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   25      | EXP_R MINUS • EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 130
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 94

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   26      | EXP_R STAR • EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 131
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 95

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   27      | EXP_R DIVIDE • EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 132
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 96

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   28      | EXP_R MOD • EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 133
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 97

   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   46      | HD_ARRAY DOT • EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

    ID  shift, and go to state 75

    EXP_L     go to state 134
    HD_ARRAY  go to state 57


State 98

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   70 DECLARE_INITIALIZE: STAR ID ASSIGN EXP_R •  [SEMICOLON, COMMA]

    LOR     shift, and go to state 81
    LAND    shift, and go to state 82
    BOR     shift, and go to state 83
    BXOR    shift, and go to state 84
    APSAND  shift, and go to state 85
    EQ      shift, and go to state 86
    NE      shift, and go to state 87
    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 70 (DECLARE_INITIALIZE)


State 99

   64 DECLARE_MORE: COMMA DECLARE_NON_INITIALIZE DECLARE_MORE •

    $default  reduce using rule 64 (DECLARE_MORE)


State 100

   63 DECLARE_MORE: COMMA DECLARE_INITIALIZE DECLARE_MORE •

    $default  reduce using rule 63 (DECLARE_MORE)


State 101

   72 STATEMENT_STRUCT_DEF: STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END • SEMICOLON

    SEMICOLON  shift, and go to state 135


State 102

   73 MORE_STRUCT_MEMBER_DEF: STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF •

    $default  reduce using rule 73 (MORE_STRUCT_MEMBER_DEF)


State 103

   47 HD_ARRAY: ID • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   66 DECLARE_NON_INITIALIZE: STAR ID •  [SEMICOLON, COMMA]

    SQUARE_LEFT  shift, and go to state 34

    $default  reduce using rule 66 (DECLARE_NON_INITIALIZE)


State 104

   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   65 DECLARE_NON_INITIALIZE: • ID
   66                       | • STAR ID
   67                       | • HD_ARRAY
   68                       | • STAR HD_ARRAY
   76 DECLARE_MORE_NON_INITIALIZE: COMMA • DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE

    ID    shift, and go to state 66
    STAR  shift, and go to state 67

    HD_ARRAY                go to state 27
    DECLARE_NON_INITIALIZE  go to state 136


State 105

   75 STRUCT_MEMBER_DEF: VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE • SEMICOLON

    SEMICOLON  shift, and go to state 137


State 106

    6 STATEMENTS_BLOCK: BEGIN • STATEMENTS END
    7                 | BEGIN • END
    8 STATEMENTS: • STATEMENT STATEMENTS
    9           | • STATEMENT
   10 STATEMENT: • STATEMENT_VAR_DEF
   11          | • STATEMENT_ASSIGN
   12          | • STATEMENT_IF
   13          | • STATEMENT_WHILE
   14          | • STATEMENT_STRUCT_DEF
   15          | • STATEMENT_RETURN
   16          | • EXP_R SEMICOLON
   17          | • SEMICOLON
   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   53 STATEMENT_IF: • IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE
   57 STATEMENT_WHILE: • WHILE ROUND_LEFT EXP_R ROUND_RIGHT WHILE_BODY
   60 STATEMENT_VAR_DEF: • VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
   61                  | • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE
   71 STATEMENT_ASSIGN: • EXP_L ASSIGN EXP_R SEMICOLON
   72 STATEMENT_STRUCT_DEF: • STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   78 STATEMENT_RETURN: • RETURN EXP_R SEMICOLON
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER     shift, and go to state 1
    DT_BOOLEAN     shift, and go to state 2
    DT_FLOAT       shift, and go to state 3
    STRUCT         shift, and go to state 4
    WHILE          shift, and go to state 138
    IF             shift, and go to state 139
    RETURN         shift, and go to state 140
    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    SEMICOLON      shift, and go to state 141
    ROUND_LEFT     shift, and go to state 50
    END            shift, and go to state 142
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    STATEMENTS            go to state 143
    STATEMENT             go to state 144
    EXP_R                 go to state 145
    EXP_L                 go to state 146
    HD_ARRAY              go to state 57
    NUMBER                go to state 58
    STATEMENT_IF          go to state 147
    STATEMENT_WHILE       go to state 148
    STATEMENT_VAR_DEF     go to state 149
    STATEMENT_ASSIGN      go to state 150
    STATEMENT_STRUCT_DEF  go to state 151
    STATEMENT_RETURN      go to state 152
    FUNC_CALL             go to state 59
    DT_STRUCT             go to state 11
    VAR_DEF_TYPE          go to state 12


State 107

   83 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT ROUND_RIGHT STATEMENTS_BLOCK •

    $default  reduce using rule 83 (STATEMENT_FUNC_DEF)


State 108

   85 RECV_FUNC_ARGS: COMMA • SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
   87 SINGLE_RECV_FUNC_ARG: • FUNC_DEF_TYPE ID
   88                     | • FUNC_DEF_TYPE RECV_HD_ARRAY
   91 DT_STRUCT: • STRUCT ID
   92 DT_POINTER: • VAR_DEF_TYPE STAR
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT
   97 FUNC_DEF_TYPE: • DT_INTEGER
   98              | • DT_FLOAT
   99              | • DT_BOOLEAN
  100              | • DT_POINTER

    DT_INTEGER  shift, and go to state 14
    DT_BOOLEAN  shift, and go to state 15
    DT_FLOAT    shift, and go to state 16
    STRUCT      shift, and go to state 17

    SINGLE_RECV_FUNC_ARG  go to state 153
    DT_STRUCT             go to state 11
    DT_POINTER            go to state 18
    VAR_DEF_TYPE          go to state 19
    FUNC_DEF_TYPE         go to state 71


State 109

   84 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS • ROUND_RIGHT STATEMENTS_BLOCK

    ROUND_RIGHT  shift, and go to state 154


State 110

   87 SINGLE_RECV_FUNC_ARG: FUNC_DEF_TYPE ID •  [COMMA, ROUND_RIGHT]
   89 RECV_HD_ARRAY: ID • SQUARE_LEFT SQUARE_RIGHT MORE_ARRAY_DIM
   90              | ID • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

    SQUARE_LEFT  shift, and go to state 155

    $default  reduce using rule 87 (SINGLE_RECV_FUNC_ARG)


State 111

   88 SINGLE_RECV_FUNC_ARG: FUNC_DEF_TYPE RECV_HD_ARRAY •

    $default  reduce using rule 88 (SINGLE_RECV_FUNC_ARG)


State 112

   44 EXP_L: ID DOT EXP_L •

    $default  reduce using rule 44 (EXP_L)


State 113

   80 FUNC_CALL: ID ROUND_LEFT ROUND_RIGHT •

    $default  reduce using rule 80 (FUNC_CALL)


State 114

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   79 FUNC_CALL: ID ROUND_LEFT EXP_R • SEND_FUNC_ARGS ROUND_RIGHT
   81 SEND_FUNC_ARGS: • COMMA EXP_R SEND_FUNC_ARGS
   82               | • %empty  [ROUND_RIGHT]

    COMMA   shift, and go to state 156
    LOR     shift, and go to state 81
    LAND    shift, and go to state 82
    BOR     shift, and go to state 83
    BXOR    shift, and go to state 84
    APSAND  shift, and go to state 85
    EQ      shift, and go to state 86
    NE      shift, and go to state 87
    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 82 (SEND_FUNC_ARGS)

    SEND_FUNC_ARGS  go to state 157


State 115

   36 EXP_R: ROUND_LEFT EXP_R ROUND_RIGHT •

    $default  reduce using rule 36 (EXP_R)


State 116

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   48 MORE_ARRAY_DIM: SQUARE_LEFT • EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 158
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 117

   47 HD_ARRAY: ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM •

    $default  reduce using rule 47 (HD_ARRAY)


State 118

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   29      | EXP_R LOR EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR]
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    LAND    shift, and go to state 82
    BOR     shift, and go to state 83
    BXOR    shift, and go to state 84
    APSAND  shift, and go to state 85
    EQ      shift, and go to state 86
    NE      shift, and go to state 87
    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 29 (EXP_R)

    Conflict between rule 29 and token LOR resolved as reduce (%left LOR).
    Conflict between rule 29 and token LAND resolved as shift (LOR < LAND).
    Conflict between rule 29 and token BOR resolved as shift (LOR < BOR).
    Conflict between rule 29 and token BXOR resolved as shift (LOR < BXOR).
    Conflict between rule 29 and token APSAND resolved as shift (LOR < APSAND).
    Conflict between rule 29 and token EQ resolved as shift (LOR < EQ).
    Conflict between rule 29 and token NE resolved as shift (LOR < NE).
    Conflict between rule 29 and token LE resolved as shift (LOR < LE).
    Conflict between rule 29 and token GE resolved as shift (LOR < GE).
    Conflict between rule 29 and token LT resolved as shift (LOR < LT).
    Conflict between rule 29 and token GT resolved as shift (LOR < GT).
    Conflict between rule 29 and token PLUS resolved as shift (LOR < PLUS).
    Conflict between rule 29 and token MINUS resolved as shift (LOR < MINUS).
    Conflict between rule 29 and token STAR resolved as shift (LOR < STAR).
    Conflict between rule 29 and token DIVIDE resolved as shift (LOR < DIVIDE).
    Conflict between rule 29 and token MOD resolved as shift (LOR < MOD).


State 119

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   30      | EXP_R LAND EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND]
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    BOR     shift, and go to state 83
    BXOR    shift, and go to state 84
    APSAND  shift, and go to state 85
    EQ      shift, and go to state 86
    NE      shift, and go to state 87
    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 30 (EXP_R)

    Conflict between rule 30 and token LOR resolved as reduce (LOR < LAND).
    Conflict between rule 30 and token LAND resolved as reduce (%left LAND).
    Conflict between rule 30 and token BOR resolved as shift (LAND < BOR).
    Conflict between rule 30 and token BXOR resolved as shift (LAND < BXOR).
    Conflict between rule 30 and token APSAND resolved as shift (LAND < APSAND).
    Conflict between rule 30 and token EQ resolved as shift (LAND < EQ).
    Conflict between rule 30 and token NE resolved as shift (LAND < NE).
    Conflict between rule 30 and token LE resolved as shift (LAND < LE).
    Conflict between rule 30 and token GE resolved as shift (LAND < GE).
    Conflict between rule 30 and token LT resolved as shift (LAND < LT).
    Conflict between rule 30 and token GT resolved as shift (LAND < GT).
    Conflict between rule 30 and token PLUS resolved as shift (LAND < PLUS).
    Conflict between rule 30 and token MINUS resolved as shift (LAND < MINUS).
    Conflict between rule 30 and token STAR resolved as shift (LAND < STAR).
    Conflict between rule 30 and token DIVIDE resolved as shift (LAND < DIVIDE).
    Conflict between rule 30 and token MOD resolved as shift (LAND < MOD).


State 120

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   31      | EXP_R BOR EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR]
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    BXOR    shift, and go to state 84
    APSAND  shift, and go to state 85
    EQ      shift, and go to state 86
    NE      shift, and go to state 87
    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 31 (EXP_R)

    Conflict between rule 31 and token LOR resolved as reduce (LOR < BOR).
    Conflict between rule 31 and token LAND resolved as reduce (LAND < BOR).
    Conflict between rule 31 and token BOR resolved as reduce (%left BOR).
    Conflict between rule 31 and token BXOR resolved as shift (BOR < BXOR).
    Conflict between rule 31 and token APSAND resolved as shift (BOR < APSAND).
    Conflict between rule 31 and token EQ resolved as shift (BOR < EQ).
    Conflict between rule 31 and token NE resolved as shift (BOR < NE).
    Conflict between rule 31 and token LE resolved as shift (BOR < LE).
    Conflict between rule 31 and token GE resolved as shift (BOR < GE).
    Conflict between rule 31 and token LT resolved as shift (BOR < LT).
    Conflict between rule 31 and token GT resolved as shift (BOR < GT).
    Conflict between rule 31 and token PLUS resolved as shift (BOR < PLUS).
    Conflict between rule 31 and token MINUS resolved as shift (BOR < MINUS).
    Conflict between rule 31 and token STAR resolved as shift (BOR < STAR).
    Conflict between rule 31 and token DIVIDE resolved as shift (BOR < DIVIDE).
    Conflict between rule 31 and token MOD resolved as shift (BOR < MOD).


State 121

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   32      | EXP_R BXOR EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR]
   33      | EXP_R • APSAND EXP_R

    APSAND  shift, and go to state 85
    EQ      shift, and go to state 86
    NE      shift, and go to state 87
    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 32 (EXP_R)

    Conflict between rule 32 and token LOR resolved as reduce (LOR < BXOR).
    Conflict between rule 32 and token LAND resolved as reduce (LAND < BXOR).
    Conflict between rule 32 and token BOR resolved as reduce (BOR < BXOR).
    Conflict between rule 32 and token BXOR resolved as reduce (%left BXOR).
    Conflict between rule 32 and token APSAND resolved as shift (BXOR < APSAND).
    Conflict between rule 32 and token EQ resolved as shift (BXOR < EQ).
    Conflict between rule 32 and token NE resolved as shift (BXOR < NE).
    Conflict between rule 32 and token LE resolved as shift (BXOR < LE).
    Conflict between rule 32 and token GE resolved as shift (BXOR < GE).
    Conflict between rule 32 and token LT resolved as shift (BXOR < LT).
    Conflict between rule 32 and token GT resolved as shift (BXOR < GT).
    Conflict between rule 32 and token PLUS resolved as shift (BXOR < PLUS).
    Conflict between rule 32 and token MINUS resolved as shift (BXOR < MINUS).
    Conflict between rule 32 and token STAR resolved as shift (BXOR < STAR).
    Conflict between rule 32 and token DIVIDE resolved as shift (BXOR < DIVIDE).
    Conflict between rule 32 and token MOD resolved as shift (BXOR < MOD).


State 122

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   33      | EXP_R APSAND EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND]

    EQ      shift, and go to state 86
    NE      shift, and go to state 87
    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 33 (EXP_R)

    Conflict between rule 33 and token LOR resolved as reduce (LOR < APSAND).
    Conflict between rule 33 and token LAND resolved as reduce (LAND < APSAND).
    Conflict between rule 33 and token BOR resolved as reduce (BOR < APSAND).
    Conflict between rule 33 and token BXOR resolved as reduce (BXOR < APSAND).
    Conflict between rule 33 and token APSAND resolved as reduce (%left APSAND).
    Conflict between rule 33 and token EQ resolved as shift (APSAND < EQ).
    Conflict between rule 33 and token NE resolved as shift (APSAND < NE).
    Conflict between rule 33 and token LE resolved as shift (APSAND < LE).
    Conflict between rule 33 and token GE resolved as shift (APSAND < GE).
    Conflict between rule 33 and token LT resolved as shift (APSAND < LT).
    Conflict between rule 33 and token GT resolved as shift (APSAND < GT).
    Conflict between rule 33 and token PLUS resolved as shift (APSAND < PLUS).
    Conflict between rule 33 and token MINUS resolved as shift (APSAND < MINUS).
    Conflict between rule 33 and token STAR resolved as shift (APSAND < STAR).
    Conflict between rule 33 and token DIVIDE resolved as shift (APSAND < DIVIDE).
    Conflict between rule 33 and token MOD resolved as shift (APSAND < MOD).


State 123

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   23      | EXP_R EQ EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE]
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 23 (EXP_R)

    Conflict between rule 23 and token LOR resolved as reduce (LOR < EQ).
    Conflict between rule 23 and token LAND resolved as reduce (LAND < EQ).
    Conflict between rule 23 and token BOR resolved as reduce (BOR < EQ).
    Conflict between rule 23 and token BXOR resolved as reduce (BXOR < EQ).
    Conflict between rule 23 and token APSAND resolved as reduce (APSAND < EQ).
    Conflict between rule 23 and token EQ resolved as reduce (%left EQ).
    Conflict between rule 23 and token NE resolved as reduce (%left NE).
    Conflict between rule 23 and token LE resolved as shift (EQ < LE).
    Conflict between rule 23 and token GE resolved as shift (EQ < GE).
    Conflict between rule 23 and token LT resolved as shift (EQ < LT).
    Conflict between rule 23 and token GT resolved as shift (EQ < GT).
    Conflict between rule 23 and token PLUS resolved as shift (EQ < PLUS).
    Conflict between rule 23 and token MINUS resolved as shift (EQ < MINUS).
    Conflict between rule 23 and token STAR resolved as shift (EQ < STAR).
    Conflict between rule 23 and token DIVIDE resolved as shift (EQ < DIVIDE).
    Conflict between rule 23 and token MOD resolved as shift (EQ < MOD).


State 124

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   22      | EXP_R NE EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE]
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 22 (EXP_R)

    Conflict between rule 22 and token LOR resolved as reduce (LOR < NE).
    Conflict between rule 22 and token LAND resolved as reduce (LAND < NE).
    Conflict between rule 22 and token BOR resolved as reduce (BOR < NE).
    Conflict between rule 22 and token BXOR resolved as reduce (BXOR < NE).
    Conflict between rule 22 and token APSAND resolved as reduce (APSAND < NE).
    Conflict between rule 22 and token EQ resolved as reduce (%left EQ).
    Conflict between rule 22 and token NE resolved as reduce (%left NE).
    Conflict between rule 22 and token LE resolved as shift (NE < LE).
    Conflict between rule 22 and token GE resolved as shift (NE < GE).
    Conflict between rule 22 and token LT resolved as shift (NE < LT).
    Conflict between rule 22 and token GT resolved as shift (NE < GT).
    Conflict between rule 22 and token PLUS resolved as shift (NE < PLUS).
    Conflict between rule 22 and token MINUS resolved as shift (NE < MINUS).
    Conflict between rule 22 and token STAR resolved as shift (NE < STAR).
    Conflict between rule 22 and token DIVIDE resolved as shift (NE < DIVIDE).
    Conflict between rule 22 and token MOD resolved as shift (NE < MOD).


State 125

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   19      | EXP_R LE EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT]
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 19 (EXP_R)

    Conflict between rule 19 and token LOR resolved as reduce (LOR < LE).
    Conflict between rule 19 and token LAND resolved as reduce (LAND < LE).
    Conflict between rule 19 and token BOR resolved as reduce (BOR < LE).
    Conflict between rule 19 and token BXOR resolved as reduce (BXOR < LE).
    Conflict between rule 19 and token APSAND resolved as reduce (APSAND < LE).
    Conflict between rule 19 and token EQ resolved as reduce (EQ < LE).
    Conflict between rule 19 and token NE resolved as reduce (NE < LE).
    Conflict between rule 19 and token LE resolved as reduce (%left LE).
    Conflict between rule 19 and token GE resolved as reduce (%left GE).
    Conflict between rule 19 and token LT resolved as reduce (%left LT).
    Conflict between rule 19 and token GT resolved as reduce (%left GT).
    Conflict between rule 19 and token PLUS resolved as shift (LE < PLUS).
    Conflict between rule 19 and token MINUS resolved as shift (LE < MINUS).
    Conflict between rule 19 and token STAR resolved as shift (LE < STAR).
    Conflict between rule 19 and token DIVIDE resolved as shift (LE < DIVIDE).
    Conflict between rule 19 and token MOD resolved as shift (LE < MOD).


State 126

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   21      | EXP_R GE EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT]
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 21 (EXP_R)

    Conflict between rule 21 and token LOR resolved as reduce (LOR < GE).
    Conflict between rule 21 and token LAND resolved as reduce (LAND < GE).
    Conflict between rule 21 and token BOR resolved as reduce (BOR < GE).
    Conflict between rule 21 and token BXOR resolved as reduce (BXOR < GE).
    Conflict between rule 21 and token APSAND resolved as reduce (APSAND < GE).
    Conflict between rule 21 and token EQ resolved as reduce (EQ < GE).
    Conflict between rule 21 and token NE resolved as reduce (NE < GE).
    Conflict between rule 21 and token LE resolved as reduce (%left LE).
    Conflict between rule 21 and token GE resolved as reduce (%left GE).
    Conflict between rule 21 and token LT resolved as reduce (%left LT).
    Conflict between rule 21 and token GT resolved as reduce (%left GT).
    Conflict between rule 21 and token PLUS resolved as shift (GE < PLUS).
    Conflict between rule 21 and token MINUS resolved as shift (GE < MINUS).
    Conflict between rule 21 and token STAR resolved as shift (GE < STAR).
    Conflict between rule 21 and token DIVIDE resolved as shift (GE < DIVIDE).
    Conflict between rule 21 and token MOD resolved as shift (GE < MOD).


State 127

   18 EXP_R: EXP_R • LT EXP_R
   18      | EXP_R LT EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT]
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 18 (EXP_R)

    Conflict between rule 18 and token LOR resolved as reduce (LOR < LT).
    Conflict between rule 18 and token LAND resolved as reduce (LAND < LT).
    Conflict between rule 18 and token BOR resolved as reduce (BOR < LT).
    Conflict between rule 18 and token BXOR resolved as reduce (BXOR < LT).
    Conflict between rule 18 and token APSAND resolved as reduce (APSAND < LT).
    Conflict between rule 18 and token EQ resolved as reduce (EQ < LT).
    Conflict between rule 18 and token NE resolved as reduce (NE < LT).
    Conflict between rule 18 and token LE resolved as reduce (%left LE).
    Conflict between rule 18 and token GE resolved as reduce (%left GE).
    Conflict between rule 18 and token LT resolved as reduce (%left LT).
    Conflict between rule 18 and token GT resolved as reduce (%left GT).
    Conflict between rule 18 and token PLUS resolved as shift (LT < PLUS).
    Conflict between rule 18 and token MINUS resolved as shift (LT < MINUS).
    Conflict between rule 18 and token STAR resolved as shift (LT < STAR).
    Conflict between rule 18 and token DIVIDE resolved as shift (LT < DIVIDE).
    Conflict between rule 18 and token MOD resolved as shift (LT < MOD).


State 128

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   20      | EXP_R GT EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT]
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 20 (EXP_R)

    Conflict between rule 20 and token LOR resolved as reduce (LOR < GT).
    Conflict between rule 20 and token LAND resolved as reduce (LAND < GT).
    Conflict between rule 20 and token BOR resolved as reduce (BOR < GT).
    Conflict between rule 20 and token BXOR resolved as reduce (BXOR < GT).
    Conflict between rule 20 and token APSAND resolved as reduce (APSAND < GT).
    Conflict between rule 20 and token EQ resolved as reduce (EQ < GT).
    Conflict between rule 20 and token NE resolved as reduce (NE < GT).
    Conflict between rule 20 and token LE resolved as reduce (%left LE).
    Conflict between rule 20 and token GE resolved as reduce (%left GE).
    Conflict between rule 20 and token LT resolved as reduce (%left LT).
    Conflict between rule 20 and token GT resolved as reduce (%left GT).
    Conflict between rule 20 and token PLUS resolved as shift (GT < PLUS).
    Conflict between rule 20 and token MINUS resolved as shift (GT < MINUS).
    Conflict between rule 20 and token STAR resolved as shift (GT < STAR).
    Conflict between rule 20 and token DIVIDE resolved as shift (GT < DIVIDE).
    Conflict between rule 20 and token MOD resolved as shift (GT < MOD).


State 129

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   24      | EXP_R PLUS EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS]
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 24 (EXP_R)

    Conflict between rule 24 and token LOR resolved as reduce (LOR < PLUS).
    Conflict between rule 24 and token LAND resolved as reduce (LAND < PLUS).
    Conflict between rule 24 and token BOR resolved as reduce (BOR < PLUS).
    Conflict between rule 24 and token BXOR resolved as reduce (BXOR < PLUS).
    Conflict between rule 24 and token APSAND resolved as reduce (APSAND < PLUS).
    Conflict between rule 24 and token EQ resolved as reduce (EQ < PLUS).
    Conflict between rule 24 and token NE resolved as reduce (NE < PLUS).
    Conflict between rule 24 and token LE resolved as reduce (LE < PLUS).
    Conflict between rule 24 and token GE resolved as reduce (GE < PLUS).
    Conflict between rule 24 and token LT resolved as reduce (LT < PLUS).
    Conflict between rule 24 and token GT resolved as reduce (GT < PLUS).
    Conflict between rule 24 and token PLUS resolved as reduce (%left PLUS).
    Conflict between rule 24 and token MINUS resolved as reduce (%left MINUS).
    Conflict between rule 24 and token STAR resolved as shift (PLUS < STAR).
    Conflict between rule 24 and token DIVIDE resolved as shift (PLUS < DIVIDE).
    Conflict between rule 24 and token MOD resolved as shift (PLUS < MOD).


State 130

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   25      | EXP_R MINUS EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS]
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 25 (EXP_R)

    Conflict between rule 25 and token LOR resolved as reduce (LOR < MINUS).
    Conflict between rule 25 and token LAND resolved as reduce (LAND < MINUS).
    Conflict between rule 25 and token BOR resolved as reduce (BOR < MINUS).
    Conflict between rule 25 and token BXOR resolved as reduce (BXOR < MINUS).
    Conflict between rule 25 and token APSAND resolved as reduce (APSAND < MINUS).
    Conflict between rule 25 and token EQ resolved as reduce (EQ < MINUS).
    Conflict between rule 25 and token NE resolved as reduce (NE < MINUS).
    Conflict between rule 25 and token LE resolved as reduce (LE < MINUS).
    Conflict between rule 25 and token GE resolved as reduce (GE < MINUS).
    Conflict between rule 25 and token LT resolved as reduce (LT < MINUS).
    Conflict between rule 25 and token GT resolved as reduce (GT < MINUS).
    Conflict between rule 25 and token PLUS resolved as reduce (%left PLUS).
    Conflict between rule 25 and token MINUS resolved as reduce (%left MINUS).
    Conflict between rule 25 and token STAR resolved as shift (MINUS < STAR).
    Conflict between rule 25 and token DIVIDE resolved as shift (MINUS < DIVIDE).
    Conflict between rule 25 and token MOD resolved as shift (MINUS < MOD).


State 131

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   26      | EXP_R STAR EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    $default  reduce using rule 26 (EXP_R)

    Conflict between rule 26 and token LOR resolved as reduce (LOR < STAR).
    Conflict between rule 26 and token LAND resolved as reduce (LAND < STAR).
    Conflict between rule 26 and token BOR resolved as reduce (BOR < STAR).
    Conflict between rule 26 and token BXOR resolved as reduce (BXOR < STAR).
    Conflict between rule 26 and token APSAND resolved as reduce (APSAND < STAR).
    Conflict between rule 26 and token EQ resolved as reduce (EQ < STAR).
    Conflict between rule 26 and token NE resolved as reduce (NE < STAR).
    Conflict between rule 26 and token LE resolved as reduce (LE < STAR).
    Conflict between rule 26 and token GE resolved as reduce (GE < STAR).
    Conflict between rule 26 and token LT resolved as reduce (LT < STAR).
    Conflict between rule 26 and token GT resolved as reduce (GT < STAR).
    Conflict between rule 26 and token PLUS resolved as reduce (PLUS < STAR).
    Conflict between rule 26 and token MINUS resolved as reduce (MINUS < STAR).
    Conflict between rule 26 and token STAR resolved as reduce (%left STAR).
    Conflict between rule 26 and token DIVIDE resolved as reduce (%left DIVIDE).
    Conflict between rule 26 and token MOD resolved as reduce (%left MOD).


State 132

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   27      | EXP_R DIVIDE EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    $default  reduce using rule 27 (EXP_R)

    Conflict between rule 27 and token LOR resolved as reduce (LOR < DIVIDE).
    Conflict between rule 27 and token LAND resolved as reduce (LAND < DIVIDE).
    Conflict between rule 27 and token BOR resolved as reduce (BOR < DIVIDE).
    Conflict between rule 27 and token BXOR resolved as reduce (BXOR < DIVIDE).
    Conflict between rule 27 and token APSAND resolved as reduce (APSAND < DIVIDE).
    Conflict between rule 27 and token EQ resolved as reduce (EQ < DIVIDE).
    Conflict between rule 27 and token NE resolved as reduce (NE < DIVIDE).
    Conflict between rule 27 and token LE resolved as reduce (LE < DIVIDE).
    Conflict between rule 27 and token GE resolved as reduce (GE < DIVIDE).
    Conflict between rule 27 and token LT resolved as reduce (LT < DIVIDE).
    Conflict between rule 27 and token GT resolved as reduce (GT < DIVIDE).
    Conflict between rule 27 and token PLUS resolved as reduce (PLUS < DIVIDE).
    Conflict between rule 27 and token MINUS resolved as reduce (MINUS < DIVIDE).
    Conflict between rule 27 and token STAR resolved as reduce (%left STAR).
    Conflict between rule 27 and token DIVIDE resolved as reduce (%left DIVIDE).
    Conflict between rule 27 and token MOD resolved as reduce (%left MOD).


State 133

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   28      | EXP_R MOD EXP_R •  [SEMICOLON, COMMA, ROUND_RIGHT, SQUARE_RIGHT, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    $default  reduce using rule 28 (EXP_R)

    Conflict between rule 28 and token LOR resolved as reduce (LOR < MOD).
    Conflict between rule 28 and token LAND resolved as reduce (LAND < MOD).
    Conflict between rule 28 and token BOR resolved as reduce (BOR < MOD).
    Conflict between rule 28 and token BXOR resolved as reduce (BXOR < MOD).
    Conflict between rule 28 and token APSAND resolved as reduce (APSAND < MOD).
    Conflict between rule 28 and token EQ resolved as reduce (EQ < MOD).
    Conflict between rule 28 and token NE resolved as reduce (NE < MOD).
    Conflict between rule 28 and token LE resolved as reduce (LE < MOD).
    Conflict between rule 28 and token GE resolved as reduce (GE < MOD).
    Conflict between rule 28 and token LT resolved as reduce (LT < MOD).
    Conflict between rule 28 and token GT resolved as reduce (GT < MOD).
    Conflict between rule 28 and token PLUS resolved as reduce (PLUS < MOD).
    Conflict between rule 28 and token MINUS resolved as reduce (MINUS < MOD).
    Conflict between rule 28 and token STAR resolved as reduce (%left STAR).
    Conflict between rule 28 and token DIVIDE resolved as reduce (%left DIVIDE).
    Conflict between rule 28 and token MOD resolved as reduce (%left MOD).


State 134

   46 EXP_L: HD_ARRAY DOT EXP_L •

    $default  reduce using rule 46 (EXP_L)


State 135

   72 STATEMENT_STRUCT_DEF: STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON •

    $default  reduce using rule 72 (STATEMENT_STRUCT_DEF)


State 136

   76 DECLARE_MORE_NON_INITIALIZE: • COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE
   76                            | COMMA DECLARE_NON_INITIALIZE • DECLARE_MORE_NON_INITIALIZE
   77                            | • %empty  [SEMICOLON]

    COMMA  shift, and go to state 104

    $default  reduce using rule 77 (DECLARE_MORE_NON_INITIALIZE)

    DECLARE_MORE_NON_INITIALIZE  go to state 159


State 137

   75 STRUCT_MEMBER_DEF: VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE SEMICOLON •

    $default  reduce using rule 75 (STRUCT_MEMBER_DEF)


State 138

   57 STATEMENT_WHILE: WHILE • ROUND_LEFT EXP_R ROUND_RIGHT WHILE_BODY

    ROUND_LEFT  shift, and go to state 160


State 139

   53 STATEMENT_IF: IF • ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE

    ROUND_LEFT  shift, and go to state 161


State 140

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   78 STATEMENT_RETURN: RETURN • EXP_R SEMICOLON
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 162
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 141

   17 STATEMENT: SEMICOLON •

    $default  reduce using rule 17 (STATEMENT)


State 142

    7 STATEMENTS_BLOCK: BEGIN END •

    $default  reduce using rule 7 (STATEMENTS_BLOCK)


State 143

    6 STATEMENTS_BLOCK: BEGIN STATEMENTS • END

    END  shift, and go to state 163


State 144

    8 STATEMENTS: • STATEMENT STATEMENTS
    8           | STATEMENT • STATEMENTS
    9           | • STATEMENT
    9           | STATEMENT •  [END]
   10 STATEMENT: • STATEMENT_VAR_DEF
   11          | • STATEMENT_ASSIGN
   12          | • STATEMENT_IF
   13          | • STATEMENT_WHILE
   14          | • STATEMENT_STRUCT_DEF
   15          | • STATEMENT_RETURN
   16          | • EXP_R SEMICOLON
   17          | • SEMICOLON
   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   53 STATEMENT_IF: • IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE
   57 STATEMENT_WHILE: • WHILE ROUND_LEFT EXP_R ROUND_RIGHT WHILE_BODY
   60 STATEMENT_VAR_DEF: • VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
   61                  | • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE
   71 STATEMENT_ASSIGN: • EXP_L ASSIGN EXP_R SEMICOLON
   72 STATEMENT_STRUCT_DEF: • STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   78 STATEMENT_RETURN: • RETURN EXP_R SEMICOLON
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER     shift, and go to state 1
    DT_BOOLEAN     shift, and go to state 2
    DT_FLOAT       shift, and go to state 3
    STRUCT         shift, and go to state 4
    WHILE          shift, and go to state 138
    IF             shift, and go to state 139
    RETURN         shift, and go to state 140
    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    SEMICOLON      shift, and go to state 141
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    $default  reduce using rule 9 (STATEMENTS)

    STATEMENTS            go to state 164
    STATEMENT             go to state 144
    EXP_R                 go to state 145
    EXP_L                 go to state 146
    HD_ARRAY              go to state 57
    NUMBER                go to state 58
    STATEMENT_IF          go to state 147
    STATEMENT_WHILE       go to state 148
    STATEMENT_VAR_DEF     go to state 149
    STATEMENT_ASSIGN      go to state 150
    STATEMENT_STRUCT_DEF  go to state 151
    STATEMENT_RETURN      go to state 152
    FUNC_CALL             go to state 59
    DT_STRUCT             go to state 11
    VAR_DEF_TYPE          go to state 12


State 145

   16 STATEMENT: EXP_R • SEMICOLON
   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R

    SEMICOLON  shift, and go to state 165
    LOR        shift, and go to state 81
    LAND       shift, and go to state 82
    BOR        shift, and go to state 83
    BXOR       shift, and go to state 84
    APSAND     shift, and go to state 85
    EQ         shift, and go to state 86
    NE         shift, and go to state 87
    LE         shift, and go to state 88
    GE         shift, and go to state 89
    LT         shift, and go to state 90
    GT         shift, and go to state 91
    PLUS       shift, and go to state 92
    MINUS      shift, and go to state 93
    STAR       shift, and go to state 94
    DIVIDE     shift, and go to state 95
    MOD        shift, and go to state 96


State 146

   37 EXP_R: EXP_L •  [SEMICOLON, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]
   71 STATEMENT_ASSIGN: EXP_L • ASSIGN EXP_R SEMICOLON

    ASSIGN  shift, and go to state 166

    $default  reduce using rule 37 (EXP_R)


State 147

   12 STATEMENT: STATEMENT_IF •

    $default  reduce using rule 12 (STATEMENT)


State 148

   13 STATEMENT: STATEMENT_WHILE •

    $default  reduce using rule 13 (STATEMENT)


State 149

   10 STATEMENT: STATEMENT_VAR_DEF •

    $default  reduce using rule 10 (STATEMENT)


State 150

   11 STATEMENT: STATEMENT_ASSIGN •

    $default  reduce using rule 11 (STATEMENT)


State 151

   14 STATEMENT: STATEMENT_STRUCT_DEF •

    $default  reduce using rule 14 (STATEMENT)


State 152

   15 STATEMENT: STATEMENT_RETURN •

    $default  reduce using rule 15 (STATEMENT)


State 153

   85 RECV_FUNC_ARGS: • COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS
   85               | COMMA SINGLE_RECV_FUNC_ARG • RECV_FUNC_ARGS
   86               | • %empty  [ROUND_RIGHT]

    COMMA  shift, and go to state 108

    $default  reduce using rule 86 (RECV_FUNC_ARGS)

    RECV_FUNC_ARGS  go to state 167


State 154

    6 STATEMENTS_BLOCK: • BEGIN STATEMENTS END
    7                 | • BEGIN END
   84 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT • STATEMENTS_BLOCK

    BEGIN  shift, and go to state 106

    STATEMENTS_BLOCK  go to state 168


State 155

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT
   89 RECV_HD_ARRAY: ID SQUARE_LEFT • SQUARE_RIGHT MORE_ARRAY_DIM
   90              | ID SQUARE_LEFT • EXP_R SQUARE_RIGHT MORE_ARRAY_DIM

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    SQUARE_RIGHT   shift, and go to state 169
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 170
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 156

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT
   81 SEND_FUNC_ARGS: COMMA • EXP_R SEND_FUNC_ARGS

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 171
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 157

   79 FUNC_CALL: ID ROUND_LEFT EXP_R SEND_FUNC_ARGS • ROUND_RIGHT

    ROUND_RIGHT  shift, and go to state 172


State 158

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   48 MORE_ARRAY_DIM: SQUARE_LEFT EXP_R • SQUARE_RIGHT MORE_ARRAY_DIM

    SQUARE_RIGHT  shift, and go to state 173
    LOR           shift, and go to state 81
    LAND          shift, and go to state 82
    BOR           shift, and go to state 83
    BXOR          shift, and go to state 84
    APSAND        shift, and go to state 85
    EQ            shift, and go to state 86
    NE            shift, and go to state 87
    LE            shift, and go to state 88
    GE            shift, and go to state 89
    LT            shift, and go to state 90
    GT            shift, and go to state 91
    PLUS          shift, and go to state 92
    MINUS         shift, and go to state 93
    STAR          shift, and go to state 94
    DIVIDE        shift, and go to state 95
    MOD           shift, and go to state 96


State 159

   76 DECLARE_MORE_NON_INITIALIZE: COMMA DECLARE_NON_INITIALIZE DECLARE_MORE_NON_INITIALIZE •

    $default  reduce using rule 76 (DECLARE_MORE_NON_INITIALIZE)


State 160

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   57 STATEMENT_WHILE: WHILE ROUND_LEFT • EXP_R ROUND_RIGHT WHILE_BODY
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 174
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 161

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   53 STATEMENT_IF: IF ROUND_LEFT • EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 175
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 162

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   78 STATEMENT_RETURN: RETURN EXP_R • SEMICOLON

    SEMICOLON  shift, and go to state 176
    LOR        shift, and go to state 81
    LAND       shift, and go to state 82
    BOR        shift, and go to state 83
    BXOR       shift, and go to state 84
    APSAND     shift, and go to state 85
    EQ         shift, and go to state 86
    NE         shift, and go to state 87
    LE         shift, and go to state 88
    GE         shift, and go to state 89
    LT         shift, and go to state 90
    GT         shift, and go to state 91
    PLUS       shift, and go to state 92
    MINUS      shift, and go to state 93
    STAR       shift, and go to state 94
    DIVIDE     shift, and go to state 95
    MOD        shift, and go to state 96


State 163

    6 STATEMENTS_BLOCK: BEGIN STATEMENTS END •

    $default  reduce using rule 6 (STATEMENTS_BLOCK)


State 164

    8 STATEMENTS: STATEMENT STATEMENTS •

    $default  reduce using rule 8 (STATEMENTS)


State 165

   16 STATEMENT: EXP_R SEMICOLON •

    $default  reduce using rule 16 (STATEMENT)


State 166

   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   71 STATEMENT_ASSIGN: EXP_L ASSIGN • EXP_R SEMICOLON
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT

    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    ROUND_LEFT     shift, and go to state 50
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    EXP_R      go to state 177
    EXP_L      go to state 56
    HD_ARRAY   go to state 57
    NUMBER     go to state 58
    FUNC_CALL  go to state 59


State 167

   85 RECV_FUNC_ARGS: COMMA SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS •

    $default  reduce using rule 85 (RECV_FUNC_ARGS)


State 168

   84 STATEMENT_FUNC_DEF: FUNCTION FUNC_DEF_TYPE ID ROUND_LEFT SINGLE_RECV_FUNC_ARG RECV_FUNC_ARGS ROUND_RIGHT STATEMENTS_BLOCK •

    $default  reduce using rule 84 (STATEMENT_FUNC_DEF)


State 169

   48 MORE_ARRAY_DIM: • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   49               | • %empty  [COMMA, ROUND_RIGHT]
   89 RECV_HD_ARRAY: ID SQUARE_LEFT SQUARE_RIGHT • MORE_ARRAY_DIM

    SQUARE_LEFT  shift, and go to state 116

    $default  reduce using rule 49 (MORE_ARRAY_DIM)

    MORE_ARRAY_DIM  go to state 178


State 170

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   90 RECV_HD_ARRAY: ID SQUARE_LEFT EXP_R • SQUARE_RIGHT MORE_ARRAY_DIM

    SQUARE_RIGHT  shift, and go to state 179
    LOR           shift, and go to state 81
    LAND          shift, and go to state 82
    BOR           shift, and go to state 83
    BXOR          shift, and go to state 84
    APSAND        shift, and go to state 85
    EQ            shift, and go to state 86
    NE            shift, and go to state 87
    LE            shift, and go to state 88
    GE            shift, and go to state 89
    LT            shift, and go to state 90
    GT            shift, and go to state 91
    PLUS          shift, and go to state 92
    MINUS         shift, and go to state 93
    STAR          shift, and go to state 94
    DIVIDE        shift, and go to state 95
    MOD           shift, and go to state 96


State 171

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   81 SEND_FUNC_ARGS: • COMMA EXP_R SEND_FUNC_ARGS
   81               | COMMA EXP_R • SEND_FUNC_ARGS
   82               | • %empty  [ROUND_RIGHT]

    COMMA   shift, and go to state 156
    LOR     shift, and go to state 81
    LAND    shift, and go to state 82
    BOR     shift, and go to state 83
    BXOR    shift, and go to state 84
    APSAND  shift, and go to state 85
    EQ      shift, and go to state 86
    NE      shift, and go to state 87
    LE      shift, and go to state 88
    GE      shift, and go to state 89
    LT      shift, and go to state 90
    GT      shift, and go to state 91
    PLUS    shift, and go to state 92
    MINUS   shift, and go to state 93
    STAR    shift, and go to state 94
    DIVIDE  shift, and go to state 95
    MOD     shift, and go to state 96

    $default  reduce using rule 82 (SEND_FUNC_ARGS)

    SEND_FUNC_ARGS  go to state 180


State 172

   79 FUNC_CALL: ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT •

    $default  reduce using rule 79 (FUNC_CALL)


State 173

   48 MORE_ARRAY_DIM: • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   48               | SQUARE_LEFT EXP_R SQUARE_RIGHT • MORE_ARRAY_DIM
   49               | • %empty  [SEMICOLON, COMMA, DOT, ROUND_RIGHT, SQUARE_RIGHT, ASSIGN, LOR, LAND, BOR, BXOR, APSAND, EQ, NE, LE, GE, LT, GT, PLUS, MINUS, STAR, DIVIDE, MOD]

    SQUARE_LEFT  shift, and go to state 116

    $default  reduce using rule 49 (MORE_ARRAY_DIM)

    MORE_ARRAY_DIM  go to state 181


State 174

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   57 STATEMENT_WHILE: WHILE ROUND_LEFT EXP_R • ROUND_RIGHT WHILE_BODY

    ROUND_RIGHT  shift, and go to state 182
    LOR          shift, and go to state 81
    LAND         shift, and go to state 82
    BOR          shift, and go to state 83
    BXOR         shift, and go to state 84
    APSAND       shift, and go to state 85
    EQ           shift, and go to state 86
    NE           shift, and go to state 87
    LE           shift, and go to state 88
    GE           shift, and go to state 89
    LT           shift, and go to state 90
    GT           shift, and go to state 91
    PLUS         shift, and go to state 92
    MINUS        shift, and go to state 93
    STAR         shift, and go to state 94
    DIVIDE       shift, and go to state 95
    MOD          shift, and go to state 96


State 175

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   53 STATEMENT_IF: IF ROUND_LEFT EXP_R • ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE

    ROUND_RIGHT  shift, and go to state 183
    LOR          shift, and go to state 81
    LAND         shift, and go to state 82
    BOR          shift, and go to state 83
    BXOR         shift, and go to state 84
    APSAND       shift, and go to state 85
    EQ           shift, and go to state 86
    NE           shift, and go to state 87
    LE           shift, and go to state 88
    GE           shift, and go to state 89
    LT           shift, and go to state 90
    GT           shift, and go to state 91
    PLUS         shift, and go to state 92
    MINUS        shift, and go to state 93
    STAR         shift, and go to state 94
    DIVIDE       shift, and go to state 95
    MOD          shift, and go to state 96


State 176

   78 STATEMENT_RETURN: RETURN EXP_R SEMICOLON •

    $default  reduce using rule 78 (STATEMENT_RETURN)


State 177

   18 EXP_R: EXP_R • LT EXP_R
   19      | EXP_R • LE EXP_R
   20      | EXP_R • GT EXP_R
   21      | EXP_R • GE EXP_R
   22      | EXP_R • NE EXP_R
   23      | EXP_R • EQ EXP_R
   24      | EXP_R • PLUS EXP_R
   25      | EXP_R • MINUS EXP_R
   26      | EXP_R • STAR EXP_R
   27      | EXP_R • DIVIDE EXP_R
   28      | EXP_R • MOD EXP_R
   29      | EXP_R • LOR EXP_R
   30      | EXP_R • LAND EXP_R
   31      | EXP_R • BOR EXP_R
   32      | EXP_R • BXOR EXP_R
   33      | EXP_R • APSAND EXP_R
   71 STATEMENT_ASSIGN: EXP_L ASSIGN EXP_R • SEMICOLON

    SEMICOLON  shift, and go to state 184
    LOR        shift, and go to state 81
    LAND       shift, and go to state 82
    BOR        shift, and go to state 83
    BXOR       shift, and go to state 84
    APSAND     shift, and go to state 85
    EQ         shift, and go to state 86
    NE         shift, and go to state 87
    LE         shift, and go to state 88
    GE         shift, and go to state 89
    LT         shift, and go to state 90
    GT         shift, and go to state 91
    PLUS       shift, and go to state 92
    MINUS      shift, and go to state 93
    STAR       shift, and go to state 94
    DIVIDE     shift, and go to state 95
    MOD        shift, and go to state 96


State 178

   89 RECV_HD_ARRAY: ID SQUARE_LEFT SQUARE_RIGHT MORE_ARRAY_DIM •

    $default  reduce using rule 89 (RECV_HD_ARRAY)


State 179

   48 MORE_ARRAY_DIM: • SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   49               | • %empty  [COMMA, ROUND_RIGHT]
   90 RECV_HD_ARRAY: ID SQUARE_LEFT EXP_R SQUARE_RIGHT • MORE_ARRAY_DIM

    SQUARE_LEFT  shift, and go to state 116

    $default  reduce using rule 49 (MORE_ARRAY_DIM)

    MORE_ARRAY_DIM  go to state 185


State 180

   81 SEND_FUNC_ARGS: COMMA EXP_R SEND_FUNC_ARGS •

    $default  reduce using rule 81 (SEND_FUNC_ARGS)


State 181

   48 MORE_ARRAY_DIM: SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM •

    $default  reduce using rule 48 (MORE_ARRAY_DIM)


State 182

    6 STATEMENTS_BLOCK: • BEGIN STATEMENTS END
    7                 | • BEGIN END
   10 STATEMENT: • STATEMENT_VAR_DEF
   11          | • STATEMENT_ASSIGN
   12          | • STATEMENT_IF
   13          | • STATEMENT_WHILE
   14          | • STATEMENT_STRUCT_DEF
   15          | • STATEMENT_RETURN
   16          | • EXP_R SEMICOLON
   17          | • SEMICOLON
   18 EXP_R: • EXP_R LT EXP_R
   19      | • EXP_R LE EXP_R
   20      | • EXP_R GT EXP_R
   21      | • EXP_R GE EXP_R
   22      | • EXP_R NE EXP_R
   23      | • EXP_R EQ EXP_R
   24      | • EXP_R PLUS EXP_R
   25      | • EXP_R MINUS EXP_R
   26      | • EXP_R STAR EXP_R
   27      | • EXP_R DIVIDE EXP_R
   28      | • EXP_R MOD EXP_R
   29      | • EXP_R LOR EXP_R
   30      | • EXP_R LAND EXP_R
   31      | • EXP_R BOR EXP_R
   32      | • EXP_R BXOR EXP_R
   33      | • EXP_R APSAND EXP_R
   34      | • PLUS EXP_R
   35      | • MINUS EXP_R
   36      | • ROUND_LEFT EXP_R ROUND_RIGHT
   37      | • EXP_L
   38      | • APSAND EXP_L
   39      | • STAR EXP_L
   40      | • CONST_STRING
   41      | • NUMBER
   42      | • FUNC_CALL
   43 EXP_L: • ID
   44      | • ID DOT EXP_L
   45      | • HD_ARRAY
   46      | • HD_ARRAY DOT EXP_L
   47 HD_ARRAY: • ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM
   50 NUMBER: • CONST_INTEGER
   51       | • CONST_BOOLEAN
   52       | • CONST_FLOAT
   53 STATEMENT_IF: • IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE
   57 STATEMENT_WHILE: • WHILE ROUND_LEFT EXP_R ROUND_RIGHT WHILE_BODY
   57                | WHILE ROUND_LEFT EXP_R ROUND_RIGHT • WHILE_BODY
   58 WHILE_BODY: • STATEMENTS_BLOCK
   59           | • STATEMENT
   60 STATEMENT_VAR_DEF: • VAR_DEF_TYPE DECLARE_INITIALIZE DECLARE_MORE
   61                  | • VAR_DEF_TYPE DECLARE_NON_INITIALIZE DECLARE_MORE
   71 STATEMENT_ASSIGN: • EXP_L ASSIGN EXP_R SEMICOLON
   72 STATEMENT_STRUCT_DEF: • STRUCT ID BEGIN STRUCT_MEMBER_DEF MORE_STRUCT_MEMBER_DEF END SEMICOLON
   78 STATEMENT_RETURN: • RETURN EXP_R SEMICOLON
   79 FUNC_CALL: • ID ROUND_LEFT EXP_R SEND_FUNC_ARGS ROUND_RIGHT
   80          | • ID ROUND_LEFT ROUND_RIGHT
   91 DT_STRUCT: • STRUCT ID
   93 VAR_DEF_TYPE: • DT_INTEGER
   94             | • DT_FLOAT
   95             | • DT_BOOLEAN
   96             | • DT_STRUCT

    DT_INTEGER     shift, and go to state 1
    DT_BOOLEAN     shift, and go to state 2
    DT_FLOAT       shift, and go to state 3
    STRUCT         shift, and go to state 4
    WHILE          shift, and go to state 138
    IF             shift, and go to state 139
    RETURN         shift, and go to state 140
    CONST_INTEGER  shift, and go to state 45
    CONST_FLOAT    shift, and go to state 46
    CONST_STRING   shift, and go to state 47
    CONST_BOOLEAN  shift, and go to state 48
    ID             shift, and go to state 49
    SEMICOLON      shift, and go to state 141
    ROUND_LEFT     shift, and go to state 50
    BEGIN          shift, and go to state 106
    APSAND         shift, and go to state 51
    PLUS           shift, and go to state 52
    MINUS          shift, and go to state 53
    STAR           shift, and go to state 54

    STATEMENTS_BLOCK      go to state 186
    STATEMENT             go to state 187
    EXP_R                 go to state 145
    EXP_L                 go to state 146
    HD_ARRAY              go to state 57
    NUMBER                go to state 58
    STATEMENT_IF          go to state 147
    STATEMENT_WHILE       go to state 148
    WHILE_BODY            go to state 188
    STATEMENT_VAR_DEF     go to state 149
    STATEMENT_ASSIGN      go to state 150
    STATEMENT_STRUCT_DEF  go to state 151
    STATEMENT_RETURN      go to state 152
    FUNC_CALL             go to state 59
    DT_STRUCT             go to state 11
    VAR_DEF_TYPE          go to state 12


State 183

    6 STATEMENTS_BLOCK: • BEGIN STATEMENTS END
    7                 | • BEGIN END
   53 STATEMENT_IF: IF ROUND_LEFT EXP_R ROUND_RIGHT • STATEMENTS_BLOCK STATEMENT_ELSE

    BEGIN  shift, and go to state 106

    STATEMENTS_BLOCK  go to state 189


State 184

   71 STATEMENT_ASSIGN: EXP_L ASSIGN EXP_R SEMICOLON •

    $default  reduce using rule 71 (STATEMENT_ASSIGN)


State 185

   90 RECV_HD_ARRAY: ID SQUARE_LEFT EXP_R SQUARE_RIGHT MORE_ARRAY_DIM •

    $default  reduce using rule 90 (RECV_HD_ARRAY)


State 186

   58 WHILE_BODY: STATEMENTS_BLOCK •

    $default  reduce using rule 58 (WHILE_BODY)


State 187

   59 WHILE_BODY: STATEMENT •

    $default  reduce using rule 59 (WHILE_BODY)


State 188

   57 STATEMENT_WHILE: WHILE ROUND_LEFT EXP_R ROUND_RIGHT WHILE_BODY •

    $default  reduce using rule 57 (STATEMENT_WHILE)


State 189

   53 STATEMENT_IF: IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK • STATEMENT_ELSE
   54 STATEMENT_ELSE: • ELSE STATEMENTS_BLOCK
   55               | • ELSE STATEMENT_IF
   56               | • %empty  [DT_INTEGER, DT_BOOLEAN, DT_FLOAT, STRUCT, WHILE, IF, RETURN, CONST_INTEGER, CONST_FLOAT, CONST_STRING, CONST_BOOLEAN, ID, SEMICOLON, ROUND_LEFT, END, APSAND, PLUS, MINUS, STAR]

    ELSE  shift, and go to state 190

    $default  reduce using rule 56 (STATEMENT_ELSE)

    STATEMENT_ELSE  go to state 191


State 190

    6 STATEMENTS_BLOCK: • BEGIN STATEMENTS END
    7                 | • BEGIN END
   53 STATEMENT_IF: • IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE
   54 STATEMENT_ELSE: ELSE • STATEMENTS_BLOCK
   55               | ELSE • STATEMENT_IF

    IF     shift, and go to state 139
    BEGIN  shift, and go to state 106

    STATEMENTS_BLOCK  go to state 192
    STATEMENT_IF      go to state 193


State 191

   53 STATEMENT_IF: IF ROUND_LEFT EXP_R ROUND_RIGHT STATEMENTS_BLOCK STATEMENT_ELSE •

    $default  reduce using rule 53 (STATEMENT_IF)


State 192

   54 STATEMENT_ELSE: ELSE STATEMENTS_BLOCK •

    $default  reduce using rule 54 (STATEMENT_ELSE)


State 193

   55 STATEMENT_ELSE: ELSE STATEMENT_IF •

    $default  reduce using rule 55 (STATEMENT_ELSE)
