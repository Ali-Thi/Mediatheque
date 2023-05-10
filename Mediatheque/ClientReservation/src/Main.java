import exception.CodeFinException;
import iosocket.Input;
import iosocket.Output;
import types.Message;
import types.Question;
import types.Reponse;
import types.TYPES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int PORT_BTTP = 1;
        final int PORT_SERVICE = 3000;
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

                    if(message.getType() == TYPES.ERREUR)
                        Output.print(System.err, message);
                    else
                        Output.print(System.out, message);

                    if(message.waitingAnswer()) {
                        System.out.println("NOTIFY");
                        Output.write(sOut, new Reponse(sc.nextLine()));
                    }
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
