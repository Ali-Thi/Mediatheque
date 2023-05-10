import exception.CodeFinException;
import iosocket.Input;
import iosocket.Output;
import types.Message;
import types.Question;
import types.Reponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int PORT_BTTP = 1;
        final int PORT_SERVICE = 5000;
        final String HOST = "localhost";
        Socket socket;
        try {
            socket = new Socket(HOST, PORT_BTTP);
            BufferedReader sIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter sOut = new PrintWriter(socket.getOutputStream(), true);
            Output.write(sOut, new Question("BTTP:" + HOST + ":" + PORT_SERVICE));

            Scanner sc = new Scanner(System.in);

            try {
                while (!socket.isClosed()) {
                    Message message = Input.read(sIn);
                    Output.print(System.out, message);
//                    System.out.print(message);
                    if(message.waitingAnswer())
                        Output.write(sOut, new Reponse(sc.nextLine()));
                    else
                        System.out.println();
                }
            } catch (CodeFinException e) {
                Output.endSocket(socket);
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
}
