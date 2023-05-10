package code;

public class Code {
    private static char charToReplace = '\n';
    private static char replaceBy = '#';

    private Code() {}

    /**
     * Code une chaine
     * @param str la chaine à coder
     * @return la chaine coder
     */
    public static String Codage(String str) {
        if(str == null)
            throw new IllegalArgumentException("Argument null.");
        return str.replace(charToReplace, replaceBy);
    }

    /**
     * Décode une chaine
     * @param str la chaine à décoder
     * @return la chaine décodé
     */
    public static String Decodage(String str) {
        if(str == null)
            throw new IllegalArgumentException("Argument null.");
        return str.replace(replaceBy, charToReplace);
    }
}
