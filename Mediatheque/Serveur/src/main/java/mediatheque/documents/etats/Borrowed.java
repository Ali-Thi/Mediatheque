package mediatheque.documents.etats;

public class Borrowed implements IState {
    /**
     * Réserve un document
     * @param idAb l'id de l'abonné voulant réserver
     * @return le nouvel état dans lequel le document est
     * @throws IllegalStateException déjà réservé
     */
    @Override
    public IState reserve(int idAb) {
        throw new IllegalStateException("Déjà reservé");
    }

    /**
     * Emprunte un document
     * @param idAb l'id de l'abonné voulant emprunter
     * @return le nouvel état dans lequel le document est
     * @throws IllegalStateException déjà emprunté
     */
    @Override
    public IState borrow(int idAb) {
        throw new IllegalStateException("Déjà emprunté");
    }

    /**
     * Retourne un document
     * @return le nouvel état dans lequel le document est
     */
    @Override
    public IState giveDocBack() {
        return new Available();
    }

    /**
     * Renvoie si le document est disponible
     * @return true si le document est disponible, false sinon
     */
    @Override
    public boolean isAvailable() {
        return false;
    }
}
