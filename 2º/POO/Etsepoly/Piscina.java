public final class Piscina extends Edificio {
    private double coste;
    public Piscina(Solar solar, String ID) {
        super(solar, ID);
        this.setCoste(calcularCoste());
    }


    public double calcularCoste(){
        return super.getSolarAsociado().valor()*0.4;
    }

    public double getCoste(){ return super.getCoste(); }

}
