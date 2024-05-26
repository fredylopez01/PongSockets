package co.edu.uptc.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket user;
    private ManagerModel game;
    private ObjectOutputStream output;
    private ObjectInputStream inputStream;

    public Client(Socket user, ManagerModel game) {
        this.user = user;
        this.game = game;
        createObjectSteam();
        listenThread();
    }
    private void createObjectSteam(){
        try{
            this.output = new ObjectOutputStream(user.getOutputStream());
            this.inputStream = new ObjectInputStream(user.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void write(Object order){
        try {
            output.reset();
            output.writeObject(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenThread(){
        Thread listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!user.isClosed()) {
                    listen();
                }
            }
        });
        listenThread.start();
    }

    public void listen(){
        try {
            String order = inputStream.readObject().toString();
            switch (order) {
                case "play" -> game.play();
                case "upRacket" -> game.upRacket(this);
                case "downRacket" -> game.downRacket(this);
                case "endGame" -> disconect();
                default -> System.out.println(order);
            }
        } catch (SecurityException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void disconect(){
        try {
            user.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Socket getUser() {
        return user;
    }
    public void setUser(Socket user) {
        this.user = user;
    }
    public String getIpString(){
        return user.getInetAddress().toString();
    }
}
