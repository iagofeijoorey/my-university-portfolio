public final class tratoTipo2 extends Trato {
//TIPO 2: PROPIEDAD POR DINERO
    private final Propiedad propiedadPropuesta;
    private final double dineroRequerido;
    public tratoTipo2(Jugador jugadorPropone, Jugador jugadorPropuesto,Propiedad propiedadPropuesta,double dineroRequerido) {
        super(jugadorPropone, jugadorPropuesto, (jugadorPropone.toString() + ": " + jugadorPropuesto.toString() + ", ¿te doy " + propiedadPropuesta.toString() + " y tú me das " + (int)dineroRequerido + "?"));
        this.propiedadPropuesta = propiedadPropuesta;
        this.dineroRequerido = dineroRequerido;
    }

    @Override
    public String getImpresion() {
        return """
                {
                    jugadorPropone: %s
                    trato: cambiar (%s, %.0f)
                }
                """.formatted(this.getJugadorPropone(),this.propiedadPropuesta.toString(),this.dineroRequerido);
    }

    @Override
    public void aceptarTrato() throws TratoException {
        //Comprobaciones
        checkPropiedadPropuesta(this.propiedadPropuesta);
        checkDineroRequerido(this.dineroRequerido);
        //Realización del trato
        propiedadPropuesta.transferirA(getJugadorPropuesto());
        this.getJugadorPropuesto().pagarA(this.getJugadorPropone(),this.dineroRequerido);
        //Información del trato
        Juego.getConsola().imprimir(this.propiedadPropuesta.toString() + " ahora es de " + this.propiedadPropuesta.getPropietario().toString() + " y " + this.getJugadorPropuesto().toString() + " le paga " + (int)this.dineroRequerido + " a " + this.getJugadorPropone().toString() + ".");
    }
}
