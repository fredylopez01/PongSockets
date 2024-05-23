package co.edu.uptc.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.Utils.MyUtils;
import co.edu.uptc.pojos.DirectionEnum;
import co.edu.uptc.pojos.Element;
import co.edu.uptc.pojos.SendedPackage;
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
    private Collisions collisions;
    private SendedPackage sendedPackage;

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
        sendedPackage = new SendedPackage();
        // isPlaying = true;
        // collisions = new Collisions(getBall(), getRacketOne(), getRacketTwo(), getNumberScreens());
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
            sendedPackage.setButtonPlay(true);
            sendedPackage.setRacket(getRacketOne());
            user.write(getSendedPackage());
            sendedPackage.setButtonPlay(false);
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
        collisions = new Collisions(getBall(), getRacketOne(), getRacketTwo(), getNumberScreens());
        threadBall();
    }

    public void threadBall(){
        Thread threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                sendedPackage.setRacket(getRacketTwo());
                users.get(users.size()-1).write(getSendedPackage());
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
        if(ballPosition < 0){
            managerBall.opposite();
            sendedPackage.setGameOver(true);
        } else if(ballPosition==getNumberScreens()){
            managerBall.opposite();
            sendedPackage.setGameOver(true);
        } else{
            sendedPackage.setBall(getBall());
        }
        if(users.size()!=0){
            Client userBall = users.get(ballPosition);
            userBall.write(getSendedPackage());
        }
    }
    public void checkCollision(){
        boolean isCrashed = false;
        if (managerBall.getHorizontalDirection() == DirectionEnum.LEFT) {
            isCrashed = collisions.isCollisionOne(getBall(), getRacketOne(), getCurrentScreen());
        } else if(managerBall.getHorizontalDirection() == DirectionEnum.RIGHT){
            isCrashed = collisions.isCollisionTwo(getBall(), getRacketTwo(), getCurrentScreen());
        }
        if (isCrashed) {
           managerBall.opposite();
        }
    }
    public void upRacket(String userIp){
        if(userIp.equals(users.get(0).getIpString())){
            racketOne.up();
            sendedPackage.setRacket(getRacketOne());
            users.get(0).write(getSendedPackage());
        } else {
            racketTwo.up();
            sendedPackage.setRacket(getRacketTwo());
            users.get(users.size()-1).write(getSendedPackage());
        }
    }
    public void downRacket(String userIp){
        if(userIp.equals(users.get(0).getIpString())){
            racketOne.down();
            sendedPackage.setRacket(getRacketOne());
            users.get(0).write(getSendedPackage());
        } else {
            racketTwo.down();
            sendedPackage.setRacket(getRacketTwo());
            users.get(users.size()-1).write(getSendedPackage());
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
    public SendedPackage getSendedPackage(){
        return sendedPackage;
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
