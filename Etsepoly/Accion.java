import java.util.ArrayList;

public class Accion extends Casilla {

    public Accion(String nombre) {
        super(nombre);
        this.setValor(0);
    }
    @Override
    public String getDescripcionCasilla() {
        return """
                {
                    nombre: %s
                    jugadores: %s
                 }
                """.formatted(super.toString(),super.jugadoresAquiToString());
    }

    public void accionar(Jugador jugador, ArrayList<Casilla> casillas, Entero tiradas, Entero dobles, Jugador banca, ArrayList<Jugador> jugadores) { //para parking
        Juego.getConsola().imprimir("Caes en la casilla de Párking, por lo que recibes el bote acumulado (" + (int)this.valor() + ")");
        jugador.sumarDinero(this.valor());
        this.setValor(0);
    }

    public void aumentarBote(double cantidad) {this.setValor(this.valor() + cantidad);}
}
