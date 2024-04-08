import java.lang.reflect.Array;
import java.util.ArrayList;

public class Jugador {
    private final String nombre;
    private double dinero;
    private final Avatar avatar;
    private Jugador ultimoBeneficiario;
    private ArrayList<Propiedad> propiedades;
    private ArrayList<CooldownAlquiler> cooldownsAlquiler;
    private int vecesDados;

    private double dineroInvertido;
    private double pagoTasasEImpuestos;
    private double pagoDeAlquileres;
    private double cobroDeAlquileres;
    private double pasarPorCasillaDeSalida;
    private double premiosInversionesOBote;
    private int vecesEnLaCarcel;
    private double puntuacion;


    public Jugador(String nombre, String ficha, char ID, int numAvatar) throws ErrorNombreAvatar {
        if (!ficha.equalsIgnoreCase("Coche") && !ficha.equalsIgnoreCase("Sombrero") && !ficha.equalsIgnoreCase("Esfinge") && !ficha.equalsIgnoreCase("Pelota")) {
            throw new ErrorNombreAvatar(ficha + " no es un nombre de avatar correcto");
        }
        this.nombre = nombre;
        this.dinero = 0;
        this.ultimoBeneficiario = this;
        this.avatar = crearAvatar(ficha, ID, numAvatar, this);
        this.propiedades = new ArrayList<>();
        this.cooldownsAlquiler = new ArrayList<>();
        this.dineroInvertido=0;
        this.pagoTasasEImpuestos=0;
        this.pagoDeAlquileres=0;
        this.cobroDeAlquileres=0;
        this.pasarPorCasillaDeSalida=0;
        this.premiosInversionesOBote=0;
        this.vecesEnLaCarcel=0;
        this.puntuacion=0;
    }

    /**
     * Constructor para banca
     */
    public Jugador() {
        this.nombre = "banca";
        this.dinero = 900000000;
        this.avatar = crearAvatar("def",'b',10,this);
        this.propiedades = new ArrayList<>();
    }
    public Avatar crearAvatar(String tipoAvatar, char ID, int numAvatar, Jugador jugador) {
        String fichaLower = tipoAvatar.toLowerCase();
        return switch (tipoAvatar) {
            case "pelota" -> new Pelota(ID, numAvatar, jugador);
            case "coche" -> new Coche(ID, numAvatar, jugador);
            case "esfinge" -> new Esfinge(ID, numAvatar, jugador);
            default -> new Sombrero(ID, numAvatar, jugador);
        };
    }
    public double getDinero() {
        return this.dinero;
    }
    public void setDinero(double cantidad) {
        this.dinero = cantidad;
    }
    public void sumarDinero(double cantidad) {
        this.dinero += cantidad;
    }
    public ArrayList<Propiedad> getPropiedades() {
        return this.propiedades;
    }

