import java.util.ArrayList;

public final class Estadisticas {
    private Propiedad propiedadMasRentable;
    private Grupo grupoMasRentable;
    private Casilla casillaMasFrecuentada;
    private Jugador jugadorMasVueltas;
    private Jugador jugadorMasVecesDados;
    private Jugador jugadorEnCabeza;
    public Estadisticas() {

    }

    public String getString(ArrayList<Casilla> casillas, ArrayList<Jugador> jugadores,ArrayList<Grupo> grupos) {
        calcularCasillaMasFrecuentada(casillas);
        calcularJugadorMasVueltas(jugadores);
        calcularJugadorMasVecesDados(jugadores);
        calcularJugadorEnCabeza(jugadores);
        calcularPropiedadMasRentable(casillas);
        calcularGrupoMasRentable(grupos);
        return """
                {
                    casillaMasRentable: %s
                    grupoMasRentable: %s
                    casillaMasFrecuentada: %s
                    jugadorMasVueltas: %s
                    jugadorMasVecesDados: %s
                    jugadorEnCabeza: %s
                }
                """.formatted(this.propiedadMasRentable.toString(),this.grupoMasRentable.toString(),this.casillaMasFrecuentada.toString(),this.jugadorMasVueltas.toString(),this.jugadorMasVecesDados.toString(),this.jugadorEnCabeza.toString());
    }

    public void calcularCasillaMasFrecuentada(ArrayList<Casilla> casillas) {
        this.casillaMasFrecuentada = casillas.get(0);
        for (Casilla c : casillas) if (c.getMayorFrecuenciaVisita() > this.casillaMasFrecuentada.getMayorFrecuenciaVisita()) this.casillaMasFrecuentada = c;
    }
    public void calcularJugadorMasVueltas(ArrayList<Jugador> jugadores) {
        this.jugadorMasVueltas = jugadores.get(0);
        for (Jugador j : jugadores) if (j.getAvatar().getPosicion() > this.jugadorMasVueltas.getAvatar().getPosicion()) this.jugadorMasVueltas = j;
    }
    public void calcularJugadorMasVecesDados(ArrayList<Jugador> jugadores) {
        this.jugadorMasVecesDados = jugadores.get(0);
        for (Jugador j : jugadores) if (j.getVecesDados() > this.jugadorMasVecesDados.getVecesDados()) this.jugadorMasVecesDados = j;
    }
    public void calcularJugadorEnCabeza(ArrayList<Jugador> jugadores) {
        this.jugadorEnCabeza = jugadores.get(0);
        for (Jugador j : jugadores) if (j.getDinero() > this.jugadorEnCabeza.getDinero()) this.jugadorEnCabeza = j;
    }
    public void calcularPropiedadMasRentable(ArrayList<Casilla> casillas) {
        if (casillas.get(1) instanceof Propiedad) this.propiedadMasRentable = ((Propiedad) casillas.get(1));
        for (Casilla c : casillas) if (c instanceof Propiedad) if (((Propiedad) c).getRentabilidad() > this.propiedadMasRentable.getRentabilidad()) this.propiedadMasRentable = ((Propiedad) c);
    }
    public void calcularGrupoMasRentable(ArrayList<Grupo> grupos) {
        this.grupoMasRentable = grupos.get(0);
        for (Grupo g : grupos) if (g.getRentabilidad() > this.grupoMasRentable.getRentabilidad()) this.grupoMasRentable = g;
    }
}
