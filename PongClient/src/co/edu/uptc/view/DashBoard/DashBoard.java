package co.edu.uptc.view.DashBoard;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

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
    private LoadPanel loadPanel;
    private ImageIcon icon;

    public DashBoard() {
        this.setLayout(new BorderLayout());
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        setSize(500, 400);
        icon = new ImageIcon(getClass().getResource("../images/icon.png"));
		setIconImage(icon.getImage());
        cards = new JPanel();
        cardLayout = new CardLayout();
        cards.setLayout(cardLayout);
        pingPongTable = new PingPongTable();
        loggingPanel = new LoggingPanel(this);
        loadPanel = new LoadPanel();
        cards.add(loggingPanel, "LoggingPanel");
        cards.add(pingPongTable, "PingPongTable");
        cards.add(loadPanel, "LoadPanel");
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
            case "connect" -> login();
            case "load" -> loadEfect();
            default -> System.out.println(comand);
        }
    }

    private void showPanel(String panelName) {
        cardLayout.show(cards, panelName);
    }

    public void login(){
        setIpAdress();
        showPanel("LoadPanel");
        Timer timer = new Timer(3000, this);
        timer.setActionCommand("load");
        timer.setRepeats(false); 
        timer.start();
    }

    public void loadEfect(){
        presenter.start();
        showPanel("PingPongTable");
        threadPainted();
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