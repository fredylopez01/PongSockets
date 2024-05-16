package co.edu.uptc.presenter;

import co.edu.uptc.pojos.Element;

public interface ContractUser {

    public interface IModel {
        public void setPresenter(IPresenter iPresenter);
        public void run();
        public Element getBall();
    }

    public interface IView {
        public void setPresenter(IPresenter iPresenter);
        public void run();
    }

    public interface IPresenter {
        public void setView(IView iView);
        public void setModel(IModel iModel);
        public void run();
        public Element getBall();
    }
}
