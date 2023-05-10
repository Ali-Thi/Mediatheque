package mediatheque.documents.etats;

public class Available implements IState {
    /**
     * Réserve un document
     * @param idAb l'id de l'abonné voulant réserver
     * @return le nouvel état dans lequel le document est
     * @throws IllegalStateException déjà réservé
     */
    @Override
    public IState reserve(int idAb) {
        return new Reserved(idAb);
    }

    /**
     * Emprunte un document
     * @param idAb l'id de l'abonné voulant emprunter
     * @return le nouvel état dans lequel le document est
     * @throws IllegalStateException déjà emprunté
     */
    @Override
    public IState borrow(int idAb) {
        return new Borrowed();
    }

    /**
     * Retourne un document
     * @return le nouvel état dans lequel le document est
     */
    @Override
    public IState giveDocBack() {
        return this;
    }

    /**
     * Renvoie si le document est disponible
     * @return true si le document est disponible, false sinon
     */
    @Override
    public boolean isAvailable() {
        return true;
    }
}
