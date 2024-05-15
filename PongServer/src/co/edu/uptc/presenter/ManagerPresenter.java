package co.edu.uptc.presenter;

import co.edu.uptc.model.ManagerModel;
import co.edu.uptc.presenter.ContractServer.IModel;
import co.edu.uptc.presenter.ContractServer.IView;
import co.edu.uptc.view.DashBoard;

public class ManagerPresenter implements ContractServer.IPresenter {
    private ContractServer.IModel model;
    private ContractServer.IView view;

    public ManagerPresenter(){

    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void setView(IView view) {
       this.view = view;
    }

    public void makeMVP(){
        ManagerModel managerModel = new ManagerModel();
        DashBoard dashBoard = new DashBoard();
        setModel(managerModel);
        setView(dashBoard);
        managerModel.setPresenter(this);
        dashBoard.setPresenter(this);
    }

    @Override
    public void run() {
        makeMVP();
        view.run();
        view.run();
    }

}
