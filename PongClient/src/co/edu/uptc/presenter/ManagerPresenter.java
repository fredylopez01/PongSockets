package co.edu.uptc.presenter;

import co.edu.uptc.model.ManagerModel;
import co.edu.uptc.pojos.Element;
import co.edu.uptc.presenter.ContractUser.IModel;
import co.edu.uptc.presenter.ContractUser.IView;
import co.edu.uptc.view.DashBoard.DashBoard;

public class ManagerPresenter implements ContractUser.IPresenter {
    private ContractUser.IView view;
    private ContractUser.IModel model;

    
    @Override
    public void run() {
        makeMVP();
        model.run();
        view.run();
    }

    public void makeMVP() {
        DashBoard dashBoard = new DashBoard();
        ManagerModel model = new ManagerModel();
        this.setView(dashBoard);
        this.setModel(model);
        dashBoard.setPresenter(this);
        model.setPresenter(this);
    }

    @Override
    public Element getBall() {
        return model.getBall();
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel iModel) {
        this.model = iModel;
    }

}
