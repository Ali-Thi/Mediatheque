package mediatheque;

public interface IDocument {

    /**
     * Retourne le numéro du document
     * @return le numéro
     */
    int id();

    /**
     * Retourne le titre du document
     * @return le titre
     */
    String title();

    /**
     * Retourne un boolean indiquant si le document est réservé aux adultes
     * @return true si le document est réservé aux adultes, false sinon
     */
    boolean forAdult();
    /**
     * Réserve un document
     * @param idAb l'id de l'abonné voulant réserver
     * @throws IllegalStateException déjà réservé
     */
    void reserve(int idAb);

    /**
     * Emprunte un document
     * @param idAb l'id de l'abonné voulant emprunter
     * @throws IllegalStateException déjà emprunté
     */
    void borrow(int idAb);

    /**
     * Retourne un document
     */
    void giveDocBack();

    /**
     * Renvoie si le document est disponible
     * @return true si le document est disponible, false sinon
     */
    boolean isAvailable();
}
