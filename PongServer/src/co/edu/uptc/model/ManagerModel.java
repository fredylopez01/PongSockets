package co.edu.uptc.model;

import co.edu.uptc.Utils.MyUtils;
import co.edu.uptc.pojos.Element;
import co.edu.uptc.presenter.ContractServer;
import co.edu.uptc.presenter.ContractServer.IPresenter;

public class ManagerModel implements ContractServer.IModel {
    private ContractServer.IPresenter presenter;
    private ManagerBall managerBall;

    public ManagerModel(){
        managerBall = new ManagerBall();
    }

    @Override
    public void run() {
        ThreadBall();
    }

    public void ThreadBall(){
        Thread threadServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    managerBall.move();
                    MyUtils.sleep(100);
                }
            }
            
        });
        threadServer.start();
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
