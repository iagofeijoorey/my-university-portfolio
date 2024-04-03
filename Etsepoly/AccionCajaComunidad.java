import java.util.ArrayList;
import java.util.Collections;

final public class AccionCajaComunidad extends Accion {
    private ArrayList<CartaCajaComunidad> barajaComunidad;
    public AccionCajaComunidad(String nombre) {
        super(nombre);
        barajaComunidad = new ArrayList<>();
        barajaComunidad.add(new CartaCajaComunidad(7, "Paga 300k€ por un fin de semana en un balneario de 5 estrellas."));
        barajaComunidad.add(new CartaCajaComunidad(8, "Colócate en la casilla de Salida. Cobra 200k€."));
        barajaComunidad.add(new CartaCajaComunidad(9, "Alquilas a tus compañeros una villa en Cannes durante una semana. Paga 50k€ a cada jugador."));
        barajaComunidad.add(new CartaCajaComunidad(10, "Tu compañía de Internet obtiene beneficios. Recibe 400k€."));
        barajaComunidad.add(new CartaCajaComunidad(11, "Paga 500k€ por invitar a todos tus amigos a un viaje a León."));
        barajaComunidad.add(new CartaCajaComunidad(12, "Recibe 500k€ de beneficios por alquilar los servicios de tu jet privado."));

    }

    @Override
    public String getDescripcionCasilla() {
        return super.getDescripcionCasilla();
    }

    public void barajar() { Collections.shuffle(barajaComunidad); }

    public CartaCajaComunidad getCarta(int n) { return this.barajaComunidad.get(n);}

    @Override
    public void accionar(Jugador jActual, ArrayList<Casilla> casillas, Entero tiradas, Entero dobles, Jugador banca, ArrayList<Jugador> jugadores) {
        String stringID;
        int ID;
        int sumar = 0;



        Juego.getConsola().imprimir("Has caido en una casilla de Caja de Comunidad. Escoge un número del 1 al 6 para revelar que acción tomarás. ");
        stringID = Juego.getConsola().leer("");
        ID = Integer.parseInt(stringID);

        barajar();

        CartaCajaComunidad cartaElegida = getCarta(ID);

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
