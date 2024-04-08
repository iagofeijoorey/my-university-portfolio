public final class Entero {
    private int numero;
    public Entero(int x) {
        this.numero = x;
    }
    public int getNumero() {
        return this.numero;
    }
    public void sumar(int x) {
        this.numero = this.numero + x;
        if (this.numero < 0) this.numero = 0;
    }
    public void setNumero(int x) {
        this.numero = x;
    }
    public static boolean esNumero(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
