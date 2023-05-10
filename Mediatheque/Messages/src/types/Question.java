package types;

public class Question extends Message {
    public Question(String message){
        super(message, TYPES.QUESTION);
    }

    /**
     * Indique s'il y a besoin d'attendre une réponse
     * @return s'il faut attendre une réponse
     */
    @Override
    public boolean waitingAnswer() {
        return true;
    }
}
