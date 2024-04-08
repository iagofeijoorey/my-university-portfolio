public class CooldownAlquiler {

    private int turnosRestantes;
    private Propiedad propiedad;

    public CooldownAlquiler(Propiedad propiedad, int turnosRestantes) {
        this.propiedad = propiedad;
        this.turnosRestantes = turnosRestantes;
    }

    public int getTurnosRestantes() {
        return turnosRestantes;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }
    public void restarTurno() {
        this.turnosRestantes--;
    }
}
