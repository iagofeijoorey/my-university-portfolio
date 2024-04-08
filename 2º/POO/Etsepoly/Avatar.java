import java.util.ArrayList;

public abstract class Avatar {
    private final char ID;
    private final int numAvatar;
    private final Jugador jugadorAsociado;
    private int posicion;
    private boolean modoAvanzado;
    private int carcel;

    public Avatar(char ID, int numAvatar, Jugador jugadorAsociado) {
        this.ID = ID;
        this.numAvatar = numAvatar;
        this.jugadorAsociado = jugadorAsociado;
        this.modoAvanzado = false;
        this.carcel = 0;
        this.posicion = 0;
    }

    @Override
    public String toString() {
        return "&" + this.ID;
    }


    public char getID() {
        return this.ID;
    }
    public int getNumAvatar() {
        return this.numAvatar;
    }
    public Jugador getJugador() {
        return this.jugadorAsociado;
    }
    public int getPosicion() {
        return this.posicion;
    }
    public void setPosicion(int pos) { this.posicion = pos; }
    public boolean getModoAvanzado() {
        return this.modoAvanzado;
    }
    public void setModoAvanzado(boolean b) {
        this.modoAvanzado = b;
    }
    public int getCarcel() {
        return this.carcel;
    }
    public void setCarcel (int x) { this.carcel = x; }
    public void goCarcel(Casilla carcel) {
        this.carcel = 3;
        this.posicion = this.posicion - (this.posicion%40 - 10);
        ((Especial) carcel).setAvatarAqui(this);
    }
    public void pasarTurnoEnCarcel() {
        if (this.carcel > 0) this.carcel = this.carcel - 1;
    }
    public void salirCarcelPagando(double cantidadSalirCarcel) {
        this.carcel = 0;
        this.jugadorAsociado.sumarDinero(-cantidadSalirCarcel);
    }
    public void moverEnBasico(int dado1, int dado2, ArrayList<Casilla> casillas, Entero tiradas, Entero dobles, Jugador banca, ArrayList<Jugador> jugadores) {
        tiradas.sumar(-1);
        if (dado1 != dado2) {
            Juego.getConsola().imprimir("Sacas " + dado1 + " y " + dado2 + ".\n");
        } else {
            tiradas.sumar(1);
            dobles.sumar(1);
            Juego.getConsola().imprimir("Sacas dobles, de " + dado1 + ".\n");
        }
        if (dobles.getNumero() == 3) {
            casillas.get(this.posicion).eliminarAvatarAqui(this);
            this.goCarcel(casillas.get(10));
            Juego.getConsola().imprimir("Has sacado dobles tres veces seguidas, por lo que vas a la cárcel.");
            tiradas.setNumero(0);
            return;
        }
        if (dado1+dado2 != 0) {
            mover(dado1 + dado2, casillas);
            accionCasilla(casillas, tiradas, dobles, banca, jugadores);
        }
    }
    public String moverEnAvanzado() {
        if (this instanceof Pelota) {
            return "Pelota";
        } else if (this instanceof Coche) {
            return "Coche";
        } else {
            Juego.getConsola().imprimir("Tipo de avatar no válido para movimiento avanzado");
            return "Error";
        }
    }
    public void mover(int rDados, ArrayList<Casilla> casillas) {
        casillas.get(this.posicion%40).eliminarAvatarAqui(this);
        this.posicion += rDados;
        casillas.get(this.posicion%40).setAvatarAqui(this);
    }
    public void accionCasilla(ArrayList<Casilla> casillas, Entero tiradas, Entero dobles, Jugador banca, ArrayList<Jugador> jugadores) {
        Casilla c = casillas.get(this.posicion%40);
        if (c instanceof Propiedad)         ((Propiedad) c).alquiler(this.jugadorAsociado,casillas.get(0).valor());
        else if (c instanceof Accion)       ((Accion) c).accionar(this.jugadorAsociado, casillas, tiradas, dobles, banca, jugadores);
        else if (c instanceof Impuesto)     ((Impuesto) c).pagarImpuestos(((Accion) casillas.get(20)), this.jugadorAsociado);
        else if (c instanceof Especial) {
            switch (c.toString()) {
                case "Salida": case "Cárcel":
                    Juego.getConsola().imprimir("Caes en la casilla " + c.toString() + ".");
                    break;
                case "IrCárcel":
                    Juego.getConsola().imprimir("Caes en la casilla de IrCárcel, por lo que vas a la Cárcel.\n");
                    this.goCarcel(casillas.get(10));
                    break;
                default:
                    Juego.getConsola().imprimir("Error al ejecutar accion de la casilla destino.");
                    break;
            }
        }
    }
}
