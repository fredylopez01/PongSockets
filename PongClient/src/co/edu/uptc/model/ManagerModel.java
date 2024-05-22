package co.edu.uptc.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

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
        receivingThread();
    }
    public void conect(){
        try {
            user = new Socket(ipAddres, 9999);
            output = new ObjectOutputStream(user.getOutputStream());
            output.writeObject(new String("conectar"));
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
						Thread.sleep(50);
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
        input = new ObjectInputStream(user.getInputStream());
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
                createButtonPlay();
            }
        }
	}
    public void createButtonPlay() throws IOException {
        if (JOptionPane.showConfirmDialog(null, "receive", "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            output = new ObjectOutputStream(user.getOutputStream());
            output.writeObject(new String("play"));
        }
    }
    @Override
    public void upRacket() {
        try {
            output = new ObjectOutputStream(user.getOutputStream());
            output.writeObject(new String("upRacket"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void downRacket() {
        try {
            output = new ObjectOutputStream(user.getOutputStream());
            output.writeObject(new String("downRacket"));
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
