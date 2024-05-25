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
        view.run();
    }
    @Override
    public void start(){
        model.run();
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
    public Element getRacket() {
        return model.getRacket();
    }
    @Override
    public void setIpAdrres(String ipAddress) {
        model.setIpAdrres(ipAddress);
    }
    @Override
    public void upRacket() {
        model.upRacket();
    }
    @Override
    public void downRacket() {
        model.downRacket();
    }
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel iModel) {
        this.model = iModel;
    }
    @Override
    public void activeButton() {
        view.activeButton();
    }
    @Override
    public void play() {
        model.play();
    }
    @Override
    public void disconect() {
        model.disconect();
    }
    @Override
    public void gameOver() {
        view.gameOver();
    }
    @Override
    public void endGame() {
        view.endGame();
    }
    @Override
    public void youWin() {
        view.youWin();
    }
}
