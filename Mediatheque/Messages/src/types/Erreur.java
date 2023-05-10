package types;

public class Erreur extends Message{
    public Erreur(String message) {
        super(message, TYPES.ERREUR);
    }

    /**
     * Indique s'il y a besoin d'attendre une réponse
     * @return s'il faut attendre une réponse
     */
    @Override
    public boolean waitingAnswer() {
        return false;
    }
}
