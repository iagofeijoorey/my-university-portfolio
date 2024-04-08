public class tratoTipo7 extends Trato {
//TIPO 7: INTERCAMBIAR POSICIONES

    public tratoTipo7(Jugador jugadorPropone, Jugador jugadorPropuesto) {
        super(jugadorPropone, jugadorPropuesto, jugadorPropone.toString() + ": " + jugadorPropuesto.toString() + ", intercambiamos nuestras posiciones?");
    }

    @Override
    public String getImpresion() {
        return """
                {
                    jugadorPropone: %s
                    trato: intercambiar posiciones (%d, %d)
                }
                """.formatted(this.getJugadorPropone(),this.getJugadorPropone().getAvatar().getPosicion()%40,this.getJugadorPropuesto().getAvatar().getPosicion()%40);
    }

    @Override
    public void aceptarTrato() {
        //Realización del trato
        int pos = this.getJugadorPropone().getAvatar().getPosicion();
        this.getJugadorPropone().getAvatar().setPosicion(this.getJugadorPropuesto().getAvatar().getPosicion());
        this.getJugadorPropuesto().getAvatar().setPosicion(pos);

        //Información del trato
        Juego.getConsola().imprimir(this.getJugadorPropone().toString() + " ahora está en la posición " + this.getJugadorPropone().getAvatar().getPosicion()%40 + " y " + this.getJugadorPropuesto().toString() + " está en " + this.getJugadorPropuesto().getAvatar().getPosicion()%40 + ".");
    }
}
