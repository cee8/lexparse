public class SyntaxError extends Exception {
    public SyntaxError(String message, String currentToken) {
        super(message + " Near token: " + currentToken);
    }
}