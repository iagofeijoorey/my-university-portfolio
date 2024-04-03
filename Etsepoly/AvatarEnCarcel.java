public class AvatarEnCarcel extends GestionAvatares {
    public AvatarEnCarcel(Avatar avatar) {
        super("Estás en la cárcel y no has sacado dobles, por lo que no puedes moverte. " + "Te quedan " + avatar.getCarcel() + " turnos en cárcel");
    }
}
