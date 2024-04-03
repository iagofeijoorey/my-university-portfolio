final public class Especial extends Casilla {
    private double valor;
    public Especial(String nombre, double valor) {
        super(nombre);
        this.valor = valor;
    }
    public double getValor() {
        return this.valor;
    }

    @Override
    public String getDescripcionCasilla() {
        switch (super.toString()) {
            case "Cárcel":
                return """
                {
                    nombre: %s
                    salir: %d
                    jugadores: %s
                 }
                """.formatted(super.toString(),(int) this.valor,super.jugadoresAquiToString());
            case "IrCárcel":
                return """
                {
                    nombre: %s
                 }
                """.formatted(super.toString());
            default:
            case "Salida":
                return """
                {
                    nombre: %s
                    bote: %d
                    jugadores: %s
                 }
                """.formatted(super.toString(),(int) this.valor,super.jugadoresAquiToString());
        }
    }
}
