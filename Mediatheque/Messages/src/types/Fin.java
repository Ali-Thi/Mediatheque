package types;

public class Fin extends Message {
    public Fin(String message){
        super(message, TYPES.FIN);
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