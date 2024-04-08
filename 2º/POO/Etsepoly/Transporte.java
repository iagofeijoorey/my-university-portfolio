final public class Transporte extends Propiedad {
    public Transporte(String nombre, Jugador propietario, Grupo g) {
        super(nombre, propietario, g);
    }

    @Override
    public void alquiler(Jugador j, double salida) {
        if (!j.pagaAlquiler(this)) {
            Juego.getConsola().imprimir("Por el trato realizado con " + this.getPropietario().toString() + " no pagas el alquiler. Te quedan " + j.turnosRestantes(this) + ".\n");
            return;
        }

        if (this.getPropietario().toString().equals("banca")) {
            Juego.getConsola().imprimir("Caes en " + this.toString() + ", que es de la banca. Para comprar, usa comprar " + this.toString() + ".");
        } else if (this.getPropietario().toString().equals(j.toString())) {
            Juego.getConsola().imprimir("Caes en " + this.toString() + ", que es de tu propiedad. ");
        } else {
            int nTrans = 0;
            for (Propiedad p : this.getPropietario().getPropiedades()) {
                if (p instanceof Transporte) nTrans++;
            }
            double aPagar = 0.25 * nTrans * salida;
            j.pagarA(this.getPropietario(),aPagar);
            Juego.getConsola().imprimir("Caes en " + this.toString() + ", de " + this.getPropietario().toString() + ", por lo que pagas el alquiler, de " + (int)aPagar + ". Tu fortuna ahora es de " + (int)j.getDinero() + ".");
        }
    }

    @Override
    public String getDescripcionCasilla() {
        return super.getDescripcionCasilla();
    }
}
