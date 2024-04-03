import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Tablero {
    private ArrayList<Casilla> casillas;
    private ArrayList<Grupo> grupos;
    public Tablero() {
        casillas = new ArrayList<>();
        grupos = new ArrayList<>();
    }

    public void inicializarTablero(int nAvatares, Jugador banca, double precio) {
        ////////////////////GRUPOS////////////////////
        ////////////////////GRUPO NEGRO
        grupos.add(new Grupo("Negro",precio));
        ////////////////////GRUPO CYAN
        grupos.add(new Grupo("Cyan", 1.3*this.grupos.get(this.grupos.size()-1).getPrecio()));
        ////////////////////GRUPO ROSA
        grupos.add(new Grupo("Rosa", 1.3*this.grupos.get(this.grupos.size()-1).getPrecio()));
        ////////////////////GRUPO AMARILLO
        grupos.add(new Grupo("Amarillo", 1.3*this.grupos.get(this.grupos.size()-1).getPrecio()));
        ////////////////////GRUPO ROJO
        grupos.add(new Grupo("Rojo", 1.3*this.grupos.get(this.grupos.size()-1).getPrecio()));
        ////////////////////GRUPO MARRÓN
        grupos.add(new Grupo("Marrón", 1.3*this.grupos.get(this.grupos.size()-1).getPrecio()));
        ////////////////////GRUPO VERDE
        grupos.add(new Grupo("Verde", 1.3*this.grupos.get(this.grupos.size()-1).getPrecio()));
        ////////////////////GRUPO AZUL
        grupos.add(new Grupo("Azul", 1.3*this.grupos.get(this.grupos.size()-1).getPrecio()));

        double salida = 0;
        for (Grupo g : grupos) {
            salida += g.getPrecio();
        }
        salida = (salida/22);

        ////////////////////GRUPO SERVICIOS
        grupos.add(new Grupo("Servicios",0.75*2*salida));
        ////////////////////GRUPO TRANSPORTES
        grupos.add(new Grupo("Transportes",4*salida));

        ////////////////////CASILLAS////////////////////
        ////////////////////SALIDA
        casillas.add(new Especial("Salida", salida));
        ////////////////////SOLAR1
        casillas.add(new Solar("Solar1",banca, grupos.get(0)));
        ////////////////////CAJA1
        casillas.add(new AccionCajaComunidad("Caja1"));
        ////////////////////SOLAR2
        casillas.add(new Solar("Solar2",banca, grupos.get(0)));
        ////////////////////IMP1
        casillas.add(new Impuesto("Imp1", salida/2));
        ////////////////////TRANS1
        casillas.add(new Transporte("Trans1",banca, grupos.get(9)));
        ////////////////////SOLAR3
        casillas.add(new Solar("Solar3",banca, grupos.get(1)));
        ////////////////////SUERTE1
        casillas.add(new AccionSuerte("Suerte1"));
        ////////////////////SOLAR4
        casillas.add(new Solar("Solar4",banca, grupos.get(1)));
        ////////////////////SOLAR5
        casillas.add(new Solar("Solar5",banca, grupos.get(1)));
        ////////////////////CÁRCEL
        casillas.add(new Especial("Cárcel", 0.25*((Especial) this.casillas.get(0)).getValor())); //castea la instancia de Casilla a Especial
        ////////////////////SOLAR6
        casillas.add(new Solar("Solar6",banca, grupos.get(2)));
        ////////////////////SERV1
        casillas.add(new Servicio("Serv1",banca, grupos.get(8)));
        ////////////////////SOLAR7
        casillas.add(new Solar("Solar7",banca, grupos.get(2)));
        ////////////////////SOLAR8
        casillas.add(new Solar("Solar8",banca, grupos.get(2)));
        ////////////////////TRANS2
        casillas.add(new Transporte("Trans2",banca, grupos.get(9)));
        ////////////////////SOLAR9
        casillas.add(new Solar("Solar9",banca, grupos.get(3)));
        ////////////////////CAJA2
        casillas.add(new AccionCajaComunidad("Caja2"));
        ////////////////////SOLAR10
        casillas.add(new Solar("Solar10",banca, grupos.get(3)));
        ////////////////////SOLAR11
        casillas.add(new Solar("Solar11",banca, grupos.get(3)));
        ////////////////////PÁRKING
        casillas.add(new Accion("Párking"));
        ////////////////////SOLAR12
        casillas.add(new Solar("Solar12",banca, grupos.get(4)));
        ////////////////////SUERTE2
        casillas.add(new AccionSuerte("Suerte2"));
        ////////////////////SOLAR13
        casillas.add(new Solar("Solar13",banca, grupos.get(4)));
        ////////////////////SOLAR14
        casillas.add(new Solar("Solar14",banca, grupos.get(4)));
        ////////////////////TRANS3
        casillas.add(new Transporte("Trans3",banca, grupos.get(9)));
        ////////////////////SOLAR15
        casillas.add(new Solar("Solar15",banca, grupos.get(5)));
        ////////////////////SOALR16
        casillas.add(new Solar("Solar16",banca, grupos.get(5)));
        ////////////////////SERV2
        casillas.add(new Servicio("Serv2",banca, grupos.get(8)));
        ////////////////////SOLAR17
        casillas.add(new Solar("Solar17",banca, grupos.get(5)));
        ////////////////////IRCÁRCEL
        casillas.add(new Especial("IrCárcel", 0));
        ////////////////////SOLAR18
        casillas.add(new Solar("Solar18",banca, grupos.get(6)));
        ////////////////////SOLAR19
        casillas.add(new Solar("Solar19",banca, grupos.get(6)));
        ////////////////////CAJA3
        casillas.add(new AccionCajaComunidad("Caja3"));
        ////////////////////SOLAR20
        casillas.add(new Solar("Solar20",banca, grupos.get(6)));
        ////////////////////TRANS4
        casillas.add(new Transporte("Trans4",banca, grupos.get(9)));
        ////////////////////SUERTE3
        casillas.add(new AccionSuerte("Suerte3"));
        ////////////////////SOLAR21
        casillas.add(new Solar("Solar21",banca, grupos.get(7)));
        ////////////////////IMP2
        casillas.add(new Impuesto("Imp2", salida));
        ////////////////////SOLAR22
        casillas.add(new Solar("Solar22",banca, grupos.get(7)));

        for (Casilla c : this.casillas) {
            c.inicializarFrecuencias(nAvatares);
        }
        casillas.get(0).aumentarFrecuencia(0);

        for (Grupo g : this.grupos) {
            g.asignarPrecios();
        }

    }

    /**
     * Método toString
     * @return tabero listo para mostrar por pantalla
     */
    @Override
    public String toString() {
        return """
                \n
                \t\t\t┌────────────┬────────────┬────────────┬────────────┬────────────┬────────────┬────────────┬────────────┬────────────┬────────────┬────────────┐    
                \t\t\t│%-12s│\u001B[31m%-12s\u001B[0m│%-12s│\u001B[31m%-12s\u001B[0m│\u001B[31m%-12s\u001B[0m│%-12s│\u001B[90m%-12s\u001B[0m│\u001B[90m%-12s\u001B[0m│%-12s│\u001B[90m%-12s\u001B[0m│%-12s│
                \t\t\t│%-12s│\u001B[31m%-12s\u001B[0m│%-12s│\u001B[31m%-12s\u001B[0m│\u001B[31m%-12s\u001B[0m│%-12s│\u001B[90m%-12s\u001B[0m│\u001B[90m%-12s\u001B[0m│%-12s│\u001B[90m%-12s\u001B[0m│%-12s│  
                \t\t\t├────────────┼\u001B[31m────────────\u001B[0m┴────────────┴\u001B[31m────────────\u001B[0m┴\u001B[31m────────────\u001B[0m┴────────────┴\u001B[90m────────────\u001B[0m┴\u001B[90m────────────\u001B[0m┴────────────┴\u001B[90m────────────\u001B[0m┼────────────┤  
                \t\t\t│\u001B[33m%-12s\u001B[0m│                                                                                                                    │\u001B[32m%-12s\u001B[0m│   
                \t\t\t│\u001B[33m%-12s\u001B[0m│                                                                                                                    │\u001B[32m%-12s\u001B[0m│
                \t\t\t├\u001B[33m────────────\u001B[0m┤                                                                                                                    ├\u001B[32m────────────\u001B[0m┤
                \t\t\t│\u001B[33m%-12s\u001B[0m│                                                                                                                    │\u001B[32m%-12s\u001B[0m│
                \t\t\t│\u001B[33m%-12s\u001B[0m│                                                                                                                    │\u001B[32m%-12s\u001B[0m│
                \t\t\t├\u001B[33m────────────\u001B[0m┤                                                                                                                    ├\u001B[32m────────────\u001B[0m┤
                \t\t\t│%-12s│                                                                                                                    │%-12s│
                \t\t\t│%-12s│                                                                                                                    │%-12s│
                \t\t\t├────────────┤                                                                                                                    ├────────────┤
                \t\t\t│\u001B[33m%-12s\u001B[0m│                                                                                                                    │\u001B[32m%-12s\u001B[0m│
                \t\t\t│\u001B[33m%-12s\u001B[0m│                                                                                                                    │\u001B[32m%-12s\u001B[0m│
                \t\t\t├\u001B[33m────────────\u001B[0m┤                                                                                                                    ├\u001B[32m────────────\u001B[0m┤
                \t\t\t│%-12s│                                                                                                                    │%-12s│   
                \t\t\t│%-12s│                                                                                                                    │%-12s│
                \t\t\t├────────────┤                                                                                                                    ├────────────┤  
                \t\t\t│\u001B[95m%-12s\u001B[0m│                                                                                                                    │%-12s│
                \t\t\t│\u001B[95m%-12s\u001B[0m│                                                                                                                    │%-12s│
                \t\t\t├\u001B[95m────────────\u001B[0m┤                                                                                                                    ├────────────┤
                \t\t\t│\u001B[95m%-12s\u001B[0m│                                                                                                                    │\u001B[94m%-12s\u001B[0m│
                \t\t\t│\u001B[95m%-12s\u001B[0m│                                                                                                                    │\u001B[94m%-12s\u001B[0m│
                \t\t\t├\u001B[95m────────────\u001B[0m┤                                                                                                                    ├\u001B[94m────────────\u001B[0m┤
                \t\t\t│%-12s│                                                                                                                    │%-12s│ 
                \t\t\t│%-12s│                                                                                                                    │%-12s│
                \t\t\t├────────────┤                                                                                                                    ├────────────┤   
                \t\t\t│\u001B[95m%-12s\u001B[0m│                                                                                                                    │\u001B[94m%-12s\u001B[0m│
                \t\t\t│\u001B[95m%-12s\u001B[0m│                                                                                                                    │\u001B[94m%-12s\u001B[0m│ 
                \t\t\t├\u001B[95m────────────\u001B[0m┼────────────┬────────────┬────────────┬────────────┬────────────┬────────────┬────────────┬────────────┬────────────┼\u001B[94m────────────\u001B[0m┤  
                \t\t\t│%-12s│\u001B[36m%-12s\u001B[0m│\u001B[36m%-12s\u001B[0m│%-12s│\u001B[36m%-12s\u001B[0m│%-12s│%-12s│\u001B[30m%-12s\u001B[0m│%-12s│\u001B[30m%-12s\u001B[0m│%-12s│
                \t\t\t│%-12s│\u001B[36m%-12s\u001B[0m│\u001B[36m%-12s\u001B[0m│%-12s│\u001B[36m%-12s\u001B[0m│%-12s│%-12s│\u001B[30m%-12s\u001B[0m│%-12s│\u001B[30m%-12s\u001B[0m│%-12s│
                \t\t\t└────────────┴\u001B[36m────────────\u001B[0m┴\u001B[36m────────────\u001B[0m┴────────────┴\u001B[36m────────────\u001B[0m┴────────────┴────────────┴\u001B[30m────────────\u001B[0m┴────────────┴\u001B[30m────────────\u001B[0m┴────────────┘                                                   
                
                
                """.formatted(
                casillas.get(20).toString(), casillas.get(21).toString(), casillas.get(22).toString(), casillas.get(23).toString(), casillas.get(24).toString(), casillas.get(25).toString(), casillas.get(26).toString(), casillas.get(27).toString(), casillas.get(28).toString(), casillas.get(29).toString(), casillas.get(30).toString(),
                casillas.get(20).avataresAquiToString(),casillas.get(21).avataresAquiToString(),casillas.get(22).avataresAquiToString(),casillas.get(23).avataresAquiToString(),casillas.get(24).avataresAquiToString(),casillas.get(25).avataresAquiToString(),casillas.get(26).avataresAquiToString(),casillas.get(27).avataresAquiToString(),casillas.get(28).avataresAquiToString(),casillas.get(29).avataresAquiToString(),casillas.get(30).avataresAquiToString(),

                casillas.get(19).toString(), casillas.get(31).toString(),
                casillas.get(19).avataresAquiToString(), casillas.get(31).avataresAquiToString(),

                casillas.get(18).toString(), casillas.get(32).toString(),
                casillas.get(18).avataresAquiToString(), casillas.get(32).avataresAquiToString(),

                casillas.get(17).toString(), casillas.get(33).toString(),
                casillas.get(17).avataresAquiToString(), casillas.get(33).avataresAquiToString(),

                casillas.get(16).toString(), casillas.get(34).toString(),
                casillas.get(16).avataresAquiToString(), casillas.get(34).avataresAquiToString(),

                casillas.get(15).toString(), casillas.get(35).toString(),
                casillas.get(15).avataresAquiToString(), casillas.get(35).avataresAquiToString(),

                casillas.get(14).toString(), casillas.get(36).toString(),
                casillas.get(14).avataresAquiToString(), casillas.get(36).avataresAquiToString(),

                casillas.get(13).toString(), casillas.get(37).toString(),
                casillas.get(13).avataresAquiToString(), casillas.get(37).avataresAquiToString(),

                casillas.get(12).toString(), casillas.get(38).toString(),
                casillas.get(12).avataresAquiToString(), casillas.get(38).avataresAquiToString(),

                casillas.get(11).toString(), casillas.get(39).toString(),
                casillas.get(11).avataresAquiToString(), casillas.get(39).avataresAquiToString(),

                casillas.get(10).toString(), casillas.get(9).toString(), casillas.get(8).toString(), casillas.get(7).toString(), casillas.get(6).toString(), casillas.get(5).toString(), casillas.get(4).toString(), casillas.get(3).toString(), casillas.get(2).toString(), casillas.get(1).toString(),casillas.get(0).toString(),
                casillas.get(10).avataresAquiToString(),casillas.get(9).avataresAquiToString(),casillas.get(8).avataresAquiToString(),casillas.get(7).avataresAquiToString(),casillas.get(6).avataresAquiToString(),casillas.get(5).avataresAquiToString(),casillas.get(4).avataresAquiToString(),casillas.get(3).avataresAquiToString(),casillas.get(2).avataresAquiToString(),casillas.get(1).avataresAquiToString(),casillas.get(0).avataresAquiToString(),
                "");
    }

    /////////////////////////////FUNCIONES
    public void setAvatar_Casilla(int numCasilla, Avatar avatar) {
        casillas.get(numCasilla).setAvatarAqui(avatar);
    }
    public void eliminarAvatar_Casilla(int numCasilla, Avatar avatar) {
        casillas.get(numCasilla).eliminarAvatarAqui(avatar);
    }
    public String getDescripcionCasilla(String nombrecasilla) {
        for (Casilla casilla : this.casillas) {
            if (casilla.toString().equalsIgnoreCase(nombrecasilla)) return casilla.getDescripcionCasilla();
        }
        return "Nombre de casilla incorrecto";
    }
    public ArrayList<Casilla> getCasillas() { return this.casillas; }
    public ArrayList<Grupo> getGrupos() { return this.grupos; }

    public Casilla obtenerCasilla (String nombre) throws CasillaNoEncontrada {
        for (Casilla c : this.casillas) {
            if (c.equals(nombre)) return c;
        }
        throw new CasillaNoEncontrada("No se ha encontrado la casilla buscada",nombre);
    }

}
