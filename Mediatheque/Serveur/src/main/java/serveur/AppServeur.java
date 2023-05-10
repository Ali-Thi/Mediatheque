package serveur;

import mediatheque.Library;
import services.Borrow;
import services.Reservation;
import services.Back;

import mediatheque.bd.BD;

import java.sql.SQLException;

public class AppServeur {
    public static void main(String[] args) {
        try {
            String pathToBd = "Library";
            BD bd = new BD(pathToBd);
            Library library = new Library(bd.getBibliotheque(), bd.getAbonnes());

            new Thread(new Server(3000, Reservation.class, library)).start();
            new Thread(new Server(4000, Borrow.class, library)).start();
            new Thread(new Server(5000, Back.class, library)).start();
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}