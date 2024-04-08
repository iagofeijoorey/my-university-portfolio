public abstract class Propiedad extends Casilla {
    private Jugador propietario;
    private final Grupo grupoAsociado;
    boolean EsHipotecada;
    private double rentabilidad;

    public Propiedad(String nombre, Jugador propietario, Grupo g) {
        super(nombre);
        this.propietario = propietario;
        this.grupoAsociado = g;
        g.addCasilla(this);
        propietario.addPropiedad(this);
        this.EsHipotecada = false;
        this.rentabilidad = 0;
    }

    public Jugador getPropietario() {
        return this.propietario;
    }

    public Grupo getGrupoAsociado() {
        return grupoAsociado;
    }

    public boolean perteneceAJugador(Jugador jugador) {
        return (this.propietario.getAvatar().getID() == jugador.getAvatar().getID());
    }

    public abstract void alquiler(Jugador j,double salida);  //deberá establecer un comportamiento diferente para este método según el tipo de propiedad

    public double valor() {
        return super.valor();
    }
    public void setValor(double valor) {
        super.setValor(valor);
    }
    public void comprar(Jugador jugador) throws AvatarNoEnCasilla, CasillaNoComprable {
        if (!(this.estaAvatar(jugador.getAvatar()))) {
            throw new AvatarNoEnCasilla("Debes estar en la casilla para poder comprarla");
        }
        if(!this.propietario.toString().equals("banca")) {
            if (this.propietario.equals(jugador)) {
                Juego.getConsola().imprimir("Esta casilla ya te pertenece.");
                return;
            } else throw new CasillaNoComprable("Esta ya pertenece a un jugador, por lo que no la puedes comprar.");
        }
        Juego.getConsola().imprimir("Has pagado " + (int) this.valor() + " a " + this.propietario.toString() + " por " + this.toString() + ". Tu fortuna actual es de " + (int)(jugador.getDinero()-this.valor()) + ".");
        jugador.pagarA(this.propietario,this.valor());
        this.rentabilidad += this.valor();
        this.propietario = jugador;

        this.propietario.quitarPropiedad(this);
        jugador.addPropiedad(this);
    }
    @Override
    public String getDescripcionCasilla() {
        return """
                {
                    nombre: %s
                    tipo: %s
                    propietario: %s
                    valor: %.0f
                    alquiler: %.0f
                }
                """.formatted(super.toString(),this.getClass().getSimpleName(),this.propietario.toString(),this.valor(),0.1*this.valor());
    }
    public String getDescripcionEnVenta() {
        return """
                {
                    nombre: %s
                    tipo: %s
                    valor: %f
                }
                """.formatted(this.toString(),this.getClass().getSimpleName(),this.valor());
    }
    public boolean esGrupoCompleto() {
        boolean ret = true;
        Jugador propietario = this.propietario;
        for (Propiedad p : this.grupoAsociado.getCasillas()) {
            if (!p.propietario.equals(propietario)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    public void hipotecar(Jugador banca) {
        banca.pagarA(this.propietario, (this.valor()/2));
        this.EsHipotecada = true;
    }

    public void deshipotecar(Jugador banca) {
        this.propietario.pagarA(banca, (this.valor()/2 + this.valor()/10));
        this.EsHipotecada = false;
    }

    public void transferirA(Jugador j) {
        this.propietario.getPropiedades().remove(this);
        this.propietario = j;
        this.propietario.getPropiedades().add(this);
    }

    public double getRentabilidad() { return this.rentabilidad; }
}
