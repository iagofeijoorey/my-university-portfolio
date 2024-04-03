import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public final class Solar extends Propiedad {

    //polimorfismo
    private ArrayList<Edificio> edificios;

    public Solar(String nombre, Jugador propietario, Grupo g) {
        super(nombre, propietario, g);
        this.edificios = new ArrayList<>();
    }

    @Override
    public void alquiler(Jugador j, double Salida) {
        if (!j.pagaAlquiler(this)) {
            Juego.getConsola().imprimir("Por el trato realizado con " + this.getPropietario().toString() + " no pagas el alquiler. Te quedan " + j.turnosRestantes(this) + ".\n");
            return;
        }

        if (this.getPropietario().toString().equals("banca")) {
            Juego.getConsola().imprimir("Caes en " + this.toString() + ", que es de la banca. Para comprar, usa comprar " + this.toString() + ".");
        } else if (this.getPropietario().toString().equals(j.toString())) {
            Juego.getConsola().imprimir("Caes en " + this.toString() + ", que es de tu propiedad. ");
            if (getFrecuenciasVisita().get(j.getAvatar().getNumAvatar()) < 3)
                Juego.getConsola().imprimir("Aún te quedan " + (3 - getFrecuenciasVisita().get(j.getAvatar().getNumAvatar())) + " veces por caer aquí para poder edificar.");
            else Juego.getConsola().imprimir("Para edificar, usa edificar <edificio>.");
        } else {
            double aPagar = calcularAlquiler();
            if (this.esGrupoCompleto()) aPagar = aPagar * 2;
            j.pagarA(this.getPropietario(), aPagar);
            Juego.getConsola().imprimir("Caes en " + this.toString() + ", de " + this.getPropietario().toString() + ", por lo que pagas el alquiler, de " + (int) aPagar + ". Tu fortuna ahora es de " + (int) j.getDinero() + ".");
        }
    }

    public double calcularAlquiler() {
        double aPagar = 0.1 * this.valor(); //alquiler base
        if (!edificios.isEmpty()) {
            int nCasas = 0, nHoteles = 0, nPistas = 0, nPiscinas = 0;
            for (Edificio e : this.edificios) { //contar el número de edificios de cada tipo
                if (e instanceof Casa) nCasas++;
                else if (e instanceof Hotel) nHoteles++;
                else if (e instanceof PistaDeporte) nPistas++;
                else if (e instanceof Piscina) nPiscinas++;
                else Juego.getConsola().imprimir("Error al calcular el alquiler\n");
            }
            aPagar += alquilerCasas(nCasas) + aPagar * nHoteles * 70 + aPagar * nPistas * 25 + aPagar * nPiscinas * 25;
        }
        return aPagar;
    }


    public double alquilerCasas(int n) {
        double alquiler = 0.1 * this.valor();
        switch (n) {
            case 0:
                break;
            case 1:
                alquiler = alquiler * 5;
                break;
            case 2:
                alquiler = alquiler * 15;
                break;
            case 3:
                alquiler = alquiler * 35;
                break;
            case 4:
                alquiler = alquiler * 50;
                break;
            default:
                Juego.getConsola().imprimir("Error al calcular el alquiler con casas.");
                return 0;
        }
        return alquiler;
    }

    public ArrayList<Edificio> getEdificios() {
        return this.edificios;
    }

    public String edificiosToString() {
        int nCasas = 0, nHoteles = 0, nPistas = 0, nPiscinas = 0;
        for (Edificio e : edificios) {
            if (e instanceof Casa) nCasas++;
            else if (e instanceof Hotel) nHoteles++;
            else if (e instanceof PistaDeporte) nPistas++;
            else if (e instanceof Piscina) nPiscinas++;
            else Juego.getConsola().imprimir("Error al obtener el String de edificios");
        }
        return nCasas + "·casa, " + nHoteles + "·hotel, " + nPiscinas + "·piscina, " + nPistas + "·pistaDeporte";
    }

    @Override
    public String getDescripcionCasilla() {
        return """
                {
                    nombre: %s
                    tipo: Solar
                    grupo: %s
                    propietario: %s
                    valor: %.0f
                    alquiler: %.0f
                                
                    valor hotel:
                    valor casa: 
                    valor piscina:
                    valor pista de deporte:
                    alquiler una casa: %.0f
                    alquiler dos casas: %.0f
                    alquiler tres casas: %.0f
                    alquiler cuatro casas: %.0f
                    alquiler hotel: %.0f
                    alquiler piscina: %.0f
                    alquiler pista de deporte: %.0f
                                
                    edificios construidos: %s
                }
                """.formatted(super.toString(), super.getGrupoAsociado().toString(), super.getPropietario().toString(), this.valor(), 0.1 * this.valor(), this.alquilerCasas(1), this.alquilerCasas(2), this.alquilerCasas(3), this.alquilerCasas(4), 70 * 0.1 * this.valor(), 25 * 0.1 * this.valor(), 25 * 0.1 * this.valor(), "[ " + this.edificiosToString() + " ]");

    }

    @Override
    public String getDescripcionEnVenta() {
        return """
                {
                    nombre: %s
                    tipo: %s
                    grupo:
                    valor: %f
                }
                """.formatted(this.toString(), this.getClass().getSimpleName(), this.valor());
    }


    public void edificar(String StrEdificio) throws EdificiosInsuficientes, DemasiadosEdificios { //excepción por si no hay suficientes edifcios?
        int nCasas = 0, nHoteles = 0, nPistas = 0, nPiscinas = 0;
        for (Edificio e : edificios) {
            if (e instanceof Casa) nCasas++;
            else if (e instanceof Hotel) nHoteles++;
            else if (e instanceof PistaDeporte) nPistas++;
            else if (e instanceof Piscina) nPiscinas++;
            else Juego.getConsola().imprimir("Error al obtener el String de edificios");
        }

        //Limite casas
        if (StrEdificio.equals("Casa") && nHoteles < getGrupoAsociado().getSize() && nCasas == 4) {
            throw new DemasiadosEdificios("Has alcanzado el límite de casas, ya puedes construir un hotel");
        }
        //Limite casas con limite de hoteles
        else if (StrEdificio.equals("Casa") && nHoteles == getGrupoAsociado().getSize() && nCasas == getGrupoAsociado().getSize()) {
            throw new DemasiadosEdificios("Has alcanzado el límite de casas");
        }
        //Limite hoteles
        else if (StrEdificio.equals("Hotel") && nHoteles == getGrupoAsociado().getSize()) {
            throw new EdificiosInsuficientes("Has alcanzado el límite de hoteles");
        }
        //Casas insuficiente spara construir hotel
        else if (StrEdificio.equals("Hotel") && nHoteles < getGrupoAsociado().getSize() && nCasas < 4) {
            throw new EdificiosInsuficientes("No hay casas suficientes para construir un hotel");
        }
        //Limite Piscinas
        else if (StrEdificio.equals("Piscina") && nPiscinas == getGrupoAsociado().getSize()) {
            throw new EdificiosInsuficientes("Has alcanzado el límite de piscinas");
        }
        //Casas y hoteles insuficientes
        else if (StrEdificio.equals("Piscina") && (nHoteles > 2 || (nCasas > 2 && nHoteles == 1))) {
            throw new EdificiosInsuficientes("Numero de casas o hoteles insuficientes. Necesitas Un hotel y dos casas al menos.");
        }
        //Limite Pistas
        else if (StrEdificio.equals("Piscina") && nHoteles == getGrupoAsociado().getSize()) {
            throw new EdificiosInsuficientes("Has alcanzado el límite de pistas de deporte");
        }
        //Hoteles insuficientes
        else if (StrEdificio.equals("Piscina") && nHoteles < 2) {
            throw new EdificiosInsuficientes("Numero de casas o hoteles insuficientes. Necesitas dos hoteles al menos.");
        }

        //Edificar como tal
        String nombreSolar;
        switch (StrEdificio) {
            case "Casa":
                nombreSolar = "Casa_" + super.toString() +"_"+nCasas;
                this.edificios.add(new Casa(this, nombreSolar));
                Juego.getConsola().imprimir("El jugador " + jugadoresAquiToString() + " ha edificado una casa.");
                break;
            case "Hotel":
                nombreSolar = "Hotel_" + super.toString() +"_"+nHoteles;
                this.edificios.add(new Casa(this, nombreSolar));
                Juego.getConsola().imprimir("El jugador " + jugadoresAquiToString() + " ha edificado un hotel.");
                break;
            case "Piscina":
                nombreSolar = "Piscina_" + super.toString() +"_"+nPiscinas;
                this.edificios.add(new Casa(this, nombreSolar));
                Juego.getConsola().imprimir("El jugador " + jugadoresAquiToString() + " ha edificado una piscina.");
                break;
            case "Pistas":
                nombreSolar = "Pista_" + super.toString() +"_"+nPistas;
                this.edificios.add(new Casa(this, nombreSolar));
                Juego.getConsola().imprimir("El jugador " + jugadoresAquiToString() + " ha edificado una pista de deportes.");
                break;
        }
    }


    public String edificiosForGrupo() {
        int nCasas = 0, nHoteles = 0, nPistas = 0, nPiscinas = 0;
        for (Edificio e : edificios) {
            if (e instanceof Casa) nCasas++;
            else if (e instanceof Hotel) nHoteles++;
            else if (e instanceof PistaDeporte) nPistas++;
            else if (e instanceof Piscina) nPiscinas++;
            else Juego.getConsola().imprimir("Error al obtener el String de edificios para grupo");
        }
        int size = this.getGrupoAsociado().getSize();
        return """
                { 
                    propiedad: %s
                    casas: %d/%d
                    hoteles: %d/%d
                    piscinas: %d/%d
                    pistasDeporte: %d/%d
                    alquiler: %.0f
                }
                """.formatted(this.toString(),nCasas,size,nHoteles,size,nPiscinas,size,nPistas,size,this.calcularAlquiler());

    }


    public void hipotecar(Jugador banca) {
        if(this.edificios.size() > 0)
        {
            int nCasas = 0, nHoteles = 0, nPistas = 0, nPiscinas = 0;
            for (Edificio e : edificios) {
                if (e instanceof Casa) nCasas++;
                else if (e instanceof Hotel) nHoteles++;
                else if (e instanceof PistaDeporte) nPistas++;
                else if (e instanceof Piscina) nPiscinas++;
                else Juego.getConsola().imprimir("Error al obtener el String de edificios para grupo");
            }
            banca.pagarA(getPropietario(), (nCasas*(this.valor()/6)/2));
            banca.pagarA(getPropietario(), (nHoteles*(this.valor()/6)/2));
            banca.pagarA(getPropietario(), (nPiscinas*(this.valor()/4)/2));
            banca.pagarA(getPropietario(), (nPistas*(this.valor()*1.25)/2));
            edificios.clear();
        }

        banca.pagarA(getPropietario(), (this.valor()/2));
        super.EsHipotecada = true;
    }

    public void venderEdificio(String edificio, int numeroAVender, Jugador banca) throws noHayEdificios, noEsUnEdificio{
        if (!edificio.equalsIgnoreCase("casa") && !edificio.equalsIgnoreCase("Hotel") && !edificio.equalsIgnoreCase("piscina") && !edificio.equalsIgnoreCase("pista")) throw new noEsUnEdificio("El edificio que has introducido no es válido.");
        if(this.edificios.isEmpty()) throw new noHayEdificios("Este solar no tiene ningún edificio.");

        double precio = 0;
        int contador = 0;
        Iterator<Edificio> iterador = this.edificios.iterator();

        while (iterador.hasNext()) {
            Edificio edificioreal = iterador.next();
            if (edificio.equalsIgnoreCase("Casa") && (edificioreal instanceof Casa) && contador < numeroAVender) {
                precio += (edificioreal.getCoste() / 2);
                iterador.remove();
                contador++;
            }
            else if (edificio.equalsIgnoreCase("Hotel") && (edificioreal instanceof Hotel) && contador < numeroAVender) {
                precio += (edificioreal.getCoste() / 2);
                iterador.remove();
                contador++;
            }
            else if (edificio.equalsIgnoreCase("Piscina") && (edificioreal instanceof Piscina) && contador < numeroAVender) {
                precio += (edificioreal.getCoste() / 2);
                iterador.remove();
                contador++;
            }
            else if (edificio.equalsIgnoreCase("Pista") && (edificioreal instanceof PistaDeporte) && contador < numeroAVender) {
                precio += (edificioreal.getCoste() / 2);
                iterador.remove();
                contador++;
            }

        }

        banca.pagarA(getPropietario(), precio);
        Juego.getConsola().imprimir("El jugador " + getPropietario().toString() + " recibe " + precio + " por la venta de " + contador + " casa(s)");
    }

}