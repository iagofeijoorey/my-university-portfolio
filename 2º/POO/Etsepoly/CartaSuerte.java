import java.util.ArrayList;

public final class CartaSuerte extends Carta {

    public CartaSuerte( int id, String descripcion) {
        super(id, descripcion);
    }

    public int getID() { return super.getID(); }
    public String getDescripcion() { return super.getDescripcion(); }
    @Override
    public void accion(Jugador jActual, Jugador banca, int sumar, ArrayList<Casilla> casillas, Entero tiradas, Entero dobles, ArrayList<Jugador> jugadores,int posicionActual) {
        switch (super.getID())
        {

            case 1: //Ve al Aeropuerto y coge un avión. Si pasas por la casilla de Salida, cobra.
                Juego.getConsola().imprimir("Ve al Aeropuerto y coge un avión. Si pasas por la casilla de Salida, cobra.");
                if (posicionActual % 40 > 35) {
                    sumar = 70 - (posicionActual % 40);
                } else {
                    sumar = 35 - (posicionActual % 40);
                }
                jActual.getAvatar().moverEnBasico(sumar, 0, casillas, tiradas, dobles, banca, jugadores);
                break;

            case 2: //Decides hacer un viaje de placer. Avanza hasta Cádiz
                Juego.getConsola().imprimir("Decides hacer un viaje de placer. Avanza hasta Cádiz");
                sumar = 39 - (posicionActual %40);
                jActual.getAvatar().moverEnBasico(sumar, 0, casillas, tiradas, dobles, banca, jugadores);
                break;

            case 3: //Has ganado el bote de la lotería! Recibe 1M€.
                Juego.getConsola().imprimir("Has ganado el bote de la lotería! Recibe 1M€.");
                banca.pagarA(jActual,1000000);
                break;

            case 4: //Paga 250k€ por la matrícula del colegio privado.
                Juego.getConsola().imprimir("Paga 250k€ por la matrícula del colegio privado.");
                jActual.pagarA(banca,250000);
                break;

            case 5: //Has sido elegido presidente de la junta directiva. Paga a cada jugador 70k€.
                Juego.getConsola().imprimir("Has sido elegido presidente de la junta directiva. Paga a cada jugador 70k€.");
                for(Jugador J : jugadores)
                {
                    jActual.pagarA(J, 70000);
                }
                break;

            case 6: //Te multan por usar el móvil mientras conduces. Paga 400k€.
                Juego.getConsola().imprimir("Te multan por usar el móvil mientras conduces. Paga 400k€.");
                jActual.pagarA(banca, 400000);
                break;
        }
    }
}
