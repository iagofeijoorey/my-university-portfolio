public final class tratoTipo6 extends Trato {
//TIPO 6: PROPIEDAD POR PROPIEDAD Y NOALQUILER NTURNOS
    private final Propiedad propiedadPropuesta;
    private final Propiedad propiedadRequerida;
    private final Propiedad noAlquiler;
    private final int nTurnos;
    public tratoTipo6(Jugador jugadorPropone, Jugador jugadorPropuesto, Propiedad propiedadPropuesta, Propiedad propiedadRequerida, Propiedad noAlquiler, int nTurnos) {
        super(jugadorPropone, jugadorPropuesto, jugadorPropone.toString() + ": " + jugadorPropuesto.toString() + ", ¿te doy " + propiedadPropuesta.toString() + " y tú me das " + propiedadRequerida.toString() + " y no pago alquiler en " + noAlquiler.toString() + " durante " + nTurnos + " turnos?");
        this.propiedadPropuesta = propiedadPropuesta;
        this.propiedadRequerida = propiedadRequerida;
        this.noAlquiler = noAlquiler;
        this.nTurnos = nTurnos;
    }

    @Override
    public String getImpresion() {
        return """
                {
                    jugadorPropone: %s
                    trato: cambiar (%s, %s) y noAlquiler en %s durante %d turnos
                }
                """.formatted(this.getJugadorPropone(),this.propiedadPropuesta.toString(),this.propiedadRequerida.toString(),this.noAlquiler,this.nTurnos);
    }

    @Override
    public void aceptarTrato() throws TratoException {
        //Comprobaciones
        checkPropiedadPropuesta(this.propiedadPropuesta);
        checkPropiedadRequerida(this.propiedadRequerida);
        checkPropiedadRequerida(this.noAlquiler);
        //Realización del trato
        this.propiedadPropuesta.transferirA(this.getJugadorPropuesto());
        this.propiedadRequerida.transferirA(this.getJugadorPropone());
        this.getJugadorPropone().crearCooldownAlquiler(this.noAlquiler,this.nTurnos);
        //Información del trato
        Juego.getConsola().imprimir(this.propiedadPropuesta.toString() + " ahora es de " + this.propiedadPropuesta.getPropietario().toString() + " y " + this.propiedadRequerida.toString() + " es de " + this.propiedadRequerida.getPropietario().toString() + ". Además, " + this.getJugadorPropone().toString() + " no paga alquiler en " + this.noAlquiler.toString() + " durante " + this.nTurnos + " turnos.");
    }

}
