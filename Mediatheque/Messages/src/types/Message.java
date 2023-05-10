package types;

import java.io.Serializable;

public abstract class Message implements Serializable {
    protected TYPES type;
    protected String message;

    public Message(String message, TYPES type){
        this.message = message;
        this.type = type;
    }

    /**
     * Indique s'il y a besoin d'attendre une réponse
     * @return s'il faut attendre une réponse
     */
    public abstract boolean waitingAnswer();

    /**
     * Retourne le type de message
     * @return le type
     */
    public TYPES getType(){
        return type;
    }

    /**
     * Retourne le message contenu dans l'instance courante
     * @return le message
     */
    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return type.getCara() + message;
    }
}
