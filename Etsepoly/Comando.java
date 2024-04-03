public interface Comando {

    ////////////////////JUGADOR
    void mostrarJugador(Jugador jActual);

    ////////////////////BANCARROTA
    boolean bancarrota(Jugador jActual);

    ////////////////////ESTADÍSTICAS
    void mostrarEstadisticas();

    ////////////////////VER TABLERO
    void mostrarTablero();

    ////////////////////LISTAR JUGADORES
    void listarJugadores();

    ////////////////////LISTAR AVATARES
    void listarAvatares();

    ////////////////////LISTAR EDIFICIOS
    void listarEdificios();

    ////////////////////LISTAR ENVENTA
    void listarEnVenta();

    ////////////////////LANZAR DADOS
    void lanzarDados(Jugador jActual, Entero tiradas, Entero dobles);

    ////////////////////CAMBIAR MODO
    void cambiarModo(Jugador j);

    ////////////////////SALIR CÁRCEL
    void salirCarcel(Jugador jActual);

    ////////////////////DESCRIBIR <CASILLA>
    void describirCasilla(String nombreCasilla);

    ////////////////////COMPRAR <PROPIEDAD>
    void comprarCasilla(String casilla, Jugador jActual);

    ////////////////////EDIFICAR <PROPIEDAD>
    void  edificar(String casilla, String StrEdificio);

    ////////////////////HIPOTECAR <PROPIEDAD>
    void hipotecar(String casilla);

    ////////////////////DESHIPOTECAR <PROPIEDAD>
    void deshipotecar(String casilla);

    ////////////////////ESTADÍSTICAS <JUGADOR>
    void mostrarEstadisticasJugador(String jugador);

    ////////////////////DESCRIBIR JUGADOR <JUGADOR>
    void describirJugador(String jugador);

    ////////////////////DESCRIBIR AVATAR <AVATAR>
    void describirAvatar(Character ID);

    ////////////////////LISTAR EDIFICIOS <COLOR>
    void listarEdificiosColor(String color);

    ////////////////////VENDER <PROPIEDAD> <CANTIDAD>
    void venderEdificios(String strEdificio, String casilla, String numAVender) throws noHayEdificios, noEsUnEdificio;

    ////////////////////TRATO <JUGADOR>: CAMBIAR (<PROPIEDAD/CANTIDAD/P y D>,<P/D/P y D>) <y noalquiler <NTURNOS>>
    void proponerTrato(String[] leido, Jugador jActual);

    ////////////////////ACEPTAR TRATO <N>
    void aceptarTrato(Jugador jActual, String n);

    ////////////////////ELIMINAR TRATO <N>
    void eliminarTrato(Jugador jActual, String n);

    ////////////////////TRATOS
    void listarTratos(Jugador jActual);

    ////////////////////ADMIN////////////////////
    public void lanzarDadosAdmin(Jugador jActual, Entero tiradas, Entero dobles, int r1, int r2);
}
