import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public final class Juego implements Comando {
    private static ConsolaNormal consola;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Trato> tratos;
    private Jugador banca;
    private Tablero tablero;
    private static Dado dado1;
    private static Dado dado2;
    private int vueltas = 0;
    private static Estadisticas estadisticas;
    private static final double salida = 216888.09727272726;

    public Juego() {
        consola = new ConsolaNormal();
        jugadores = new ArrayList<>();
        tablero = new Tablero();
        dado1 = new Dado();
        dado2 = new Dado();
        tratos = new ArrayList<>();
        estadisticas = new Estadisticas();
    }
    public static ConsolaNormal getConsola() {
        return consola;
    }
    public static int getDado1() {
        return dado1.getNumero();
    }
    public static int getDado2() {
        return dado2.getNumero();
    }


    public void inicializarJuego() {
        Menu menu = new Menu();
        menu.mostrarMenu();
        menu.leer(jugadores);
        banca = new Jugador();
        this.tablero.inicializarTablero(jugadores.size(), banca, 200000);
        for (Jugador jActual : this.jugadores) {
            jActual.getAvatar().mover(0,this.tablero.getCasillas());
            jActual.sumarDinero(dineroInicial(this.tablero.getCasillas()));
        }
    }
    public double dineroInicial(ArrayList<Casilla> casillas) {
        double dineroInicial = 0;
        for (Casilla c : casillas) {
            if (c instanceof Solar) dineroInicial += ((Solar) c).valor();
        }
        return dineroInicial/3;
    }

    public void ejecutarJuego() {
        boolean finalizarPartida = false;
        Entero tiradas = new Entero(0);
        Entero dobles = new Entero(0);

        while (!finalizarPartida) {
            for (Jugador jActual : this.jugadores) {
                finalizarPartida = turnoJugador(jActual,tiradas,dobles);
                if (jActual.getAvatar().getPosicion()/40 > this.vueltas) this.vueltas = jActual.getAvatar().getPosicion()/40;
                if (finalizarPartida) {
                    break;
                }
            }
        }
        consola.imprimir("\nPartida finalizada.\n");
    }

    public boolean turnoJugador(Jugador jActual, Entero tiradas, Entero dobles) {
        boolean finTurno = false;
        tiradas.setNumero(1);
        dobles.setNumero(0);
        String menu = crearMenu();
        consola.imprimir("\n\n");
        listarTratos(jActual);
        while (!finTurno) {
            String[] leido = consola.leer(menu).toLowerCase().split(" ");
                   /* ═════════════════════════════════════════════════ */
               /* ═══════════════════════════════════════════════════════════ */
             /* ══════════════════════════════════════════════════════════════════ */
            /* ═══════════════════════════════════════════════════════════════════════ */
            /* ══════════════════════════════════════════════[OPCIONES MENÚ]══════════════════════════════════════════════ */
            ////////////////////JUGADOR
            if (leido.length == 1 && leido[0].equals("jugador")) mostrarJugador(jActual);
            ////////////////////BANCARROTA
            else if (leido.length == 1 && leido[0].equals("bancarrota")) finTurno = bancarrota(jActual);
            ////////////////////ESTADÍSTICAS
            else if (leido.length == 1 && leido[0].equals("estadisticas")) mostrarEstadisticas();
            ////////////////////ACABAR TURNO
            else if (leido.length == 2 && leido[0].equals("acabar") && leido[1].equals("turno")) { if (tiradas.getNumero() > 0) consola.imprimir("Aún tienes tiradas este turno."); else finTurno = true; }
            ////////////////////FINALIZAR PARTIDA
            else if (leido.length == 2 && leido[0].equals("finalizar") && leido[1].equals("partida")) return true;
            ////////////////////VER TABLERO
            else if (leido.length == 2 && leido[0].equals("ver") && leido[1].equals("tablero")) mostrarTablero();
            ////////////////////LISTAR JUGADORES
            else if (leido.length == 2 && leido[0].equals("listar") && leido[1].equals("jugadores")) listarJugadores();
            ////////////////////LISTAR AVATARES
            else if (leido.length == 2 && leido[0].equals("listar") && leido[1].equals("avatares")) listarAvatares();
            ////////////////////LISTAR EDIFICIOS
            else if (leido.length == 2 && leido[0].equals("listar") && leido[1].equals("edificios")) listarEdificios();
            ////////////////////LISTAR ENVENTA
            else if (leido.length == 2 && leido[0].equals("listar") && leido[1].equals("enventa")) listarEnVenta();
            ////////////////////LANZAR DADOS
            else if (leido.length == 2 && leido[0].equals("lanzar") && leido[1].equals("dados")) lanzarDados(jActual, tiradas, dobles);
            ////////////////////CAMBIAR MODO
            else if (leido.length == 2 && leido[0].equals("cambiar") && leido[1].equals("modo")) cambiarModo(jActual);
            ////////////////////SALIR CÁRCEL
            else if (leido.length == 2 && leido[0].equals("salir") && leido[1].equals("carcel")) salirCarcel(jActual);
            ////////////////////DESCRIBIR <CASILLA>
            else if (leido.length == 2 && leido[0].equals("describir")) describirCasilla(leido[1]);
            ////////////////////COMPRAR <PROPIEDAD>
            else if (leido.length == 2 && leido[0].equals("comprar")) comprarCasilla(leido[1], jActual);
            ////////////////////EDIFICAR <PROPIEDAD>
            else if (leido.length == 2 && leido[0].equals("edificar")) edificar(leido[1], leido[2]);
            ////////////////////HIPOTECAR <PROPIEDAD>
            else if (leido.length == 2 && leido[0].equals("hipotecar")) hipotecar(leido[1]);
            ////////////////////DESHIPOTECAR <PROPIEDAD>
            else if (leido.length == 2 && leido[0].equals("deshipotecar")) deshipotecar(leido[1]);
            ////////////////////ESTADÍSTICAS <JUGADOR>
            else if (leido.length == 2 && leido[0].equals("estadisticas")) mostrarEstadisticasJugador(leido[1]);
            ////////////////////DESCRIBIR JUGADOR <JUGADOR>
            else if (leido.length == 3 && leido[0].equals("describir") && leido[1].equals("jugador")) describirJugador(leido[2]);
            ////////////////////DESCRIBIR AVATAR <AVATAR>
            else if (leido.length == 3 && leido[0].equals("describir") && leido[1].equals("avatar")) describirAvatar(leido[2].charAt(0));
            ////////////////////LISTAR EDIFICIOS <COLOR>
            else if (leido.length == 3 && leido[0].equals("listar") && leido[1].equals("edificios")) listarEdificiosColor(leido[2]);
            ////////////////////VENDER <PROPIEDAD> <CANTIDAD>
            else if (leido.length == 4 && leido[0].equals("vender")) venderEdificios(leido[1], leido[2], leido[3]);
            ////////////////////TRATO <JUGADOR>: CAMBIAR (<PROPIEDAD/CANTIDAD/P y D>,<P/D/P y D>) <y noalquiler <NTURNOS>>
            else if (leido.length > 3 && leido[0].equals("trato")) proponerTrato(leido,jActual);
            ////////////////////ACEPTAR TRATO <N>
            else if (leido.length == 3 && leido[0].equals("aceptar")) aceptarTrato(jActual,leido[2]);
            ////////////////////ELIMINAR TRATO <N>
            else if (leido.length == 3 && leido[0].equals("eliminar")) eliminarTrato(jActual,leido[2]);
            ////////////////////TRATOS
            else if (leido.length == 1 && leido[0].equals("tratos")) listarTratos(jActual);
            ////////////////////ADMIN////////////////////
            else if (leido.length == 4 && leido[0].equals("lanzar")) lanzarDadosAdmin(jActual, tiradas, dobles, Integer.parseInt(leido[2]), Integer.parseInt(leido[3]));
            else {
                consola.imprimir("\nNo es una opción válida.\n");
            }
            /*═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════*/
        }
        return false;
    }

    public String crearMenu() {
        return """
                             
                
                ╔═════════════════ MENÚ ═════════════════╗
                ║                OPCIONES                ║
                ║                                        ║
                ║                 jugador                ║
                ║              lanzar dados              ║
                ║              cambiar modo              ║
                ║            listar jugadores            ║
                ║             listar avatares            ║
                ║           describir <Casilla>          ║
                ║             listar edificios           ║
                ║         listar edificios <Color>       ║
                ║       describir jugador <jugador>      ║
                ║        describir avatar <avatar>       ║
                ║           comprar <propiedad>          ║
                ║          edificar <propiedad>          ║
                ║      vender <propiedad> <cantidad>     ║
                ║           hipotecar propiedad          ║
                ║         deshipotecar propiedad         ║
                ║               bancarrota               ║
                ║                  trato                 ║
                ║             aceptar trato <N>          ║
                ║             listar enventa             ║
                ║          estadisticas <jugador>        ║
                ║              estadisticas              ║
                ║              salir carcel              ║
                ║              ver tablero               ║
                ║                                        ║
                ║              acabar turno              ║
                ║                                        ║
                ║                                        ║
                ║           finalizar partida            ║
                ║                                        ║
                ╚════════════════════════════════════════╝
                
                
                """;
    }

    /* ══════════════════════════════════════[OPCIONES MENÚ]══════════════════════════════════════ */

    ////////////////////JUGADOR
    @Override
    public void mostrarJugador(Jugador jActual) { consola.imprimir(jActual.toString("menu")); }
    ////////////////////BANCARROTA
    @Override
    public boolean bancarrota(Jugador jActual) {
        consola.imprimir("¿Estás seguro de que quieres declararte en bancarrota?");
        String[] leido = consola.leer("").toLowerCase().split(" ");
        if(leido[0].equalsIgnoreCase("S"))
        {
            return false;
        }
        else
        {
            if(jActual.getDinero() > 0)//Si aun le queda dinero y se quiere ir de la partida
            {
                consola.imprimir("El jugador Pedro se ha declarado en bancarrota. Sus propiedades pasan\na estar de nuevo en venta al precio al que estaban.\nSe ha saltado el turno al siguiente jugador.\n");
                transferirPropiedades(jActual, banca);
                jugadores.remove(jActual);
                return true;
            }
            else //Si se declara en bancarrota por no tener dinero
            {
                consola.imprimir("El jugador Pedro se ha declarado en bancarrota. Sus propiedades y\nfortuna pasan al jugador " + jActual.getUltBeneficiario() + ".\nSe ha saltado el turno al siguiente jugador.\n");
                transferirPropiedades(jActual, jActual.getUltBeneficiario());
                jugadores.remove(jActual);
                return true;
            }
        }
    }

    ///Parte de bancarrota
    public void transferirPropiedades(Jugador jActual, Jugador beneficiario)
    {
        for (Casilla c : this.tablero.getCasillas()) {
            if (c instanceof Propiedad) {
                if(((Propiedad) c).getPropietario().toString().equals(jActual.toString()))
                {
                    ((Propiedad) c).transferirA(beneficiario);
                }
            }
        }
    }
    ////////////////////ESTADÍSTICAS
    @Override
    public void mostrarEstadisticas() {
        consola.imprimir(estadisticas.getString(this.tablero.getCasillas(),this.jugadores,this.tablero.getGrupos()));
    }
    ////////////////////VER TABLERO
    @Override
    public void mostrarTablero() { consola.imprimir(this.tablero.toString()); }
    ////////////////////LISTAR JUGADORES
    @Override
    public void listarJugadores() {
        for (Jugador j : jugadores) describirJugador(j.toString());
    }
    ////////////////////LISTAR AVATARES
    @Override
    public void listarAvatares() { for (Jugador j : jugadores) describirAvatar(j.getAvatar().getID()); }
    ////////////////////LISTAR EDIFICIOS
    @Override
    public void listarEdificios() {
        for (Casilla c : this.tablero.getCasillas()) {
            if (c instanceof Solar) {
                for (Edificio e : ((Solar) c).getEdificios()) consola.imprimir(e.toString());
            }
        }
    }
    ////////////////////LISTAR ENVENTA
    @Override
    public void listarEnVenta() {
        for (Casilla c : this.tablero.getCasillas()) {
            if (c instanceof Propiedad) {
                consola.imprimir(((Propiedad) c).getDescripcionEnVenta());
            }
        }
    }
    ////////////////////LANZAR DADOS
    @Override
    public void lanzarDados(Jugador jActual, Entero tiradas, Entero dobles) {
        try {
            int posicionInicial = jActual.getAvatar().getPosicion()%40;
            int POS_INICIAL = jActual.getAvatar().getPosicion();
            //Lanzar los dados
            dado1.lanzar(); dado2.lanzar();
            //Comprobaciones de si se puede mover el jugador
            if (tiradas.getNumero() == 0) {
                throw new NoTiradasRestantes();
            }
            if (jActual.getAvatar().getCarcel() > 0) {
                if (dado1.getNumero() != dado2.getNumero()) {
                    tiradas.sumar(-1);
                    jActual.getAvatar().setCarcel(jActual.getAvatar().getCarcel() - 1);
                    throw new AvatarEnCarcel(jActual.getAvatar());
                } else {
                    jActual.getAvatar().setCarcel(0);
                }
            }

            if (!jActual.getAvatar().getModoAvanzado()) {
                jActual.getAvatar().moverEnBasico(dado1.getNumero(),dado2.getNumero(),this.tablero.getCasillas(),tiradas,dobles, banca, jugadores);
            } else {
                String tipoFicha = jActual.getAvatar().moverEnAvanzado();
                if (tipoFicha.equals("Pelota")) modoAvanzadoPelota(jActual,tiradas,dobles);
                else if (tipoFicha.equals("Coche")) modoAvanzadoCoche(jActual,tiradas,dobles);
                else {
                    consola.imprimir("Error al reconocer el tipo de ficha para el movimiento avanzado");
                    return;
                }
            }



            //DAR DINERO DE PASAR POR LA SALIDA
            int POS_FINAL = jActual.getAvatar().getPosicion();
            if (POS_FINAL != POS_INICIAL) {
                if (POS_FINAL > POS_INICIAL) { //avanzando
                    for (int i = POS_INICIAL;i <= POS_FINAL; i++) {
                        if ((i%40) == 0) {
                            jActual.sumarDinero(salida);
                            //consola.imprimir("Salida: " + mediaPrecioSolares);
                        }
                    }
                } else { //retrocediendo
                    for (int i = POS_INICIAL;i >= POS_FINAL; i--) {
                        if ((i%40) == 0) {
                            jActual.sumarDinero(-salida);
                        }
                    }
                }
            }

            consola.imprimir("\nHas avanzado " + (jActual.getAvatar().getPosicion() - posicionInicial) + " casillas, desde " + this.tablero.getCasillas().get(posicionInicial%40) + " hasta " + this.tablero.getCasillas().get(jActual.getAvatar().getPosicion()%40));
            jActual.masUnoVecesDados();
        } catch (AvatarEnCarcel | NoTiradasRestantes Error) {
            consola.imprimir(Error.getMessage()+"\n");
        }
    }
    ////////////////////CAMBIAR MODO
    @Override
    public void cambiarModo(Jugador j) {
        if (j.getAvatar() instanceof Pelota || j.getAvatar() instanceof Coche) {
            if (j.getAvatar().getModoAvanzado()) {
                j.getAvatar().setModoAvanzado(false);
                consola.imprimir("Se ha desactivado el modo avanzado");
            } else {
                j.getAvatar().setModoAvanzado(true);
                consola.imprimir("Se ha activado el modo avanzado");
            }
        } else {
            consola.imprimir("No es un avatar válido para el modo de movimiento avanzado (solo disponible para Pelota y Coche).");
        }
    }
    ////////////////////SALIR CÁRCEL
    @Override
    public void salirCarcel(Jugador jActual) {
        if (jActual.getAvatar().getCarcel() == 0) {
            consola.imprimir("El jugador no está en la cárcel.");
        } else {
            jActual.getAvatar().salirCarcelPagando(((Especial) this.tablero.getCasillas().get(0)).getValor());
        }
    }
    ////////////////////DESCRIBIR <CASILLA>
    @Override
    public void describirCasilla(String nombreCasilla) { consola.imprimir(this.tablero.getDescripcionCasilla(nombreCasilla)); }
    ////////////////////COMPRAR <PROPIEDAD>
    @Override
    public void comprarCasilla(String casilla, Jugador jActual) {
        try {
            for (Casilla c : this.tablero.getCasillas()) {
                if (c.toString().equalsIgnoreCase(casilla)) {
                    if (!(c instanceof Propiedad)) {
                        throw new CasillaNoComprable("Esta casilla no se puede comprar");
                    } else ((Propiedad) c).comprar(jActual);
                }
            }
        } catch (CasillaNoComprable | AvatarNoEnCasilla Error) {
            consola.imprimir(Error.getMessage()+"\n");
        }
    }
    ////////////////////EDIFICAR <PROPIEDAD>
    @Override
    public void edificar(String casilla, String StrEdificio) {
        try {
            for (Casilla c : this.tablero.getCasillas()) {
                if (c.toString().equalsIgnoreCase(casilla)) {
                    if (!(c instanceof Solar)) throw new CasillaNoEdificable("Esta casilla no se puede edificar");
                    else ((Solar) c).edificar(StrEdificio);
                }
            }
        }
        catch (CasillaNoEdificable | EdificiosInsuficientes | DemasiadosEdificios Error)  {
            consola.imprimir(Error.getMessage() + "\n");
        }
    }
    ////////////////////HIPOTECAR <PROPIEDAD>
    @Override
    public void hipotecar(String casilla) {
        try {
            for (Casilla c : this.tablero.getCasillas()) {
                if (c.toString().equalsIgnoreCase(casilla)) {
                    if (!(c instanceof Propiedad)) throw new CasillaNoHipotecable("Esta casilla no se puede hipotecar");
                    else{
                        if(c instanceof Solar) ((Solar) c).hipotecar(banca);
                        else ((Propiedad) c).hipotecar(banca);
                    }
                }
            }
        }
        catch (CasillaNoHipotecable Error)  {
            consola.imprimir(Error.getMessage() + "\n");
        }
    }
    ////////////////////DESHIPOTECAR <PROPIEDAD>
    @Override
    public void deshipotecar(String casilla) {
        try {
            for (Casilla c : this.tablero.getCasillas()) {
                if (c.toString().equalsIgnoreCase(casilla)) {
                    if (!(c instanceof Propiedad)) throw new CasillaNoHipotecable("Esta casilla no se puede hipotecar");
                    else ((Solar) c).deshipotecar(banca);
                }
            }
        }
        catch (CasillaNoHipotecable Error)  {
            consola.imprimir(Error.getMessage() + "\n");
        }
    }
    ////////////////////ESTADÍSTICAS <JUGADOR>
    @Override
    public void mostrarEstadisticasJugador(String jugador) {
        for (Jugador j : this.jugadores) {
            if (j.toString().equals(jugador))
                consola.imprimir(j.estadisticasJugador());
        }
    }
    ////////////////////DESCRIBIR JUGADOR <JUGADOR>
    @Override
    public void describirJugador(String jugador) {
        for (Jugador j : this.jugadores) {
            if (j.toString().equals(jugador)) consola.imprimir(j.toString("descripcionJugador"));
        }
    }
    ////////////////////DESCRIBIR AVATAR <AVATAR>
    @Override
    public void describirAvatar(Character ID) {
        ID = Character.toUpperCase(ID);
        for (Jugador j : this.jugadores) {
            if (j.equals(ID)) consola.imprimir(j.getDescripcionAvatar(this.tablero.getCasillas()));
        }
    }
    ////////////////////LISTAR EDIFICIOS <COLOR>
    @Override
    public void listarEdificiosColor(String color) {
        for (Grupo g : this.tablero.getGrupos()) {
            if (g.toString().equalsIgnoreCase(color)) {
                consola.imprimir(g.listarEdificios());
            }
        }
    }
    ////////////////////VENDER <PROPIEDAD> <CANTIDAD>
    @Override
    public void venderEdificios(String strEdificio, String casilla, String numAVender) {
        int numAVenderEntero = Integer.parseInt(numAVender);
        try {
            for (Casilla c : this.tablero.getCasillas()) {
                if (c.toString().equalsIgnoreCase(casilla)) {
                    if (!(c instanceof Solar)) throw new CasillaNoEdificable("Esta casilla no puede contener edificios.");
                    else {
                        ((Solar) c).venderEdificio(strEdificio, numAVenderEntero, banca);
                    }
                }
            }
        }
        catch (CasillaNoEdificable | noHayEdificios Error)  {
            consola.imprimir(Error.getMessage() + "\n");
        }
        catch (noEsUnEdificio Error) {
            consola.imprimir(Error.getMessage() + ". Seleccione uno edificio valido para volver a intentarlo\n");
            String[] leido = consola.leer("").toLowerCase().split(" ");
            try{
                for (Casilla c : this.tablero.getCasillas()) {
                    if (c.toString().equalsIgnoreCase(casilla)) {
                        ((Solar) c).venderEdificio(leido[1], numAVenderEntero, banca);
                    }
                }
            }
            catch ( noEsUnEdificio | noHayEdificios Error2)  {
                consola.imprimir(Error2.getMessage() + "\n");
            }


        }
    }
    ////////////////////TRATO <JUGADOR>: CAMBIAR (<PROPIEDAD/CANTIDAD/P y D>,<P/D/P y D>) <y noalquiler <NTURNOS>>
    @Override
    public void proponerTrato(String[] leido, Jugador jActual) {
        try {
            //Arreglar los datos introducidos
            for (int i = 0; i < leido.length; i++) {
                leido[i] = leido[i].replaceAll("[:,()]", "");
            }
            //Extraer el jugador al que se le propone el trato
            String jugadorPropuesto = leido[1];
            boolean existeJugador = false;
            Jugador jPropuesto = null;
            for (Jugador j : this.jugadores) {
                if (jugadorPropuesto.equalsIgnoreCase(j.toString())) {
                    existeJugador = true;
                    jPropuesto = j;
                }
            }
            if (!existeJugador) throw new ErrorJugador("El jugador propuesto no existe");
            if (jPropuesto.toString().equals(jActual.toString())) {
                consola.imprimir("No puedes proponerte un trato a ti mismo");
                return;
            }

            if (leido.length == 5) { //tipo 1, 2 o 3
                if (!Entero.esNumero(leido[3])) { //tipo 1 o 2
                    if (!Entero.esNumero(leido[4])) { //tipo 1
                        //comprobar Propiedades válidas
                        Casilla propiedadPropuesta = this.tablero.obtenerCasilla(leido[3]);
                        Casilla propiedadRequerida = this.tablero.obtenerCasilla(leido[4]);
                        if (propiedadPropuesta instanceof Propiedad) {
                            if (((Propiedad) propiedadRequerida).getPropietario().toString().equalsIgnoreCase(jPropuesto.toString())) {
                                if (((Propiedad) propiedadPropuesta).getPropietario().toString().equals(jActual.toString())) {
                                    this.tratos.add(new tratoTipo1(jActual,jPropuesto,(Propiedad) propiedadPropuesta,(Propiedad) propiedadRequerida));
                                } else consola.imprimir("Error: " + leido[4] + " no te pertenece, por lo que no la puedes proponer.");
                            } else consola.imprimir("Error: " + leido[3] + " no pertenece a " + jPropuesto.toString() + ".");
                        } else consola.imprimir("Error: " + leido[3] + " no es una Propiedad.");
                    } else { //tipo 2
                        Casilla propiedadPropuesta = this.tablero.obtenerCasilla(leido[3]);
                        double dineroRequerido = Double.parseDouble(leido[4]);
                        if (propiedadPropuesta instanceof Propiedad) {
                            if (((Propiedad) propiedadPropuesta).getPropietario().toString().equals(jActual.toString())) {
                                this.tratos.add(new tratoTipo2(jActual,jPropuesto,(Propiedad) propiedadPropuesta,dineroRequerido));
                            } else consola.imprimir("Error: " + leido[3] + " no te pertenece, por lo que no la puedes proponer.");
                        } else consola.imprimir("Error: " + leido[3] + " no es una Propiedad.");
                    }
                } else { //tipo 3
                    double dineroPropuesto = Double.parseDouble(leido[3]);
                    Casilla propiedadRequerida = this.tablero.obtenerCasilla(leido[4]);
                    if (((Propiedad) propiedadRequerida).getPropietario().toString().equals(jPropuesto.toString())) {
                        if (jActual.getDinero() >= dineroPropuesto) {
                            this.tratos.add(new tratoTipo3(jActual,jPropuesto,dineroPropuesto,(Propiedad) propiedadRequerida));
                        } else consola.imprimir("Error: No dispones de la cantidad de dinero propuesta para realizar el trato.");
                    } else consola.imprimir("Error: " + propiedadRequerida.toString() + " no pertenece a " + jPropuesto.toString() + ".");
                }
            } else if (leido.length == 7) { //tipo 4 o 5
                Casilla propiedadPropuesta = this.tablero.obtenerCasilla(leido[3]);
                if (propiedadPropuesta instanceof Propiedad) {
                    if (((Propiedad) propiedadPropuesta).getPropietario().toString().equalsIgnoreCase(jActual.toString())) {
                        if (leido[5].equalsIgnoreCase("y")) { //tipo 4  en 6 el dinero
                            Casilla propiedadRequerida = this.tablero.obtenerCasilla(leido[4]);
                            if (propiedadRequerida instanceof Propiedad) {
                                if (((Propiedad) propiedadRequerida).getPropietario().toString().equalsIgnoreCase(jPropuesto.toString())) {
                                    if (Entero.esNumero(leido[6])) {
                                        double dineroRequerido = Double.parseDouble(leido[6]);
                                        this.tratos.add(new tratoTipo4(jActual,jPropuesto,(Propiedad)propiedadPropuesta,(Propiedad)propiedadRequerida,dineroRequerido));
                                    } else consola.imprimir("Error: no has introducido una cantida de dinero correcta.");
                                } else consola.imprimir("Error: " + propiedadRequerida.toString() + " no pertenece a " + jPropuesto.toString() + ".");
                            } else consola.imprimir("Error: " + propiedadRequerida.toString() + " no es una Propiedad.");
                        } else if (leido[4].equalsIgnoreCase("y")) { //tipo 5
                            Casilla propiedadRequerida = this.tablero.obtenerCasilla(leido[6]);
                            if (propiedadRequerida instanceof Propiedad) {
                                if (((Propiedad) propiedadRequerida).getPropietario().toString().equalsIgnoreCase(jPropuesto.toString())) {
                                    if (Entero.esNumero(leido[5])) {
                                        double dineroPropuesto = Double.parseDouble(leido[6]);
                                        this.tratos.add(new tratoTipo5(jActual,jPropuesto,(Propiedad)propiedadPropuesta,dineroPropuesto,(Propiedad)propiedadRequerida));
                                    } else consola.imprimir("Error: no has introducido una cantida de dinero correcta.");
                                } else consola.imprimir("Error: " + propiedadRequerida.toString() + " no pertenece a " + jPropuesto.toString() + ".");
                            } else consola.imprimir("Error: " + propiedadRequerida.toString() + " no es una Propiedad.");
                        } else consola.imprimir("Comando no válido para el trato.");
                    } else consola.imprimir("Error: " + propiedadPropuesta.toString() + " no te pertenece, por lo que no la puedes proponer.");
                } else consola.imprimir("Error: " + propiedadPropuesta.toString() + " no es una Propiedad.");
            } else if (leido.length == 9) { //tipo 6
                Casilla propiedadPropuesta = this.tablero.obtenerCasilla(leido[3]);
                Casilla propiedadRequerida = this.tablero.obtenerCasilla(leido[4]);
                if (leido[6].equalsIgnoreCase("noalquiler")) {
                    Casilla noAlquiler = this.tablero.obtenerCasilla(leido[7]);
                    if (noAlquiler instanceof Propiedad) {
                        if (((Propiedad) noAlquiler).getPropietario().toString().equalsIgnoreCase(jPropuesto.toString())) {
                            if (propiedadPropuesta instanceof Propiedad) {
                                if (((Propiedad) propiedadPropuesta).getPropietario().toString().equalsIgnoreCase(jActual.toString())) {
                                    if (propiedadRequerida instanceof Propiedad) {
                                        if (((Propiedad) propiedadRequerida).getPropietario().toString().equalsIgnoreCase(jPropuesto.toString())) {
                                            if (Entero.esNumero(leido[8])) {
                                                int nTurnos = Integer.parseInt(leido[8]);
                                                if (!noAlquiler.toString().equalsIgnoreCase(propiedadRequerida.toString())) {
                                                    this.tratos.add(new tratoTipo6(jActual,jPropuesto,(Propiedad) propiedadPropuesta,(Propiedad) propiedadRequerida,(Propiedad) noAlquiler,nTurnos));
                                                } else consola.imprimir("Error: No puedes solicitar no pagar alquiler en la misma Propiedad que quieres comprar");
                                            } else consola.imprimir("Error: El número de turnos introducido no es válido");
                                        } else consola.imprimir("Error: " + propiedadRequerida.toString() + " no pertenece a " + jPropuesto.toString() + ".");
                                    } else consola.imprimir("Error: " + propiedadRequerida.toString() + " no es una Propiedad.");
                                } else consola.imprimir("Error: " + propiedadPropuesta.toString() + " no te pertenece.");
                            } else consola.imprimir("Error: " + propiedadPropuesta.toString() + " no es una Propiedad.");
                        } else consola.imprimir("Error: La Propiedad que se solicita para no pagar alquiler no pertenece al jugador al que se le propone");
                    } else consola.imprimir("Error: " + noAlquiler.toString() + "no es una Propiedad.");
                } else consola.imprimir("Comando no válido para el trato.");
            } else if (leido.length == 4 && leido[2].equalsIgnoreCase("intercambiar")) { //tipo 7
                 this.tratos.add(new tratoTipo7(jActual,jPropuesto));
            } else consola.imprimir("Comando no válido para el trato.");
        } catch (ErrorJugador | CasillaNoEncontrada Error) {
            consola.imprimir(Error.getMessage() + "\n");
        }
    }
    ////////////////////ACEPTAR TRATO <N>
    @Override
    public void aceptarTrato(Jugador jActual, String n) {
        if(!this.tratos.isEmpty()) {
            if (Entero.esNumero(n)) {
                int nTrato = Integer.parseInt(n);
                if (nTrato - 1 < this.tratos.size() && nTrato - 1 >= 0) {
                    if (this.tratos.get(nTrato-1).getJugadorPropuesto().toString().equalsIgnoreCase(jActual.toString())) {
                        try {
                            //
                            if (this.tratos.get(nTrato - 1) instanceof tratoTipo7) {
                                Jugador jPropone = tratos.get(nTrato - 1).getJugadorPropone();
                                this.tablero.getCasillas().get(jActual.getAvatar().getPosicion()%40).eliminarAvatarAqui(jActual.getAvatar());
                                this.tablero.getCasillas().get(jPropone.getAvatar().getPosicion()%40).eliminarAvatarAqui(jPropone.getAvatar());
                            }
                            //
                            this.tratos.get(nTrato - 1).aceptarTrato();
                            //
                            if (this.tratos.get(nTrato - 1) instanceof tratoTipo7) {
                                Jugador jPropone = tratos.get(nTrato - 1).getJugadorPropone();
                                this.tablero.getCasillas().get(jActual.getAvatar().getPosicion()%40).setAvatarAqui(jActual.getAvatar());
                                this.tablero.getCasillas().get(jPropone.getAvatar().getPosicion()%40).setAvatarAqui(jPropone.getAvatar());
                            }
                            //
                            this.tratos.remove(this.tratos.get(nTrato - 1));
                        } catch (TratoException Error) {
                            consola.imprimir(Error.getMessage());
                        }
                    } else consola.imprimir("Error: Este trato no te ha sido propuesto a ti, por lo que no lo puedes aceptar.");
                } else consola.imprimir("Error: Número de trato incorrecto.");
            } else consola.imprimir("Error: Formato de número de trato incorrecto.");
        } else consola.imprimir("Error: No hay ningún trato creado");
    }
    ////////////////////ELIMINAR TRATO <N>
    @Override
    public void eliminarTrato(Jugador jActual, String n) {
        if(!this.tratos.isEmpty()) {
            if (Entero.esNumero(n)) {
                int nTrato = Integer.parseInt(n);
                if (nTrato - 1 < this.tratos.size() && nTrato - 1 >= 0) {
                    if (this.tratos.get(nTrato-1).getJugadorPropone().toString().equalsIgnoreCase(jActual.toString())) {
                                this.tratos.remove(nTrato - 1);
                                consola.imprimir("Trato " + nTrato + " eliminado.");
                    } else consola.imprimir("Error: Este trato no ha sido propuesto por ti, por lo que no lo puedes eliminar.");
                } else consola.imprimir("Error: Número de trato incorrecto.");
            } else consola.imprimir("Error: Formato de número de trato incorrecto.");
        } else consola.imprimir("Error: No hay ningún trato creado");
    }
    ////////////////////TRATOS
    @Override
    public void listarTratos(Jugador jActual) {
        int i = 1;
        for (Trato t : this.tratos) {
            if (t.getJugadorPropuesto().toString().equalsIgnoreCase(jActual.toString())) {
                consola.imprimir("Trato " + i + " " +t.getImpresion());
            } i++;
        }
    }
    ////////////////////ADMIN////////////////////
    @Override
    public void lanzarDadosAdmin(Jugador jActual, Entero tiradas, Entero dobles, int r1, int r2) {
        try {
            int POS_INICIAL = jActual.getAvatar().getPosicion();
            int posicionInicial = jActual.getAvatar().getPosicion();
            //Lanzar los dados
            dado1.setNumero(r1); dado2.setNumero(r2);
            //Comprobaciones de si se puede mover el jugador
            if (tiradas.getNumero() == 0) {
                throw new NoTiradasRestantes();
            }
            if (jActual.getAvatar().getCarcel() > 0) {
                if (dado1.getNumero() != dado2.getNumero()) {
                    tiradas.sumar(-1);
                    jActual.getAvatar().setCarcel(jActual.getAvatar().getCarcel() - 1);
                    throw new AvatarEnCarcel(jActual.getAvatar());
                } else {
                    jActual.getAvatar().setCarcel(0);
                    consola.imprimir("Así que sales de la Cárcel. Ahora puedes tirar.");
                }
            }

            if (!jActual.getAvatar().getModoAvanzado()) {
                jActual.getAvatar().moverEnBasico(dado1.getNumero(),dado2.getNumero(),this.tablero.getCasillas(),tiradas,dobles, banca, jugadores);
            } else {
                String tipoFicha = jActual.getAvatar().moverEnAvanzado();
                if (tipoFicha.equals("Pelota")) modoAvanzadoPelota(jActual,tiradas,dobles);
                else if (tipoFicha.equals("Coche")) modoAvanzadoCoche(jActual,tiradas,dobles);
                else {
                    consola.imprimir("Error al reconocer el tipo de ficha para el movimiento avanzado");
                    return;
                }
            }


            //DAR DINERO DE PASAR POR LA SALIDA
            int POS_FINAL = jActual.getAvatar().getPosicion();
            if (POS_FINAL != POS_INICIAL) {
                if (POS_FINAL > POS_INICIAL) { //avanzando
                    for (int i = POS_INICIAL;i <= POS_FINAL; i++) {
                        if ((i%40) == 0) {
                            jActual.sumarDinero(salida);
                        }
                    }
                } else { //retrocediendo
                    for (int i = POS_INICIAL;i >= POS_FINAL; i--) {
                        if ((i%40) == 0) {
                            jActual.sumarDinero(-salida);
                        }
                    }
                }
            }

            mostrarTablero();
            consola.imprimir("Has avanzado " + (jActual.getAvatar().getPosicion() - posicionInicial) + " casillas, desde " + this.tablero.getCasillas().get(posicionInicial%40) + " hasta " + this.tablero.getCasillas().get(jActual.getAvatar().getPosicion()%40));
        } catch (AvatarEnCarcel | NoTiradasRestantes Error) {
            consola.imprimir(Error.getMessage()+"\n");
        }
    }
/* ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════ */

    ////////////////////////////////////////FUNCIONES AUXILIARES/////////////////////////////////////////// (para hacer más legible el código)
    public void modoAvanzadoPelota(Jugador j,Entero tiradas, Entero dobles) {
        tiradas.sumar(-1);
        int posInicial = j.getAvatar().getPosicion();
        int rDados = dado1.getNumero()+dado2.getNumero();
        if (rDados < 5) {
            if (posInicial < rDados) {
                rDados = -posInicial;
            } else {
                rDados = -rDados;
            }
        }
        if (dado1.getNumero() == dado2.getNumero()) {
            tiradas.sumar(1);
            dobles.sumar(1);
            consola.imprimir("Sacas dobles. ");
        }
        if (dobles.getNumero() ==3) {
            this.tablero.getCasillas().get(j.getAvatar().getPosicion()).eliminarAvatarAqui(j.getAvatar());
            j.getAvatar().goCarcel(this.tablero.getCasillas().get(10));
            consola.imprimir("Has sacado dobles tres veces seguidas, por lo que vas a la cárcel");
            tiradas.setNumero(0);
            return;
        }

        if (rDados < 0) {
            consola.imprimir("El modo avanzado de pelota está activado y has sacado menos de 5, por lo que retrocedes casillas.");
            j.getAvatar().mover(rDados,this.tablero.getCasillas());
            j.getAvatar().accionCasilla(this.tablero.getCasillas(), tiradas, dobles, banca, jugadores);
        } else {
            if (rDados == 4) {
                j.getAvatar().mover(rDados,this.tablero.getCasillas());
                j.getAvatar().accionCasilla(this.tablero.getCasillas(), tiradas, dobles, banca, jugadores);
            } else { //para cuando el resultado de los dados es 4 o más
                j.getAvatar().mover(5,this.tablero.getCasillas());
                j.getAvatar().accionCasilla(this.tablero.getCasillas(), tiradas, dobles, banca, jugadores);
                for (int i=7;i < rDados-1;i+=2) {
                    j.getAvatar().mover(2,this.tablero.getCasillas());
                    j.getAvatar().accionCasilla(this.tablero.getCasillas(), tiradas, dobles, banca, jugadores);
                    accionModoAvanzadoPelota(j);
                }
                int avanzado = j.getAvatar().getPosicion() - posInicial;
                j.getAvatar().mover(rDados-avanzado,this.tablero.getCasillas());
                j.getAvatar().accionCasilla(this.tablero.getCasillas(), tiradas, dobles, banca, jugadores);
            }
        }


       // consola.imprimir("Has avanzado " + (j.getAvatar().getPosicion() - posInicial) + " casillas, desde " + this.tablero.getCasillas().get(posInicial%40).toString() + " hasta " + this.tablero.getCasillas().get(j.getAvatar().getPosicion()%40).toString() + ".");
    }
    public void modoAvanzadoCoche(Jugador j,Entero tiradas,Entero dobles) {}
    public void accionModoAvanzadoPelota(Jugador j) {
        boolean continuar = false;
        while (!continuar) {
            String[] leido = consola.leer(((Pelota) j.getAvatar()).menuPelotaToString()).toLowerCase().split(" ");
            if (leido[0].equalsIgnoreCase("continuar")) continuar = true;
            else if (leido[0].equalsIgnoreCase("jugador")) mostrarJugador(j);
            else if (leido[0].equalsIgnoreCase("describir")) describirCasilla(leido[1]);
            else if (leido[0].equalsIgnoreCase("comprar")) comprarCasilla(leido[1], j);
            else if (leido[0].equalsIgnoreCase("edificar")) edificar(leido[1], leido[2]);
            else if (leido[0].equalsIgnoreCase("ver")) mostrarTablero();
            else consola.imprimir("\nNo es una opción válida.\n");
        }
    }
}
