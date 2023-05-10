package mediatheque.documents.etats;

public class Reserved implements IState {

    private int idMember;

    public Reserved(){
        idMember = -1;
    }

    public Reserved(int idMember){
        this.idMember = idMember;
    }

    /**
     * Réserve un document
     * @param idMember l'id de l'abonné voulant réserver
     * @throws IllegalStateException déjà réservé
     */
    @Override
    public IState reserve(int idMember) {
        throw new IllegalStateException("Déjà reservé");
    }

    /**
     * Emprunte un document
     * @param idMember l'id de l'abonné voulant emprunter
     * @throws IllegalStateException déjà emprunté
     */
    @Override
    public IState borrow(int idMember) {
        if(idMember == this.idMember || this.idMember < 0)
            return new Borrowed();
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
