package mediatheque.bd;

import mediatheque.Member;
import mediatheque.IDocument;
import mediatheque.documents.DVD;

import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

public class BD {

    private Connection connection;

    public BD(String url) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + url, "postgres", "root");
    }

    /**
     * Récupère un document depuis la base de données
     * @param result le ResultSet
     * @return le document
     * @throws SQLException erreur lors de la lecture
     */
    private IDocument readDocument(ResultSet result) throws SQLException {
        return new DVD(result.getInt("id"), result.getString("title"), result.getBoolean("for_adult"));
    }

    /**
     * Récupère un abonné depuis la base de données
     * @param result le ResultSet
     * @return l'abonné
     * @throws SQLException erreur lors de la lecture
     */
    private Member readAbonne(ResultSet result) throws SQLException {
        return new Member(result.getInt("id"), result.getString("first_name"), result.getString("last_name"));
    }

    /**
     * Récupère tous les documents de la base de données
     * @return une map de tous les documents
     */
    public ConcurrentHashMap<Integer, IDocument> getBibliotheque() {
        ConcurrentHashMap<Integer, IDocument> listDoc = new ConcurrentHashMap<>();
        try {
            Statement req = connection.createStatement();
            ResultSet result = req.executeQuery("SELECT * FROM documents");

            while (result.next()) {
                IDocument doc = readDocument(result);

                int idEmprunteur = result.getInt("id_borrower");
                if (!result.wasNull())
                    doc.borrow(idEmprunteur);

                int idReserveur = result.getInt("id_reserver");
                if (!result.wasNull())
                    doc.reserve(idReserveur);

                listDoc.put(doc.id(), doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listDoc;
    }

    /**
     * Récupère tous les abonnés de la base de données
     * @return une map de tous les abonnés
     */
    public ConcurrentHashMap<Integer, Member> getAbonnes() {
        ConcurrentHashMap<Integer, Member> listAb = new ConcurrentHashMap<>();
        try {
            Statement req = connection.createStatement();
            ResultSet result = req.executeQuery("SELECT * FROM members");

            while (result.next()) {
                Member ab = readAbonne(result);
                listAb.put(ab.id(), ab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAb;
    }
}
