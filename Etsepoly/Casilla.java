import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Casilla {
    private final String nombre;
    private ArrayList<Avatar> avataresAqui; //Contiene los Avatares que hay en la casilla
    private ArrayList<Integer> frecuenciasVisita; //Contiene el número de veces que cada Avatar ha caído en la casilla
    private double valor;

    public Casilla(String nombre) {
        this.nombre = nombre;
        avataresAqui = new ArrayList<>();
        frecuenciasVisita = new ArrayList<>();
    }

    public ArrayList<Integer> getFrecuenciasVisita() {
        return frecuenciasVisita;
    }
    public Integer getMayorFrecuenciaVisita() {
        Integer f = this.frecuenciasVisita.get(0);
        for (Integer i : this.frecuenciasVisita) {
            if (i > f) f = i;
        }
        return f;
    }

    /**
     * Función para inicializar el ArrayList que contiene el número de veces que cada Avatar ha caído en la casilla
     * @param nAvatares número de Avatares de la partida
     */
    public void inicializarFrecuencias(int nAvatares) {
        for (int i = 0;i < nAvatares;i++) {
            frecuenciasVisita.add(0);
        }
    }

    /**
     * Función que se debe ejecutar cada vez que un Avatar cae en una casilla
     * @param numAvatar posición del Avatar en el ArrayList de Avatares
     */
    public void aumentarFrecuencia(int numAvatar) {
        frecuenciasVisita.set(numAvatar,frecuenciasVisita.get(numAvatar) + 1);
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof String) {
            String c = this.nombre;
            if (c.equalsIgnoreCase((String) object)) {
                return true;
            }
        }
        if (object instanceof Casilla) {
            Propiedad p = (Propiedad) object;
            return p.toString().equalsIgnoreCase(this.nombre);
        }

        return false;
    }

    public String avataresAquiToString() {
        String avaAqui = "";
        for (Avatar a : this.avataresAqui) {
            avaAqui += a.toString();
        }
        return avaAqui;
    }
    public void setAvatarAqui(Avatar avatar) {
        this.aumentarFrecuencia(avatar.getNumAvatar());
        this.avataresAqui.add(avatar);
    }
    public void eliminarAvatarAqui(Avatar avatar) {
        if(this.avataresAqui.contains(avatar)) this.avataresAqui.remove(this.avataresAqui.indexOf(avatar));
    }

    public boolean estaAvatar(Avatar avatar) {
        return this.avataresAqui.contains(avatar);
    }

    public ArrayList<String> getJugadoresAqui() {
        ArrayList<String> jugadoresAqui = new ArrayList<>();
        for (Avatar a : this.avataresAqui) {
            jugadoresAqui.add(a.getJugador().toString());
        }
        return jugadoresAqui;
    }
    public String jugadoresAquiToString() {
        return "[ " + String.join(", ", getJugadoresAqui()) + " ]";
    }

    public int frecuenciaVisita(Avatar a) {
        return this.frecuenciasVisita.get(a.getNumAvatar());
    }
    public double valor() {
        return this.valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public abstract String getDescripcionCasilla();
}
