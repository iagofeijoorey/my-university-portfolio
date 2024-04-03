import java.util.ArrayList;

public final class CartaCajaComunidad extends Carta {

    public CartaCajaComunidad( int id, String descripcion) {
        super(id, descripcion);
    }

    public int getID() { return super.getID(); }
    public String getDescripcion() { return super.getDescripcion(); }

    public void accion(Jugador jActual, Jugador banca, int sumar, ArrayList<Casilla> casillas, Entero tiradas, Entero dobles, ArrayList<Jugador> jugadores,int posicionActual) {

        switch (super.getID() - 6)
        {
            case 1: //Paga 300k€ por un fin de semana en un balneario de 5 estrellas.
                Juego.getConsola().imprimir("Paga 300k€ por un fin de semana en un balneario de 5 estrellas.");
                jActual.pagarA(banca,300000);
                break;

            case 2: //Colócate en la casilla de Salida. Cobra 200k€.
                Juego.getConsola().imprimir("Colócate en la casilla de Salida. Cobra 200k€.");
                sumar = 40 - (posicionActual%40);
                jActual.getAvatar().moverEnBasico(sumar, 0, casillas, tiradas, dobles, banca, jugadores);
                banca.pagarA(jActual, 200000);
                break;

            case 3: //Alquilas a tus compañeros una villa en Cannes durante una semana. Paga 50k€ a cada jugador.
                Juego.getConsola().imprimir("Alquilas a tus compañeros una villa en Cannes durante una semana. Paga 50k€ a cada jugador.");
                for(Jugador J : jugadores)
                {
                    jActual.pagarA(J, 50000);
                }
                break;

            case 4: //Tu compañía de Internet obtiene beneficios. Recibe 400k€.
                Juego.getConsola().imprimir("Tu compañía de Internet obtiene beneficios. Recibe 400k€.");
                banca.pagarA(jActual,400000);
                break;

            case 5: //Paga 500k€ por invitar a todos tus amigos a un viaje a León.
                Juego.getConsola().imprimir("Paga 500k€ por invitar a todos tus amigos a un viaje a León.");
                jActual.pagarA(banca,500000);
                break;

            case 6: //Recibe 500k€ de beneficios por alquilar los servicios de tu jet privado.
                Juego.getConsola().imprimir("Recibe 500k€ de beneficios por alquilar los servicios de tu jet privado.");
                banca.pagarA(jActual,500000);
                break;

            default:
                Juego.getConsola().imprimir("Error, " + super.getID() + " no es válido.");
                break;
        }
        //Juego.getConsola().imprimir("Numero " + super.getID());
    }

}
