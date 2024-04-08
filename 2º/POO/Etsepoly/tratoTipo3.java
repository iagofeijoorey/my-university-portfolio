public final class tratoTipo3 extends Trato {
//TIPO 3: DINERO POR PROPIEDAD
    private final double dineroPropuesto;
    private final Propiedad propiedadRequerida;
    public tratoTipo3(Jugador jugadorPropone, Jugador jugadorPropuesto, double dineroPropuesto, Propiedad propiedadPropuesta) {
        super(jugadorPropone,jugadorPropuesto,jugadorPropone.toString() + ": " + jugadorPropuesto.toString() + ", ¿te doy " + (int)dineroPropuesto + " y tú me das " + propiedadPropuesta.toString() + "?");
        this.dineroPropuesto = dineroPropuesto;
        this.propiedadRequerida = propiedadPropuesta;
    }

    @Override
    public String getImpresion() {
        return """
                {
                    jugadorPropone: %s
                    trato: cambiar (%.0f, %s)
                }
                """.formatted(this.getJugadorPropone(),this.dineroPropuesto,this.propiedadRequerida.toString());
    }

    @Override
    public void aceptarTrato() throws TratoException {
        //Comprobaciones
        checkDineroPropuesto(this.dineroPropuesto);
        checkPropiedadRequerida(this.propiedadRequerida);
        //Realización del trato
        this.getJugadorPropone().pagarA(this.getJugadorPropuesto(),dineroPropuesto);
        this.propiedadRequerida.transferirA(this.getJugadorPropone());
        //Información del trato
        Juego.getConsola().imprimir(this.propiedadRequerida.toString() + " es ahora de " + this.propiedadRequerida.getPropietario().toString() + " y " + this.getJugadorPropuesto().toString() + " recibe " + (int)this.dineroPropuesto + " de " + this.getJugadorPropone().toString());
    }
}
