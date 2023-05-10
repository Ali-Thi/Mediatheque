import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import exception.CodeFinException;
import iosocket.Input;
import iosocket.Output;
import types.Erreur;
import types.Message;
import types.Reponse;
import types.TYPES;

public class Main {
    private static ServerSocket servSocket;
    private static Socket socketVersCli;
    private static Socket socketVersServ;

    /**
     * Client permettant la connexion à un service du serveur.
     * Pour cela le premier message reçu doit être la chaine de connexion respectant le pattern suivant : BTTP:<HÔTE>:<PORT DU SERVICE>.
     * Ensuite le client tente une connexion sur le <PORT DE SERVICE> et transmets les messages d'un acteur à l'autre (client au service/service au client)
     * @param args les arguments du main, le premier argument doit être le port sur lequel le client attend les questions
     * @throws IOException Erreur de lecture de la socket
     * @throws CodeFinException Si un message de fin de communication est reçu via l'une des socket
     */
    public static void main(String[] args) throws IOException, CodeFinException {
        servSocket = new ServerSocket(Integer.parseInt(args[0]));
        socketVersCli = servSocket.accept();
        try {
            BufferedReader sInCli = new BufferedReader(new InputStreamReader(socketVersCli.getInputStream()));
            PrintWriter sOutCli = new PrintWriter(socketVersCli.getOutputStream(), true);
            Message message;

            //Attente de la chaine de connexion
            message = Input.read(sInCli);
            if(message.getType() != TYPES.QUESTION) {
                Output.write(sOutCli, new Erreur("Erreur interne de communication, format attendu non respecté."));
            } else {
                String[] commandes = message.getMessage().split(":");
                String host = commandes[1];
                String port = commandes[2];
                if (commandes.length == 3 && commandes[0].equals("BTTP")) {
                    //Connexion au service
                    socketVersServ = new Socket(host, Integer.parseInt(port));
                    BufferedReader sInServ = new BufferedReader(new InputStreamReader(socketVersServ.getInputStream()));
                    PrintWriter sOutServ = new PrintWriter(socketVersServ.getOutputStream(), true);

                    Output.write(sOutCli, new Reponse("Connecté PORT=" + Integer.parseInt(args[0]) + ", HOST=" + host));

                    //Transmission des messages d'un acteur à l'autre
                    while (!socketVersCli.isClosed() || !socketVersServ.isClosed()) {
                        Output.write(sOutCli, Input.read(sInServ));
                        Output.write(sOutServ, Input.read(sInCli));
                    }
                }
            }
            Output.endSocket(socketVersCli);
        } catch (CodeFinException e) {
            Output.endSocket(socketVersCli);
            Output.endSocket(socketVersServ);
            throw e;
        }
    }
}