import tokens.ClosureSymbols;
import tokens.Token;
import tokens.TokenType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ClojureScriptLexer {
    private int position = 0;

     public ArrayList<Token> lex(String code) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        while (position < code.length()) {
            char current = code.charAt(position);
            if (Character.isWhitespace(current)) {
                position++;
                continue;
            }

            if (Character.isDigit(current) || isNegativeNumber(code)) {
                tokens.add(this.lexNumber(code));
            } else if (current == '"' || (current == '#' && position + 1 < code.length() && code.charAt(position + 1) == '"')){
                tokens.add(this.lexString(code));
            } else if (Character.isLetter(current) || oneOf(current, '_', '$', ':', '#', '@')) {
                tokens.add(this.lexIdentifier(code));
            } else if (isComment(current)) {
                tokens.add(this.lexComment(code));
            }  else if (isCollection(current)) {
                tokens.add(this.lexCollection(code));
            } else if (isOperator(String.valueOf(current))) {
                tokens.add(this.lexOperator(code));
            } else if (current == '\'') {
                position++;
            }else {
                throw new Error("Unexpected character: " + current + " at position: " + position);
            }
        }
        return tokens;
    }

    private Token lexNumber(String code) {
        StringBuilder value = new StringBuilder();
        if (isNegativeNumber(code)) {
            // Add the negative sign
            addToValueAndIncreasePosition(value, code);
        }
        addNextDigits(value, code);

        // Numbers with decimal point
        if (code.charAt(position) == '.') {
            addToValueAndIncreasePosition(value, code);
            addNextDigits(value, code);
        }

        // Scientific notation: 1e10 or 1e-10
        if (code.charAt(position) == 'e') {
            addToValueAndIncreasePosition(value, code);
            char next = code.charAt(position);

            if (next == '-' || Character.isDigit(next)) {
                addToValueAndIncreasePosition(value, code);
            } else {
                throw new Error("Unexpected character: " + next);
            }
            addNextDigits(value, code);
            return new Token(TokenType.NUMBER, value.toString());
        }

        if (Character.isLetter(code.charAt(position))) {
            throw new Error("Unexpected character: " + code.charAt(position));
        }

        return new Token(TokenType.NUMBER, value.toString());
    }

    private Token lexIdentifier(String code) {
        StringBuilder value = new StringBuilder();
        if (oneOf(code.charAt(position), '#', '@')) {
            addToValueAndIncreasePosition(value, code);
        }

        if (code.charAt(position) == ':') {
            if (position + 1 < code.length() && code.charAt(position + 1) == ':') {
                addToValueAndIncreasePosition(value, code);
            }
            addToValueAndIncreasePosition(value, code);
        }

        while (isInBounds(position, code) && (Character.isLetterOrDigit(code.charAt(position)) || oneOf(code.charAt(position), '-', '$', '_', '.', '/', '*', '>', '<'))) {
            addToValueAndIncreasePosition(value, code);
        }

        if (isInBounds(position, code) && oneOf(code.charAt(position), '?', '!', '=')) {
            addToValueAndIncreasePosition(value, code);
        }

        if (isKeyword(value.toString())) {
            return new Token(TokenType.KEYWORD, value.toString());
        } else if (isOperator(value.toString())) {
            return new Token(TokenType.OPERATOR, value.toString());
        } else if (isBoolean(value.toString())) {
            return new Token(TokenType.BOOLEAN, value.toString());
        }

        return new Token(TokenType.IDENTIFIER, value.toString());
    }

    private Token lexCollection(String code) {
        StringBuilder value = new StringBuilder();
        addToValueAndIncreasePosition(value, code);
        return new Token(TokenType.COLLECTION, value.toString());
    }

    private Token lexComment(String code) {
        StringBuilder value = new StringBuilder();
        while (position < code.length() && code.charAt(position) != '\n') {
            addToValueAndIncreasePosition(value, code);
        }
        return new Token(TokenType.COMMENT, value.toString());
    }

    private Token lexOperator(String code) {
        StringBuilder value = new StringBuilder();
        while (position < code.length() && !Character.isDigit(code.charAt(position)) && !Character.isWhitespace(code.charAt(position))) {
                addToValueAndIncreasePosition(value, code);
        }

        if (isOperator(value.toString())) {
            return new Token(TokenType.OPERATOR, value.toString());
        } else {
            throw new Error("Unexpected character: " + value.toString());
        }
    }

    private Token lexString(String code) {
        StringBuilder value = new StringBuilder();
        handleIfRegex(value, code);
        readStringWhileNotClosed(value, code);
        return new Token(TokenType.STRING, value.toString());
    }

    public static void main(String[] args) {
        Path path = Path.of("src/main/java/file.txt");
        String code;

        try {
            code = Files.readString(path).replaceAll("\r", "\n").trim();
            ClojureScriptLexer lexer = new ClojureScriptLexer();
            ArrayList<Token> tokens = lexer.lex(code);
            System.out.printf("%-15s %-15s\n", "Token", "Value");

            for (Token token : tokens) {
                System.out.printf("%-15s %-15s\n", token.type, token.value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Helper methods
    private void addToValueAndIncreasePosition(StringBuilder value, String code) {
        value.append(code.charAt(position));
        position++;
    }

    private boolean isNegativeNumber(String code) {
        return code.charAt(position) == '-' && position + 1 < code.length() && Character.isDigit(code.charAt(position + 1));
    }

    private void addNextDigits(StringBuilder value, String code) {
        while (position < code.length() && Character.isDigit(code.charAt(position)) ) {
            addToValueAndIncreasePosition(value, code);
        }
    }

    private void handleIfRegex( StringBuilder value, String code) {
        if (code.charAt(position) == '#' && position + 1 < code.length() && code.charAt(position + 1) == '"') {
            addToValueAndIncreasePosition(value, code);
        }
    }

    private void readStringWhileNotClosed(StringBuilder value, String code) {
         // Add opening "
        addToValueAndIncreasePosition(value, code);

        while (position < code.length() && code.charAt(position) != '"') {
            addToValueAndIncreasePosition(value, code);
        }

        if (position == code.length()) {
            throw new Error("String is not closed at position " + position);
        }

        // Add closing "
        addToValueAndIncreasePosition(value, code);
    }

    private boolean isKeyword(String value) {
        return ClosureSymbols.clojureSymbols.get("keywords").contains(value);
    }

    private boolean isOperator(String value) {
        return ClosureSymbols.clojureSymbols.get("operator").contains(value);
    }

    private boolean isCollection(char value) {
        return ClosureSymbols.clojureSymbols.get("collections").contains(String.valueOf(value));
    }

    private boolean isComment(char current) {
        return current == ';';
    }

    private boolean isBoolean(String value) {
        return value.equals("true") || value.equals("false");
    }

    public boolean isInBounds(int position, String code) {
        return position < code.length();
    }

    private boolean oneOf(char current, char... values) {
        for (char value : values) {
            if (current == value) {
                return true;
            }
        }
        return false;
    }
}
