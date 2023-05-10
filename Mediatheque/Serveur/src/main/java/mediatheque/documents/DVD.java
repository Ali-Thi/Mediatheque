package mediatheque.documents;

import mediatheque.IDocument;
import mediatheque.documents.etats.Available;
import mediatheque.documents.etats.IState;

public class DVD implements IDocument {
    private final int id;
    private final String title;
    private final boolean forAdult;
    private IState etat;

    public DVD(int id, String title, boolean forAdult){
        this.id = id;
        this.title = title;
        this.forAdult = forAdult;
        this.etat = new Available();
    }

    /**
     * Retourne le numéro du document
     * @return le numéro
     */
    @Override
    public int id() {
        return id;
    }

    /**
     * Retourne le titre du document
     * @return le titre
     */
    @Override
    public String title() {
        return title;
    }

    /**
     * Retourne un boolean indiquant si le document est réservé aux adultes
     *
     * @return true si le document est réservé aux adultes, false sinon
     */
    @Override
    public boolean forAdult() {
        return forAdult;
    }

    /**
     * Réserve un document
     * @param idMember l'id de l'abonné voulant réserver
     * @throws IllegalStateException déjà réservé
     */
    @Override
    public synchronized void reserve(int idMember) {
        etat = etat.reserve(idMember);
    }

    /**
     * Emprunte un document
     * @param idMember l'id de l'abonné voulant emprunter
     * @throws IllegalStateException déjà emprunté
     */
    @Override
    public synchronized void borrow(int idMember) {
        etat = etat.borrow(idMember);
    }

    /**
     * Retourne un document
     */
    @Override
    public synchronized void giveDocBack() {
        etat = etat.giveDocBack();
    }

    /**
     * Renvoie si le document est disponible
     * @return true si le document est disponible, false sinon
     */
    @Override
    public synchronized boolean isAvailable() {
        return etat.isAvailable();
    }


    @Override
    public boolean equals(Object o) {
        if(o instanceof DVD doc){
            return doc.id() == id && doc.title() == title && doc.forAdult() == forAdult;
        }
        return false;
    }
}
