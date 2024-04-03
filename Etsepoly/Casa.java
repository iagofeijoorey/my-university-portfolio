public final class Casa extends Edificio {
    private double coste;

    public Casa(Solar solar, String ID) {
        super(solar, ID);
        this.setCoste(calcularCoste());
    }


    public double calcularCoste(){
        return super.getSolarAsociado().valor()*0.6;
    }

    public double getCoste(){ return super.getCoste(); }

}
