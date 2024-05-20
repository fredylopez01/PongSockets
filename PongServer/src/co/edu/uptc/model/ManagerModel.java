package co.edu.uptc.model;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.Utils.MyUtils;
import co.edu.uptc.pojos.DirectionEnum;
import co.edu.uptc.pojos.Element;
import co.edu.uptc.presenter.ContractServer;
import co.edu.uptc.presenter.ContractServer.IPresenter;

public class ManagerModel implements ContractServer.IModel {
    private ContractServer.IPresenter presenter;
    private ManagerBall managerBall;
    private ManagerRacket playerRacketOne;
    private ManagerRacket playerRacketTwo;
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
        playerRacketOne = new ManagerRacket(1);
        playerRacketTwo = new ManagerRacket(2);
        // isPlaying = true;
        // threadBall();
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
                    checkCollision();
                    updateScreen();
                    try {
                        sendBall();
                        createSendSocket(users.get(0), getRacketOne());
                        createSendSocket(users.get(users.size()-1), getRacketTwo());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                    MyUtils.sleep(50);
                }
            }
            
        });
        threadServer.start();
    }

    public void updateScreen(){
        if(!managerBall.isOnScreen()){
            if(managerBall.getHorizontalDirection()==DirectionEnum.LEFT){
                ballPosition--;
                managerBall.setIsOnScreen(true);
            } else {
                ballPosition++;
                managerBall.setIsOnScreen(true);
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
                        MyUtils.sleep(50);
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
            switch (object.toString()) {
                case "conectar":
                    if(users.size()==1){
                        createSendSocket(user, "button");
                    }
                    break;
                case "play":
                    isPlaying = true;
                    threadBall();
                    break;
                case "upRacket":
                    if(user.getInetAddress().equals(users.get(0).getInetAddress())){
                        upRacketOne();
                    } else {
                        upRacketTwo();
                    }
                    break;
                case "downRacket":
                    if(user.getInetAddress().equals(users.get(0).getInetAddress())){
                        downRacketOne();
                    } else {
                        downRacketTwo();
                    }
                    break;
                default:
                    break;
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
        if(users.size()==1){
            ballPosition = 0;
            sendedObject = this.getBall();
        } else if(ballPosition < 0){
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
            createSendSocket(userBall, sendedObject);
        }
    }
    public void checkCollision(){
        Rectangle rectangle1 = new Rectangle(
            getBall().getX(), 
            getBall().getY(), 
            getBall().getWidth(), 
            getBall().getHeight()
        );
        Rectangle line1 = new Rectangle(
            getRacketOne().getX(), 
            getRacketOne().getY(), 
            getRacketOne().getWidth(), 
            getRacketOne().getHeight()
        );
        Rectangle line2= new Rectangle(
            getRacketTwo().getX(), 
            getRacketTwo().getY(), 
            getRacketTwo().getWidth(), 
            getRacketTwo().getHeight()
        );
        if(managerBall.getHorizontalDirection()==DirectionEnum.LEFT 
            && rectangle1.intersects(line1)){
            managerBall.setHorizontalDirection(DirectionEnum.RIGHT);
        } else if( managerBall.getHorizontalDirection()==DirectionEnum.RIGHT
                    && rectangle1.intersects(line2)){
            managerBall.setHorizontalDirection(DirectionEnum.LEFT);
        }
    }

    public void createSendSocket(Socket socket, Object order) {
        try{
            Socket sendedSocket = new Socket(socket.getInetAddress(), 9090);
            ObjectOutputStream output = new ObjectOutputStream(sendedSocket.getOutputStream());
            output.writeObject(order);
            output.close();
            sendedSocket.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void upRacketOne(){
        playerRacketOne.up();
    }
    public void downRacketOne(){
        playerRacketOne.down();
    }
    public void upRacketTwo(){
        playerRacketTwo.up();
    }
    public void downRacketTwo(){
        playerRacketTwo.down();
    }
    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
    }
    @Override
    public Element getBall(){
        return managerBall.getElement();
    }
    @Override
    public Element getRacketOne() {
        return playerRacketOne.getRacket();
    }
    @Override
    public Element getRacketTwo() {
        return playerRacketTwo.getRacket();
    }
    public ContractServer.IPresenter getPresenter() {
        return presenter;
    }
    
}