    /**
     * Método toString
     * @return nombre del jugador
     */
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
        if (object instanceof Character) {
            Character c = this.avatar.getID();
            return (c.equals(object));
        }
        if (object instanceof Jugador) {
            Jugador j = (Jugador) object;
            return (this.avatar.getID() == j.avatar.getID());
        }
        return false;
    }
    public Avatar getAvatar() {
        return this.avatar;
    }
    public String propiedadesToString() {
        String ret = "[ ";
        for (Propiedad p : this.propiedades) {
            ret += p.toString() + ", ";
        }
        if (!this.propiedades.isEmpty()) ret = ret.substring(0,ret.length() - 2);
        return ret + " ]";
    }
    public String edificiosToString() {
        String ret = "[ ";
        for (Propiedad p : this.propiedades) {
            if (p instanceof Solar) {
                ret += ((Solar) p).edificiosToString() + " (" + p.toString() + "), ";
            }
        }
        if (!this.propiedades.isEmpty()) ret = ret.substring(0,ret.length() - 2);
        return ret + " ]";
    }
    public String toString(String tipoRepresentacion) {
        switch (tipoRepresentacion) {
            case "descripcionJugador":
                return """
                        {
                            nombre: %s
                            avatar: %c
                            fortuna: %.0f
                            propiedades: %s
                            hipotecas: [  ]
                            edificios: %s
                        }
                        """.formatted(this.toString(),this.avatar.getID(),this.dinero,this.propiedadesToString(),this.edificiosToString());
            case "descripcionAvatar":

            case "menu":
                return """
                        {
                            nombre: %s
                            avatar: %s
                        }
                        
                        """.formatted(this.toString(),this.avatar.getID());
            default:
                return "Tipo de representación no válido.";
        }
    }
    public String getDescripcionAvatar(ArrayList<Casilla> casillas) {
        return """
                        {
                            id: %c
                            tipo: %s
                            casilla: %s
                            jugador: %s
                        }
                        """.formatted(this.avatar.getID(),this.avatar.getClass().getSimpleName(),casillas.get(this.avatar.getPosicion()%40),this.toString());
    }


    public void pagarA(Jugador j, double cantidad) {
        this.sumarDinero(-cantidad);
        this.ultimoBeneficiario = j;
        j.sumarDinero(cantidad);
    }

    public Jugador getUltBeneficiario() { return this.ultimoBeneficiario; }


    public void addPropiedad(Propiedad p) {
        this.propiedades.add(p);
    }
    public void quitarPropiedad(Propiedad p) {
        this.propiedades.remove(p);
    }

    public void crearCooldownAlquiler(Propiedad p, int nTurnos) {
        this.cooldownsAlquiler.add(new CooldownAlquiler(p,nTurnos));
    }
    public boolean pagaAlquiler(Propiedad p) {
        for (CooldownAlquiler w : this.cooldownsAlquiler) {
            if (w.getPropiedad().toString().equalsIgnoreCase(p.toString()) && (w.getTurnosRestantes() > 0)) {
                w.restarTurno();
                if (w.getTurnosRestantes() == 0) this.cooldownsAlquiler.remove(w);
                return false;
            }
        }
        return true;
    }
    public int turnosRestantes(Propiedad p) {
        for (CooldownAlquiler w : this.cooldownsAlquiler) {
            if (w.getPropiedad().toString().equalsIgnoreCase(p.toString())) {
                return w.getTurnosRestantes();
            }
        }
        return 0;
    }
    public void masUnoVecesDados() {
        this.vecesDados++;
    }
    public int getVecesDados() {
        return this.vecesDados;
    }

    public double getDineroInvertido(){ return this.dineroInvertido;}
    public void sumarDineroInvertido(double cantidad){ this.dineroInvertido= this.dineroInvertido+cantidad;}

    public double getPagoTasasEImpuestos(){ return this.pagoTasasEImpuestos;}
    public void sumarPagoTasasEImpuestos(double cantidad){ this.pagoTasasEImpuestos= this.pagoTasasEImpuestos+cantidad;}

    public double getPagoDeAlquileres(){ return this.pagoDeAlquileres;}
    public void sumarPagoDeAlquileres(double cantidad){ this.pagoDeAlquileres= this.pagoDeAlquileres+cantidad;}

    public double getCobroDeAlquileres(){ return this.cobroDeAlquileres;}
    public void sumarCobroDeAlquileres(double cantidad){ this.cobroDeAlquileres= this.cobroDeAlquileres+cantidad;}

    public double getPasarPorCasillaDeSalida(){ return this.pasarPorCasillaDeSalida;}
    public void sumarPasarPorCasillaDeSalida(double cantidad){ this.pasarPorCasillaDeSalida= this.pasarPorCasillaDeSalida+cantidad;}

    public double getPremiosInversionesOBote(){ return this.premiosInversionesOBote;}
    public void sumarPremiosInversionesOBote(double cantidad){ this.premiosInversionesOBote= this.premiosInversionesOBote+cantidad;}

    public int getvecesEnLaCarcel(){ return this.vecesEnLaCarcel;}
    public void aumentarVecesEnLaCarcel(){ this.vecesEnLaCarcel= this.vecesEnLaCarcel+1;}

    public String estadisticasJugador() {
        return """
                        {
                            dineroInvertido: %.0f
                            pagoTasasEImpuestos: %.0f
                            pagoDeAlquileres: %.0f
                            cobroDeAlquileres: %.0f
                            pasarPorCasillaDeSalida: %.0f
                            premiosInversionesOBote: %.0f
                            vecesEnLaCarcel: %d
                        }
                        """.formatted(this.getDineroInvertido(),this.getPagoTasasEImpuestos(),this.getPagoDeAlquileres(),this.getCobroDeAlquileres(),this.getPasarPorCasillaDeSalida(), this.getPremiosInversionesOBote(), this.getvecesEnLaCarcel());

    }
}
