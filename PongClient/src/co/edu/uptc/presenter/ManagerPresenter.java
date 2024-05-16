package co.edu.uptc.presenter;

import co.edu.uptc.presenter.ContractPlay.View;
import co.edu.uptc.view.DashBoard.DashBoard;

public class ManagerPresenter implements ContractPlay.Presenter {
    private ContractPlay.View view;

    @Override
    public void setView(View view) {
        this.view = view;
    }

    public void makeMVP() {
        DashBoard dashBoard = new DashBoard();
        this.view = dashBoard;
        dashBoard.setPresenter(this);
    }

    @Override
    public void run() {
        makeMVP();
        view.run();
    }

}
