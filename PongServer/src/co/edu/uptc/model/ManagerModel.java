package co.edu.uptc.model;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.Utils.MyUtils;
import co.edu.uptc.Utils.Values;
import co.edu.uptc.pojos.Element;
import co.edu.uptc.presenter.ContractServer;
import co.edu.uptc.presenter.ContractServer.IPresenter;

public class ManagerModel implements ContractServer.IModel {
    private ContractServer.IPresenter presenter;
    private ManagerBall managerBall;
    private ServerSocket serverSocket;
    private List<Socket> users;
    private int ballPosition;

    public ManagerModel(){
        managerBall = new ManagerBall();
        try {
            serverSocket = new ServerSocket(Values.serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        users = new ArrayList<>();
    }

    @Override
    public void run() {
        threadBall();
        threadServerSocket();
    }

    public void threadBall(){
        Thread threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    managerBall.move();
                    MyUtils.sleep(100);
                }
            }
            
        });
        threadServer.start();
    }

    public void threadServerSocket(){
        Thread threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try{
                        receive();
                        System.out.println("yes");
                        
                        sendBall();
                        MyUtils.sleep(100);
                    } catch(ClassNotFoundException | IOException e){
                        e.printStackTrace();
                    }
                }
            }
            
        });
        threadServer.start();
    }

    public void receive() throws ClassNotFoundException, IOException{
        Socket user = serverSocket.accept();
        addUser(user);
        ObjectInputStream input = new ObjectInputStream(user.getInputStream());
        Object object = input.readObject();
        if(object instanceof String){
            System.out.println(object);
        } else if(object instanceof Element){
            System.out.println("Element");
        }
    }

    public void addUser(Socket user){
        boolean is = false;
        for (Socket socket : users) {
            if(socket.getInetAddress().equals(user.getInetAddress())){
                is=true;
            }
        }
        if(!is){
            users.add(user);
        }
    }

    public void sendBall() throws IOException{
        Object sendedObject = null;
        if(ballPosition < 0){
            ballPosition = 0;
            sendedObject = "Perdio";
        } else if(ballPosition==users.size()){
            ballPosition = users.size()-1;
            sendedObject = "Perdio";
        } else{
            sendedObject = this.getBall();
        }
        Socket userBall = users.get(ballPosition);
        Socket sendedSocket = new Socket(userBall.getInetAddress(), 9090);
        ObjectOutputStream output = new ObjectOutputStream(sendedSocket.getOutputStream());
        output.writeObject(sendedObject);
        output.close();
        sendedSocket.close();
    }

    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
    }

    @Override
    public Element getBall(){
        return managerBall.getElement();
    }

    public ContractServer.IPresenter getPresenter() {
        return presenter;
    }
    
}
