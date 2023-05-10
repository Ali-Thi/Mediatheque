package iosocket;

import java.io.*;

import code.Code;
import exception.CodeFinException;
import types.Message;
import types.TYPES;

public class Input {
    private Input(){}

    /**
     * Lis un flux d'entrée
     * @param sIn flux d'entrée
     * @return le message lu
     * @throws IOException levé par les méthodes de lecture/écriture
     * @throws CodeFinException un message de fin a été reçu
     */
    public static Message read(InputStream sIn) throws IOException, CodeFinException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(sIn);
        Object o = ois.readObject();
        if(o instanceof Message m) {
            if (TYPES.getType(m.toString()) == TYPES.FIN) {
                throw new CodeFinException();
            } else {
                return m;
            }
        }
        throw new ClassNotFoundException();
    }
}
