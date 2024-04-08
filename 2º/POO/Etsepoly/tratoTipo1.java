public final class tratoTipo1 extends Trato {
//TIPO 1: PROPIEDAD POR PROPIEDAD
    private final Propiedad propiedadPropuesta;
    private final Propiedad propiedadRequerida;
    public tratoTipo1(Jugador jugadorPropone,Jugador jugadorPropuesto,Propiedad propiedadPropuesta,Propiedad propiedadRequerida) {
        super(jugadorPropone,jugadorPropuesto,(jugadorPropone.toString() + ": " + jugadorPropuesto.toString() + ", ¿te doy " + propiedadPropuesta.toString() + " y tú me das " + (propiedadRequerida.toString()) + "?"));
        this.propiedadPropuesta = propiedadPropuesta;
        this.propiedadRequerida = propiedadRequerida;
    }

    @Override
    public String getImpresion() {
        return """
                {
                    jugadorPropone: %s
                    trato: cambiar (%s, %s)
                }
                """.formatted(this.getJugadorPropone(),this.propiedadPropuesta.toString(),this.propiedadRequerida.toString());
    }

    @Override
    public void aceptarTrato() throws TratoException {
        //Comprobaciones
        checkPropiedadPropuesta(propiedadPropuesta);
        checkPropiedadRequerida(propiedadRequerida);
        //Realización del trato
        propiedadPropuesta.transferirA(getJugadorPropuesto());
        propiedadRequerida.transferirA(getJugadorPropone());
        //Información del trato
        Juego.getConsola().imprimir(this.propiedadPropuesta.toString() + " ahora es de " + this.propiedadPropuesta.getPropietario().toString() + " y " + this.propiedadRequerida.toString() + " es de " + this.propiedadRequerida.getPropietario().toString() + ".");
    }
}
