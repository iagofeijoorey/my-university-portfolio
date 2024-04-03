import java.util.ArrayList;

public class Grupo {
    private String nombre;
    private double precio;
    private ArrayList<Propiedad> casillas;
    private double rentabilidad;

    public Grupo (String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.casillas = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public int getSize() {
        return this.casillas.size();
    }
    public ArrayList<Propiedad> getCasillas() {
        return this.casillas;
    }
    public void addCasilla (Propiedad p) {
        this.casillas.add(p);
    }

    public void asignarPrecios() {
        for (Propiedad p : this.casillas) {
            p.setValor(this.precio/this.casillas.size());
        }
    }
    public String listarEdificios() {
        String ret = "";
        for (Propiedad p : this.casillas) {
            if (p instanceof Solar) {
                ret += ((Solar) p).edificiosForGrupo();
            }
        }
        return ret;
    }
    public double getRentabilidad() {
        calcularRentabilidad();
        return this.rentabilidad;
    }
    public void calcularRentabilidad() {
        this.rentabilidad = this.casillas.get(0).getRentabilidad();
        for (Propiedad p : this.casillas) if (p.getRentabilidad() > this.rentabilidad) this.rentabilidad = p.getRentabilidad();
    }


}
