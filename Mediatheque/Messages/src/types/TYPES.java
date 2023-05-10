package types;

import java.lang.reflect.InvocationTargetException;

public enum TYPES {
    QUESTION(Question.class, 'Q'),
    REPONSE(Reponse.class, 'R'),
    ERREUR(Erreur.class, 'E'),
    FIN(Fin.class, 'F');

    private Class<? extends Message> classe;
    private char cara;

    TYPES(Class<? extends Message> classe, char cara) {
        this.classe = classe;
        this.cara = cara;
    }

    public char getCara(){return cara;}

    /**
     * Retourne le type de message qui a crée la chaine
     * @param message le chaine
     * @return le type
     */
    public static TYPES getType(String message){
        char messageClasse = message.charAt(0);
        for (TYPES type : TYPES.values()) {
            if(messageClasse == type.getCara())
                return type;
        }
        throw new IllegalArgumentException("Le message n'a pas été crée correctement.");
    }
}
