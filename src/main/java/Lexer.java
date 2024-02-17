

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private String originalCode = "";
    private static final String[][] TOKEN_PATTERNS = {
            {"true", "true"},
            {"false", "false"},
            {"if", "if"},
            {"else", "else"},
            {"while", "while"},
            {"read", "read"},
            {":=", ":="},
            {"print", "print"},
            {"(", "\\("},
            {")", "\\)"},
            {"{", "\\{"},
            {"}", "\\}"},
            {";", ";"},
            {"=", "="},
            {"<", "<"},
            {">", ">"},
            {"+", "\\+"},
            {"-", "-"},
            {"id", "[a-z][a-z0-9]*"},
            {"num", "[0-9]+"}
    };

    public List<String> lex(String code) throws SyntaxError {
        List<String> tokens = new ArrayList<>();
        originalCode = code;
        while (!code.isEmpty()) {
            code = code.trim();
            boolean matched = false;

            for (String[] tokenPattern : TOKEN_PATTERNS) {
                Pattern pattern = Pattern.compile("^" + tokenPattern[1]);
                Matcher matcher = pattern.matcher(code);

                if (matcher.find()) {
                    tokens.add(tokenPattern[0]); 
                    code = code.substring(matcher.end());
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                System.out.println("Parse error");
                System.exit(0);
            }
        }

        return tokens;
    }
}