package mediatheque.documents.etats;

public interface IState {
    /**
     * Réserve un document
     * @param idAb l'id de l'abonné voulant réserver
     * @return le nouvel état dans lequel le document est
     * @throws IllegalStateException déjà réservé
     */
    IState reserve(int idAb);

    /**
     * Emprunte un document
     * @param idAb l'id de l'abonné voulant emprunter
     * @return le nouvel état dans lequel le document est
     * @throws IllegalStateException déjà emprunté
     */
    IState borrow(int idAb);

    /**
     * Retourne un document
     * @return le nouvel état dans lequel le document est
     */
    IState giveDocBack();

    /**
     * Renvoie si le document est disponible
     * @return true si le document est disponible, false sinon
     */
    boolean isAvailable();
}
