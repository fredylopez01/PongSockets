package co.edu.uptc.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import co.edu.uptc.pojos.Element;
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
    public void receivingThread() {
		Thread receivingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						receiveBall();
						Thread.sleep(30);
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
        if(receive instanceof Element){
            Element element = (Element) receive;
            if(element.getType() == 1){
                ball = element;
            } else {
                racket = element;
            }
        } else if(receive instanceof String){
            System.out.println(receive);
            if(receive.equals("button")){
                presenter.activeButton();
            } else if(receive.equals("Perdio")){
                // user.close();
            }
        }
	}
    @Override
    public void play() {
        try {
            output.writeObject(new String("play"));
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void upRacket() {
        try {
            output.writeObject(new String("upRacket"));
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void downRacket() {
        try {
            output.writeObject(new String("downRacket"));
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
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