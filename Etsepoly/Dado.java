public final class Dado {
    private int numero;

    public Dado()
    {
        this.numero = 0;
    }

    public int getNumero()
    {
        return this.numero;
    }
    public void setNumero(int x)
    {
        this.numero = x;
    }

    /**
     * Genera un número aleatorio en el dado
     */
    public void lanzar()
    {
        // Genera un número aleatorio entre 1 y 6 (representa el lanzamiento de un dado)
        this.numero = (int) (Math.random() * 6) + 1;
    }
}
