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

    public void write(Object order){
        try {
            output.writeObject(order);
            output.reset();
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
            System.out.println(order+ "--" + getIpString());
            switch (order) {
                case "play" -> game.play();
                case "upRacket" -> game.upRacket(this.getIpString());
                case "downRacket" -> game.downRacket(this.getIpString());
                default -> System.out.println(order);
            }
        } catch (SecurityException | IOException | ClassNotFoundException e) {
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
