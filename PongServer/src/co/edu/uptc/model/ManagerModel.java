package co.edu.uptc.model;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.ServerSocket;
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
    private ManagerRacket racketOne;
    private ManagerRacket racketTwo;
    private ServerSocket serverSocket;
    private List<Client> users;
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
        racketOne = new ManagerRacket(1);
        racketTwo = new ManagerRacket(2);
        // isPlaying = true;
        // threadBall();
    }

    @Override
    public void run() {
        threadServerSocket();
    }

    public void threadServerSocket(){
        Thread threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isReceiving) {
                    try{
                        acceptClient();
                    } catch(ClassNotFoundException | IOException e){
                        e.printStackTrace();
                    }
                }
            }
            
        });
        threadServer.start();
    }

    public void acceptClient() throws ClassNotFoundException, IOException{
        Client user = new Client(serverSocket.accept(), this);
        users.add(user);
        // addUser(user);
        if(users.size()==1){
            user.write( new String("button"));
            user.write(getRacketOne());
        }
    }

    public void addUser(Client user){
        boolean is = false;
        for (Client client : users) {
            if(client.getIpString().equals(user.getIpString())){
                is=true;
            }
        }
        if(!is){
            users.add(user);
        }
    }

    public void play(){
        isReceiving = false;
        isPlaying = true;
        threadBall();
    }

    public void threadBall(){
        Thread threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                users.get(users.size()-1).write(getRacketTwo());
                while (isPlaying) {
                    managerBall.move();
                    checkCollision();
                    updateScreen();
                    try {
                        sendBall();
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

    public void sendBall() throws IOException{
        Object sendedObject = null;
        if(ballPosition < 0){
            ballPosition = users.size()-1;
            sendedObject = new String("Perdio");
        } else if(ballPosition==users.size()){
            ballPosition = 0;
            sendedObject = new String("Perdio");
        } else{
            sendedObject = this.getBall();
        }
        if(users.size()!=0){
            Client userBall = users.get(ballPosition);
            userBall.write(sendedObject);
        }
    }
    public void checkCollision(){
        Rectangle ballRect = new Rectangle(
            getBall().getX()*getCurrentScreen(),
            getBall().getY(),
            getBall().getWidth(),
            getBall().getHeight()
        );
        Rectangle racketRect = null;
        if (managerBall.getHorizontalDirection() == DirectionEnum.LEFT) {
            racketRect = new Rectangle(
                getRacketOne().getX(),
                getRacketOne().getY(),
                getRacketOne().getWidth(),
                getRacketOne().getHeight()
            );
        } else if (managerBall.getHorizontalDirection() == DirectionEnum.RIGHT) {
            racketRect = new Rectangle(
                getRacketTwo().getX()*users.size(),
                getRacketTwo().getY(),
                getRacketTwo().getWidth(),
                getRacketTwo().getHeight()
            );
        }
        if (racketRect != null && ballRect.intersects(racketRect)) {
           managerBall.opposite();
        }
    }
    public void upRacket(String userIp){
        if(userIp.equals(users.get(0).getIpString())){
            racketOne.up();
            users.get(0).write(getRacketOne());
        } else {
            racketTwo.up();
            users.get(users.size()-1).write(getRacketTwo());
        }
    }
    public void downRacket(String userIp){
        if(userIp.equals(users.get(0).getIpString())){
            racketOne.down();
            users.get(0).write(getRacketOne());
        } else {
            racketTwo.down();
            users.get(users.size()-1).write(getRacketTwo());
        }
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
        return racketOne.getRacket();
    }
    @Override
    public Element getRacketTwo() {
        return racketTwo.getRacket();
    }
    @Override
    public int getCurrentScreen() {
        return ballPosition+1;
    }
    @Override
    public int getNumberScreens() {
        if(users.size() == 0){
            return 1;
        }
        return users.size();
    }
    public ContractServer.IPresenter getPresenter() {
        return presenter;
    }
    public boolean isPlaying() {
        return isPlaying;
    }
    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    public boolean isReceiving() {
        return isReceiving;
    }
    public void setReceiving(boolean isReceiving) {
        this.isReceiving = isReceiving;
    }
    
}
