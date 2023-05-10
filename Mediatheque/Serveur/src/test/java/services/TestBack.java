package services;

import exception.CodeFinException;
import iosocket.Input;
import iosocket.Output;
import mediatheque.IDocument;
import mediatheque.Library;
import mediatheque.Member;
import mediatheque.documents.DVD;
import org.junit.Test;
import serveur.Server;
import types.Message;
import types.Question;
import types.TYPES;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class TestBack {
    private Socket sckCli;
    private Library lib;

    public TestBack() throws IOException {
        Member m = new Member(1, "Tony", "Montana");
        IDocument doc = new DVD(1, "title", false);

        Map<Integer, Member> listMember = new HashMap<>();
        Map<Integer, IDocument> listDocs = new HashMap<>();

        listMember.put(m.id(), m);
        listDocs.put(doc.id(), doc);

        lib = new Library(listDocs, listMember);

        final int PORT = 5000;
        new Thread(new Server(PORT, Back.class, lib)).start();

        sckCli = new Socket("localhost", PORT);
    }

    @Test
    public void test() throws IOException, CodeFinException, ClassNotFoundException {
        lib.borrow(1, 1);
        Input.read(sckCli.getInputStream());
        Output.write(sckCli.getOutputStream(), new Question("1"));
        Message retour = Input.read(sckCli.getInputStream());
        assertEquals(TYPES.REPONSE, retour.getType());
        Output.endSocket(sckCli);
    }
}
