public class ArgumentosInsuficientes extends ErroresMonopoly{
    public ArgumentosInsuficientes(String message, int i){
        super("Argumentos introducidos = (" + i + "): " + message);
    }
}
