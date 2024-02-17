import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Parse {
    private List<String> tokens;
    private String currentToken;
    private int index;

    public static void main(String[] args) {
            
        try (Scanner scanner = new Scanner(System.in)) {
            StringBuilder stringbuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringbuilder.append(scanner.nextLine().trim());
            }
            String code = stringbuilder.toString();

            Lexer lexer = new Lexer();
            List<String> tokens = lexer.lex(code);
            // System.out.println("Tokens: " + tokens.toString());

            Parse parser = new Parse();
            parser.parse(tokens);

            // System.out.println("Program parsed successfully");
        } catch (SyntaxError e) {
            System.out.println("Parse error");
        } catch (Exception e) {
            System.out.println("Parse error");
        }
    }

    public void parse(List<String> inputTokens) {
        this.tokens = inputTokens;
        this.index = 0;
        try{
            // System.out.println("Currently Parsing" + tokens);
            currentToken = tokens.get(index);
            sl();
            // System.out.println("Program parsed successfully");
        } catch (SyntaxError e) {
            System.out.println("Parse error");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Parse error");
            e.printStackTrace();
        }
    }

    public void match(String expectedToken) throws SyntaxError {
        // System.out.println("Matching: " + expectedToken + ", Current Token: " + currentToken); 
        if (currentToken.equals(expectedToken)) {
            index++;
            if (index < tokens.size()) {
                currentToken = tokens.get(index);
            }
            else {
                currentToken = null;
                System.out.println("Program parsed successfully");
                System.exit(0);
            }
        } else {
            // throw new SyntaxError("Expected " + expectedToken + ", but found " + currentToken, currentToken);
            System.out.println("Parse error");
            System.exit(0);
        }

    }

    public void sl() throws SyntaxError {
        if (currentToken.equals("if") || currentToken.equals("while") || currentToken.equals("id") || currentToken.equals("print")) {
            s();
            match(";");
            sl();
        }
    }

    public void s() throws SyntaxError {
        switch (currentToken) {
            case "if":
                match("if");
                match("(");
                c(); 
                match(")");
                match("{");
                sl(); 
                match("}");
                match("else");
                match("{");
                sl(); 
                match("}");
                break;
            case "while":
                match("while");
                match("(");
                c(); 
                match(")");
                match("{");
                sl(); 
                match("}");
                break;
            case "id":
                match("id");
                match(":=");
                e(); 
                break;
            case "print":
                match("print");
                match("(");
                e(); 
                match(")");
                break;
            default:
                // throw new SyntaxError("Unexpected token: " + currentToken, currentToken);
                System.out.println("Parse error");
                System.exit(0);
        }  
    }

    public void c() throws SyntaxError {
        if (currentToken.equals("true") || currentToken.equals("false")) {
            match(currentToken);
        } else {
            e(); 
            r(); 
            e(); 
        }
    }

    public void e() throws SyntaxError {
        t(); 
        tt();
    }
    
    public void tt() throws SyntaxError {
        if (currentToken.equals("+") || currentToken.equals("-")) {
            addop(); 
            t(); 
            tt(); 
        }
    }
        
    public void t() throws SyntaxError {
        switch (currentToken) {
            case "(":
                match("(");
                e(); 
                match(")");
                break;
            case "id":
                match("id"); 
                break;
            case "read":
                match("read");
                match("id");
                break;
            case "num":
                match("num"); 
                break;
            default:
                // throw new SyntaxError("Unexpected token: " + currentToken, currentToken);
                System.out.println("Parse error");
                System.exit(0);
        }

    }

    public void r() throws SyntaxError {
        if (currentToken.equals("=") || currentToken.equals("<") || currentToken.equals(">")) {
            match(currentToken); 
        } else {
            // throw new SyntaxError("Unexpected token: " + currentToken, currentToken);
            System.out.println("Parse error");
            System.exit(0);
        }
    }

    public void addop() throws SyntaxError {
        if (currentToken.equals("+") || currentToken.equals("-")) {
            match(currentToken); 
        } else {
            // throw new SyntaxError("Unexpected token: " + currentToken, currentToken);
            System.out.println("Parse error");
            System.exit(0);
        }
    }
}