public abstract class Trato {
    private final Jugador jugadorPropone;
    private final Jugador jugadorPropuesto;
    private final String descripcion;

    public Trato(Jugador jugadorPropone,Jugador jugadorPropuesto, String descripcion) {
        this.jugadorPropone = jugadorPropone;
        this.jugadorPropuesto = jugadorPropuesto;
        this.descripcion = descripcion;
        Juego.getConsola().imprimir(descripcion);
    }
    public Jugador getJugadorPropone() {
        return jugadorPropone;
    }
    public Jugador getJugadorPropuesto() {
        return jugadorPropuesto;
    }
    public String getDescripcion() { return this.descripcion; }
    public abstract String getImpresion();
    public abstract void aceptarTrato() throws TratoException;
    public void checkPropiedadPropuesta(Propiedad propiedadPropuesta) throws TratoException {
        if (!propiedadPropuesta.getPropietario().toString().equals(getJugadorPropone().toString())) {
            throw new TratoException("La propiedad propuesta ya no es propiedad del jugador que propuso el trato.");
        }
    }
    public void checkPropiedadRequerida(Propiedad propiedadRequerida) throws TratoException {
        if (!propiedadRequerida.getPropietario().toString().equals(getJugadorPropuesto().toString())) {
            throw new TratoException("La propiedad requerida ya no es de tu propiedad.");
        }
    }
    public void checkDineroPropuesto(double dineroPropuesto) throws TratoException{
        if (jugadorPropone.getDinero() < dineroPropuesto) {
            throw new TratoException("El jugador que propuso el trato no tiene suficiente dinero para realizar el trato.");
        }
    }
    public void checkDineroRequerido(double dineroRequerido) throws TratoException {
        if (jugadorPropuesto.getDinero() < dineroRequerido) {
            throw new TratoException("No tienes suficiente dinero para aceptar el trato (te falta " + (dineroRequerido-jugadorPropuesto.getDinero()) + ").");
        }
    }
}
