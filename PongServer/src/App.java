import co.edu.uptc.presenter.ContractServer;
import co.edu.uptc.presenter.ManagerPresenter;

public class App {
    public static void main(String[] args) throws Exception {
        ContractServer.IPresenter presenter = new ManagerPresenter();
        presenter.run();
    }
}
