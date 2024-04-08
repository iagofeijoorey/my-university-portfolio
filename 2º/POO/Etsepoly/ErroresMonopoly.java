public class ErroresMonopoly extends Exception {
    public ErroresMonopoly(String message) {
        super("Error: " + message);
    }
}
