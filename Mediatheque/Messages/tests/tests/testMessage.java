package tests;

import org.junit.jupiter.api.Test;
import types.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class testMessage {

    @Test
    public void testQuestion(){
        Message message = new Question("Test");
        assertEquals(message.toString(), "QTest");
        assertEquals(TYPES.getType(message.toString()), TYPES.QUESTION);
        assertTrue(message.waitingAnswer());
    }

    @Test
    public void testReponse(){
        Message message = new Reponse("Test");
        assertEquals(message.toString(), "RTest");
        assertEquals(TYPES.getType(message.toString()), TYPES.REPONSE);
        assertFalse(message.waitingAnswer());
    }

    @Test
    public void testErreur(){
        Message message = new Erreur("Test");
        assertEquals(message.toString(), "ETest");
        assertEquals(TYPES.getType(message.toString()), TYPES.ERREUR);
        assertFalse(message.waitingAnswer());
    }

    @Test
    public void testFin(){
        Message message = new Fin("Test");
        assertEquals(message.toString(), "FTest");
        assertEquals(TYPES.getType(message.toString()), TYPES.FIN);
        assertFalse(message.waitingAnswer());
    }
}
