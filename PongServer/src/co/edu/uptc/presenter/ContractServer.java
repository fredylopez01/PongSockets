package co.edu.uptc.presenter;

import co.edu.uptc.pojos.Element;

public class ContractServer {

    public interface IPresenter {
        public void setView(IView iView);
        public void setModel(IModel iModel);
        public void run();
        public Element getBall();
        public Element getRacketOne();
        public Element getRacketTwo();
        public int getCurrentScreen();
        public int getNumberScreens();
    }

    public interface IModel {
        public void setPresenter(IPresenter iPresenter);
        public void run();
        public Element getBall();
        public Element getRacketOne();
        public Element getRacketTwo();
        public int getCurrentScreen();
        public int getNumberScreens();
    }

    public interface IView {
        public void setPresenter(IPresenter iPresenter);
        public void run();
    }

}