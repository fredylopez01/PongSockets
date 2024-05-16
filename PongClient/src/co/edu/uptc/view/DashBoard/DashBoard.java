package co.edu.uptc.view.DashBoard;

import javax.swing.JFrame;
import co.edu.uptc.presenter.ContractUser;
import co.edu.uptc.presenter.ContractUser.IPresenter;
import java.awt.BorderLayout;

public class DashBoard extends JFrame implements ContractUser.IView {
    private ContractUser.IPresenter presenter;
    private PingPongTable pingPongTable;

    public DashBoard() {
        this.setLayout(new BorderLayout());
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        setBounds(1, 1, 850, 600);
        pingPongTable = new PingPongTable();
        this.add(pingPongTable);
        
    }

    @Override
    public void run() {
        setVisible(true);
    }

    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
    }

    public ContractUser.IPresenter getPresenter() {
        return presenter;
    }

}