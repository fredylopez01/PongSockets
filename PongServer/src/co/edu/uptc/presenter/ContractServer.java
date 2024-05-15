package co.edu.uptc.presenter;

public class ContractServer {

    public interface IPresenter {
        public void setView(IView iView);
        public void setModel(IModel iModel);
        public void run();
    }

    public interface IModel {
        public void setPresenter(IPresenter iPresenter);
        public void run();
    }

    public interface IView {
        public void setPresenter(IPresenter iPresenter);
        public void run();
    }

}