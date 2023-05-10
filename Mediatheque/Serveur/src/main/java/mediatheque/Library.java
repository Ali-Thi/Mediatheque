package mediatheque;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Library {

    private static class RetourDoc extends TimerTask {
        private int idDoc;
        private Library media;

        public RetourDoc(Library media, int idDoc) {
            this.media = media;
            this.idDoc = idDoc;
        }

        @Override
        public void run() {
            this.media.giveDocBack(idDoc);
        }
    }
    private final ConcurrentHashMap<Integer, IDocument> mapDocument = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Member> mapMember = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Timer> mapTimer = new ConcurrentHashMap<>();

    public Library(Map<Integer, IDocument> mapDoc, Map<Integer, Member> mapAb){
        mapDocument.putAll(mapDoc);
        mapMember.putAll(mapAb);
    }

    private boolean verifIdDocument(int idDocument){
        return mapDocument.containsKey(idDocument);
    }

    private boolean verifIdMember(int idMember){
        return mapMember.containsKey(idMember);
    }

    /**
     * Retourne une liste de tous les documents disponibles sous la forme d'une liste de tableau de String à 2 élements [id du document, titre du document]
     * @return la liste
     */
    public List<String[]> getDocAvailable(){
        List<String[]> availables = new LinkedList<>();
        for (IDocument doc : mapDocument.values()) {
            if (doc.isAvailable()) {
                availables.add(new String[]{
                        String.valueOf(doc.id()),
                        doc.title()
                });
            }
        }
        return availables;
    }

    /**
     * Permet à un abonné d'emprunter un document
     * @param idDocument le numéro du document
     * @param idMember le numéro de l'abonné
     */
    public void borrow(int idDocument, int idMember){
        if(!verifIdDocument(idDocument)) throw new IllegalArgumentException("Numéro de document inconnu.");
        if(!verifIdMember(idMember)) throw new IllegalArgumentException("Numéro de d'abonné inconnu.");
        mapDocument.get(idDocument).borrow(mapMember.get(idMember).id());
    }

    /**
     * Permet à un abonné de réserver un document
     * @param idDocument le numéro du document
     * @param idMember le numéro de l'abonné
     */
    public void reserve(int idDocument, int idMember){
        if(!verifIdDocument(idDocument)) throw new IllegalArgumentException("Numéro de document inconnu.");
        if(!verifIdMember(idMember)) throw new IllegalArgumentException("Numéro d'abonné inconnu.");
        mapDocument.get(idDocument).reserve(mapMember.get(idMember).id());

        int seconde = 1000;
        int minute = 60 * seconde;
        int heure = 60 * minute;
        Timer timer = new Timer();
        timer.schedule(new RetourDoc(this, idDocument), 2*heure);
        mapTimer.put(idDocument, timer);
    }

    /**
     * Permet de retourner un document
     */
    public void giveDocBack(int idDocument){
        if(!verifIdDocument(idDocument)) throw new IllegalArgumentException("Numéro de document inconnu.");
        if(mapTimer.containsKey(idDocument)) mapTimer.get(idDocument).cancel();
        mapDocument.get(idDocument).giveDocBack();
    }
}
