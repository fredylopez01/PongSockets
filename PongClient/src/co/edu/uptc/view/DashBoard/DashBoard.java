package co.edu.uptc.view.DashBoard;

import javax.swing.JFrame;

import co.edu.uptc.Utils.MyUtils;
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

    public void threadPainted(){
        Thread threadPainted = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    pingPongTable.setBall(presenter.getBall());
                    pingPongTable.repaint();
                    MyUtils.sleep(15);
                }
            }
        });
        threadPainted.start();
    }

    @Override
    public void run() {
        setVisible(true);
        threadPainted();
    }

    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
    }

    public ContractUser.IPresenter getPresenter() {
        return presenter;
    }

}