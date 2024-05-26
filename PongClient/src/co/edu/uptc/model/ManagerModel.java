package co.edu.uptc.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import co.edu.uptc.pojos.Element;
import co.edu.uptc.pojos.SendedPackage;
import co.edu.uptc.presenter.ContractUser;
import co.edu.uptc.presenter.ContractUser.IPresenter;

public class ManagerModel implements ContractUser.IModel {
    private ContractUser.IPresenter presenter;
    private String ipAddres;
    private Element ball;
    private Element racket;
    private Socket user;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int myPosition;

    @Override
    public void run() {
        conect();
    }
    public void conect(){
        try {
            user = new Socket(ipAddres, 9999);
            output = new ObjectOutputStream(user.getOutputStream());
            input = new ObjectInputStream(user.getInputStream());
            receivingThread();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public void write(Object object){
        try {
            output.writeObject(object);
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void receivingThread() {
		Thread receivingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!user.isClosed()) {
					try {
						receiveBall();
						Thread.sleep(40);
					} catch (ClassNotFoundException | IOException | InterruptedException e) {
                        System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		});
		receivingThread.start();
	}
	
	public void receiveBall() throws IOException, ClassNotFoundException {
		Object receive = input.readObject();
        if(receive instanceof SendedPackage){
            readSendedPackage((SendedPackage)receive);
        }
	}
    private void readSendedPackage(SendedPackage sendedPackage){
        if(sendedPackage.getMyPosition() != -1){
            myPosition = sendedPackage.getMyPosition();
        }
        if (sendedPackage.isButtonPlay()) {
            presenter.activeButton();
        }
        if(sendedPackage.isEndGame()){
            endGame(sendedPackage);
        }
        if(sendedPackage.getBallPosition() == myPosition){
            ball = sendedPackage.getBall();
        }
        if(sendedPackage.getFirstPlayer() == myPosition){
            racket = sendedPackage.getRacketOne();
        } else if(sendedPackage.getLastPlayer() == myPosition){
            racket = sendedPackage.getRacketTwo();
        }
    }
    public void endGame(SendedPackage sendedPackage){
        disconect();
        if(sendedPackage.getLoser() == myPosition){
            presenter.gameOver();
        } else if(sendedPackage.getWinner() == myPosition){
            presenter.youWin();
        } else {
            presenter.endGame();
        }
    }
    @Override
    public void play() {
        write(new String("play"));
    }
    @Override
    public void upRacket() {
        write(new String("upRacket"));
    }
    @Override
    public void downRacket() {
        write(new String("downRacket"));
    }
    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
    }
    @Override
    public void disconect(){
        write(new String("endGame"));
        try {
            user.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ContractUser.IPresenter getPresenter() {
        return presenter;
    }
    @Override
    public void setIpAdrres(String ipAddress) {
        this.ipAddres = ipAddress;
    }
    @Override
    public Element getBall() {
        return ball;
    }
    @Override
    public Element getRacket(){
        return racket;
    }

}