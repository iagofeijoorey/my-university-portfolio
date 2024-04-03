import java.util.ArrayList;
import java.util.Collections;

final public class AccionSuerte extends Accion {

    private ArrayList<CartaSuerte> barajaSuerte;
    public AccionSuerte(String nombre) {
        super(nombre);
        barajaSuerte = new ArrayList<>();
        barajaSuerte.add(new CartaSuerte(1, "Ve al Aeropuerto y coge un avión. Si pasas por la casilla de Salida, cobra."));
        barajaSuerte.add(new CartaSuerte(2, "Decides hacer un viaje de placer. Avanza hasta Cádiz"));
        barajaSuerte.add(new CartaSuerte(3, "Has ganado el bote de la lotería! Recibe 1M€."));
        barajaSuerte.add(new CartaSuerte(4, "Paga 250k€ por la matrícula del colegio privado."));
        barajaSuerte.add(new CartaSuerte(5, "Has sido elegido presidente de la junta directiva. Paga a cada jugador 70k€."));
        barajaSuerte.add(new CartaSuerte(6, "Te multan por usar el móvil mientras conduces. Paga 400k€."));
    }
    @Override
    public String getDescripcionCasilla() {
        return super.getDescripcionCasilla();
    }

    public void barajar() { Collections.shuffle(barajaSuerte); }

    public CartaSuerte getCarta(int n) { return this.barajaSuerte.get(n);}

    @Override
    public void accionar(Jugador jActual, ArrayList<Casilla> casillas, Entero tiradas, Entero dobles, Jugador banca, ArrayList<Jugador> jugadores) {
        String stringID;
        int ID;
        int sumar = 0;



        Juego.getConsola().imprimir("Has caído en una casilla de Suerte. Escoge un número del 1 al 6 para revelar que acción tomarás. ");
        stringID = Juego.getConsola().leer("");
        ID = Integer.parseInt(stringID);

        barajar();

        CartaSuerte cartaElegida = getCarta(ID);

        int posicionActual = 0, i = 0;
        for(Casilla c: casillas)
        {
            if ((c.estaAvatar(jActual.getAvatar())))
            {
                posicionActual = i;
            }
            else i++;
        }
        cartaElegida.accion(jActual,banca,sumar,casillas,tiradas,dobles,jugadores,posicionActual);

    }
}