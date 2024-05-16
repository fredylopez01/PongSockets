package co.edu.uptc.view.DashBoard;

import javax.swing.JFrame;
import co.edu.uptc.presenter.ContractPlay;
import co.edu.uptc.presenter.ContractPlay.Presenter;
import java.awt.BorderLayout;

public class DashBoard extends JFrame implements ContractPlay.View {
    private ContractPlay.Presenter presenter;
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
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void run() {
        setVisible(true);
    }
    public ContractPlay.Presenter getPresenter() {
        return presenter;
    }

}