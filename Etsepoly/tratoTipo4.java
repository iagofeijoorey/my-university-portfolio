public final class tratoTipo4 extends Trato{
//TIPO 4: PROPIEDAD POR PROPIEDAD Y DINERO
    private final Propiedad propiedadPropuesta;
    private final Propiedad propiedadRequerida;
    private final double dineroRequerido;
    public tratoTipo4(Jugador jugadorPropone, Jugador jugadorPropuesto, Propiedad propiedadPropuesta, Propiedad propiedadRequerida, double dineroRequerido) {
        super(jugadorPropone,jugadorPropuesto,(jugadorPropone.toString() + ": " + jugadorPropuesto.toString() + ", ¿te doy " + propiedadPropuesta.toString() + " y tú me das " + propiedadRequerida.toString() + " y " + (int)dineroRequerido + "?"));
        this.propiedadPropuesta = propiedadPropuesta;
        this.propiedadRequerida = propiedadRequerida;
        this.dineroRequerido = dineroRequerido;
    }

    @Override
    public String getImpresion() {
        return """
                {
                    jugadorPropone: %s
                    trato: cambiar (%s, %s y %.0f)
                }
                """.formatted(this.getJugadorPropone(),this.propiedadPropuesta.toString(),this.propiedadRequerida.toString(),this.dineroRequerido);
    }

    @Override
    public void aceptarTrato() throws TratoException {
        //Comprobaciones
        checkPropiedadPropuesta(this.propiedadPropuesta);
        checkPropiedadRequerida(this.propiedadRequerida);
        checkDineroRequerido(this.dineroRequerido);
        //Realización del trato
        this.propiedadPropuesta.transferirA(this.getJugadorPropuesto());
        this.propiedadRequerida.transferirA(this.getJugadorPropone());
        this.getJugadorPropuesto().pagarA(this.getJugadorPropone(),this.dineroRequerido);
        //Información del trato
        Juego.getConsola().imprimir(this.propiedadPropuesta.toString() + " es ahora de " + this.propiedadPropuesta.getPropietario().toString() + ", " + this.propiedadRequerida + " de " + this.propiedadRequerida.getPropietario().toString() + " y " + this.getJugadorPropone().toString() + " recibe " + (int)this.dineroRequerido + " de " + this.getJugadorPropuesto().toString());
    }
}
