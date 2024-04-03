public final class tratoTipo5 extends Trato {
//TIPO 5: PROPIEDAD Y DINERO POR PROPIEDAD
    private final Propiedad propiedadPropuesta;
    private final double dineroPropuesto;
    private final Propiedad propiedadRequerida;

    public tratoTipo5(Jugador jugadorPropone, Jugador jugadorPropuesto, Propiedad propiedadPropuesta, double dineroPropuesto, Propiedad propiedadRequerida) {
        super(jugadorPropone,jugadorPropuesto,(jugadorPropone.toString() + ": " + jugadorPropuesto.toString() + ", ¿te doy " + propiedadPropuesta.toString() + " y " + (int)dineroPropuesto + " y tú me das " + propiedadRequerida.toString() + "?"));
        this.propiedadPropuesta = propiedadPropuesta;
        this.dineroPropuesto = dineroPropuesto;
        this.propiedadRequerida = propiedadRequerida;
    }

    @Override
    public String getImpresion() {
        return """
                {
                    jugadorPropone: %s
                    trato: cambiar (%s y %.0f, %s)
                }
                """.formatted(this.getJugadorPropone(),this.propiedadPropuesta.toString(),this.dineroPropuesto,this.propiedadRequerida.toString());
    }

    @Override
    public void aceptarTrato() throws TratoException {
        //Comprobaciones
        checkPropiedadPropuesta(this.propiedadPropuesta);
        checkDineroPropuesto(this.dineroPropuesto);
        checkPropiedadRequerida(this.propiedadRequerida);
        //Realización del trato
        this.propiedadPropuesta.transferirA(this.getJugadorPropuesto());
        this.getJugadorPropone().pagarA(this.getJugadorPropuesto(),this.dineroPropuesto);
        this.propiedadRequerida.transferirA(this.getJugadorPropone());
        //Información
        Juego.getConsola().imprimir(this.propiedadPropuesta.toString() + " es ahora de " + this.propiedadPropuesta.toString() + " y " + this.propiedadRequerida + " de " + this.propiedadRequerida.getPropietario().toString() + ". " + this.getJugadorPropuesto().toString() + " recibe " + (int)this.dineroPropuesto + " de " + this.getJugadorPropone().toString() + ".");
    }
}
