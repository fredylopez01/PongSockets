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
        if(users.size()==1){
            sendedPackage.setButtonPlay(true);
            sendedPackage.setRacketOne(getRacketOne());
            sendedPackage.setBallPosition(ballPosition);
            sendedPackage.setFirstPlayer(0);
        }
        sendedPackage.setMyPosition(users.size()-1);
        user.write(sendedPackage);
        sendedPackage.setButtonPlay(false);
        sendedPackage.setMyPosition(-1);
    }

    public void play(){
        isReceiving = false;
        isPlaying = true;
        collisions = new Collisions(getBall(), getRacketOne(), getRacketTwo(), getNumberScreens());
        sendedPackage.setLastPlayer(users.size()-1);
        sendedPackage.setRacketTwo(getRacketTwo());
        users.get(users.size()-1).write(sendedPackage);
        threadBall();
    }

    public void threadBall(){
        Thread threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isPlaying && !sendedPackage.isEndGame()) {
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
            ballPosition = getNumberScreens()-1;
            endGame(0, sendedPackage.getLastPlayer());
        } else if(ballPosition==getNumberScreens()){
            ballPosition = 0;
            endGame(sendedPackage.getLastPlayer(), 0);
        } else{
            sendedPackage.setBall(getBall());
            Client userBall = users.get(ballPosition);
            sendedPackage.setBallPosition(ballPosition);
            userBall.write(sendedPackage);
        }
    }
    public void endGame(int loser, int winner){
        sendedPackage.setEndGame(true);
        sendedPackage.setLoser(loser);
        sendedPackage.setWinner(winner);
        for (Client client : users) {
            client.write(sendedPackage);
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
    public void upRacket(Client client){
        if(client.equals(users.get(0))){
            racketOne.up();
            sendedPackage.setRacketOne(getRacketOne());
        } else if(client.equals(users.get(getNumberScreens()-1))) {
            racketTwo.up();
            sendedPackage.setRacketTwo(getRacketTwo());
        }
        client.write(sendedPackage);
    }
    public void downRacket(Client client){
        if(client.equals(users.get(0))){
            racketOne.down();
            sendedPackage.setRacketOne(getRacketOne());
        } else if(client.equals(users.get(getNumberScreens()-1))) {
            racketTwo.down();
            sendedPackage.setRacketTwo(getRacketTwo());
        }
        client.write(sendedPackage);
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
