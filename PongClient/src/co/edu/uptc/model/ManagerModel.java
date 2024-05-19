package co.edu.uptc.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
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
    private Socket user;

    @Override
    public void run() {
        conect();
        receivingThread();
    }
    public void conect(){
        try {
            user = new Socket(ipAddres, 9999);
            ObjectOutputStream output = new ObjectOutputStream(user.getOutputStream());
            output.writeObject("conectar");
            output.close();
            user.close();
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
						e.printStackTrace();
					}
				}
			}
		});
		receivingThread.start();
	}
	
	public void receiveBall() throws IOException, ClassNotFoundException {
		ServerSocket serverSocket = new ServerSocket(9090);
		Socket receiveSocket = serverSocket.accept();
		ObjectInputStream input = new ObjectInputStream(receiveSocket.getInputStream());
		Object receive = input.readObject();
        if(receive instanceof Element){
            ball = (Element) receive;
        } else if(receive instanceof String){
            System.out.println(receive);
            if(receive.equals("button")){
                if (JOptionPane.showConfirmDialog(null, receive, "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    user = new Socket(ipAddres, 9999);
                    ObjectOutputStream output = new ObjectOutputStream(user.getOutputStream());
                    output.writeObject("play");
                    output.close();
                    user.close();
                }
            }
        }
		input.close();
		serverSocket.close();
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

}
