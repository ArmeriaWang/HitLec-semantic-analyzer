package wang.armeria.common;

import java.util.Objects;

/**
 * 词法分析单元token（Immutable）
 */
public class Token {

    private final TokenType tokenType;
    private final Object value;
    private final Position position;

    public Token(TokenType tokenType, Object value, Position position) {
        this.value = value;
        this.tokenType = tokenType;
        this.position = position;
    }

    public static Token parseToken(String line1, String line2) {
        TokenType tokenType;
        Object value = null;
        if (line1.length() < 2) {
            throw new IllegalArgumentException();
        }

        for (int i = 1; i < line1.length() - 1; i++) {
            if (line1.charAt(i) == ',') {
                tokenType = TokenType.valueOf(line1.substring(1, i).trim());
                String valueString = line1.substring(i + 1, line1.length() - 1);
                switch (tokenType) {
                    case CONST_INTEGER:
                        value = Integer.parseInt(valueString);
                        break;
                    case CONST_BOOLEAN:
                        value = Boolean.parseBoolean(valueString);
                        break;
                    case CONST_FLOAT:
                        value = Float.parseFloat(valueString);
                        break;
                    case CONST_STRING:
                    case IDENTIFIER:
                        value = valueString;
                }
                return new Token(tokenType, value, Position.parsePosition(line2));
            }
        }
        throw new IllegalArgumentException();
    }

    public Object getValue() {
        return value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return String.format("(%-13s,%s)\n%s", tokenType, value == null ? "" : value.toString(), position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return tokenType == token.tokenType && value.equals(token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType, value);
    }
}
