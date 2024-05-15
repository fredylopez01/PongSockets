package co.edu.uptc.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.BorderLayout;

import co.edu.uptc.Utils.MyUtils;
import co.edu.uptc.Utils.Values;
import co.edu.uptc.presenter.ContractServer;
import co.edu.uptc.presenter.ContractServer.IPresenter;

public class DashBoard extends JFrame implements ContractServer.IView {
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
        icon = null;
		// setIconImage(icon.getImage());
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
                    myPanel.repaint();
                    MyUtils.sleep(100);
                }
            }
        });
        threadPainted.start();
    }

    @Override
    public void setPresenter(IPresenter iPresenter) {
        this.presenter = iPresenter;
    }
    
}
