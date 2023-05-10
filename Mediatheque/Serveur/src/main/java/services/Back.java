package services;

import mediatheque.Library;
import exception.CodeFinException;
import exception.TooManyAttempt;
import iosocket.Output;
import types.Erreur;
import types.Fin;
import types.Question;
import types.Reponse;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class Back extends Service {
    Library library;

    public Back(Socket socket, Library library) {
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
//            while(!socket.isClosed()) {
                Output.write(sOut, new Question("Veuillez entrer le numéro du document à retourner : "));
                int idDocument = parseToInt(sIn, sOut);

                try {
                    library.giveDocBack(idDocument);
                    Output.write(sOut, new Reponse("Le document n°" + idDocument + " a bien été retourné !"));
                } catch (IllegalArgumentException ex) {
                    Output.write(sOut, new Erreur(ex.getMessage()));
                }
//            }
        } catch (TooManyAttempt | CodeFinException | IOException e) {
            e.printStackTrace();
            Output.endSocket(socket);
        }
    }
}