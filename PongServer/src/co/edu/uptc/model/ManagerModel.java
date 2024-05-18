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
import co.edu.uptc.pojos.DirectionEnum;
import co.edu.uptc.pojos.Element;
import co.edu.uptc.presenter.ContractServer;
import co.edu.uptc.presenter.ContractServer.IPresenter;

public class ManagerModel implements ContractServer.IModel {
    private ContractServer.IPresenter presenter;
    private ManagerBall managerBall;
    private ServerSocket serverSocket;
    private List<Socket> users;
    private int ballPosition;
    private boolean isPlaying;
    private boolean isReceiving;

    public ManagerModel(){
        managerBall = new ManagerBall();
        try {
            serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        users = new ArrayList<>();
        isReceiving = true;
    }

    @Override
    public void run() {
        threadServerSocket();
    }

    public void threadBall(){
        Thread threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isPlaying) {
                    managerBall.move();
                    updateScreen();
                    try {
                        sendBall();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                    MyUtils.sleep(60);
                }
            }
            
        });
        threadServer.start();
    }

    public void updateScreen(){
        if(!managerBall.isOnScreen()){
            if(managerBall.getHorizontalDirection()==DirectionEnum.LEFT){
                ballPosition--;
            } else {
                ballPosition++;
            }
        }
    }

    public void threadServerSocket(){
        Thread threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isReceiving) {
                    try{
                        receive();
                        MyUtils.sleep(1000);
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
        if(users.size()==1){
            createSendSocket(user, "button");
        }
        ObjectInputStream input = new ObjectInputStream(user.getInputStream());
        Object object = input.readObject();
        if(object instanceof String){
            if(object.equals("play")){
                isReceiving = false;
                isPlaying = true;
                threadBall();
            }
        } else if(object instanceof Element){
            System.out.println("Element");
        }
        user.close();
        input.close();
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
        if(users.size()!=0){
            Socket userBall = users.get(ballPosition);
            createSendSocket(userBall, this.getBall());
        }
    }

    public void createSendSocket(Socket socket, Object order) throws IOException{
        Socket sendedSocket = new Socket(socket.getInetAddress(), 9090);
        ObjectOutputStream output = new ObjectOutputStream(sendedSocket.getOutputStream());
        output.writeObject(order);
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
