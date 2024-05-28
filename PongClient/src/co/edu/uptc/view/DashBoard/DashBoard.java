package co.edu.uptc.view.DashBoard;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import co.edu.uptc.Utils.MyUtils;
import co.edu.uptc.Utils.Values;
import co.edu.uptc.presenter.ContractUser;
import co.edu.uptc.presenter.ContractUser.IPresenter;
import co.edu.uptc.view.DashBoard.panels.EndPanel;
import co.edu.uptc.view.DashBoard.panels.LoadPanel;
import co.edu.uptc.view.DashBoard.panels.LoggingPanel;
import co.edu.uptc.view.DashBoard.utils.JDMessage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DashBoard extends JFrame implements ContractUser.IView, ActionListener, KeyListener, WindowListener {
    private ContractUser.IPresenter presenter;
    private PingPongTable pingPongTable;
    private LoggingPanel loggingPanel;
    private JPanel cards;
    private CardLayout cardLayout;
    private LoadPanel loadPanel;
    private ImageIcon icon;
    private JDMessage message;
    private EndPanel endPanel;

    public DashBoard() {
        this.setLayout(new BorderLayout());
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        setSize(Values.widthWindow, Values.heightWindow);
        icon = new ImageIcon(getClass().getResource("/co/edu/uptc/view/images/icon.png"));
		setIconImage(icon.getImage());
        createPanels();
        this.addKeyListener(this);
        this.setFocusable(true);
    }
    private void createPanels(){
        cards = new JPanel();
        cardLayout = new CardLayout();
        cards.setLayout(cardLayout);
        pingPongTable = new PingPongTable();
        loggingPanel = new LoggingPanel(this);
        loadPanel = new LoadPanel();
        endPanel = new EndPanel(this);
        cards.add(loggingPanel, "LoggingPanel");
        cards.add(pingPongTable, "PingPongTable");
        cards.add(loadPanel, "LoadPanel");
        cards.add(endPanel, "EndPanel");
        this.add(cards, BorderLayout.CENTER);
        message = new JDMessage(this, this);
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
                    pingPongTable.setRacket(presenter.getRacket());
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
            case "play" -> play();
            case "endGame" -> showPanel("LoggingPanel");
            default -> System.out.println(comand);
        }
    }

    private void showPanel(String panelName) {
        cardLayout.show(cards, panelName);
    }
    private void login(){
        setIpAdress();
        showPanel("LoadPanel");
        Timer timer = new Timer(3000, this);
        timer.setActionCommand("load");
        timer.setRepeats(false); 
        timer.start();
    }
    private void loadEfect(){
        presenter.start();
        pingPongTable.reset();
        showPanel("PingPongTable");
        threadPainted();
    }
    private void play(){
        message.dispose();
        presenter.play();
    }
    @Override
    public void gameOver(){
        endPanel.gameOver();
        showPanel("EndPanel");
    }
    @Override
    public void endGame(){
        endPanel.endGame();
        showPanel("EndPanel");
    }
    @Override
    public void youWin(){
        endPanel.youWin();
        showPanel("EndPanel");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP -> presenter.upRacket();
            case KeyEvent.VK_DOWN -> presenter.downRacket();
            default -> System.out.println(keyCode);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        
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
    @Override
    public void activeButton() {
        message.setVisible(true);
    }
    @Override
    public void windowOpened(WindowEvent e) {
    }
    @Override
    public void windowClosing(WindowEvent e) {
    }
    @Override
    public void windowClosed(WindowEvent e) {
        presenter.disconect();
    }
    @Override
    public void windowIconified(WindowEvent e) {
    }
    @Override
    public void windowDeiconified(WindowEvent e) {
    }
    @Override
    public void windowActivated(WindowEvent e) {
    }
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}