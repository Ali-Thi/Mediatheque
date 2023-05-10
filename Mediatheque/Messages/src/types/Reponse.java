package types;

public class Reponse extends Message{
    public Reponse(String message){
        super(message, TYPES.REPONSE);
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