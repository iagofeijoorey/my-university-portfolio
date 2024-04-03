public abstract class Edificio {
    private Solar solarAsociado;
    private String IDAsociado;
    private double coste;

    public Edificio(Solar solar, String ID) {
        this.IDAsociado = ID;
        this.solarAsociado = solar;
        this.coste = 0;
    }
    public Solar getSolarAsociado() {
        return this.solarAsociado;
    }
    public void setCoste(double coste) {
        this.coste = coste;
    }

    @Override
    public String toString() {
        return """
                {
                    id: %s
                    propietario: %s
                    casilla: %s
                    grupo:
                    coste:
                }
                """.formatted(this.IDAsociado, this.solarAsociado.getPropietario(),this.solarAsociado.toString());
    }

    public double getCoste() { return this.coste; }
}
