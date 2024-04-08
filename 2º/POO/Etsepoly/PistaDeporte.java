public final class PistaDeporte extends Edificio {
    private double coste;

    public PistaDeporte(Solar solar, String ID) {
        super(solar, ID);
        this.setCoste(calcularCoste());
    }


    public double calcularCoste(){
        return super.getSolarAsociado().valor()*1.25;
    }

    public double getCoste(){ return super.getCoste(); }
}
