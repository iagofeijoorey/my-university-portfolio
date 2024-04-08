import java.util.Scanner;

public final class ConsolaNormal implements Consola {
    private final Scanner scanner;

    public ConsolaNormal() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void imprimir(String mensaje) {
        System.out.print(mensaje);
    }

    @Override
    public String leer(String descripcion) {
        imprimir(descripcion);
        return scanner.nextLine();
    }
}
