package test;

import exception.CodeFinException;
import iosocket.Input;
import iosocket.Output;
import org.junit.jupiter.api.Test;
import types.Message;
import types.Question;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testInput {

    @Test
    public void test() throws CodeFinException, IOException, ClassNotFoundException {
        Message m = new Question("Test");
        Socket sck = new Socket();

        Output.write(sck.getOutputStream(), m);

        assertEquals(m, Input.read(sck.getInputStream()));

        Output.print(System.out, m);
    }
}
