package co.edu.uptc.view.DashBoard;

import javax.swing.JFrame;
import javax.swing.JPanel;

import co.edu.uptc.Utils.MyUtils;
import co.edu.uptc.presenter.ContractUser;
import co.edu.uptc.presenter.ContractUser.IPresenter;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoard extends JFrame implements ContractUser.IView, ActionListener {
    private ContractUser.IPresenter presenter;
    private PingPongTable pingPongTable;
     private LoggingPanel loggingPanel;
    private JPanel cards;
    private CardLayout cardLayout;

    public DashBoard() {
        this.setLayout(new BorderLayout());
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        setSize(850, 600);
        cards = new JPanel();
        cardLayout = new CardLayout();
        cards.setLayout(cardLayout);
        pingPongTable = new PingPongTable();
        loggingPanel = new LoggingPanel(this);
        cards.add(loggingPanel, "LoggingPanel");
        cards.add(pingPongTable, "PingPongTable");
        this.add(cards, BorderLayout.CENTER);
    }

    @Override
    public void run() {
        setVisible(true);
        threadPainted();
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
    public void actionPerformed(ActionEvent e) {
        String comand = e.getActionCommand();
        switch (comand) {
            case "continue" -> login();
            default -> System.out.println(comand);
        }
    }

    private void showPanel(String panelName) {
        cardLayout.show(cards, panelName);
    }

    public void login(){
        setIpAdress();
        presenter.start();
        showPanel("PingPongTable");
    }

    public void setIpAdress(){
        presenter.setIpAdrres(loggingPanel.getTxtIP().getText()); ;
    }

    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
    }

    public ContractUser.IPresenter getPresenter() {
        return presenter;
    }

}