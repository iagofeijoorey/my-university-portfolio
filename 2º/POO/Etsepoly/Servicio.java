final public class Servicio extends Propiedad {

    public Servicio(String nombre, Jugador propietario, Grupo g) {
        super(nombre, propietario, g);
    }

    @Override
    public void alquiler(Jugador j, double salida) { //ejecuta el pago de alquiler: j paga el alquiler correspondiente al propietario de la casilla
        if (!j.pagaAlquiler(this)) {
            Juego.getConsola().imprimir("Por el trato realizado con " + this.getPropietario().toString() + " no pagas el alquiler. Te quedan " + j.turnosRestantes(this) + ".\n");
            return;
        }

        if (this.getPropietario().toString().equals("banca")) {
            Juego.getConsola().imprimir("Caes en " + this.toString() + ", que es de la banca. Para comprar, usa comprar " + this.toString() + ".");
        } else if (this.getPropietario().toString().equals(j.toString())) {
            Juego.getConsola().imprimir("Caes en " + this.toString() + ", que es de tu propiedad. ");
        } else {
            double aPagar = salida/200;
            double rDados = Juego.getDado1() + Juego.getDado2();
            aPagar = aPagar * 4 * rDados;
            j.pagarA(this.getPropietario(),aPagar);
            Juego.getConsola().imprimir("Caes en " + this.toString() + ", de " + this.getPropietario().toString() + ", por lo que pagas el alquiler, de " + (int)aPagar + ". Tu fortuna ahora es de " + (int)j.getDinero() + ".");
        }
    }

    @Override
    public String getDescripcionCasilla() {
        return super.getDescripcionCasilla();
    }

}
