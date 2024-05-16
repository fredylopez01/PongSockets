package co.edu.uptc.model;

import java.net.Socket;

import co.edu.uptc.pojos.Element;
import co.edu.uptc.presenter.ContractUser;
import co.edu.uptc.presenter.ContractUser.IPresenter;

public class ManagerModel implements ContractUser.IModel {
    private ContractUser.IPresenter presenter;
    private Element ball;
    private Socket user;


    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
    }
    @Override
    public void run() {
        
    }
    public ContractUser.IPresenter getPresenter() {
        return presenter;
    }
    public Element getBall() {
        return ball;
    }

}
