package services;

import mediatheque.Library;
import iosocket.*;
import exception.*;
import types.*;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class Borrow extends Service {
    Library library;

    public Borrow(Socket socket, Library library) {
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
                Output.write(sOut, new Question("Veuillez entrer le numéro de l'abonné souhaitant emprunter : "));
                int idAbonne = parseToInt(sIn, sOut);
                Output.write(sOut, new Question("Veuillez entrer le numéro du document à emprunter : "));
                int idDocument = parseToInt(sIn, sOut);

                try {
                    library.borrow(idDocument, idAbonne);
                    Output.write(sOut, new Reponse("Le document n°" + idDocument + " a bien été emprunté !"));
                } catch (IllegalArgumentException ex) {
                    Output.write(sOut, new Erreur(ex.getMessage()));
                }
            }
        } catch (TooManyAttempt | CodeFinException | IOException e) {
            e.printStackTrace();
            Output.endSocket(socket);
        }
    }
}