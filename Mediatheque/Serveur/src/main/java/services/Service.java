package services;

import exception.CodeFinException;
import exception.TooManyAttempt;
import mediatheque.Library;

import java.io.*;

import iosocket.*;
import types.Erreur;

import javax.swing.text.html.Option;
import java.net.Socket;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class Service implements Runnable {
    protected Socket socket;
    protected Library library;

    public Service(Socket socket, Library library) {
        this.socket = socket;
        this.library = Optional.ofNullable(library).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException();
            }
        });
    }

    /**
     * Boucle tant qu'un entier n'a pas été envoyé via la socket ou que le nombre de tentative max nbAttemptMax n'a pas été atteint
     * @param sIn le flux de lecture de la socket
     * @param sOut le flux d'écriture de la socket
     * @return l'entier
     * @throws TooManyAttempt le nombre de tentatives max a été atteint
     * @throws IOException Erreur de la lecture ou de l'écriture dans la socket
     * @throws CodeFinException Message de fin envoyé via la socket
     */
    protected int parseToInt(InputStream sIn, OutputStream sOut) throws TooManyAttempt, IOException, CodeFinException {
        for(int i = 0 ; i < 5 ; ++i){
            try{
                return Integer.parseInt(Input.read(sIn).getMessage());
            } catch (NumberFormatException | ClassNotFoundException n) {
                Output.write(sOut, new Erreur("Veuillez entrer un entier : "));
            }
        }
        throw new TooManyAttempt();
    }
}