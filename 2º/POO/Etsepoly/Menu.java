import java.util.ArrayList;
import java.util.Collections;


public final class Menu {
    String ImgMenu; //Menú como tal
    private final ConsolaNormal consola;

    public Menu() {
        this.ImgMenu = """
                
                ╔═════════════ NUEVA PARTIDA ════════════╗
                ║                OPCIONES                ║
                ║                                        ║
                ║              iniciar juego             ║
                ║    crear jugador <nombre> <avatar>     ║
                ║            listar jugadores            ║
                ║                                        ║
                ╚════════════════════════════════════════╝
                
                
                """;
        consola = new ConsolaNormal();
    }

    /**
     * Muestra por pantalla el menú
     */
    public void mostrarMenu() {
        consola.imprimir(this.ImgMenu);
    }

    /**
     * Ejecuta el menú
     * @param jugadores ArrayList de jugadores donde se asignarán los jugadores creados
     */
    public void leer(ArrayList <Jugador> jugadores)
    {
        boolean fin = false;
        ArrayList<Character> IDs = listaIDs();

        while (!fin)
        {
            try
            {
                String[] leido = consola.leer("").split(" ");

                if (leido[0].equalsIgnoreCase("iniciar") && leido[1].equalsIgnoreCase("juego"))
                {
                    if (jugadores.size() < 2)
                    {
                        throw new JugadoresInsuficientes("Debe haber al menos dos jugadores para iniciar el juego.");
                    }

                    fin = true;
                    consola.imprimir("\nIniciando partida...");

                }
                else if (leido[0].equalsIgnoreCase("crear") && leido[1].equalsIgnoreCase("jugador")) { //excepción para que no crees más de 6 jugadores
                    if (leido.length != 4) throw new ArgInsufJugador("Para crear un jugador ejecuta crear jugador <nombre> <avatar>",leido.length);
                    if (jugadores.size() == 6)
                    {
                        throw new ExcesoJugadores("Has alcanzado el número máximo de jugadores.");
                    }
                    jugadores.add(new Jugador(leido[2],leido[3],IDs.get(jugadores.size()),jugadores.size())); //excepción por si el nombre del avatar está mal
                    consola.imprimir(jugadores.get(jugadores.size()-1).toString("menu")); //excepción por si el array está vacío
                }
                else if (leido[0].equalsIgnoreCase("listar") && leido[1].equalsIgnoreCase("jugadores"))
                {
                    listarJugadores(jugadores);
                }
                else
                {
                    consola.imprimir("No es una opción válida\n\n");
                }
            }
            catch(JugadoresInsuficientes | ExcesoJugadores | ErrorNombreAvatar | ArgInsufJugador Error)
            {
                consola.imprimir(Error.getMessage()+"\n");
            }
        }
    }

    /**
     * Muestra la información básica sobre los jugadores creados
     * @param jugadores array de los jugadores
     */
    public void listarJugadores(ArrayList<Jugador> jugadores) {
        for (Jugador jActual : jugadores) {
            consola.imprimir(jActual.toString("menu"));
        }
    }

    /**
     * Genera la lista de IDs para asignar
     * @return array con el abecedario ordenado aleatoriamente
     */
    public ArrayList<Character> listaIDs() {
        ArrayList<Character> IDs = new ArrayList<>();
        for (char letra = 'A'; letra <= 'Z'; letra++) {
            IDs.add(letra);
        }
        Collections.shuffle(IDs); //ordena el array de manera aleatoria
        return IDs;
    }

}
