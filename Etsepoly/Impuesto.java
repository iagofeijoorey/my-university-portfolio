final public class Impuesto extends Casilla {

    public Impuesto (String nombre, double impuesto) {
        super(nombre);
        this.setValor(impuesto);
    }

    @Override
    public String getDescripcionCasilla() {
        return "Completar";
    }
    public void pagarImpuestos(Accion parking, Jugador jugador) {
        Juego.getConsola().imprimir("Caes en " + this.toString() + ", por lo que pagas " + (int)this.valor() + ".");
        parking.aumentarBote(this.valor());
        jugador.sumarDinero(-this.valor());
    }
}
