package serveur;

import mediatheque.Library;
import services.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private final int port;
    private final Class<? extends Service> service;
    private final Library library;

    public Server(int port, Class<? extends Service> service, Library library) {
        this.service = service;
        this.port = port;
        this.library = library;
    }

    @Override
    public void run() {
        try {
            ServerSocket monServeur = new ServerSocket(this.port);
            Socket socket = monServeur.accept();
            (new Thread((Runnable) this.service.getConstructor(Socket.class, Library.class).newInstance(socket, library))).start();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IOException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
