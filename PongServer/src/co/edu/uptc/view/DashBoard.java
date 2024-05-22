package co.edu.uptc.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import co.edu.uptc.Utils.MyUtils;
import co.edu.uptc.Utils.Values;
import co.edu.uptc.presenter.ContractServer;
import co.edu.uptc.presenter.ContractServer.IPresenter;

public class DashBoard extends JFrame implements ContractServer.IView, KeyListener {
    private ContractServer.IPresenter presenter;
    private ImageIcon icon;
    private MyPanel myPanel;
    
    public DashBoard(){
        initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Values.widthWindow, Values.heightWindow);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        myPanel = new MyPanel();
        this.add(myPanel, BorderLayout.CENTER);
        this.setTitle("Ping Pong Game");
        icon = new ImageIcon(getClass().getResource("images/icon.png"));
		setIconImage(icon.getImage());
        this.addKeyListener(this);
    }

    public void run(){
        this.setVisible(true);
        threadPainted();
    }

    public void threadPainted(){
        Thread threadPainted = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    myPanel.setBall(presenter.getBall());
                    myPanel.setRacketOne(presenter.getRacketOne());
                    myPanel.setRacketTwo(presenter.getRacketTwo());
                    myPanel.setCurrentScreen(presenter.getCurrentScreen());
                    myPanel.setNumberScreens(presenter.getNumberScreens());
                    myPanel.repaint();
                    MyUtils.sleep(20);
                }
            }
        });
        threadPainted.start();
    }

    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // int keyCode = e.getKeyCode();
        // switch (keyCode) {
        //     case KeyEvent.VK_W -> presenter.upRacketOne();
        //     case KeyEvent.VK_S -> presenter.downRacketOne();
        //     case KeyEvent.VK_UP -> presenter.upRacketTwo();
        //     case KeyEvent.VK_DOWN -> presenter.downRacketTwo();
        //     default -> System.out.println(keyCode);
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
