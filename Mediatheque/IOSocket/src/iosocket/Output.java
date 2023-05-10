package iosocket;

import types.*;

import java.io.*;
import java.net.Socket;

public class Output {
    private Output() {}

    /**
     * Écrit sur un flux sortant
     * @param sOut le flux
     * @param message le message à écrire
     */
    public static void write(OutputStream sOut, Message message) throws IOException {
        if(sOut == null || message == null)
            throw new IllegalArgumentException("Argument null.");
        ObjectOutputStream oos = new ObjectOutputStream(sOut);
        oos.writeObject(message);
    }

    /**
     * Écrit sur un flux sortant le contenu d'un message
     * @param sOut le flux
     * @param message le message
     */
    public static void print(PrintStream sOut, Message message){
        if(sOut == null || message == null)
            throw new IllegalArgumentException("Argument null.");
        sOut.print(message.toString().substring(1));
    }

    /**
     * Met fin à une communication
     * @param socket la socket à fermer
     */
    public static void endSocket(Socket socket){
        if(socket == null)
            throw new IllegalArgumentException("Argument null.");
        try {
            write(socket.getOutputStream(), new Fin("Fin de l'échange."));
            socket.close();
        } catch (IOException ignored){}
    }
}
