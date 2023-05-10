package services;

import mediatheque.Library;
import exception.CodeFinException;
import exception.TooManyAttempt;
import iosocket.Output;
import types.Erreur;
import types.Question;
import types.Reponse;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class Reservation extends Service {

    public Reservation(Socket socket, Library library) {
        super(socket, library);
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        if (socket == null) {
            throw new RuntimeException("socket non initialisé !");
        }
        try {
            InputStream sIn = socket.getInputStream();
            OutputStream sOut = socket.getOutputStream();
            while(!socket.isClosed()) {
                StringBuilder str = new StringBuilder();
                for (String[] elem : library.getDocAvailable()) {
                    str.append(elem[0]).append(" - ").append(elem[1]).append("\n");
                }
                Output.write(sOut, new Question(str + "Veuillez entrer le numéro du document à réserver : "));
                int idDocument = parseToInt(sIn, sOut);
                Output.write(sOut, new Question("Veuillez entrer le numéro de l'abonné souhaitant réserver : "));
                int idAbonne = parseToInt(sIn, sOut);

                try {
                    library.reserve(idDocument, idAbonne);
                    Output.write(sOut, new Reponse("Le document n°" + idDocument + " a bien été reservé !"));
                } catch (IllegalArgumentException | IllegalStateException ex) {
                    Output.write(sOut, new Erreur(ex.getMessage()));
                }
            }
        } catch (TooManyAttempt | CodeFinException | IOException e) {
            e.printStackTrace();
            Output.endSocket(socket);
        }
    }
}
