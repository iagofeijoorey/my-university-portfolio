import java.util.ArrayList;

public abstract class Carta {
    private int id;
    private String descripcion;

    public Carta(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getDescripcion() { return this.descripcion; }

    public int getID() { return this.id; }
    public abstract void accion(Jugador jActual, Jugador banca, int sumar, ArrayList<Casilla> casillas, Entero tiradas, Entero dobles, ArrayList<Jugador> jugadores,int posicionActual);
}
