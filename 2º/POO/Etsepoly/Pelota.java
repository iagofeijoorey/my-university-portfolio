public final class Pelota extends Avatar {

    public Pelota(char ID, int numAvatar, Jugador jugador) {
        super(ID, numAvatar, jugador);
    }

    public String menuPelotaToString() {
        return """
                    
                    
                    ╔═════════════════ MENÚ ═════════════════╗
                    ║                OPCIONES                ║
                    ║                                        ║
                    ║                 jugador                ║
                    ║           describir <casilla>          ║
                    ║           comprar <propiedad>          ║
                    ║          edificar <propiedad>          ║
                    ║              ver tablero               ║
                    ║                                        ║
                    ║           continuar moviendo           ║
                    ║                                        ║
                    ╚════════════════════════════════════════╝
                    
                    """;
    }
}
